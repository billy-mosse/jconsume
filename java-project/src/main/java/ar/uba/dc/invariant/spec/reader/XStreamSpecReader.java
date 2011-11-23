package ar.uba.dc.invariant.spec.reader;

import java.io.Reader;
import java.util.Set;
import java.util.TreeSet;

import ar.uba.dc.invariant.spec.SpecReader;
import ar.uba.dc.invariant.spec.bean.CallSiteSpecification;
import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.CreationSiteSpecification;
import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.Specification;

import com.thoughtworks.xstream.XStream;

public class XStreamSpecReader implements SpecReader {

	protected XStream xstream = new XStream();
	
	public XStreamSpecReader() {
		xstream = new XStream();
		
		// Specification class
		xstream.alias("spec", Specification.class);
		xstream.addImplicitCollection(Specification.class, "classes", ClassSpecification.class);
		
		// ClassSpecification class
		xstream.alias("class", ClassSpecification.class);
		xstream.aliasAttribute(ClassSpecification.class, "className", "decl");
		xstream.useAttributeFor(ClassSpecification.class, "className");
		xstream.addImplicitCollection(ClassSpecification.class, "methods", MethodSpecification.class);
		xstream.addImplicitCollection(ClassSpecification.class, "classInvariants", InvariantSpecification.class);
		
		// MethodSpecificacion class
		xstream.alias("method", MethodSpecification.class);
		xstream.aliasAttribute(MethodSpecification.class, "signature", "decl");
		xstream.aliasField("relevant-parameters", MethodSpecification.class, "parameters");
		xstream.addDefaultImplementation(TreeSet.class, Set.class);
		xstream.registerLocalConverter(MethodSpecification.class, "parameters", new RelevantParameterConverter());
		xstream.addImplicitCollection(MethodSpecification.class, "requirements", String.class);
		xstream.addImplicitCollection(MethodSpecification.class, "invariants", InvariantSpecification.class);
		xstream.addImplicitCollection(MethodSpecification.class, "callSites", CallSiteSpecification.class);
		xstream.addImplicitCollection(MethodSpecification.class, "creationSites", CreationSiteSpecification.class);
		
		// RequirementSpecification
		xstream.alias("requires", String.class);
		
		// InvariantSpecificacion class
		xstream.alias("invariant", InvariantSpecification.class);
		xstream.aliasAttribute(InvariantSpecification.class, "id", "id");
		
		// CallSiteSpecification class
		xstream.alias("call-site", CallSiteSpecification.class);
		xstream.useAttributeFor(CallSiteSpecification.class, "id");
		xstream.useAttributeFor(CallSiteSpecification.class, "offset");
		xstream.useAttributeFor(CallSiteSpecification.class, "loopInvariant");
		xstream.aliasAttribute(CallSiteSpecification.class, "loopInvariant", "loop-invariant");
		xstream.aliasAttribute(CallSiteSpecification.class, "captureAllPartitions", "capture");
		xstream.aliasAttribute(CallSiteSpecification.class, "implementation", "impl");
		xstream.aliasAttribute(CallSiteSpecification.class, "operator", "op");
		
		// CreationSiteSpecification class
		xstream.alias("creation-site", CreationSiteSpecification.class);
		xstream.useAttributeFor(CreationSiteSpecification.class, "id");
		xstream.useAttributeFor(CreationSiteSpecification.class, "offset");
		xstream.useAttributeFor(CreationSiteSpecification.class, "loopInvariant");
		xstream.aliasAttribute(CreationSiteSpecification.class, "loopInvariant", "loop-invariant");
		xstream.aliasAttribute(CreationSiteSpecification.class, "captureAllPartitions", "capture");
		xstream.aliasAttribute(CreationSiteSpecification.class, "implementation", "impl");
		xstream.aliasAttribute(CreationSiteSpecification.class, "operator", "op");
	}
	
	public Specification read(Reader reader) {
		return (Specification) xstream.fromXML(reader);
	}
}
