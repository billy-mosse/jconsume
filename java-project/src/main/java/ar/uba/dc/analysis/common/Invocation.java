package ar.uba.dc.analysis.common;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.tree.MethodNode;

import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethodBuilder;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.escape.graph.PaperNode;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationParameter;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.callanalyzer.PaperCallAnalyzer;
import ar.uba.dc.analysis.memory.impl.summary.EscapeBasedLifeTimeOracle;
import ar.uba.dc.analysis.memory.impl.summary.RichPaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.SimplePaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.soot.SootUtils;
import soot.RefLikeType;
import soot.SootMethod;

import com.google.common.base.*;
import com.google.common.collect.Iterables;

import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartition;
import ar.uba.dc.analysis.memory.impl.summary.PaperPointsToHeapPartitionBinding;

import ar.uba.dc.analysis.escape.graph.node.PaperMethodNode;


public class Invocation {
	
	private boolean isCallStatement;
	private PaperPointsToHeapPartition heapPartition;
	
	protected String nameCalled;

	protected boolean isReturnRefLikeType;
	private Set<PaperPointsToHeapPartitionBinding> hpBindings;


	public Invocation()
	{
		
	}
	
	private static Log log = LogFactory.getLog(Invocation.class);
	
	public Invocation(Line line, SootMethod m, Set<IntermediateRepresentationMethod> ir_methods, Set<PaperPointsToHeapPartition> nodes, String fullName)
	{
		this.class_called = m.getDeclaringClass().toString();
		this.setCallStatement(true);
		this.called_implementation_signature = m.getSignature();
		this.nameCalled = m.getName();
		
		this.isReturnRefLikeType = m.getReturnType() instanceof RefLikeType;
		
		this.setParameters(new LinkedHashSet<IntermediateRepresentationParameter>());
		Set<IntermediateRepresentationParameter> s = SootUtils.getParameters(m, false);
		for(IntermediateRepresentationParameter p : s)
		{
			this.parameters.add((IntermediateRepresentationParameter)p);
		}

		this.setHpBindings(new HashSet<PaperPointsToHeapPartitionBinding>());
		
		boolean found = false;
		for(IntermediateRepresentationMethod ir_method : ir_methods)
		{
			if(ir_method.getDeclaringClass() == this.class_called && ir_method.getName() == this.nameCalled)
			{
				for(PaperPointsToHeapPartition calleePartition : ir_method.getEscapeNodes())
				{	
					PaperPointsToHeapPartition callerPartition = PaperCallAnalyzer.bind(calleePartition, line, nodes, fullName);
					if(callerPartition != null)
					{
						this.getHpBindings().add(new PaperPointsToHeapPartitionBinding(calleePartition,callerPartition));
						found = true;
					}
				}
			}
		}
		if(!found)
		{
			log.debug("Let's assume that this method is in unanalizable_methods.xml");

			for(PaperPointsToHeapPartition hp : nodes)
			{
				RichPaperPointsToHeapPartition rich_hp = (RichPaperPointsToHeapPartition) hp;
				PaperNode node = rich_hp.getNode();
				
				
				String s1= line.getFullNameCalled();
				String s2= rich_hp.belongsTo;
				
				if(node.getClass() == PaperMethodNode.class && s1.equals(s2))
				{
					
					//faltan los nodos globales (????)
					this.getHpBindings().add(new PaperPointsToHeapPartitionBinding(new SimplePaperPointsToHeapPartition(-1), new SimplePaperPointsToHeapPartition(hp.getNumber())));
					
					//if(line.getName() == mNode)
					
					
				}
				
				
			}
			
			//this.getHpBindings().add(new PaperPointsToHeapPartitionBinding(calleePartition,callerPartition));
			
		}
	}
	
	public Invocation(NewStatement newStmt, HeapPartition heapPartition) {			
		this.setCallStatement(false);
		this.setParameters(newStmt.getIntermediateRepresentationParameters());
		this.setClass_called("");
		this.called_implementation_signature = "";
		this.heapPartition =  new RichPaperPointsToHeapPartition(heapPartition);
		this.nameCalled = "";
		this.setHpBindings(new HashSet<PaperPointsToHeapPartitionBinding>());
		
	}
	
	public String getNameCalled() {
		return nameCalled;
	}


	public void setNameCalled(String nameCalled) {
		this.nameCalled = nameCalled;
	}


	public String getFullNameCalled()
	{
		return this.class_called + "." + this.nameCalled;
	}

	public PaperPointsToHeapPartition getHeapPartition() {
		return heapPartition;
	}	

	public void setHeapPartition(PaperPointsToHeapPartition heapPartition) {
		this.heapPartition = heapPartition;
	}

	public Set<IntermediateRepresentationParameter> getParameters() {
		return parameters;
	}

	protected Set<IntermediateRepresentationParameter> parameters;
	protected String class_called;
	
	protected String called_implementation_signature;
	

	
	public String toHumanReadableString() {
		
		String s = (this.getParameters() != null ? Joiner.on(", ").skipNulls().join(Iterables.transform(this.getParameters(), new Function<IntermediateRepresentationParameter, String >()
				{
					public String apply(IntermediateRepresentationParameter parameter) { return parameter.getName(); }
				}
				
				
				)) : "");
		
		return (this.isCallStatement ? this.class_called: "") + String.format("(%s)", s);
		
		
		//return "<invoke " + this.name + "(" + arguments. + ")>;"; //Falta el invariante
		//String pepito = "blabla"; return pepito; //usar expresiones lambda o algo aprecido a linq
		//return this.name + this.arguments
	}

	public String getClass_called() {
		return class_called;
	}

	public void setClass_called(String class_called) {
		this.class_called = class_called;
	}

	public void setParameters(Set<IntermediateRepresentationParameter> parameters) {
		this.parameters = parameters;
	}

	public String getCalled_implementation_signature() {
		return called_implementation_signature;
	}

	public void setCalled_implementation_signature(String called_implementation_signature) {
		this.called_implementation_signature = called_implementation_signature;
	}

	public boolean isCallStatement() {
		return isCallStatement;
	}

	public void setCallStatement(boolean isCallStatement) {
		this.isCallStatement = isCallStatement;
	}
	
	public boolean isReturnRefLikeType()
	{
		return this.isReturnRefLikeType;
	}	
	
	public void setIsReturnRefLikeType(boolean isReturnRefLikeType)
	{
		this.isReturnRefLikeType = isReturnRefLikeType;
	}


	public Set<PaperPointsToHeapPartitionBinding> getHpBindings() {
		return hpBindings;
	}


	public void setHpBindings(Set<PaperPointsToHeapPartitionBinding> hpBindings) {
		this.hpBindings = hpBindings;
	}

}
