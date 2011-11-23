package ar.uba.dc.invariant.spec.reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.StringReader;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import ar.uba.dc.invariant.spec.bean.CallSiteSpecification;
import ar.uba.dc.invariant.spec.bean.ClassSpecification;
import ar.uba.dc.invariant.spec.bean.CreationSiteSpecification;
import ar.uba.dc.invariant.spec.bean.InvariantSpecification;
import ar.uba.dc.invariant.spec.bean.MethodSpecification;
import ar.uba.dc.invariant.spec.bean.Specification;

public class XStreamSpecReaderTest {
	
	@Test
	public void readerParseWhenIsNoClasses() {
		XStreamSpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><spec></spec>"));
		
		assertThat(spec, is(notNullValue()));
		assertThat(spec.getClasses().size(), is(equalTo(0)));
	}
	
	@Test
	public void readerSupportMultipleClassesPerFile() {
		XStreamSpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<spec>" +
							"<class decl=\"ar.uba.dc.simple.EjemploSimple\"></class>" +
							"<class decl=\"ar.uba.dc.simple.EjemploSimple2\"></class>" +
						"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		assertThat(spec.getClasses().size(), is(equalTo(2)));

		assertThat(spec.get("EjemploSimple"), is(nullValue()));
		assertThat(spec.get("ar.uba.dc.simple.EjemploSimple"), is(notNullValue()));
		assertThat(spec.get("ar.uba.dc.simple.EjemploSimple").getClassName(), is(equalTo("ar.uba.dc.simple.EjemploSimple")));
		
		assertThat(spec.get("EjemploSimple2"), is(nullValue()));
		assertThat(spec.get("ar.uba.dc.simple.EjemploSimple2"), is(notNullValue()));
		assertThat(spec.get("ar.uba.dc.simple.EjemploSimple2").getClassName(), is(equalTo("ar.uba.dc.simple.EjemploSimple2")));
	}
	
