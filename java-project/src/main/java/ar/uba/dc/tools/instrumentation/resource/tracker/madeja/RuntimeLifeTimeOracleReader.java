package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ar.uba.dc.tools.instrumentation.resource.tracker.runtime.RuntimeLifeTimeOracle;

public class RuntimeLifeTimeOracleReader {

	private static final String NEW_STMT_PATTERN = "New@";
	
	private static final String CALL_STMT_PATTERN = "Invoke@";
	
	private static final String END_CALL_PATTERN = "}";
	
	private static final String EMPTY_CALL_PATTERN = "{ }";
	
	private static final String MAPS_TO_PATTERN = "-->";
	
	public void read(RuntimeLifeTimeOracle oracle, Reader reader) {
		Scanner src = new Scanner(reader);
		while(src.hasNextLine()) {
			String line = src.nextLine().trim();
			if(line.startsWith(NEW_STMT_PATTERN)) {
				this.parseNewStmt(oracle,src, line);
			} else if( line.startsWith(CALL_STMT_PATTERN)) {
				this.parseCallStmt(oracle, src, line);
			}
			 
		} 
	}
	
	 private void parseNewStmt(RuntimeLifeTimeOracle oracle, Scanner scanner, String firstLine) {
		 String[] components = firstLine.split(";");
		 String newStmt = components[0];
		 String partition = components[1];
		 oracle.registerPartition(newStmt, partition);
	 }
	 
	 private void parseCallStmt(RuntimeLifeTimeOracle oracle, Scanner scanner, String fisrtLine) {
		 if(!fisrtLine.endsWith(EMPTY_CALL_PATTERN)) {
			 boolean endCall = false;
			 String callStmt = fisrtLine.substring(0, fisrtLine.indexOf("{")).trim();
			 Map<String, String> mapping = new HashMap<String, String>();
			 while(scanner.hasNextLine() && !endCall) {
					String line = scanner.nextLine().trim();
					if(line.equals(END_CALL_PATTERN))
						endCall = true; //Termino la seccion del call
					else {
						String[] mapsTo = line.split(MAPS_TO_PATTERN);
						mapping.put(mapsTo[0], mapsTo[1]);
					}
			 }
			 if(!mapping.isEmpty())
				 oracle.registerPartition(callStmt, mapping);
		 }
	 }
}
