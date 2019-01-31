package ar.uba.dc.tools.analysis.summary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Body;
import soot.BodyTransformer;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.Stmt;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import soot.tagkit.BytecodeOffsetTag;
import soot.tagkit.LineNumberTag;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.analysis.escape.summary.io.reader.XMLReader;
import ar.uba.dc.analysis.escape.summary.io.writer.XMLWriter;
import ar.uba.dc.annotations.AnnotationSiteInvariantForJson;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.soot.xstream.StatementIdConverter;
import ar.uba.dc.soot.xstream.StatementNotFoundException;
import ar.uba.dc.util.Timer;
import ar.uba.dc.util.location.MethodLocationStrategy;

import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

/**
 * Esta clase tiene como unico proposito corregir problemas que surjen de correr un escape sobre jlayer 
 * habiendo comentado las tablas estaticas de la clase huffcodetab
 * 
 * @author testis
 */
public class RepositoryCorrector extends BodyTransformer {
	
	private static Log log = LogFactory.getLog(RepositoryCorrector.class);

	public static void main(String[] args) throws FileNotFoundException {
		String propertiesFile = null;
		
		if (args.length >= 2) {
			if (StringUtils.isNotBlank(args[1])) {
				propertiesFile = args[1];
			}
		}
		
		String methodFilter = StringUtils.EMPTY;
		if (args.length >= 3) {
			methodFilter = args[2].trim();
		}
		
		log.info("Check repository for [" + args[0] + "] using [" + StringUtils.defaultString(propertiesFile, "null") + "] as configuration");
		
		final Context context = ContextFactory.getContext(propertiesFile, false);

		EdgePredicate predicate = context.getFactory().getEdgePredicate();
		if (predicate != null) {
			ReachableMethods.setEdgePredicate(predicate);
		}
		
		String[] opts = { 
				"-app",
				"-soot-classpath", context.getString(Context.APPLICATION_CLASSPATH),
				"-f", "none",
				"-src-prec","class",
				"-keep-line-number", 
				"-keep-bytecode-offset",
				"-x", "gnu", 
				"-x", "spec.io",
				"-x", "java.lang.StringBuffer",
				"-p", "jb",	"use-original-names:true", 
				//"-p", "jb.dae", "enabled:false",
				//"-p", "jb.ule", "enabled:false",
				"-p", "jb.ulp", "enabled:true",
				"-p", "jj",	"use-original-names:true", 
				//"-p", "jj.dae", "enabled:false",
				//"-p", "jj.ule", "enabled:false",
				"-p", "jj.ulp", "enabled:true",
				"-p", "cg.spark", "enabled:true",
				"-main-class", args[0], 
				args[0]
			};
		
		SootUtils.insertTransformer("jtp", "jtp.repo-corrector", new RepositoryCorrector(context.getString(Context.ESCAPE_OUTPUT_FOLDER), context.getFactory().getEscapeRepositoryLocation("output"), methodFilter));
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		
		soot.Main.main(opts);
		
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}
	
	private MethodLocationStrategy location;
	private String repoDir;
	private String methodFilter;
	
