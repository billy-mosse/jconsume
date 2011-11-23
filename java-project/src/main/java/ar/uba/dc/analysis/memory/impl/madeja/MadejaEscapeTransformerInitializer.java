package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import madeja.analysis.IFixedPointMethodAnalysis;
import madeja.analysis.IMethodAnalysis;
import madeja.analysis.SceneAnalysis;
import madeja.analysis.localpointsto.LocalPointsToAnalysis;
import madeja.analysis.memory.FamilyScopeAnalysis;
import madeja.analysis.pointerinterference.PointerInterferenceAnalysis;
import madeja.analysis.pointerinterference.XylophoneXMLDumper;
import madeja.analysis.tribes.TribeAnalysis;
import soot.SootMethod;
import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis;
import ar.uba.dc.analysis.common.SceneTransformer;
import ar.uba.dc.analysis.memory.impl.runner.PhaseInitializer;
import ar.uba.dc.config.Context;
import ar.uba.dc.soot.SootUtils;

public class MadejaEscapeTransformerInitializer implements PhaseInitializer {

	private boolean dump = false;
	
	
	
	public boolean dump() {
		return dump;
	}


	public void setDump(boolean dump) {
		this.dump = dump;
	}


	@Override
	public void initialize(Context context, String mainClass) {
		
		// Local points-to analysis
		//SootUtils.insertTransformer("wjtp", "wjtp.lpta",new AppAnalysis(new madeja.analysis.localpointsto.LocalPointsToAnalysis()));
		IMethodAnalysisAdapter analysisAdapter = new IMethodAnalysisAdapter(new LocalPointsToAnalysis());
		SootUtils.insertTransformer("wjtp", "wjtp.lpta", new SceneTransformer(context, mainClass, null, analysisAdapter));
		
		SootUtils.setPhaseOptions("wjtp","wjtp.lpta","verbosemethods:false");
		
		// Pointer Interference analysis
	//	SootUtils.insertTransformer("wjtp", "wjtp.pia", new FixedPointSceneAnalysis(new madeja.analysis.pointerinterference.PointerInterferenceAnalysis()));
			
		FixedPointMethodAnalysisAdapter fixedPointMethodAnalysisAdapter = new FixedPointMethodAnalysisAdapter(new PointerInterferenceAnalysis());
		SootUtils.insertTransformer("wjtp", "wjtp.pia", new SceneTransformer(context, mainClass, null, fixedPointMethodAnalysisAdapter));
	
		//Tribe analysis
		analysisAdapter = new IMethodAnalysisAdapter(new TribeAnalysis());
		SootUtils.insertTransformer("wjtp", "wjtp.ta", new SceneTransformer(context, mainClass, null, analysisAdapter));
		
		//SootUtils.setPhaseOptions("wjtp","wjtp.ta","verbosemethods:false");
		//SootUtils.setPhaseOptions("wjtp","wjtp.ta","verbose:false");
		//SootUtils.setPhaseOptions("wjtp","wjtp.pia","verbose:false");

	
		//Scope analysis
		analysisAdapter = new IMethodAnalysisAdapter(new FamilyScopeAnalysis());
		SootUtils.insertTransformer("wjtp", "wjtp.lma", new SceneTransformer(context, mainClass, null, analysisAdapter));		

		if(this.dump) {
			XylophoneXMLDumper dumper =new madeja.analysis.pointerinterference.XylophoneXMLDumper("families.xml");
			
			SootUtils.insertTransformer("wjtp","wjtp.pia-xmld",new SceneAnalysis(dumper));
		}
		
	}
	
	public class IMethodAnalysisAdapter extends AbstractInterproceduralAnalysis {

		private IMethodAnalysis iMethodAnalysis;
		
		
		
		public IMethodAnalysisAdapter(IMethodAnalysis iMethodAnalysis) {
			super();
			this.iMethodAnalysis = iMethodAnalysis;
		}



		@Override
		protected void doAnalysis() {
			SortedSet<SootMethod> queue = new TreeSet<SootMethod>(getOrderComparator());

			// init
			for (SootMethod o : order.keySet()) { 
				queue.add(o);
			}

			// fixpoint iterations
			while (!queue.isEmpty()) {
				SootMethod m = queue.first();
				queue.remove(m);
				this.iMethodAnalysis.analyze(m);
			}
			
		}
		
	}
	
	
	public class FixedPointMethodAnalysisAdapter extends AbstractInterproceduralAnalysis {

		private IFixedPointMethodAnalysis fixedPointMethodAnalysis;
		
		
		
		public FixedPointMethodAnalysisAdapter(IFixedPointMethodAnalysis fixedPointMethodAnalysis) {
			super();
			this.fixedPointMethodAnalysis = fixedPointMethodAnalysis;
		}



		@SuppressWarnings("unchecked")
		@Override
		protected void doAnalysis() {
			SortedSet<SootMethod> queue = new TreeSet<SootMethod>(getOrderComparator());

			// init
			for (SootMethod o : order.keySet()) { 
				queue.add(o);
			}

			// fixpoint iterations
			while (!queue.isEmpty()) {
				SootMethod m = queue.first();
				queue.remove(m);
				//FIXME
				Set changes = this.fixedPointMethodAnalysis.analyze(m);
				if(!changes.isEmpty())
					queue.addAll(changes);
			}
			
		}
		
	}

}
