package ar.uba.dc.analysis.escape;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import soot.Unit;
import soot.jimple.Stmt;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;
import ar.uba.dc.analysis.escape.visitor.StatementVisitor;
import ar.uba.dc.soot.Box;

public class IntraproceduralAnalysis extends ForwardFlowAnalysis<Unit, Box<EscapeSummary>> {

	private static Log log = LogFactory.getLog(IntraproceduralAnalysis.class);
	
	private SootMethod methodUnderAnalysis;
	private CallAnalyzer callAnalyzer;
	private StatementVisitor visitor = new StatementVisitor();
	private int sensitivity;
	
	@Override
	protected void flowThrough(Box<EscapeSummary> in, Unit unit, Box<EscapeSummary> out) {
		Stmt stmt = (Stmt) unit;
		log.debug(" | |- exec " + stmt);
		out.setValue(new EscapeSummary(in.getValue()));
		visitor.setMethodUnderAnalysis(methodUnderAnalysis);
		visitor.setIn(in);
		visitor.setOut(out);
		visitor.setCallAnalyzer(callAnalyzer);
		visitor.setSensitivity(sensitivity);
		stmt.apply(visitor);
	}
	
	@Override
	protected void copy(Box<EscapeSummary> source, Box<EscapeSummary> dest) {
		dest.setValue(new EscapeSummary(source.getValue()));		
	}

	@Override
	protected Box<EscapeSummary> entryInitialFlow() {
		return new Box<EscapeSummary>(new EscapeSummary(methodUnderAnalysis));
	}

	@Override
	protected void merge(Box<EscapeSummary> in1, Box<EscapeSummary> in2, Box<EscapeSummary> out) {
		if (out != in1) {
			out.setValue(new EscapeSummary(in1.getValue()));
		}
		out.getValue().union(in2.getValue());
	}

	@Override
	protected Box<EscapeSummary> newInitialFlow() {
		return new Box<EscapeSummary>(new EscapeSummary(methodUnderAnalysis));
	}
	
	 /**
     * Perform escape analysis on the Jimple unit graph g, as part of
     * a larger interprocedural analysis.
     * Once constructed, you may call getResult to query the analysis result.
     */
	public IntraproceduralAnalysis(SootMethod methodUnderAnalysis, CallAnalyzer callAnalyzer, int sensitivity) {
		super(new ExceptionalUnitGraph(methodUnderAnalysis.retrieveActiveBody()));
    	this.methodUnderAnalysis = methodUnderAnalysis;
    	this.callAnalyzer = callAnalyzer;
    	this.sensitivity = sensitivity;
    	doAnalysis();
    	
	}

	/**
     * Put into dst the purity graph obtained by merging all purity graphs at the method return.
     * It is a valid summary that can be used in methodCall if you do interprocedural analysis. 
     */
	public void copyResult(Box<EscapeSummary> dest) {
		EscapeSummary r = new EscapeSummary(methodUnderAnalysis);
    	for (Unit unit : graph.getTails()) {
    		Stmt stmt = (Stmt) unit;
    		Box<EscapeSummary> ref = (Box<EscapeSummary>) getFlowAfter(stmt);
    		
    		r.union(ref.getValue());
    	}    	
    	dest.setValue(r);
	}
}