	public RepositoryCorrector(String repoDir, MethodLocationStrategy location, String methodFilter) {
		this.location = location;
		this.repoDir = repoDir;
		this.methodFilter = methodFilter;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(Body b, String phaseName, Map options) {
		if (b.getMethod().toString().startsWith(methodFilter)) {
			log.info(" |- Checking summary of [" + b.getMethod() + "]");
			
			SootMethod conflictMethod = SootUtils.getMethod("javazoom.jl.decoder.huffcodetab", "void <clinit>()");
			ConflictResolverStatementIdConverter conflictResolver = new ConflictResolverStatementIdConverter();
			conflictResolver.register(conflictMethod, 63, 1, new StatementMapping(conflictMethod, 104, 1, "$r0 = newarray (int[])[1]"));
			conflictResolver.register(conflictMethod, 63, 7, new StatementMapping(conflictMethod, 104, 7, "$r1 = newarray (int)[2]"));
			conflictResolver.register(conflictMethod, 66, new StatementMapping(conflictMethod, 106, 15, "$r0 = newarray (int[])[7]"));
			conflictResolver.register(conflictMethod, 68, new StatementMapping(conflictMethod, 109, 104, "$r0 = newarray (int[])[17]"));
			conflictResolver.register(conflictMethod, 70, new StatementMapping(conflictMethod, 113, 327, "$r0 = newarray (int[])[17]"));
			conflictResolver.register(conflictMethod, 72, new StatementMapping(conflictMethod, 117, 549, "$r0 = newarray (int[])[1]"));
			conflictResolver.register(conflictMethod, 74, new StatementMapping(conflictMethod, 119, 563, "$r0 = newarray (int[])[31]"));
			conflictResolver.register(conflictMethod, 76, new StatementMapping(conflictMethod, 125, 976, "$r0 = newarray (int[])[31]"));
			conflictResolver.register(conflictMethod, 78, new StatementMapping(conflictMethod, 131, 1390, "$r0 = newarray (int[])[71]"));
			conflictResolver.register(conflictMethod, 80, new StatementMapping(conflictMethod, 141, 2346, "$r0 = newarray (int[])[71]"));
			conflictResolver.register(conflictMethod, 82, new StatementMapping(conflictMethod, 151, 3303, "$r0 = newarray (int[])[71]"));
			conflictResolver.register(conflictMethod, 84, new StatementMapping(conflictMethod, 161, 4261, "$r0 = newarray (int[])[127]"));
			conflictResolver.register(conflictMethod, 86, new StatementMapping(conflictMethod, 176, 5982, "$r0 = newarray (int[])[127]"));
			conflictResolver.register(conflictMethod, 88, new StatementMapping(conflictMethod, 191, 7703, "$r0 = newarray (int[])[127]"));
			conflictResolver.register(conflictMethod, 90, new StatementMapping(conflictMethod, 206, 9423, "$r0 = newarray (int[])[511]"));
			conflictResolver.register(conflictMethod, 92, new StatementMapping(conflictMethod, 260, 16892, "$r0 = newarray (int[])[1]"));
			conflictResolver.register(conflictMethod, 94, new StatementMapping(conflictMethod, 262, 16907, "$r0 = newarray (int[])[511]"));
			conflictResolver.register(conflictMethod, 96, new StatementMapping(conflictMethod, 316, 24379, "$r0 = newarray (int[])[511]"));
			conflictResolver.register(conflictMethod, 98, new StatementMapping(conflictMethod, 371, 31857, "$r0 = newarray (int[])[512]"));
			conflictResolver.register(conflictMethod, 100, new StatementMapping(conflictMethod, 425, 39343, "$r0 = newarray (int[])[31]"));
			conflictResolver.register(conflictMethod, 102, new StatementMapping(conflictMethod, 431, 39755, "$r0 = newarray (int[])[31]"));
			conflictResolver.register(conflictMethod, 439, new StatementMapping(conflictMethod, 439, 40171, "$r2 = newarray (int)[32]"));       
			
			XMLReader reader = new XMLReader(conflictResolver);
			XMLWriter writer = new XMLWriter();
			
			File summaryFile = new File(location.getLocation(b.getMethod()));
			try {
				log.info(" |- processing file [" + summaryFile.getAbsolutePath().replace(repoDir + File.separatorChar, StringUtils.EMPTY) + "]");
				EscapeSummary summary = reader.read(new FileReader(summaryFile));
				final String destPath = summaryFile.getAbsolutePath();  
				MethodLocationStrategy strategy = new MethodLocationStrategy() {
					
					@Override
					public String getLocation(IntermediateRepresentationMethod method) {
						throw new java.lang.UnsupportedOperationException();
					}
					@Override
					public String getLocation(SootMethod method) {
						return destPath;
					}
					
					@Override
					public String getXMLLocation(IntermediateRepresentationMethod method, String mainClass) {
						throw new java.lang.UnsupportedOperationException();

					}

					@Override
					public String getBasePath() {
						throw new java.lang.UnsupportedOperationException();
					}

					@Override
					public String getHumanReadableLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
						throw new java.lang.UnsupportedOperationException();
					}
					
					@Override
					public String getJsonIRLocation(IntermediateRepresentationMethod ir_method, String mainClass) {
						throw new java.lang.UnsupportedOperationException();
					}
					@Override
					public String getJsonIRLocation(
							AnnotationSiteInvariantForJson siteInvariant,
							String mainClass) {
						// TODO Auto-generated method stub
						return null;
					}
					@Override
					public String getEscapeAnnotationsLocation(String mainClass) {
						// TODO Auto-generated method stub
						throw new java.lang.UnsupportedOperationException();
					}
				};
				writer.setLocationStrategy(strategy);
				writer.write(summary);
			} catch (StatementNotFoundException e) {
				log.error(" | |- Conversion fail: " + e.getMessage());
				SootMethod method = SootUtils.getMethod(e.getSootClass(), e.getSootMethod());
				printBody(method.retrieveActiveBody());
			} catch (Throwable e) {
				log.error(" | |- Conversion fail: " + e.getMessage());
			}
		}
	}

	private void printBody(Body body) {
		for(Unit unit : body.getUnits()) {
			LineNumberTag lineNumberTag = (LineNumberTag) unit.getTag("LineNumberTag");
			BytecodeOffsetTag bytecodeOffsetTag = (BytecodeOffsetTag) unit.getTag("BytecodeOffsetTag");
			
			int padding = 4;
			String unitString = unit.toString();
			String lineNumberString = StringUtils.repeat(" ", padding);
			String bytecodeOffsetString = StringUtils.repeat(" ", padding);
			
			if (lineNumberTag != null) {
				lineNumberString = StringUtils.leftPad(Integer.toString(lineNumberTag.getLineNumber()), padding);
			}
			
			if (bytecodeOffsetTag != null) {
				bytecodeOffsetString = StringUtils.leftPad(Integer.toString(bytecodeOffsetTag.getBytecodeOffset()), padding);
			}
		
			log.error("| | |- " + lineNumberString + " - " + bytecodeOffsetString + " - " + unitString);
		}
	}
	
