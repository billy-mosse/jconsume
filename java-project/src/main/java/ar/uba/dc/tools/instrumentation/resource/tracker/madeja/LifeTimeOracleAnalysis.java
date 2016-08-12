package ar.uba.dc.tools.instrumentation.resource.tracker.madeja;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.Edge;
import ar.uba.dc.analysis.common.AbstractInterproceduralAnalysis;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.MethodBody;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.impl.DefaultMethodDecorator;
import ar.uba.dc.analysis.memory.code.impl.visitor.IdStatementVisitor;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.analysis.memory.summary.MemorySummaryFactory;
import ar.uba.dc.util.Timer;

public class LifeTimeOracleAnalysis extends AbstractInterproceduralAnalysis {

	private static Log log = LogFactory.getLog(LifeTimeOracleAnalysis.class);

	public static final String OUTPUT_RELATIVE_PATH = "ea.runtime";
	
	protected Map<SootMethod, MemorySummary> memorySummaries = new LinkedHashMap<SootMethod, MemorySummary>();
	
	protected MemorySummaryFactory summaryFactory;
	
	private LifeTimeSummaryRepository lifeTimeSummaries;
	
	private LifeTimeOracle lifeTimeOracle;
	
	private IdStatementVisitor statementVisitor;
	
	private HeapPartitionVisitor<String> heapPartitionVisitor;

	public LifeTimeSummaryRepository getRepository() {
		return lifeTimeSummaries;
	}

	public void setRepository(LifeTimeSummaryRepository repository) {
		this.lifeTimeSummaries = repository;
	}

	
	
	public LifeTimeOracle getLifeTimeOracle() {
		return lifeTimeOracle;
	}

	public void setLifeTimeOracle(LifeTimeOracle lifeTimeOracle) {
		this.lifeTimeOracle = lifeTimeOracle;
	}
	
	public MemorySummaryFactory getSummaryFactory() {
		return summaryFactory;
	}

	public void setSummaryFactory(MemorySummaryFactory summaryFactory) {
		this.summaryFactory = summaryFactory;
	}

	
	public IdStatementVisitor getStatementVisitor() {
		return statementVisitor;
	}

	public void setStatementVisitor(IdStatementVisitor statementVisitor) {
		this.statementVisitor = statementVisitor;
	}

	public HeapPartitionVisitor<String> getHeapPartitionVisitor() {
		return heapPartitionVisitor;
	}

	public void setHeapPartitionVisitor(
			HeapPartitionVisitor<String> heapPartitionVisitor) {
		this.heapPartitionVisitor = heapPartitionVisitor;
	}

	@Override
	protected void doAnalysis() {
	
		Timer t = new Timer();
		log.info("LifeTimeOracle Analysis began");
		t.start();
	
		internalDoAnalysis();
		
		dump();
		
		t.stop();
		log.info("LifeTimeOracle Analysis finished. Took " + t.getElapsedTime() + " (" + t.getElapsedSeconds() + " seconds)");
	
	}
	
	private void dump() {
		
		File file = new File(OUTPUT_RELATIVE_PATH);
		try {
			Writer writer = new FileWriter(file);
			for (SootMethod sootMethod : this.lifeTimeSummaries.getMethods()) {
				LifeTimeSummary summary  = this.lifeTimeSummaries.get(sootMethod);
				Map<String, String> mapping = summary.getNewStmtsMapping();
				writer.write(sootMethod.getSignature() + " {" );
				writer.write("\n");
				writer.write("\n");
				for (String key : mapping.keySet() ) {
					writer.write("   ");
					writer.write(key + ";" + mapping.get(key));
					writer.write("\n");
				}
				
				Map<String, Map<String, String>> calls = summary.getPartitionMapping();
				for (String call: calls.keySet()) {
					Map<String, String> hpMapping = calls.get(call);
					writer.write("   ");
					writer.write(call + " {");
					
					if(!hpMapping.isEmpty()) {
						writer.write("\n");
						for (String calleeHp : hpMapping.keySet()) {
							writer.write("   ");
							writer.write("   ");
							writer.write(calleeHp);
							writer.write("-->");
							writer.write(hpMapping.get(calleeHp));
							writer.write("\n");	
						}
						writer.write("   ");
					} else {
						writer.write(" ");
					}
					writer.write("}");
					writer.write("\n");
				}
				
				writer.write("\n");
				writer.write("}");
				writer.write("\n");
				writer.write("\n");
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void internalDoAnalysis() {
		SortedSet<SootMethod> queue = new TreeSet<SootMethod>(getOrderComparator());
		queue.addAll(order.keySet());
	
	
		for (SootMethod methodUnderAnalysis : queue) {
			//	if (analyseKnownMethods || !repository.contains(methodUnderAnalysis)) {
			log.info(" |- processing " + methodUnderAnalysis.toString());
			
			//Summary de memoria
			MemorySummary memorySummary = this.summaryFactory.initialSummary(methodUnderAnalysis);
			
			//Summary con informacion de particiones
			LifeTimeSummary lifeTimeSummary = new LifeTimeSummary();
			
			MethodBody abstraction = new DefaultMethodDecorator().decorate(methodUnderAnalysis);

			log.debug(" |- Processing new statements");
			for (NewStatement newStmt : abstraction.getNewStatements()) {
				
				HeapPartition partition = this.lifeTimeOracle.getPartition(newStmt);
				
				if(!partition.isTemporal()) {
					//No importa el valor
					memorySummary.setResidual(partition,null);
				}
			
				//Registramo el new en el LifeTime summary
				lifeTimeSummary.registerPartition(newStmt.apply(this.statementVisitor), partition.apply(this.heapPartitionVisitor));
			}
			
			
			for (CallStatement callStmt : abstraction.getCallStatements()) {
				
				// Iteramos por c/implementacion posible (si no es un virtual call, habra un solo eje).
				Iterator<Edge> it = callGraph.edgesOutOf(callStmt.getStatement());
				Map<String, String> hpMapping = new LinkedHashMap<String, String>();	
				while (it.hasNext()) {
					Edge edge = it.next();
					SootMethod callee = edge.tgt();
					
					MemorySummary calleeSummary = this.memorySummaries.get(callee);
					if(calleeSummary != null) {
						
						for (HeapPartition calleeHp : calleeSummary.getResidualPartitions()) {
							HeapPartition callerHp = this.lifeTimeOracle.bind(calleeHp, callStmt);
							
							if(callerHp != null) {
								if(!callerHp.isTemporal())
									memorySummary.setResidual(callerHp, null);
								hpMapping.put(calleeHp.apply(this.heapPartitionVisitor), callerHp.apply(this.heapPartitionVisitor));
							}
						}
					
					}
				}
				
				lifeTimeSummary.registerPartition(callStmt.apply(this.statementVisitor), hpMapping);
			}
			
			
			this.lifeTimeSummaries.put(methodUnderAnalysis,lifeTimeSummary);
			this.memorySummaries.put(methodUnderAnalysis, memorySummary);
		}
	
	}

	
	protected class TemporalPartition implements HeapPartition {

		@Override
		public <T> T apply(HeapPartitionVisitor<T> visitor) {
			return visitor.visit(this);
		}

		@Override
		public boolean isTemporal() {
			return true;
		}
		
		public TemporalPartition clone() {
			return new TemporalPartition();
		}
	}

}
