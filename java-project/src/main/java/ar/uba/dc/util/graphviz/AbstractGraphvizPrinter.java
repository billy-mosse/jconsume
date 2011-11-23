package ar.uba.dc.util.graphviz;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.util.dot.DotGraph;
import ar.uba.dc.util.ConsoleException;
import ar.uba.dc.util.ConsoleUtils;

/**
 * Clase abstracta para generar una imagen en base a un {@link DotGraph}
 * 
 * @author testis
 */
public abstract class AbstractGraphvizPrinter {

	private static Log log = LogFactory.getLog(AbstractGraphvizPrinter.class);
	
	/**
	 * Directorio donde dejar la imagen
	 */
	protected String outputDir;

	protected void plot(DotGraph dot, String fileName) {
		new File(outputDir).mkdirs();
		File f = new File(outputDir, fileName.replaceAll("<", "").replaceAll(">", "").replaceAll(":", "").replaceAll(" ", "_"));
		dot.plot(f.getPath());
		String command = "dot -Tgif " + f.getAbsolutePath() + " -o " + f.getAbsolutePath().replace(DotGraph.DOT_EXTENSION, ".gif");
		try {
			ConsoleUtils.execCommand(command, false);
		} catch (ConsoleException e) {
			log.error("No fue posible generar el grafo [" + fileName + "]: " + e.getMessage(), e);
		}
	}
	
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getOutputDir() {
		return outputDir;
	}
}