	private class ConflictResolverStatementIdConverter extends StatementIdConverter {

		private Map<LineConflict, StatementMapping> lineConflicts = new HashMap<LineConflict, StatementMapping>();
		private Map<OffsetConflict, StatementMapping> offsetConflicts = new HashMap<OffsetConflict, StatementMapping>();
		
		public ConflictResolverStatementIdConverter() {
			super("method", "value", "line-number", "bytecode-offset");
		}
	
		public void register(SootMethod method, int lineNumber, StatementMapping mapping) {
			lineConflicts.put(new LineConflict(method, lineNumber), mapping);
		}

		public void register(SootMethod method, int lineNumber, int offsetNumber, StatementMapping mapping) {
			offsetConflicts.put(new OffsetConflict(method, lineNumber, offsetNumber), mapping);
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			StatementId stmtId = new StatementId(null, null);
			
			SootMethod method = null;
			String statementString = null;
			Integer lineNumber = null;
			Integer offsetNumber = null;
			
			reader.moveDown();
				method = (SootMethod) context.convertAnother(stmtId, SootMethod.class);
			reader.moveUp();
			reader.moveDown();
				statementString = reader.getValue();
	        reader.moveUp();
	        reader.moveDown();
	        	lineNumber = Integer.valueOf(reader.getValue());
		    reader.moveUp();
	        reader.moveDown();
		        offsetNumber = Integer.valueOf(reader.getValue());
	        reader.moveUp();
			
	        StatementMapping mapping = findStatementMapping(method, statementString, lineNumber, offsetNumber);
	        
	        Stmt stmt = SootUtils.findStatementInMethod(mapping.getMethod(), mapping.getStatement(), mapping.getLineNumber(), mapping.getOffsetNumber());
	        
	        if (stmt == null) {
	        	throw new StatementNotFoundException(method, statementString, lineNumber, offsetNumber);
	        }
	        
			return new StatementId(method, stmt);
		}

		private StatementMapping findStatementMapping(SootMethod method, String statement, Integer lineNumber, Integer offsetNumber) {
			StatementMapping result = new StatementMapping(method, lineNumber, offsetNumber, statement);
			
			OffsetConflict offsetConflict = new OffsetConflict(method, lineNumber, offsetNumber);
			LineConflict lineConflict = new LineConflict(method, lineNumber);
			
			//System.out.println("ORIGINAL: " + result);
			if (offsetConflicts.containsKey(offsetConflict)) {
				result = offsetConflicts.get(offsetConflict);
			} else if (lineConflicts.containsKey(lineConflict)) {
				result = lineConflicts.get(lineConflict);
			}
			//System.out.println("SELECTED: " + result);
			//System.out.println("");
			
			return result;
		}
	}
	
	private class StatementMapping {
		private SootMethod method;
		private String statement;
		private Integer lineNumber;
		private Integer offsetNumber;
		
		public StatementMapping(SootMethod method, int lineNumber, int offsetNumber, String statement) {
			this.method = method;
			this.statement = statement;
			this.lineNumber = lineNumber;
			this.offsetNumber = offsetNumber;
		}
		public SootMethod getMethod() {
			return method;
		}
		public String getStatement() {
			return statement;
		}
		public Integer getLineNumber() {
			return lineNumber;
		}
		public Integer getOffsetNumber() {
			return offsetNumber;
		}
		@Override
		public String toString() {
			return "StatementMapping [lineNumber=" + lineNumber + ", method="
					+ method + ", offsetNumber=" + offsetNumber
					+ ", statement=" + statement + "]";
		}
	}
	
	private class LineConflict {
		private SootMethod method;
		private Integer lineNumber;
		public LineConflict(SootMethod method, Integer lineNumber) {
			super();
			this.method = method;
			this.lineNumber = lineNumber;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
			result = prime * result + ((method == null) ? 0 : method.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof LineConflict)) {
				return false;
			}
			LineConflict other = (LineConflict) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (lineNumber == null) {
				if (other.lineNumber != null) {
					return false;
				}
			} else if (!lineNumber.equals(other.lineNumber)) {
				return false;
			}
			if (method == null) {
				if (other.method != null) {
					return false;
				}
			} else if (!method.equals(other.method)) {
				return false;
			}
			return true;
		}
		private RepositoryCorrector getOuterType() {
			return RepositoryCorrector.this;
		}		
	}
	
	private class OffsetConflict extends LineConflict {
		private Integer offsetNumber;

		public OffsetConflict(SootMethod method, Integer lineNumber, Integer offsetNumber) {
			super(method, lineNumber);
			this.offsetNumber = offsetNumber;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((offsetNumber == null) ? 0 : offsetNumber.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!super.equals(obj)) {
				return false;
			}
			if (!(obj instanceof OffsetConflict)) {
				return false;
			}
			OffsetConflict other = (OffsetConflict) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (offsetNumber == null) {
				if (other.offsetNumber != null) {
					return false;
				}
			} else if (!offsetNumber.equals(other.offsetNumber)) {
				return false;
			}
			return true;
		}

		private RepositoryCorrector getOuterType() {
			return RepositoryCorrector.this;
		}
	}
}