	@Test(expected=Exception.class)
	public void readerThrowsExceptionWhenIsNoSpec() {
		XStreamSpecReader reader = new XStreamSpecReader();
		
		reader.read(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
	}
	
	@Test
	public void readerSupportMethods() {
		XStreamSpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<spec>" +
							"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
								"<method decl=\"void main(java.lang.String[])\"></method>" +
								"<method decl=\"java.lang.Integer someMethod(int)\"></method>" +
							"</class>" +
						"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		ClassSpecification classSpec = spec.get("ar.uba.dc.simple.EjemploSimple");
		
		assertThat(classSpec, is(notNullValue()));
		assertThat(classSpec.getMethods().size(), is(equalTo(2)));
		
		assertThat(classSpec.getMethod("void main(java.lang.String[])"), is(notNullValue()));
		assertThat(classSpec.getMethod("void main(java.lang.String[])").getSignature(), is(equalTo("void main(java.lang.String[])")));
		
		assertThat(classSpec.getMethod("java.lang.Integer someMethod(int)"), is(notNullValue()));
		assertThat(classSpec.getMethod("java.lang.Integer someMethod(int)").getSignature(), is(equalTo("java.lang.Integer someMethod(int)")));
	}
	
	@Test
	public void readerSupportGlobalInvariantsForClasses() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"someInvariant\">" +
							"<constraints>a > 20</constraints>" +
						"</invariant>" +
						"<invariant>" +
							"<constraints>b > 20</constraints>" +
						"</invariant>" +
						"<method decl=\"void main(java.lang.String[])\"></method>" +
					"</class>" +
				"</spec>"));

		assertThat(spec, is(notNullValue()));
		
		ClassSpecification classSpec = spec.get("ar.uba.dc.simple.EjemploSimple");
		
		assertThat(classSpec, is(notNullValue()));
		assertThat(classSpec.getClassInvariants().size(), is(equalTo(2)));
		
		InvariantSpecification invSpec = classSpec.getClassInvariants().get(0);
		assertThat(invSpec.getId(), is(equalTo("someInvariant")));
		assertThat(invSpec.getConstraints(), is(equalTo("a > 20")));
		
		invSpec = classSpec.getClassInvariants().get(1);
		assertThat(invSpec.getId(), is(nullValue()));
		assertThat(invSpec.getConstraints(), is(equalTo("b > 20")));
		
		invSpec = classSpec.getClassInvariant("someInvariant");
		assertThat(invSpec.getId(), is(notNullValue()));
		assertThat(invSpec.getConstraints(), is(equalTo("a > 20")));
		
		assertThat(classSpec.getClassInvariant(""), is(nullValue()));
		assertThat(classSpec.getClassInvariant("aaa"), is(nullValue()));
		
		assertThat(classSpec.getMethod("void main(java.lang.String[])"), is(notNullValue()));
	}
	
	@Test
	public void parserSupportRelevantParametersForMethod() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"someInvariant\">" +
							"<constraints>a > 20</constraints>" +
						"</invariant>" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<relevant-parameters>k, n, args, args.size</relevant-parameters>" +
						"</method>" +
						"<method decl=\"java.lang.String main()\" />" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		
		assertThat(methodSpec.getParameters().size(), is(equalTo(4)));
		assertThat(methodSpec.getParameters(), hasItem("args"));
		assertThat(methodSpec.getParameters(), hasItem("k"));
		assertThat(methodSpec.getParameters(), hasItem("n"));
		assertThat(methodSpec.getParameters(), hasItem("args.size"));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("java.lang.String main()");
		
		assertThat(methodSpec.getParameters().size(), is(equalTo(0)));
	}
	
	@Test
	public void readerSupportRequirementsForMethods() {
		XStreamSpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
						"<spec>" +
							"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
								"<method decl=\"java.lang.Integer someMethod(int)\"></method>" +
								"<method decl=\"void main(java.lang.String[])\">" +
									"<requires></requires>" +
								"</method>" +
								"<method decl=\"java.lang.Integer someMethod(long)\">" +
									"<requires></requires>" +
									"<requires></requires>" +
								"</method>" +
								"<method decl=\"java.lang.Integer someMethod(float)\">" +
									"<relevant-parameters>a, b, size, java.lang.Integer.cte, java.lang.Integer.cte2</relevant-parameters>" +
									"<requires><![CDATA[a > 0 or b < 5]]></requires>" +
									"<requires><![CDATA[java.lang.Integer.cte >= java.lang.Integer.cte2 and size < 5]]></requires>" +
								"</method>" +
							"</class>" +
						"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		ClassSpecification classSpec = spec.get("ar.uba.dc.simple.EjemploSimple");
		
		assertThat(classSpec, is(notNullValue()));
		assertThat(classSpec.getMethods().size(), is(equalTo(4)));
		
		MethodSpecification methodSpec = classSpec.getMethod("java.lang.Integer someMethod(int)"); 
		
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("java.lang.Integer someMethod(int)")));
		assertThat(methodSpec.getRequirements().size(), is(equalTo(0)));
		
		methodSpec = classSpec.getMethod("void main(java.lang.String[])"); 
		
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("void main(java.lang.String[])")));
		assertThat(methodSpec.getRequirements().size(), is(equalTo(1)));
		assertThat(methodSpec.getRequirements().get(0), is(equalTo(StringUtils.EMPTY)));
		
		methodSpec = classSpec.getMethod("java.lang.Integer someMethod(long)"); 
		
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("java.lang.Integer someMethod(long)")));
		assertThat(methodSpec.getRequirements().size(), is(equalTo(2)));
		assertThat(methodSpec.getRequirements().get(0), is(equalTo(StringUtils.EMPTY)));
		assertThat(methodSpec.getRequirements().get(1), is(equalTo(StringUtils.EMPTY)));
		
		methodSpec = classSpec.getMethod("java.lang.Integer someMethod(float)"); 
		
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("java.lang.Integer someMethod(float)")));
		assertThat(methodSpec.getRequirements().size(), is(equalTo(2)));
		assertThat(methodSpec.getRequirements().get(0), is(equalTo("a > 0 or b < 5")));
		assertThat(methodSpec.getRequirements().get(1), is(equalTo("java.lang.Integer.cte >= java.lang.Integer.cte2 and size < 5")));
	}
	
	@Test
	public void parserSupportInvariantsForMethods() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"someInvariant\">" +
							"<constraints>a > 20</constraints>" +
						"</invariant>" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<relevant-parameters>k, n, args, args.size</relevant-parameters>" +
							"<invariant id=\"someInvariant\">" +
								"<constraints>a > 20</constraints>" +
							"</invariant>" +
							"<invariant id=\"other_invariant\">" +
								"<constraints>j == 30 and $t.size == 20</constraints>" +
							"</invariant>" +
							"<invariant>" +
								"<constraints>j == 40 and $t.size == 20</constraints>" +
							"</invariant>" +
						"</method>" +
						"<method decl=\"java.lang.String main()\">" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getInvariants().size(), is(equalTo(3)));
		
		InvariantSpecification invSpec = methodSpec.getInvariant("someInvariant");
		assertThat(invSpec.getId(), is(equalTo("someInvariant")));
		assertThat(invSpec.getConstraints(), is(equalTo("a > 20")));
		
		invSpec = methodSpec.getInvariant("other_invariant");
		assertThat(invSpec.getId(), is(equalTo("other_invariant")));
		assertThat(invSpec.getConstraints(), is(equalTo("j == 30 and $t.size == 20")));
		
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("java.lang.String main()");
		assertThat(methodSpec.getInvariants().size(), is(equalTo(0)));
	}
	
	@Test
	public void parserSupportCreationSitesForMethods() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<call-site offset=\"0\" srccode-offset=\"3\">" +
		      					"<constraints>@loop and $t.k == c</constraints>" +
		      				"</call-site>" +
		    		   		"<call-site id=\"unId\" offset=\"43-45\" srccode-offset=\"\">" +
		    		   			"<constraints>@loop and $t.j == c</constraints>" +
		    		   		"</call-site>" +
		    		   		"<call-site id=\"otroId\" offset=\"1\">" +
	    		   				"<constraints>@loop and $a == w</constraints>" +
	    		   			"</call-site>" +
	    		   			"<call-site id=\"unId\">" +
    		   					"<constraints>@loop and $t.n == c</constraints>" +
    		   				"</call-site>" +
						"</method>" +
						"<method decl=\"java.lang.String main()\">" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getCallSites().size(), is(equalTo(4)));
		
		CallSiteSpecification callSiteSpec = methodSpec.getCallSites().get(0);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("0")));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("@loop and $t.k == c")));
		
		callSiteSpec = methodSpec.getCallSites().get(1);
		assertThat(callSiteSpec.getId(), is(equalTo("unId")));
		assertThat(callSiteSpec.getOffset(), is(equalTo("43-45")));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("@loop and $t.j == c")));
		
		callSiteSpec = methodSpec.getCallSites().get(2);
		assertThat(callSiteSpec.getId(), is(equalTo("otroId")));
		assertThat(callSiteSpec.getOffset(), is(equalTo("1")));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("@loop and $a == w")));
		
		callSiteSpec = methodSpec.getCallSites().get(3);
		assertThat(callSiteSpec.getId(), is(equalTo("unId")));
		assertThat(callSiteSpec.getOffset(), is(nullValue()));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("@loop and $t.n == c")));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("java.lang.String main()");
		assertThat(methodSpec.getCallSites().size(), is(equalTo(0)));
	}
	
	@Test
	public void parserSupportCallSitesForMethods() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<creation-site offset=\"0\" srccode-offset=\"3\">" +
		      					"<constraints>@loop and $t.k == c</constraints>" +
		      				"</creation-site>" +
		    		   		"<creation-site id=\"unId\" offset=\"43-45\" srccode-offset=\"\">" +
		    		   			"<constraints>@loop and $t.j == c</constraints>" +
		    		   		"</creation-site>" +
		    		   		"<creation-site id=\"otroId\" offset=\"1\">" +
	    		   				"<constraints>@loop and $a == w</constraints>" +
	    		   			"</creation-site>" +
	    		   			"<creation-site id=\"unId\">" +
    		   					"<constraints>@loop and $t.n == c</constraints>" +
    		   				"</creation-site>" +
						"</method>" +
						"<method decl=\"java.lang.String main()\">" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getCreationSites().size(), is(equalTo(4)));
		
		CreationSiteSpecification creationSiteSpec = methodSpec.getCreationSites().get(0);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("0")));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("@loop and $t.k == c")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(1);
		assertThat(creationSiteSpec.getId(), is(equalTo("unId")));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("43-45")));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("@loop and $t.j == c")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(2);
		assertThat(creationSiteSpec.getId(), is(equalTo("otroId")));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("1")));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("@loop and $a == w")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(3);
		assertThat(creationSiteSpec.getId(), is(equalTo("unId")));
		assertThat(creationSiteSpec.getOffset(), is(nullValue()));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("@loop and $t.n == c")));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("java.lang.String main()");
		assertThat(methodSpec.getCreationSites().size(), is(equalTo(0)));
	}
	
	@Test
	public void dontGenerateParameterWithEmptyName() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
						"</method>" +
						"<method decl=\"void main()\">" +
							"<relevant-parameters></relevant-parameters>" +
						"</method>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>a,</relevant-parameters>" +
						"</method>" +
						"<method decl=\"void main(long)\">" +
							"<relevant-parameters>a,</relevant-parameters>" +
						"</method>" +
						"<method decl=\"void main(float)\">" +
							"<relevant-parameters>,a</relevant-parameters>" +
							"</method>" +
						"<method decl=\"void main(double)\">" +
							"<relevant-parameters>a,,b</relevant-parameters>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getParameters().size(), is(equalTo(0)));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main()");
		assertThat(methodSpec.getParameters().size(), is(equalTo(0)));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(int)");
		assertThat(methodSpec.getParameters().size(), is(equalTo(1)));
		assertThat(methodSpec.getParameters(), hasItem("a"));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(long)");
		assertThat(methodSpec.getParameters().size(), is(equalTo(1)));
		assertThat(methodSpec.getParameters(), hasItem("a"));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(float)");
		assertThat(methodSpec.getParameters().size(), is(equalTo(1)));
		assertThat(methodSpec.getParameters(), hasItem("a"));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(double)");
		assertThat(methodSpec.getParameters().size(), is(equalTo(2)));
		assertThat(methodSpec.getParameters(), hasItem("a"));
		assertThat(methodSpec.getParameters(), hasItem("b"));
	}
	
	@Test
	public void deleteSpacesInParametersDeclaration() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
						"</method>" +
						"<method decl=\"void main(int, char, double)\">" +
							"<relevant-parameters>a</relevant-parameters>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		List<MethodSpecification> methodSpecs = spec.get("ar.uba.dc.simple.EjemploSimple").getMethods();
		assertThat(methodSpecs.size(), is(equalTo(2)));
		assertThat(methodSpecs.get(0).getSignature(), is(equalTo("void main(java.lang.String[])")));
		assertThat(methodSpecs.get(1).getSignature(), is(equalTo("void main(int,char,double)")));
	}
	
	@Test
	public void deleteSpacesInInputForMethodSearch() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
						"</method>" +
						"<method decl=\"void main(int, char, double)\">" +
							"<relevant-parameters>a</relevant-parameters>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("void main(java.lang.String[])")));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(int, char, double)");
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("void main(int,char,double)")));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(int,char, double)");
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("void main(int,char,double)")));
		
		methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(int,char,double)");
		assertThat(methodSpec, is(notNullValue()));
		assertThat(methodSpec.getSignature(), is(equalTo("void main(int,char,double)")));
	}
	
	@Test
	public void supportLoopInvariantAttributeInCallAndCreationSites() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<creation-site offset=\"7\" loop-invariant=\"true\">" +
		      					"<constraints>$t.k == c</constraints>" +
		      				"</creation-site>" +
		      				"<creation-site offset=\"3\" loop-invariant=\"false\">" +
		      					"<constraints>$t.k == d</constraints>" +
		      				"</creation-site>" +
			      			"<creation-site offset=\"5\">" +
		      					"<constraints>$t.k == e</constraints>" +
		      				"</creation-site>" +
		      				"<call-site offset=\"1\" loop-invariant=\"true\">" +
		      					"<constraints>$t.j == w</constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"2\" loop-invariant=\"false\">" +
		      					"<constraints>$t.j == x</constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"3\">" +
		      					"<constraints>$t.j == y</constraints>" +
		      				"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getCreationSites().size(), is(equalTo(3)));
		assertThat(methodSpec.getCallSites().size(), is(equalTo(3)));
		
		CreationSiteSpecification creationSiteSpec = methodSpec.getCreationSites().get(0);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("7")));
		assertThat(creationSiteSpec.isLoopInvariant(), is(equalTo(true)));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == c")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(1);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("3")));
		assertThat(creationSiteSpec.isLoopInvariant(), is(equalTo(false)));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == d")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(2);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("5")));
		assertThat(creationSiteSpec.isLoopInvariant(), is(nullValue()));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == e")));
		
		CallSiteSpecification callSiteSpec = methodSpec.getCallSites().get(0);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("1")));
		assertThat(callSiteSpec.isLoopInvariant(), is(equalTo(true)));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == w")));
		
		callSiteSpec = methodSpec.getCallSites().get(1);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("2")));
		assertThat(callSiteSpec.isLoopInvariant(), is(equalTo(false)));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == x")));
		
		callSiteSpec = methodSpec.getCallSites().get(2);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("3")));
		assertThat(callSiteSpec.isLoopInvariant(), is(nullValue()));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == y")));
	}
	
	@Test
	public void supportCaptureAllAttributeInCallAndCreationSites() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<creation-site offset=\"7\" capture=\"true\">" +
		      					"<constraints>$t.k == c</constraints>" +
		      				"</creation-site>" +
		      				"<creation-site offset=\"3\" capture=\"false\">" +
		      					"<constraints>$t.k == d</constraints>" +
		      				"</creation-site>" +
			      			"<creation-site offset=\"5\">" +
		      					"<constraints>$t.k == e</constraints>" +
		      				"</creation-site>" +
		      				"<call-site offset=\"1\" capture=\"true\">" +
		      					"<constraints>$t.j == w</constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"2\" capture=\"false\">" +
		      					"<constraints>$t.j == x</constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"3\">" +
		      					"<constraints>$t.j == y</constraints>" +
		      				"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getCreationSites().size(), is(equalTo(3)));
		assertThat(methodSpec.getCallSites().size(), is(equalTo(3)));
		
		CreationSiteSpecification creationSiteSpec = methodSpec.getCreationSites().get(0);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("7")));
		assertThat(creationSiteSpec.getCaptureAllPartitions(), is(equalTo(true)));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == c")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(1);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("3")));
		assertThat(creationSiteSpec.getCaptureAllPartitions(), is(equalTo(false)));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == d")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(2);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("5")));
		assertThat(creationSiteSpec.getCaptureAllPartitions(), is(nullValue()));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == e")));
		
		CallSiteSpecification callSiteSpec = methodSpec.getCallSites().get(0);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("1")));
		assertThat(callSiteSpec.getCaptureAllPartitions(), is(equalTo(true)));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == w")));
		
		callSiteSpec = methodSpec.getCallSites().get(1);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("2")));
		assertThat(callSiteSpec.getCaptureAllPartitions(), is(equalTo(false)));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == x")));
		
		callSiteSpec = methodSpec.getCallSites().get(2);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("3")));
		assertThat(callSiteSpec.getCaptureAllPartitions(), is(nullValue()));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == y")));
	}
	
	@Test
	public void supportImplementationAttributeInCallAndCreationSites() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<creation-site offset=\"7\" impl=\"ar.uba.dc.simple.Ejemplo01\">" +
		      					"<constraints>$t.k == c</constraints>" +
		      				"</creation-site>" +
			      			"<creation-site offset=\"5\">" +
		      					"<constraints>$t.k == e</constraints>" +
		      				"</creation-site>" +
		      				"<call-site offset=\"1\" impl=\"ar.uba.dc.simple.Ejemplo01\">" +
		      					"<constraints>$t.j == w</constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"3\">" +
		      					"<constraints>$t.j == y</constraints>" +
		      				"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getCreationSites().size(), is(equalTo(2)));
		assertThat(methodSpec.getCallSites().size(), is(equalTo(2)));
		
		CreationSiteSpecification creationSiteSpec = methodSpec.getCreationSites().get(0);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("7")));
		assertThat(creationSiteSpec.forImplementation(), is(equalTo("ar.uba.dc.simple.Ejemplo01")));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == c")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(1);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("5")));
		assertThat(creationSiteSpec.forImplementation(), is(nullValue()));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == e")));
		
		CallSiteSpecification callSiteSpec = methodSpec.getCallSites().get(0);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("1")));
		assertThat(callSiteSpec.forImplementation(), is(equalTo("ar.uba.dc.simple.Ejemplo01")));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == w")));
		
		callSiteSpec = methodSpec.getCallSites().get(1);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("3")));
		assertThat(callSiteSpec.forImplementation(), is(nullValue()));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == y")));
	}
	
	@Test
	public void supportOperatorAttributeInCallAndCreationSites() {
		XStreamSpecReader reader = new XStreamSpecReader();
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<creation-site offset=\"7\" op=\"sum\">" +
		      					"<constraints>$t.k == c</constraints>" +
		      				"</creation-site>" +
			      			"<creation-site offset=\"5\">" +
		      					"<constraints>$t.k == e</constraints>" +
		      				"</creation-site>" +
		      				"<call-site offset=\"1\" op=\"max\">" +
		      					"<constraints>$t.j == w</constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"3\">" +
		      					"<constraints>$t.j == y</constraints>" +
		      				"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		assertThat(spec, is(notNullValue()));
		
		MethodSpecification methodSpec = spec.get("ar.uba.dc.simple.EjemploSimple").getMethod("void main(java.lang.String[])");
		assertThat(methodSpec.getCreationSites().size(), is(equalTo(2)));
		assertThat(methodSpec.getCallSites().size(), is(equalTo(2)));
		
		CreationSiteSpecification creationSiteSpec = methodSpec.getCreationSites().get(0);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("7")));
		assertThat(creationSiteSpec.forOperator(), is(equalTo("sum")));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == c")));
		
		creationSiteSpec = methodSpec.getCreationSites().get(1);
		assertThat(creationSiteSpec.getId(), is(nullValue()));
		assertThat(creationSiteSpec.getOffset(), is(equalTo("5")));
		assertThat(creationSiteSpec.forOperator(), is(nullValue()));
		assertThat(creationSiteSpec.getConstraints(), is(equalTo("$t.k == e")));
		
		CallSiteSpecification callSiteSpec = methodSpec.getCallSites().get(0);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("1")));
		assertThat(callSiteSpec.forOperator(), is(equalTo("max")));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == w")));
		
		callSiteSpec = methodSpec.getCallSites().get(1);
		assertThat(callSiteSpec.getId(), is(nullValue()));
		assertThat(callSiteSpec.getOffset(), is(equalTo("3")));
		assertThat(callSiteSpec.forOperator(), is(nullValue()));
		assertThat(callSiteSpec.getConstraints(), is(equalTo("$t.j == y")));
	}
}
