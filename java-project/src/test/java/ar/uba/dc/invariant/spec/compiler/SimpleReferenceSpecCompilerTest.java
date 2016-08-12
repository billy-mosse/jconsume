package ar.uba.dc.invariant.spec.compiler;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import soot.SootMethod;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.code.impl.DefaultCallStatement;
import ar.uba.dc.analysis.common.code.impl.DefaultNewStatement;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.barvinok.expression.operation.impl.DefaultDomainUnifier;
import ar.uba.dc.invariant.spec.SpecReader;
import ar.uba.dc.invariant.spec.bean.Specification;
import ar.uba.dc.invariant.spec.compiler.constraints.parser.RegexpConstraintsParser;
import ar.uba.dc.invariant.spec.compiler.constraints.resolver.RegexpReferenceResolver;
import ar.uba.dc.invariant.spec.compiler.exceptions.DuplicateIdentifierException;
import ar.uba.dc.invariant.spec.compiler.exceptions.InvalidReferenceException;
import ar.uba.dc.invariant.spec.compiler.exceptions.SiteWithoutOffsetException;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnidentifiedInvariantException;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnknownInvariantException;
import ar.uba.dc.invariant.spec.compiler.exceptions.UnknownParameterException;
import ar.uba.dc.invariant.spec.reader.XStreamSpecReader;

public class SimpleReferenceSpecCompilerTest {

	private SimpleReferencesSpecCompiler compiler = null;
	
	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    @Before
    public void setUp() {
    	RegexpConstraintsParser parser = new RegexpConstraintsParser();
    	parser.setResolver(new RegexpReferenceResolver());
    	compiler = new SimpleReferencesSpecCompiler();
    	compiler.setParser(parser);
    	compiler.setResolver(new RegexpReferenceResolver());
    	compiler.setDomainUnifier(new DefaultDomainUnifier());
    }
	
    @Test(expected=IllegalArgumentException.class)
	public void throwExceptionWhenNullIsPassed() {
		new FastSpecCompiler().compile(null);
	}
    
