package ar.uba.dc.analysis.common.method.information.rules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XMLRuleSetRepository implements RuleSetRepository {

	private static Log log = LogFactory.getLog(XMLRuleSetRepository.class);
	
	protected RuleSetReader reader;
	protected String rulesFile;
	
	public RuleSet get() {
		try {
			
			return reader.read(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(rulesFile)));
			//return reader.read(new FileReader(new File(rulesFile)));
		} catch (Exception e) {
			log.error("No fue posible cargar el archivo de reglas para metodos no analizables [" + rulesFile + "]: " + e.getMessage(), e);
			return null;
		}
	}

	public void setReader(RuleSetReader reader) {
		this.reader = reader;
	}

	public void setRulesFile(String rulesFile) {
		this.rulesFile = rulesFile;
	}
}
