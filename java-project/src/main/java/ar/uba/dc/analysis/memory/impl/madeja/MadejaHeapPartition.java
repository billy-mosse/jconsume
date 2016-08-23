package ar.uba.dc.analysis.memory.impl.madeja;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import madeja.analysis.pointerinterference.families.ClassFamilyMember;
import madeja.analysis.pointerinterference.families.Family;
import madeja.analysis.pointerinterference.families.IFamilyMember;
import madeja.analysis.pointerinterference.families.ParamFamilyMember;
import madeja.analysis.tribes.Tribe;
import ar.uba.dc.analysis.memory.HeapPartition;
import ar.uba.dc.analysis.memory.HeapPartitionVisitor;


public class MadejaHeapPartition implements HeapPartition {

	private Tribe tribe;

	private boolean isTemporal;

	
	public MadejaHeapPartition(Tribe tribe) {
		super();
		this.tribe = tribe;
		this.isTemporal = !this.tribe.getBaseFamily().isEscape();
	}

	@Override
	public boolean isTemporal() {
		return this.isTemporal;
	}
	
	
	
	public Tribe getTribe() {
		return tribe;
	}

	public void setTribe(Tribe tribe) {
		this.tribe = tribe;
	}

	public void setTemporal(boolean isTemporal) {
		this.isTemporal = isTemporal;
	}

	public MadejaHeapPartition clone()  {
		return new MadejaHeapPartition(tribe);
	}

	@SuppressWarnings("unchecked")
	public String key() {
		Family family = this.getTribe().getBaseFamily();
		List<String> escapes = new LinkedList<String>();
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");	
		Iterator familyIterator = family.iterator();

		while(familyIterator.hasNext()) {

			IFamilyMember fm = (IFamilyMember) familyIterator.next();
			
			if(fm instanceof ParamFamilyMember) {
				escapes.add(((ParamFamilyMember)fm).getParameter().toString());
			} else if(fm instanceof ClassFamilyMember) {
				escapes.add("static");
			} 
		}

		if(family.isReturned()) {
			escapes.add("return");
		}
		boolean sep = false;
		for (String string : escapes) {
			if(sep) 
				stringBuffer.append(",");
			else
				sep = true;
			
			stringBuffer.append(string);
			
		}
		stringBuffer.append("}");
		return stringBuffer.toString();
	}

	
	@Override
	public String toString() {
		return this.key();
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
