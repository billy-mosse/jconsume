package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import madeja.analysis.pointerinterference.families.ClassFamilyMember;
import madeja.analysis.pointerinterference.families.Family;
import madeja.analysis.pointerinterference.families.FamilyManager;
import madeja.analysis.pointerinterference.families.IFamilyMember;
import madeja.analysis.pointerinterference.families.LocalFamilyMember;
import madeja.analysis.pointerinterference.families.SyntheticSootClass;
import madeja.analysis.tribes.Tribe;
import madeja.util.JimpleUtil;
import soot.Local;
import soot.SootMethod;
import soot.jimple.AssignStmt;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;
import ar.uba.dc.analysis.memory.LifeTimeOracle;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.invariant.InvariantProvider;

public class MadejaLifeTimeOracle implements LifeTimeOracle {

	private InvariantProvider invariantProvider;
	
	private Map<SootMethod, MethodEscapeInfo> escapeInfo = new HashMap<SootMethod, MethodEscapeInfo>();
	
	public InvariantProvider getInvariantProvider() {
		return invariantProvider;
	}

	public void setInvariantProvider(InvariantProvider invariantProvider) {
		this.invariantProvider = invariantProvider;
	}
	
	private MethodEscapeInfo getMethodEscapeInfo(SootMethod sootMethod) {
		MethodEscapeInfo info = this.escapeInfo.get(sootMethod);
		if(info == null) {
			info = new MethodEscapeInfo(sootMethod);
			this.escapeInfo.put(sootMethod, info);
		}
		return info;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HeapPartition bind(HeapPartition calleePartition, CallStatement callStmt) {
		
		if(this.invariantProvider != null && this.invariantProvider.captureAllPartitions(callStmt))
			return new DefaultPartition();
		
		MethodEscapeInfo escapeInfo = this.getMethodEscapeInfo(callStmt.belongsTo());
		
		//Tribe que origina la particion
		Tribe tribePartition = ((MadejaHeapPartition) calleePartition).getTribe();
		
		//Hay que ver si la tribu esta linkeada a una tribu que escapa del metodo
		Collection<Family> families = (Collection<Family>) FamilyManager.v().getFamilies(callStmt.belongsTo());
		
		for (Family family : families) {
				//Tribe asociada a la familia
				Tribe tribe = Tribe.getTribe(family); 
				
				//Estan linkeadas?
				Tribe linkedTribe = this.getLinkedTribe(callStmt.getStatement(), tribe, tribePartition);
				if(linkedTribe != null)
					return escapeInfo.getPartition(tribe);
		}
		
		ClassFamilyMember l = tribePartition.getBaseFamily().checkMemberForClass(SyntheticSootClass.SYNTHETIC_CLASS);
		if(l != null) {
			ClassFamilyMember familyMember = FamilyManager.v().getSyntheticClassFamilyMember(callStmt.belongsTo());
			Family family = FamilyManager.v().getCurrentFamilyFor(familyMember);
			Tribe newTribe = Tribe.getTribe(family);
			return escapeInfo.getPartition(newTribe);
		}
	
		//throw new RuntimeException("Unable to bind partition such partition");

		return new DefaultPartition();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HeapPartition getPartition(NewStatement newStmt) {
		MethodEscapeInfo escapeInfo = this.getMethodEscapeInfo(newStmt.belongsTo());
		List<Family> families = FamilyManager.v().getFamilies(newStmt.belongsTo());
		Iterator<Family> familyIterator = families.iterator();
		while(familyIterator.hasNext()) {
			Family family = familyIterator.next();
			Iterator members = family.iterator();
			
			while(members.hasNext()) {
				
				IFamilyMember familyMember = (IFamilyMember) members.next();
				if (familyMember instanceof LocalFamilyMember ) {
				    Local l = ((LocalFamilyMember)familyMember).getLocal();
				    List definitions = JimpleUtil.getAssignmentsOfLocal(l);
				    Iterator defIt = definitions.iterator();
				    while(defIt.hasNext()) {
						Stmt def = (Stmt)defIt.next();
						if(def.equals(((AssignStmt)newStmt.getStatement()))) {
							return escapeInfo.getPartition(Tribe.getTribe(family));
						}
				    }
				}
			}
		}
		//No se encontro una particion. FIXME generar una exception
		throw new RuntimeException("Unnable to get partition for:" + newStmt );
		//return new DefaultPartition();
		
	}
	
	
	

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Tribe getLinkedTribe(Stmt link, Tribe root, Tribe target) {
		
		Set<Tribe> linkedTribes = root.getLinkedTribes(link);
		if(linkedTribes != null) {
			for (Tribe tribe : linkedTribes) {
				if(target.equals(tribe))
					return tribe;
				else {
					Set<Stmt> links = tribe.getLinkStatements();
					for (Stmt stmt : links) {
						Tribe linkedTribe = getLinkedTribe(stmt, tribe, target);
						if(linkedTribe != null)
							return linkedTribe;
					}
				}
			}
		}			
		return null;
	}
	

	class DefaultPartition implements HeapPartition {

		@Override
		public boolean isTemporal() {
			return true;
		}
		
		public DefaultPartition clone() {
			return new DefaultPartition();
		}

		@Override
		public <T> T apply(HeapPartitionVisitor<T> visitor) {
			return visitor.visit(this);
		}
		
		@Override
		public String toHumanReadableString()
		{
			return "soy un nodo";
		}
		
	}
}
