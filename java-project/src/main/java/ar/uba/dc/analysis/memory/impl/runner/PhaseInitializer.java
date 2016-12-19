package ar.uba.dc.analysis.memory.impl.runner;

import ar.uba.dc.config.Context;

/**
 * @author martin
 *
 */
public interface PhaseInitializer {	
	void initialize(Context context, String className);
	void initialize(Context context, String className, boolean debugIR);

}
