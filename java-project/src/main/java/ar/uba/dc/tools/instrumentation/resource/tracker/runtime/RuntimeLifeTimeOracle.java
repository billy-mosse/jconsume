package ar.uba.dc.tools.instrumentation.resource.tracker.runtime;

import java.util.Map;

/**
 * @author martin
 *
 */
public interface RuntimeLifeTimeOracle {

	/**
	 * Registra los objetos asociadoa a newStmt con la particion partition.
	 * @param newStmt
	 * @param partition
	 */
	void registerPartition(String newStmt, String partition);
	
	/**
	 * Registra el binding entre la particion del callee y el calleer.
	 * @param newStmt
	 * @param partition
	 */
	void registerPartition(String callStmt, String callerPartition, String calleePartition);
	
	/**
	 * Registra el binding de particiones dado por mapsTo para callStmt
	 * @param callStmt
	 * @param mapsTo
	 */
	void registerPartition(String callStmt, Map<String, String> mapsTo);
	


	/**
	 * Retorna la particion asociada al newStmt
	 * @param newStmt
	 * @return
	 */
	String getPartition(String newStmt);
	

	/**
	 * Retorna la particion del caller que fue asocia a la particion calleePartition por el oraculo de escape. 
	 * @param callStmt
	 * @param calleePartition
	 * @return
	 */
	String bindPartition(String callStmt, String calleePartition);

}