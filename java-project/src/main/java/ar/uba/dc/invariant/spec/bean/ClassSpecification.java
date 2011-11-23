package ar.uba.dc.invariant.spec.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class ClassSpecification {

	private String className;
	
	private List<MethodSpecification> methods = new ArrayList<MethodSpecification>();
	
	private List<InvariantSpecification> classInvariants = new ArrayList<InvariantSpecification>();
	
	public ClassSpecification() {
		super();
	}

	public ClassSpecification(String className) {
		super();
		this.className = className;
	}
	
	/**
	 * BugFix porque no se invoca al constructor por defecto con XStream
	 * 
	 * http://xstream.codehaus.org/faq.html
	 * 
	 * @return
	 */
	private Object readResolve() {
		if (methods == null) {
			methods = new ArrayList<MethodSpecification>();
		}
		
		if (classInvariants == null) {
			classInvariants = new ArrayList<InvariantSpecification>();
		}
		
	    return this;
	}
	
	public String getClassName() {
		return className;
	}

	public void addMethod(MethodSpecification methodSpec) {
		methods.add(methodSpec);
	}
	
	public List<MethodSpecification> getMethods() {
		return new ArrayList<MethodSpecification>(methods);
	}

	public MethodSpecification getMethod(String signature) {
		int idxParams = signature.lastIndexOf("(");
		signature = signature.substring(0, idxParams) + signature.substring(idxParams).replaceAll(" ", StringUtils.EMPTY);
		
		return (MethodSpecification) CollectionUtils.find(methods, new BeanPropertyValueEqualsPredicate("signature", signature));
	}

	public List<InvariantSpecification> getClassInvariants() {
		return new ArrayList<InvariantSpecification>(classInvariants);
	}
	
	public InvariantSpecification getClassInvariant(final String id) {
		return (InvariantSpecification) CollectionUtils.find(classInvariants, new BeanPropertyValueEqualsPredicate("id", id));
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && 
			   (obj instanceof ClassSpecification) &&
			   className.equals(((ClassSpecification) obj).getClassName());
	}

	@Override
	public int hashCode() {
		return className.hashCode();
	}
}
