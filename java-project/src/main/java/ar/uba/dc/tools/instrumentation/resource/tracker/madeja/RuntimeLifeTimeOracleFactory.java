package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.io.InputStream;
import java.io.InputStreamReader;

import ar.uba.dc.tools.instrumentation.resource.tracker.runtime.MapRuntimeLifeTimeOracle;
import ar.uba.dc.tools.instrumentation.resource.tracker.runtime.RuntimeLifeTimeOracle;


public class RuntimeLifeTimeOracleFactory  {
	

	
	public static final String EA_ORACLE_FILE_NAME = "ea.runtime";
	
	public static final String EA_ORACLE_PROPERTY_NAME = "tracker.ea";
	
	
	public RuntimeLifeTimeOracle getLifeTimeOracle() {
		
		MapRuntimeLifeTimeOracle oracle = new MapRuntimeLifeTimeOracle();
		
		
		String file = System.getProperty(EA_ORACLE_PROPERTY_NAME, EA_ORACLE_FILE_NAME);
		InputStream in = ClassLoader.getSystemResourceAsStream(file);
		InputStreamReader reader = new InputStreamReader(in);
		RuntimeLifeTimeOracleReader lifeTimeOracleReader = new RuntimeLifeTimeOracleReader();
		lifeTimeOracleReader.read(oracle, reader);
		
		return oracle;
	}	
	
		
}