    @Test(expected=UnknownInvariantException.class)
    public void checkUnknownReferencesInCreationSites() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<creation-site offset=\"1\">" +
			      				"<constraints><![CDATA[@loop_invariant and 1 <= $t.k <= m and @loop and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test
    public void expandRangeOffsetsInCreationSites() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
			      			"<call-site offset=\"6,8\">" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"10-10,15,16-17\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		DomainSet inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 0L));
		assertThat(inv.getParameters().isEmpty(), is(true));
		assertThat(inv.getVariables().isEmpty(), is(true));
		assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		
		for (Long i = 1L; i <= 4L; i++) {
			inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(2)));
			assertThat(inv.getVariables(), hasItem("$t.k"));
			assertThat(inv.getVariables(), hasItem("j"));
			assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and (j == args or j == 3)")));
		}
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 5L));
		assertThat(inv.getParameters().isEmpty(), is(true));
		assertThat(inv.getVariables().isEmpty(), is(true));
		assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		
		for (Long i : new Long[] {6L, 8L}) {
			inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(1)));
			assertThat(inv.getVariables(), hasItem("i"));
			assertThat(inv.getConstraints(), is(equalTo("i == 3*m and args > 1")));
		}
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 7L));
		assertThat(inv.getParameters().isEmpty(), is(true));
		assertThat(inv.getVariables().isEmpty(), is(true));
		assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		
		for (Long i : new Long[] {10L, 15L, 16L, 17L}) {
			inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(1)));
			assertThat(inv.getVariables(), hasItem("$t.values.size"));
			assertThat(inv.getConstraints(), is(equalTo("$t.values.size == args")));
		}
    }
    
    @Test
    public void returnUnionOfCreationsSitesForAnOffset() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<creation-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[(1 <= $t.k <= m and j == args) or (1 <= $t.k <= m and j == 3)]]></constraints>" +
			      			"</creation-site>" +
			      			"<creation-site offset=\"2,4,9\">" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</creation-site>" +
		      				"<creation-site offset=\"7,9\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	    }});
		
		for (Long i : new Long[] {1L, 3L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(2)));
			assertThat(inv.getVariables(), hasItem("$t.k"));
			assertThat(inv.getVariables(), hasItem("j"));
			assertThat(inv.getConstraints(), is(equalTo("(1 <= $t.k <= m and j == args) or (1 <= $t.k <= m and j == 3)")));
		}
		
		for (Long i : new Long[] {2L, 4L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(3)));
			assertThat(inv.getVariables(), hasItem("$t.k"));
			assertThat(inv.getVariables(), hasItem("j"));
			assertThat(inv.getVariables(), hasItem("i"));
			assertThat(inv.getConstraints(), is(equalTo("(1 <= $t.k <= m and j == args and i == 3*m and args > 1) or (1 <= $t.k <= m and j == 3 and i == 3*m and args > 1)")));
		}
		
		for (Long i : new Long[] {7L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(1)));
			assertThat(inv.getVariables(), hasItem("$t.values.size"));
			assertThat(inv.getConstraints(), is(equalTo("$t.values.size == args")));
		}
		
		for (Long i : new Long[] {9L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(2)));
			assertThat(inv.getVariables(), hasItem("$t.values.size"));
			assertThat(inv.getVariables(), hasItem("i"));
			assertThat(inv.getConstraints(), is(equalTo("i == 3*m and args > 1 and $t.values.size == args")));
		}
    }
    
    @Test
    public void replaceReferencesInCreationSites() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"class_invariant\">" +
							"<constraints><![CDATA[this.field > 0 and this.field < 25]]></constraints>" +
						"</invariant>" +
						"<method decl=\"void main(long)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0 <= i <= m]]></constraints>" +
							"</invariant>" +
							
							"<creation-site offset=\"0\">" +
			      				"<constraints><![CDATA[(@loop and 1 <= $t.k <= m) and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
						"</method>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0 <= i <= m]]></constraints>" +
							"</invariant>" +
							
							"<creation-site offset=\"0\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
						"</method>" +
						"<method decl=\"void main(double)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0 <= i <= m]]></constraints>" +
							"</invariant>" +
							
							"<creation-site offset=\"0\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
							
							"<creation-site offset=\"1\">" +
			      				"<constraints><![CDATA[@class_invariant and 1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +		
			      			
			      			"<creation-site offset=\"2\">" +
		      					"<constraints><![CDATA[1 <= $t.k <= m and @loop and (j == args or j == 3)]]></constraints>" +
		      				"</creation-site>" +
		      			
							"<creation-site offset=\"3\">" +
			      				"<constraints><![CDATA[(@class_invariant) and (1 <= $t.k <= m and @loop) and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod0 = context.mock(SootMethod.class, "method_0");
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod0).getSubSignature(); will(returnValue("void main(long)"));
	    }});
			
		DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod0, null, 0L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getVariables().size(), is(equalTo(3)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getVariables(), hasItem("i"));
		assertThat(inv.getConstraints(), is(equalTo("(0 <= i <= m and 1 <= $t.k <= m) and (j == args or j == 3)")));
		
		final SootMethod sootMethod = context.mock(SootMethod.class, "method_1");
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	    }});
			
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, 0L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and (j == args or j == 3)")));
		
		final SootMethod sootMethod2 = context.mock(SootMethod.class, "method_2");
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod2).getSubSignature(); will(returnValue("void main(double)"));
	    }});
		
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod2, null, 0L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and (j == args or j == 3)")));
		
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod2, null, 1L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getConstraints(), is(equalTo("this.field > 0 and this.field < 25 and 1 <= $t.k <= m and (j == args or j == 3)")));
		
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod2, null, 2L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(3)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getVariables(), hasItem("i"));
		assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and 0 <= i <= m and (j == args or j == 3)")));
		
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod2, null, 3L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(3)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getVariables(), hasItem("i"));
		assertThat(inv.getConstraints(), is(equalTo("(this.field > 0 and this.field < 25) and (1 <= $t.k <= m and 0 <= i <= m) and (j == args or j == 3)")));
    }
    
    @Test(expected=UnknownInvariantException.class)
    public void checkUnknownReferencesInCallSites() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<call-site offset=\"1\">" +
			      				"<constraints><![CDATA[@loop_invariant and 1 <= $t.k <= m and @loop and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test
    public void expandRangeOffsetsInCallSites() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<creation-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
			      			"<creation-site offset=\"6,8\">" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</creation-site>" +
		      				"<creation-site offset=\"10-10,15,16-17\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	    }});
		
		DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, 0L));
		assertThat(inv.getParameters().isEmpty(), is(true));
		assertThat(inv.getVariables().isEmpty(), is(true));
		assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		
		for (Long i = 1L; i <= 4L; i++) {
			inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(2)));
			assertThat(inv.getVariables(), hasItem("$t.k"));
			assertThat(inv.getVariables(), hasItem("j"));
			assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and (j == args or j == 3)")));
		}
		
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, 5L));
		assertThat(inv.getParameters().isEmpty(), is(true));
		assertThat(inv.getVariables().isEmpty(), is(true));
		assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		
		for (Long i : new Long[] {6L, 8L}) {
			inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(1)));
			assertThat(inv.getVariables(), hasItem("i"));
			assertThat(inv.getConstraints(), is(equalTo("i == 3*m and args > 1")));
		}
		
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, 7L));
		assertThat(inv.getParameters().isEmpty(), is(true));
		assertThat(inv.getVariables().isEmpty(), is(true));
		assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
		
		for (Long i : new Long[] {10L, 15L, 16L, 17L}) {
			inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, null, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(1)));
			assertThat(inv.getVariables(), hasItem("$t.values.size"));
			assertThat(inv.getConstraints(), is(equalTo("$t.values.size == args")));
		}
    }
    
    @Test
    public void returnUnionOfCallSitesForAnOffset() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[(1 <= $t.k <= m and j == args) or (1 <= $t.k <= m and j == 3)]]></constraints>" +
			      			"</call-site>" +
			      			"<call-site offset=\"2,4,9\">" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"7,9\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		for (Long i : new Long[] {1L, 3L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(2)));
			assertThat(inv.getVariables(), hasItem("$t.k"));
			assertThat(inv.getVariables(), hasItem("j"));
			assertThat(inv.getConstraints(), is(equalTo("(1 <= $t.k <= m and j == args) or (1 <= $t.k <= m and j == 3)")));
		}
		
		for (Long i : new Long[] {2L, 4L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(3)));
			assertThat(inv.getVariables(), hasItem("$t.k"));
			assertThat(inv.getVariables(), hasItem("j"));
			assertThat(inv.getVariables(), hasItem("i"));
			assertThat(inv.getConstraints(), is(equalTo("(1 <= $t.k <= m and j == args and i == 3*m and args > 1) or (1 <= $t.k <= m and j == 3 and i == 3*m and args > 1)")));
		}
		
		for (Long i : new Long[] {7L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(1)));
			assertThat(inv.getVariables(), hasItem("$t.values.size"));
			assertThat(inv.getConstraints(), is(equalTo("$t.values.size == args")));
		}
		
		for (Long i : new Long[] {9L}) {
			DomainSet inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, i));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("args"));
			assertThat(inv.getParameters(), hasItem("m"));
			assertThat(inv.getVariables().size(), is(equalTo(2)));
			assertThat(inv.getVariables(), hasItem("$t.values.size"));
			assertThat(inv.getVariables(), hasItem("i"));
			assertThat(inv.getConstraints(), is(equalTo("i == 3*m and args > 1 and $t.values.size == args")));
		}
    }
    
    @Test
    public void replaceReferencesInCallSites() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"class_invariant\">" +
							"<constraints><![CDATA[this.field > 0 and this.field < 25]]></constraints>" +
						"</invariant>" +
						"<method decl=\"void main(long)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0 <= i <= m]]></constraints>" +
							"</invariant>" +
							
							"<call-site offset=\"0\">" +
			      				"<constraints><![CDATA[(@loop and 1 <= $t.k <= m) and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0 <= i <= m]]></constraints>" +
							"</invariant>" +
							
							"<call-site offset=\"0\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
						"<method decl=\"void main(double)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0 <= i <= m]]></constraints>" +
							"</invariant>" +
							
							"<call-site offset=\"0\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
							
							"<call-site offset=\"1\">" +
			      				"<constraints><![CDATA[@class_invariant and 1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +		
			      			
			      			"<call-site offset=\"2\">" +
		      					"<constraints><![CDATA[1 <= $t.k <= m and @loop and (j == args or j == 3)]]></constraints>" +
		      				"</call-site>" +
		      			
							"<call-site offset=\"3\">" +
			      				"<constraints><![CDATA[(@class_invariant) and (1 <= $t.k <= m and @loop) and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod0 = context.mock(SootMethod.class, "method_0");
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod0).getSubSignature(); will(returnValue("void main(long)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
			
		DomainSet inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod0, stmt, 0L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getVariables().size(), is(equalTo(3)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getVariables(), hasItem("i"));
		assertThat(inv.getConstraints(), is(equalTo("(0 <= i <= m and 1 <= $t.k <= m) and (j == args or j == 3)")));
		
		final SootMethod sootMethod = context.mock(SootMethod.class, "method_1");
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
			
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 0L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and (j == args or j == 3)")));
		
		final SootMethod sootMethod2 = context.mock(SootMethod.class, "method_2");
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod2).getSubSignature(); will(returnValue("void main(double)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod2, stmt, 0L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and (j == args or j == 3)")));
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod2, stmt, 1L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getConstraints(), is(equalTo("this.field > 0 and this.field < 25 and 1 <= $t.k <= m and (j == args or j == 3)")));
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod2, stmt, 2L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(3)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getVariables(), hasItem("i"));
		assertThat(inv.getConstraints(), is(equalTo("1 <= $t.k <= m and 0 <= i <= m and (j == args or j == 3)")));
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod2, stmt, 3L));
		assertThat(inv.getParameters().size(), is(equalTo(3)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("this.field"));
		assertThat(inv.getVariables().size(), is(equalTo(3)));
		assertThat(inv.getVariables(), hasItem("$t.k"));
		assertThat(inv.getVariables(), hasItem("j"));
		assertThat(inv.getVariables(), hasItem("i"));
		assertThat(inv.getConstraints(), is(equalTo("(this.field > 0 and this.field < 25) and (1 <= $t.k <= m and 0 <= i <= m) and (j == args or j == 3)")));
    }
    
    @Test(expected=UnidentifiedInvariantException.class)
    public void throwExceptionIfClassHasUnidentifiedInvariants() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"class_inv\">" +
							"<constraints>this.field > 5</constraints>" +
						"</invariant>" +
						"<invariant>" +
							"<constraints>@class_inv</constraints>" +
						"</invariant>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<invariant id=\"\">" +
								"<constraints><![CDATA[i > 0]]></constraints>" +
							"</invariant>" +
							"<invariant>" +
								"<constraints><![CDATA[l < 5]]></constraints>" +
							"</invariant>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test(expected=UnidentifiedInvariantException.class)
    public void throwExceptionIfMethodHasUnidentifiedInvariants() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[i > 0]]></constraints>" +
							"</invariant>" +
							"<invariant>" +
								"<constraints><![CDATA[l < 5]]></constraints>" +
							"</invariant>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test
    public void checkUniqueIds() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"loop\">" +
							"<constraints><![CDATA[i > 0]]></constraints>" +
						"</invariant>" +
						"<invariant id=\"loop\">" +
							"<constraints><![CDATA[i < 0]]></constraints>" +
						"</invariant>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
					"</class>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple02\">" +
						"<invariant id=\"loop\">" +
							"<constraints><![CDATA[i > 0]]></constraints>" +
						"</invariant>" +
						"<invariant id=\"loop_2\">" +
							"<constraints><![CDATA[i < 0]]></constraints>" +
						"</invariant>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop_3\">" +
								"<constraints><![CDATA[i > 0]]></constraints>" +
							"</invariant>" +
							"<invariant id=\"loop_3\">" +
								"<constraints><![CDATA[i < 0]]></constraints>" +
							"</invariant>" +
							
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
					"</class>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple03\">" +
						"<invariant id=\"loop\">" +
							"<constraints><![CDATA[i > 0]]></constraints>" +
						"</invariant>" +
						"<invariant id=\"loop_2\">" +
							"<constraints><![CDATA[i < 0]]></constraints>" +
						"</invariant>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop_3\">" +
								"<constraints><![CDATA[i > 0]]></constraints>" +
							"</invariant>" +
							"<invariant id=\"loop_2\">" +
								"<constraints><![CDATA[i < 0]]></constraints>" +
							"</invariant>" +
							
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
					"</class>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple04\">" +
						"<invariant id=\"loop\">" +
							"<constraints><![CDATA[i > 0]]></constraints>" +
						"</invariant>" +
						"<invariant id=\"loop_2\">" +
							"<constraints><![CDATA[i < 0]]></constraints>" +
						"</invariant>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop_3\">" +
								"<constraints><![CDATA[i > 0]]></constraints>" +
							"</invariant>" +
							
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
						"<method decl=\"void main(double)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<invariant id=\"loop_3\">" +
								"<constraints><![CDATA[i > 0]]></constraints>" +
							"</invariant>" +
							
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		try {
			// NO puede haber ids repetidos entre los invariantes de clase
			compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
			fail("Deberia haber dado error");
		} catch (DuplicateIdentifierException e) {}
		
		try {
			// NO puede haber ids repetidos entre los invariantes de metodo
			compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple02"));
			fail("Deberia haber dado error");
		} catch (DuplicateIdentifierException e) {}
		
		try {
			// No puede haber invariantes repetidos entre los de clase y los de metodo
			compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple03"));
			fail("Deberia haber dado error");
		} catch (DuplicateIdentifierException e) {}
		
		// Pueden repetirse los Ids entre metodos distintos
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple04"));
    }
    
    @Test(expected=InvalidReferenceException.class)
    public void throwExceptionWhenClassInvariantHasReferences() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<invariant id=\"class_inv\">" +
							"<constraints>this.field > 5</constraints>" +
						"</invariant>" +
						"<invariant id=\"class_inv_2\">" +
							"<constraints>@class_inv</constraints>" +
						"</invariant>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<invariant id=\"loop\">" +
								"<constraints>@class_inv</constraints>" +
							"</invariant>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test(expected=InvalidReferenceException.class)
    public void throwExceptionWhenMethodInvariantHasReferences() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<invariant id=\"loop_2\">" +
								"<constraints>@loop</constraints>" +
							"</invariant>" +
							"<invariant id=\"loop\">" +
								"<constraints>m > 5</constraints>" +
							"</invariant>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test(expected=SiteWithoutOffsetException.class)
    public void throwsExceptionWhenCreationSiteDoesNotHaveOffset() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<creation-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
			      			"<creation-site>" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</creation-site>" +
		      				"<creation-site offset=\"10-10,15,16-17\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test(expected=SiteWithoutOffsetException.class)
    public void throwsExceptionWhenCreationSiteHasEmtpyOffset() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<creation-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</creation-site>" +
			      			"<creation-site offset=\"\">" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</creation-site>" +
		      				"<creation-site offset=\"10-10,15,16-17\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test(expected=SiteWithoutOffsetException.class)
    public void throwsExceptionWhenCallSiteDoesNotHaveOffset() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
			      			"<call-site>" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"10-10,15,16-17\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test(expected=SiteWithoutOffsetException.class)
    public void throwsExceptionWhenCallSiteHasEmtpyOffset() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 <= $t.k <= m and (j == args or j == 3)]]></constraints>" +
			      			"</call-site>" +
			      			"<call-site offset=\"\">" +
		      					"<constraints><![CDATA[i == 3*m and args > 1]]></constraints>" +
		      				"</call-site>" +
		      				"<call-site offset=\"10-10,15,16-17\">" +
	      						"<constraints><![CDATA[$t.values.size == args]]></constraints>" +
	      					"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test
    public void dontGenerateVariablesWithEmptyName() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<call-site offset=\"1-4\">" +
			      				"<constraints><![CDATA[1 == 1]]></constraints>" +
			      			"</call-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		DomainSet inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 1L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getVariables().size(), is(equalTo(0)));
		assertThat(inv.getConstraints(), is(equalTo("1 == 1")));
    }
    
    @Test(expected=UnknownParameterException.class)
    public void throwExceptionWhenRequirementsReferenceToUnknownParameter() {
    	SpecReader reader = new XStreamSpecReader();
    	
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires><![CDATA[m > 0]]></requires>" +
							"<requires><![CDATA[n <= 5]]></requires>" +
						"</method>" +
					"</class>" +
				"</spec>"));
    	
    	compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
    }
    
    @Test
    public void joinAllRequirementsInOneConstraint() {
    	SpecReader reader = new XStreamSpecReader();
		
		Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(java.lang.String[])\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
						"</method>" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires><![CDATA[m > 0 or m < -5]]></requires>" +
							"<requires><![CDATA[args == 5]]></requires>" +
						"</method>" +
						"<method decl=\"void main(long)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires><![CDATA[m > 0]]></requires>" +
							"<requires><![CDATA[args==5]]></requires>" +
						"</method>" +
						"<method decl=\"void main(float)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires><![CDATA[m > 0 and m < 5]]></requires>" +
						"</method>" +
						"<method decl=\"void main(double)\">" +
							"<relevant-parameters>args, m, n</relevant-parameters>" +
							"<requires><![CDATA[0 < m < 5 and n > 0]]></requires>" +
							"<requires><![CDATA[args == 5]]></requires>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(
	        	onConsecutiveCalls(
	        		returnValue("void main(java.lang.String[])"),
	        		returnValue("void main(int)"),
	        		returnValue("void main(long)"),
	        		returnValue("void main(float)"),
	        		returnValue("void main(double)")
	        	));
	    }});
		
		assertThat(classProvider.getRequirements(sootMethod), is(nullValue()));
		assertThat(classProvider.getRequirements(sootMethod).getConstraints(), is(equalTo("(m > 0 and args == 5) or (m < -5 and args == 5)")));
		assertThat(classProvider.getRequirements(sootMethod).getConstraints(), is(equalTo("m > 0 and args==5")));
		assertThat(classProvider.getRequirements(sootMethod).getConstraints(), is(equalTo("m > 0 and m < 5")));
		assertThat(classProvider.getRequirements(sootMethod).getConstraints(), is(equalTo("0 < m < 5 and n > 0 and args == 5")));
    }
 
    @Test
    public void addRequirementsToSpecifiedSites() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires><![CDATA[m > 0 or m < -5]]></requires>" +
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0<= $t.i <= args]]></constraints>" + 
							"</invariant>" +
							
							"<call-site offset=\"10-10,15,16-17\">" +
      							"<constraints><![CDATA[(@loop) and $t.values.size == args]]></constraints>" +
      						"</call-site>" +
      						
      						"<creation-site offset=\"10-10,15,16-17\">" +
	  							"<constraints><![CDATA[(@loop) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, stmt, 15L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.values.size"));
		assertThat(inv.getVariables(), hasItem("$t.i"));
		assertThat(inv.getConstraints(), is(equalTo("(m > 0 and (0<= $t.i <= args) and $t.values.size == args) or (m < -5 and (0<= $t.i <= args) and $t.values.size == args)")));
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 15L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("$t.values.size"));
		assertThat(inv.getVariables(), hasItem("$t.i"));
		assertThat(inv.getConstraints(), is(equalTo("(m > 0 and (0<= $t.i <= args) and $t.values.size == args) or (m < -5 and (0<= $t.i <= args) and $t.values.size == args)")));
    }
    
    @Test
    public void returnRequirementToSitesUnspecified() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires><![CDATA[m > 0 or m < -5]]></requires>" +
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0<= $t.i <= args]]></constraints>" + 
							"</invariant>" +
							
							"<call-site offset=\"10-10,15,16-17\">" +
      							"<constraints><![CDATA[(@loop) and $t.values.size == args]]></constraints>" +
      						"</call-site>" +
      						
      						"<creation-site offset=\"10-10,15,16-17\">" +
	  							"<constraints><![CDATA[(@loop) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		DomainSet inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, stmt, 2L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getVariables().size(), is(equalTo(0)));
		assertThat(inv.getConstraints(), is(equalTo("m > 0 or m < -5")));
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 2L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getVariables().size(), is(equalTo(0)));
		assertThat(inv.getConstraints(), is(equalTo("m > 0 or m < -5")));
		
		spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires><![CDATA[m > 0 or m < -5]]></requires>" +
							"<invariant id=\"loop\">" +
								"<constraints><![CDATA[0<= $t.i <= args]]></constraints>" + 
							"</invariant>" +
							
							"<call-site offset=\"10-10,15,16-17\">" +
      							"<constraints><![CDATA[(@loop) and $t.values.size == args]]></constraints>" +
      						"</call-site>" +
      						
      						"<creation-site offset=\"10-10,15,16-17\">" +
	  							"<constraints><![CDATA[(@loop) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
						"</method>" +
					"</class>" +
				"</spec>"));
		
		classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		inv = classProvider.getInvariant(new DefaultNewStatement(sootMethod, stmt, 2L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getVariables().size(), is(equalTo(0)));
		assertThat(inv.getConstraints(), is(equalTo("m > 0 or m < -5")));
		
		inv = classProvider.getInvariant(new DefaultCallStatement(sootMethod, stmt, 2L));
		assertThat(inv.getParameters().size(), is(equalTo(2)));
		assertThat(inv.getParameters(), hasItem("m"));
		assertThat(inv.getParameters(), hasItem("args"));
		assertThat(inv.getVariables().size(), is(equalTo(0)));
		assertThat(inv.getConstraints(), is(equalTo("m > 0 or m < -5")));
    }
    
    @Test
    public void compileLoopInvariantStatementsWithInvariants() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
														
							"<call-site offset=\"10-10,15,16-17\" loop-invariant=\"true\">" +
      							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
      						"</call-site>" +
      						
      						"<call-site offset=\"4,5-7\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</call-site>" +
      						
      						"<call-site offset=\"1\" loop-invariant=\"true\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</call-site>" +
	  						
	  						"<call-site offset=\"21-40\" loop-invariant=\"false\">" +
								"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
							"</call-site>" +
      						
      						"<creation-site offset=\"10-10,15,16-17\" loop-invariant=\"true\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"1\" loop-invariant=\"true\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"4,5-7\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"21-40\" loop-invariant=\"false\">" +
  								"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
  							"</creation-site>" +

						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		for (Long i : new Long[] { 1L, 10L, 15L, 17L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getVariables().size(), is(equalTo(2)));
				assertThat(inv.getVariables(), hasItem("$t.i"));
				assertThat(inv.getVariables(), hasItem("$t.values.size"));
				assertThat(inv.getConstraints(), is(equalTo("(0<= $t.i <= args) and $t.values.size == args")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 4L, 6L, 21L, 40L, 35L, 41L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				if (i <= 40L) {
					DomainSet inv = classProvider.getInvariant(s);
					assertThat(inv.getParameters().size(), is(equalTo(2)));
					assertThat(inv.getParameters(), hasItem("m"));
					assertThat(inv.getParameters(), hasItem("args"));
					assertThat(inv.getVariables().size(), is(equalTo(2)));
					assertThat(inv.getVariables(), hasItem("$t.i"));
					assertThat(inv.getVariables(), hasItem("$t.values.size"));
					assertThat(inv.getConstraints(), is(equalTo("(0<= $t.i <= args) and $t.values.size == args")));
				}
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(false)));
			}
		}
    }
    
    @Test
    public void emptyInvariantsAreNotProcessed() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<creation-site offset=\"100\">" +
								"<constraints />" +
							"</creation-site>" +
							
							"<call-site offset=\"100\">" +
								"<constraints />" +
							"</call-site>" +
							
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
    	
    	for (Long i : new Long[] { 100L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				if (i <= 40L) {
					DomainSet inv = classProvider.getInvariant(s);
					assertThat(inv.getParameters().size(), is(equalTo(2)));
					assertThat(inv.getParameters(), hasItem("args"));
					assertThat(inv.getParameters(), hasItem("m"));
					assertThat(inv.getVariables().size(), is(equalTo(0)));
					assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
				}
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(false)));
			}
		}
    }
    
    @Test
    public void loopInvariantIsAcumulative() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<call-site offset=\"3-6\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</call-site>" +
							
							"<call-site offset=\"5-12\" loop-invariant=\"true\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</call-site>" +
							
							"<call-site offset=\"10-17\" loop-invariant=\"false\">" +
      							"<constraints><![CDATA[args == 0]]></constraints>" +
      						"</call-site>" +
      						
      						"<call-site offset=\"15-20\">" +
	  							"<constraints><![CDATA[i == 6]]></constraints>" +
	  						"</call-site>" +
      						
	  						"<creation-site offset=\"3-6\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"5-12\" loop-invariant=\"true\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</creation-site>" +
							
							"<creation-site offset=\"10-17\" loop-invariant=\"false\">" +
	  							"<constraints><![CDATA[args == 0]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"15-20\">" +
	  							"<constraints><![CDATA[i == 6]]></constraints>" +
	  						"</creation-site>" +
      						
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		for (Long i : new Long[] { 1L, 25L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(0)));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 3L, 4L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 5L, 6L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5 and i == 5")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 7L, 8L, 9L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 10L, 11L, 12L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5 and args == 0")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 13L, 14L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo("args == 0")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 15L, 16L, 17L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("args == 0 and i == 6")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 18L, 19L, 20L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 6")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(false)));
			}
		}
    }
    
    @Test
    public void compileLoopInvariantStatementsWithoutInvariants() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<call-site offset=\"3\" loop-invariant=\"true\" />" +
							
							"<call-site offset=\"4\" loop-invariant=\"true\">" +
	  							"<constraints />" +
	  						"</call-site>" +
	  						
	  						"<creation-site offset=\"3\" loop-invariant=\"true\" />" +
							
							"<creation-site offset=\"4\" loop-invariant=\"true\">" +
	  							"<constraints />" +
	  						"</creation-site>" +
	  						
						"</method>" +
						"<method decl=\"void main(long)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires>m > 0</requires>" +
							
							"<call-site offset=\"3\" loop-invariant=\"true\" />" +
							
							"<call-site offset=\"4\" loop-invariant=\"true\">" +
	  							"<constraints />" +
	  						"</call-site>" +
	  						
	  						"<creation-site offset=\"3\" loop-invariant=\"true\" />" +
							
							"<creation-site offset=\"4\" loop-invariant=\"true\">" +
	  							"<constraints />" +
	  						"</creation-site>" +
	  						
						"</method>" +
					"</class>" +
				"</spec>"));
    	
    	CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethodInt = context.mock(SootMethod.class, "SootMethodInt");
		final SootMethod sootMethodLong = context.mock(SootMethod.class, "SootMethodLong");
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethodInt).getSubSignature(); will(returnValue("void main(int)"));
	        atLeast(1).of(sootMethodLong).getSubSignature(); will(returnValue("void main(long)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		for (Long i : new Long[] { 3L, 4L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethodInt, stmt, i), new DefaultCallStatement(sootMethodInt, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 3L, 4L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethodLong, stmt, i), new DefaultCallStatement(sootMethodLong, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo("m > 0")));
				assertThat(classProvider.isLoopInvariant(s), is(equalTo(true)));
			}
		}
    }
    
    @Test
    public void compileCaptureAllPartitionsWithInvariants() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
														
							"<call-site offset=\"10-10,15,16-17\" capture=\"true\">" +
      							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
      						"</call-site>" +
      						
      						"<call-site offset=\"4,5-7\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</call-site>" +
      						
      						"<call-site offset=\"1\" capture=\"true\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</call-site>" +
	  						
	  						"<call-site offset=\"21-40\" capture=\"false\">" +
								"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
							"</call-site>" +
      						
      						"<creation-site offset=\"10-10,15,16-17\" capture=\"true\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"1\" capture=\"true\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"4,5-7\">" +
	  							"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"21-40\" capture=\"false\">" +
  								"<constraints><![CDATA[(0<= $t.i <= args) and $t.values.size == args]]></constraints>" +
  							"</creation-site>" +

						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		for (Long i : new Long[] { 1L, 10L, 15L, 17L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getVariables().size(), is(equalTo(2)));
				assertThat(inv.getVariables(), hasItem("$t.i"));
				assertThat(inv.getVariables(), hasItem("$t.values.size"));
				assertThat(inv.getConstraints(), is(equalTo("(0<= $t.i <= args) and $t.values.size == args")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 4L, 6L, 21L, 40L, 35L, 41L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				if (i <= 40L) {
					DomainSet inv = classProvider.getInvariant(s);
					assertThat(inv.getParameters().size(), is(equalTo(2)));
					assertThat(inv.getParameters(), hasItem("m"));
					assertThat(inv.getParameters(), hasItem("args"));
					assertThat(inv.getVariables().size(), is(equalTo(2)));
					assertThat(inv.getVariables(), hasItem("$t.i"));
					assertThat(inv.getVariables(), hasItem("$t.values.size"));
					assertThat(inv.getConstraints(), is(equalTo("(0<= $t.i <= args) and $t.values.size == args")));
				}
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(false)));
			}
		}
    }
    
    @Test
    public void captureAllPartitionsIsAcumulative() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<call-site offset=\"3-6\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</call-site>" +
							
							"<call-site offset=\"5-12\" capture=\"true\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</call-site>" +
							
							"<call-site offset=\"10-17\" capture=\"false\">" +
      							"<constraints><![CDATA[args == 0]]></constraints>" +
      						"</call-site>" +
      						
      						"<call-site offset=\"15-20\">" +
	  							"<constraints><![CDATA[i == 6]]></constraints>" +
	  						"</call-site>" +
      						
	  						"<creation-site offset=\"3-6\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"5-12\" capture=\"true\">" +
	  							"<constraints><![CDATA[i == 5]]></constraints>" +
	  						"</creation-site>" +
							
							"<creation-site offset=\"10-17\" capture=\"false\">" +
	  							"<constraints><![CDATA[args == 0]]></constraints>" +
	  						"</creation-site>" +
	  						
	  						"<creation-site offset=\"15-20\">" +
	  							"<constraints><![CDATA[i == 6]]></constraints>" +
	  						"</creation-site>" +
      						
						"</method>" +
					"</class>" +
				"</spec>"));
		
		CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethod = context.mock(SootMethod.class);
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethod).getSubSignature(); will(returnValue("void main(int)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		for (Long i : new Long[] { 1L, 25L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(0)));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 3L, 4L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 5L, 6L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5 and i == 5")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 7L, 8L, 9L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 10L, 11L, 12L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 5 and args == 0")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 13L, 14L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo("args == 0")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 15L, 16L, 17L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("args == 0 and i == 6")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(false)));
			}
		}
		
		for (Long i : new Long[] { 18L, 19L, 20L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethod, stmt, i), new DefaultCallStatement(sootMethod, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(1)));
				assertThat(inv.getVariables(), hasItem("i"));
				assertThat(inv.getConstraints(), is(equalTo("i == 6")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(false)));
			}
		}
    }
    
    @Test
    public void compileCaptureAllPartitionsWithoutInvariants() {
    	SpecReader reader = new XStreamSpecReader();
		
    	Specification spec = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<spec>" +
					"<class decl=\"ar.uba.dc.simple.EjemploSimple\">" +
						"<method decl=\"void main(int)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							
							"<call-site offset=\"3\" capture=\"true\" />" +
							
							"<call-site offset=\"4\" capture=\"true\">" +
	  							"<constraints />" +
	  						"</call-site>" +
	  						
	  						"<creation-site offset=\"3\" capture=\"true\" />" +
							
							"<creation-site offset=\"4\" capture=\"true\">" +
	  							"<constraints />" +
	  						"</creation-site>" +
	  						
						"</method>" +
						"<method decl=\"void main(long)\">" +
							"<relevant-parameters>args, m</relevant-parameters>" +
							"<requires>m > 0</requires>" +
							
							"<call-site offset=\"3\" capture=\"true\" />" +
							
							"<call-site offset=\"4\" capture=\"true\">" +
	  							"<constraints />" +
	  						"</call-site>" +
	  						
	  						"<creation-site offset=\"3\" capture=\"true\" />" +
							
							"<creation-site offset=\"4\" capture=\"true\">" +
	  							"<constraints />" +
	  						"</creation-site>" +
	  						
						"</method>" +
					"</class>" +
				"</spec>"));
    	
    	CompiledClassInvariantProvider classProvider = compiler.compile(spec.get("ar.uba.dc.simple.EjemploSimple"));
		
		final SootMethod sootMethodInt = context.mock(SootMethod.class, "SootMethodInt");
		final SootMethod sootMethodLong = context.mock(SootMethod.class, "SootMethodLong");
		final Stmt stmt = context.mock(Stmt.class);
		final InvokeExpr invoke = context.mock(InvokeExpr.class);
		context.checking(new Expectations() {{
	        atLeast(1).of(sootMethodInt).getSubSignature(); will(returnValue("void main(int)"));
	        atLeast(1).of(sootMethodLong).getSubSignature(); will(returnValue("void main(long)"));
	        allowing(stmt).getInvokeExpr(); will(returnValue(invoke));
	        ignoring(invoke).getMethod();
	    }});
		
		for (Long i : new Long[] { 3L, 4L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethodInt, stmt, i), new DefaultCallStatement(sootMethodInt, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(true)));
			}
		}
		
		for (Long i : new Long[] { 3L, 4L }) {
			for (Statement s : new Statement[] { new DefaultNewStatement(sootMethodLong, stmt, i), new DefaultCallStatement(sootMethodLong, stmt, i) }) {
				DomainSet inv = classProvider.getInvariant(s);
				assertThat(inv.getParameters().size(), is(equalTo(2)));
				assertThat(inv.getParameters(), hasItem("args"));
				assertThat(inv.getParameters(), hasItem("m"));
				assertThat(inv.getVariables().size(), is(equalTo(0)));
				assertThat(inv.getConstraints(), is(equalTo("m > 0")));
				assertThat(classProvider.captureAllPartitions(s), is(equalTo(true)));
			}
		}
    }
}
