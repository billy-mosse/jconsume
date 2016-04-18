package ar.uba.dc.tools.invariant.migration.spec;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.nfunk.jep.ASTConstant;
import org.nfunk.jep.ASTFunNode;
import org.nfunk.jep.ASTStart;
import org.nfunk.jep.ASTVarNode;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
import org.nfunk.jep.ParserVisitor;
import org.nfunk.jep.SimpleNode;

import soot.SootClass;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.invariant.spec.SpecCompiler;
import ar.uba.dc.invariant.spec.SpecReader;
import ar.uba.dc.invariant.spec.SpecWriter;
import ar.uba.dc.invariant.spec.bean.CallSiteSpecification;
import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.CreationSiteSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.Specification;
import ar.uba.dc.invariant.spec.compiler.compilation.DefaultClassInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.DefaultMethodInvariantProvider;
import ar.uba.dc.invariant.spec.compiler.compilation.InvariantId;
import ar.uba.dc.util.location.FullPackageLocationStrategy;


public class SpecToMadejaMigrator {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		String propertiesFile = null;
		String subdir = StringUtils.EMPTY;
		boolean includeEmpty = true;
		
		if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
			propertiesFile = args[0];
		}
		
		if (args.length > 1 && StringUtils.isNotBlank(args[1])) {
			subdir = args[1];
		}
		
		if (args.length > 2 && StringUtils.isNotBlank(args[2])) {
			includeEmpty = Boolean.valueOf(args[2].trim().toLowerCase());
		}
		
		Context context = ContextFactory.getContext(propertiesFile);
        
		SpecReader reader = context.getFactory().getSpecReader("full-references");
		SpecCompiler compiler = context.getFactory().getSpecCompiler("full-references");
		SpecWriter writer = context.getFactory().getSpecWriter();
		
		String outputFolder = context.getString(Context.OUTPUT_FOLDER);
		File allSpecFile = new File(outputFolder, "all.spec");
		if (allSpecFile.exists()) {
			allSpecFile.delete();
		} else {
			if (allSpecFile.getParentFile() != null && !allSpecFile.getParentFile().exists()) {
				allSpecFile.getParentFile().mkdirs();
			}
		}
		
		Specification allSpec = new Specification();
		String repoPath = context.getString("invariants.repository");
		String subdirPath = new File(repoPath, subdir).getAbsolutePath();
		Iterator<File> it = FileUtils.iterateFiles(new File(repoPath), new String[] { "spec" }, true);
		while (it.hasNext()) {
			File specFile = it.next();
			
			System.out.println("Process spec: " + specFile);
			if (!specFile.getAbsolutePath().startsWith(subdirPath)) {
				System.out.println(" |- Skipping. It doesn't belong to subdir [" + subdir + "]");
				
			} else {
				Specification spec = reader.read(new FileReader(specFile));
				
				if (spec.getClasses().size() > 1) {
					throw new RuntimeException("No esperamos procesar mas de una clase por archivo");
				}
				
				Specification dest = new Specification();
				for (ClassSpecification classSpec : spec.getClasses()) {
					ClassSpecification destClassSpec = new ClassSpecification(classSpec.getClassName());
					DefaultClassInvariantProvider compiled = (DefaultClassInvariantProvider) compiler.compile(classSpec);
					
					for (MethodSpecification methodSpec : classSpec.getMethods()) {
						MethodSpecification destMethodSpec = new MethodSpecification(methodSpec.getSignature().replaceAll("<", StringUtils.EMPTY).replaceAll(">", StringUtils.EMPTY));
						destMethodSpec.addAllParameters(methodSpec.getParameters());
						DefaultMethodInvariantProvider methodProvider = compiled.get(methodSpec.getSignature());
						DomainSet requirements = methodProvider.getRequirements();
						
						if (requirements != null) {
							destMethodSpec.addRequirement(adaptConstraints(requirements.getConstraints()));
						}
						
						for (InvariantId id : methodProvider.getInvariantIdSet()) {						
							String constraints = methodProvider.getInvariant(id).getConstraints();
							// FIXME: si hay ORs en las constraints esto no sirve 
							if (requirements != null) {
								constraints = StringUtils.replace(constraints, " and " + requirements.getConstraints(), StringUtils.EMPTY);
								constraints = StringUtils.replace(constraints, requirements.getConstraints() + " and ", StringUtils.EMPTY);
							}
							constraints = adaptConstraints(constraints);
							
							String implementation = null;
							if (!StringUtils.isBlank(id.getImplementation())) {
								implementation = id.getImplementation();
							}
							
							String operator = null;
							if (!StringUtils.isBlank(id.getOperator())) {
								operator = id.getOperator();
							}
							
							if (InvariantId.Type.NEW.equals(id.getType())) {
								CreationSiteSpecification siteSpec = new CreationSiteSpecification(id.getOffset().toString(), methodProvider.getCaptureAllPartitions(id));
								siteSpec.setConstraints(constraints);
								siteSpec.setImplementation(implementation);
								siteSpec.setOperator(operator);
								destMethodSpec.add(siteSpec);
							} else {
								CallSiteSpecification siteSpec = new CallSiteSpecification(id.getOffset().toString(), methodProvider.getCaptureAllPartitions(id));
								siteSpec.setConstraints(constraints);
								siteSpec.setImplementation(implementation);
								siteSpec.setOperator(operator);
								destMethodSpec.add(siteSpec);
							}
							
						}
	
						if (includeEmpty || !destMethodSpec.getRequirements().isEmpty() || !destMethodSpec.getSitesSpecification().isEmpty()) {
							destClassSpec.addMethod(destMethodSpec);
						}
					}
					
					if (includeEmpty || !destClassSpec.getMethods().isEmpty()) {
						dest.add(destClassSpec);
						allSpec.add(destClassSpec);
					}
				}
				
				SootClass clazz = new SootClass(spec.getClasses().get(0).getClassName());
				
				FullPackageLocationStrategy destination = new FullPackageLocationStrategy();
				destination.setBasePath(outputFolder);
				destination.setExtension(".spec");
				File dstFile = new File(destination.getLocation(clazz));
				System.out.println(" |- Writing fast version in: " + dstFile);
				if (!dstFile.getParentFile().exists()) {
					dstFile.getParentFile().mkdirs();
				}
				writer.write(dest, new FileWriter(dstFile));
				System.out.println(" |- Finished");
			}
		}
		writer.write(allSpec, new FileWriter(allSpecFile, false));
		System.out.println("No more files to process");
	}

	private static String adaptConstraints(String constraints) {
		constraints = constraints.replaceAll("(?i) and ", " && ");
		constraints = constraints.replaceAll("(?i) or ", " || ");
		final StringBuilder sb = new StringBuilder("");
		JEP parser = new JEP();
		
		parser.setAllowUndeclared(true);
		parser.setImplicitMul(true);
		
		try {
			Node node = parser.parse(constraints);
			node.jjtAccept(new ParserVisitor() {
				
				public Object visit(ASTConstant arg0, Object arg1) throws ParseException {
					StringBuilder sb = (StringBuilder) arg1;
					String value = Integer.toString(((Double) arg0.getValue()).intValue()); 
					sb.append(value);
					
					return value;
				}
				
				public Object visit(ASTVarNode arg0, Object arg1) throws ParseException {
					StringBuilder sb = (StringBuilder) arg1;
					sb.append(arg0.getName());
					return arg0.getName();
				}
				
				public Object visit(ASTFunNode arg0, Object arg1) throws ParseException {
					if (arg0.jjtGetNumChildren() != 2) {
						throw new RuntimeException("La funcion " + arg0.getName() + " no es 2-aria. Mirar esto");
					}
					
					String result = null;
					StringBuilder sbResult = (StringBuilder) arg1;
					StringBuilder sbIzq = new StringBuilder();
					StringBuilder sbDer = new StringBuilder();
					
					String retIzq = (String) arg0.jjtGetChild(0).jjtAccept(this, sbIzq);
					String retDer = (String) arg0.jjtGetChild(1).jjtAccept(this, sbDer);
					
					if ("<".equals(arg0.getName())) {
						int matches = StringUtils.countMatches(sbIzq.toString(), " >= ") + StringUtils.countMatches(sbIzq.toString(), " > ");
						if (matches == 1) {
							sbResult.append("(" + sbDer.toString() + ") > (" + retIzq + ") and " + sbIzq.toString());
						} else {
							sbResult.append("(" + sbDer.toString() + ") > (" + sbIzq.toString() + ")");
							if (matches == 0) {
								result = retDer;
							}
						}
					} else if ("<=".equals(arg0.getName())) {
						int matches = StringUtils.countMatches(sbIzq.toString(), " >= ") + StringUtils.countMatches(sbIzq.toString(), " > ");
						if (matches == 1) {
							sbResult.append("(" + sbDer.toString() + ") >= (" + retIzq + ") and " + sbIzq.toString());	
						} else {
							sbResult.append("(" + sbDer.toString() + ") >= (" + sbIzq.toString() + ")");
							if (matches == 0) {
								result = retDer;
							}
						}
					} else if (">".equals(arg0.getName()) || ">=".equals(arg0.getName())) {
						int matches = StringUtils.countMatches(sbIzq.toString(), " >= ") + StringUtils.countMatches(sbIzq.toString(), " > ");
						if (matches == 1) {
							sbResult.append("(" + sbIzq.toString() + ") and (" + retIzq + ") " + arg0.getName() + sbDer.toString());	
						} else {
							sbResult.append("(" + sbIzq.toString() + ") " + arg0.getName() + " (" + sbDer.toString() + ")");
							if (matches == 0) {
								result = retDer;
							}
						}
					} else {
						String name = arg0.getName();
						if ("&&".equals(name)) {
							name = "and";
						} else if ("||".equals(name)) {
							name = "or";
						}
 						sbResult.append("(" + sbIzq.toString() + ") " + name + " (" + sbDer.toString() + ")");
 						if (!name.equals("and") && !name.equals("or")) {
 							result = sbResult.toString();
 						}
					}
					
					return result;
				}
				
				public Object visit(ASTStart arg0, Object arg1) throws ParseException {
					return null;
				}
				
				public Object visit(SimpleNode arg0, Object arg1) throws ParseException {
					return null;
				}
			}, sb);
			
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Fallo al parsera la constraints [" + constraints + "]: " + e.getMessage());
		}
		
		constraints = sb.toString();
		
		constraints = StringUtils.replace(constraints, "(", "");
		constraints = StringUtils.replace(constraints, ")", "");
		constraints = constraints.replaceAll("(\\d+) > (\\w+)", "$2 < $1");
		constraints = constraints.replaceAll("(\\d+) >= (\\w+)", "$2 <= $1");
		constraints = constraints.replaceAll("(\\d+) => (\\w+)", "$2 <= $1");

		return constraints;
	}
}
