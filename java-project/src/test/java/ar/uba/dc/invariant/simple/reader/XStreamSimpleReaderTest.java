package ar.uba.dc.invariant.simple.reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.StringReader;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.spec.reader.XStreamSpecReader;
@Ignore

public class XStreamSimpleReaderTest {

	protected XStreamSimpleReader reader;
	
	@Before
	public void setUp() {
		reader = new XStreamSimpleReader();
	}
	
	@Test
	public void readerParseWhenIsNoClasses() {
		Map<String, DomainSet> dic = reader.read(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?><invariants></invariants>"));
		
		assertThat(dic, is(notNullValue()));
		assertThat(dic.size(), is(equalTo(0)));
	}
	
	@Test(expected=Exception.class)
	public void readerThrowsExceptionWhenIsNoInvariants() {
		XStreamSpecReader reader = new XStreamSpecReader();
		
		reader.read(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"));
	}
	
	@Test
	public void resultHasOnlyParsedInvariants() {
		Map<String, DomainSet> dic = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<invariants>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2</string>" +
						"<invariant>" +
							"<parameter>args0</parameter>" +
							"<variable>i</variable>" +
							"<variable>m</variable>" +
							"<constraints><![CDATA[1 <= i <= args0 and m == 10 + i]]></constraints>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-new-1</string>" +
						"<invariant>" +
							"<parameter>args0</parameter>" +
							"<variable>i</variable>" +
							"<variable>m</variable>" +
							"<constraints><![CDATA[1 <= i <= args0 and m == 10 + i]]></constraints>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void someName(int)-new-5</string>" +
						"<invariant>" +
							"<parameter>args0</parameter>" +
							"<variable>i</variable>" +
							"<variable>m</variable>" +
							"<constraints><![CDATA[1 <= i <= args0 and m == 10 + i]]></constraints>" +
						"</invariant>" +
					"</entry>" +
				"</invariants>"));
		
		assertThat(dic, is(notNullValue()));
		assertThat(dic.size(), is(equalTo(3)));
		assertThat(dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2"), is(notNullValue()));
		assertThat(dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-new-1"), is(notNullValue()));
		assertThat(dic.get("ar.uba.dc.simple.EjemploSimple: void someName(int)-new-5"), is(notNullValue()));
	}
	
	@Test
	public void resultHasOnlyParsedVariables() {
		Map<String, DomainSet> dic = reader.read(new StringReader(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<invariants>" +
				"<entry>" +
					"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0</string>" +
					"<invariant>" +
						"<variable>i</variable>" +
						"<variable>m</variable>" +
					"</invariant>" +
				"</entry>" +
				"<entry>" +
					"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1</string>" +
					"<invariant>" +
						"<variable>m</variable>" +
					"</invariant>" +
				"</entry>" +
				"<entry>" +
					"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2</string>" +
					"<invariant>" +
					"</invariant>" +
				"</entry>" +
			"</invariants>"));
		
		assertThat(dic, is(notNullValue()));
		assertThat(dic.size(), is(equalTo(3)));
		
		DomainSet inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0");
		assertThat(inv, is(notNullValue()));
		assertThat(inv.getVariables().size(), is(equalTo(2)));
		assertThat(inv.getVariables(), hasItem("i"));
		assertThat(inv.getVariables(), hasItem("m"));
		
		inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1");
		assertThat(inv, is(notNullValue()));
		assertThat(inv.getVariables().size(), is(equalTo(1)));
		assertThat(inv.getVariables(), hasItem("m"));
		
		inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2");
		assertThat(inv, is(notNullValue()));
		assertThat(inv.getVariables().size(), is(equalTo(0)));
	}
	
	@Test
	public void resultHasOnlyParsedParameters() {
		Map<String, DomainSet> dic = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<invariants>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0</string>" +
						"<invariant>" +
							"<parameter>i</parameter>" +
							"<parameter>m</parameter>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1</string>" +
						"<invariant>" +
							"<parameter>m</parameter>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2</string>" +
						"<invariant>" +
						"</invariant>" +
					"</entry>" +
				"</invariants>"));
			
			assertThat(dic, is(notNullValue()));
			assertThat(dic.size(), is(equalTo(3)));
			
			DomainSet inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getParameters().size(), is(equalTo(2)));
			assertThat(inv.getParameters(), hasItem("i"));
			assertThat(inv.getParameters(), hasItem("m"));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getParameters().size(), is(equalTo(1)));
			assertThat(inv.getParameters(), hasItem("m"));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getParameters().size(), is(equalTo(0)));
	}
	
	@Test
	public void resultHasOnlyParsedConstraints() {
		Map<String, DomainSet> dic = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<invariants>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0</string>" +
						"<invariant>" +
							"<constraints><![CDATA[i == 20]]></constraints>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1</string>" +
						"<invariant>" +
							"<constraints></constraints>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2</string>" +
						"<invariant></invariant>" +
					"</entry>" +
				"</invariants>"));
			
			assertThat(dic, is(notNullValue()));
			assertThat(dic.size(), is(equalTo(3)));
			
			DomainSet inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getConstraints(), is(equalTo("i == 20")));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getConstraints(), is(equalTo(StringUtils.EMPTY)));
	}
	
	@Test
	public void dontGenerateParameterWithEmptyName() {
		Map<String, DomainSet> dic = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<invariants>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0</string>" +
						"<invariant>" +
							"<parameter></parameter>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1</string>" +
						"<invariant>" +
							"<parameter />" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2</string>" +
						"<invariant>" +
						"</invariant>" +
					"</entry>" +
				"</invariants>"));
			
			assertThat(dic, is(notNullValue()));
			assertThat(dic.size(), is(equalTo(3)));
			
			DomainSet inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getParameters().size(), is(equalTo(0)));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getParameters().size(), is(equalTo(0)));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getParameters().size(), is(equalTo(0)));
	}
	
	@Test
	public void dontGenerateVariableWithEmptyName() {
		Map<String, DomainSet> dic = reader.read(new StringReader(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<invariants>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0</string>" +
						"<invariant>" +
							"<variable></variable>" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1</string>" +
						"<invariant>" +
							"<variable />" +
						"</invariant>" +
					"</entry>" +
					"<entry>" +
						"<string>ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2</string>" +
						"<invariant>" +
						"</invariant>" +
					"</entry>" +
				"</invariants>"));
			
			assertThat(dic, is(notNullValue()));
			assertThat(dic.size(), is(equalTo(3)));
			
			DomainSet inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-0");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getVariables().size(), is(equalTo(0)));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-1");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getVariables().size(), is(equalTo(0)));
			
			inv = dic.get("ar.uba.dc.simple.EjemploSimple: void main(java.lang.String[])-call-2");
			assertThat(inv, is(notNullValue()));
			assertThat(inv.getVariables().size(), is(equalTo(0)));
	}
}
