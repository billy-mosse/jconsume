package ar.uba.dc.analysis.memory.impl.runner;

import ar.uba.dc.analysis.escape.runner.EscapeSceneTransformer;
import ar.uba.dc.config.Context;
import ar.uba.dc.soot.SootUtils;

/**
 * @author martin
 *
 */
public class EscapeSceneTransformerInitializer implements PhaseInitializer {

	@Override
	public void initialize(Context context, String className) {
		EscapeSceneTransformer transformer = EscapeSceneTransformer.v(context, className, false);
		SootUtils.insertTransformer("wjtp", "wjtp.escape", transformer);
	}
	
	@Override
	public void initialize(Context context, String className, boolean debugIR) {
		EscapeSceneTransformer transformer = EscapeSceneTransformer.v(context, className, debugIR);
		SootUtils.insertTransformer("wjtp", "wjtp.escape", transformer);
	}

}
