package ar.uba.dc.tools.analysis.summary;

import java.io.FileNotFoundException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Body;
import soot.BodyTransformer;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.toolkits.callgraph.EdgePredicate;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import soot.tagkit.BytecodeOffsetTag;
import soot.tagkit.LineNumberTag;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.escape.EscapeSummary;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;
import ar.uba.dc.soot.xstream.StatementNotFoundException;
import ar.uba.dc.util.Timer;

/**
 * Intenta recuperar todos los summaries de un repositorio devolviendo aquellos
 * que no pudieron ser levantados. Esto indique probablemente que el codigo de los mismos
 * a cambiado desde la ultima vez que se genero en resumen
 * 
 * @author testis
 */
public class RepositoryUpdateChecker extends BodyTransformer {

	private static Log log = LogFactory.getLog(RepositoryUpdateChecker.class);
	
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
		
		SootUtils.insertTransformer("jtp", "jtp.repo-checker", new RepositoryUpdateChecker(context.getFactory().getEscapeRepository(), methodFilter));
		
		Timer analysisTimer = new Timer();
		analysisTimer.start();
		
		soot.Main.main(opts);
		
		analysisTimer.stop();
		
		log.info("Finish. Took " + analysisTimer.getElapsedTime() + " (" + analysisTimer.getElapsedSeconds() + " seconds)");
	}

	private SummaryRepository<EscapeSummary, SootMethod> escapeRepository;
	private String methodFilter;
	
	public RepositoryUpdateChecker(SummaryRepository<EscapeSummary, SootMethod> escapeRepository, String methodFilter) {
		this.escapeRepository = escapeRepository;
		this.methodFilter = methodFilter;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(Body b, String phaseName, Map options) {
		if (b.getMethod().toString().startsWith(methodFilter)) {
			log.info(" |- Checking summary of [" + b.getMethod() + "]");
	
			try {
				escapeRepository.get(b.getMethod());
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
}
