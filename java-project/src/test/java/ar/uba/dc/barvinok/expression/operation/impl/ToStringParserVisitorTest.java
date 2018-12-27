package ar.uba.dc.barvinok.expression.operation.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.nfunk.jep.ParseException;
@Ignore

public class ToStringParserVisitorTest {

	private JEP parser;
	
	@Before
	public void setUp() {
		parser = new JEP();
		parser.setAllowUndeclared(true);
		parser.addFunction("max", new MaxMathCommand());
	}
	
	@Test
	public void leaveVariablesUntouchIfMappingIsNull() throws ParseException {
		Node n = parser.parse("i > 0 && 4 == 4 && (j >= 0 || $t.size < 20)");
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), null);
		
		assertThat(result.getString(), is(equalTo("i > 0 && 4 == 4 && (j >= 0 || $t.size < 20)")));
	}

	@Test
	public void leaveVariablesUntouchIfMappingAsNoKeyForIt() throws ParseException {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("i", "arg.size");
		mapping.put("$t.size", "$t.values.size");
		Node n = parser.parse("i > 0 && cant == 4 && (j >= 0 || $t.size < 20)");
		
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("arg.size > 0 && cant == 4 && (j >= 0 || $t.values.size < 20)")));
		
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), new HashMap<String, String>());
		assertThat(result.getString(), is(equalTo("i > 0 && cant == 4 && (j >= 0 || $t.size < 20)")));
	}
	
	@Test
	public void touchVariablesIfHasEntryInTheMapping() throws ParseException {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("i", "arg.size");
		mapping.put("j", "arg.size");
		mapping.put("$t.size", "$t.values.size");
		Node n = parser.parse("i > 0 && cant == 4 && (j >= 0 || $t.size < 20)");
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		
		assertThat(result.getString(), is(equalTo("arg.size > 0 && cant == 4 && (arg.size >= 0 || $t.values.size < 20)")));
	}
	
	@Test
	public void applyFunctionAliasIfExists() throws ParseException {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("i", "arg.size");
		mapping.put("j", "arg.size");
		mapping.put("$t.size", "$t.values.size");
		Node n = parser.parse("i > 0 && cant == 4 && (j >= 0 || $t.size < 20)");
		ToStringParserVisitor visitor = new ToStringParserVisitor();
		visitor.addAliasFor("&&", "and");
		visitor.addAliasFor(">=", "<");
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(visitor, mapping);
		
		assertThat(result.getString(), is(equalTo("arg.size > 0 and cant == 4 and (arg.size < 0 || $t.values.size < 20)")));
	}
	
	public void onlyBinaryAndUnaryOperationAreSupported() throws ParseException {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("i", "arg.size");
		mapping.put("j", "arg.size");
		mapping.put("$t.size", "$t.values.size");
		Node n = parser.parse("i > 0 && cant == 4 && !(j >= 0 || $t.size < 20)");
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		
		assertThat(result.getString(), is(equalTo("arg.size > 0 && cant == 4 && !(arg.size >= 0 || $t.values.size < 20)")));
	}
	
	@Test
	public void binaryOperationAlwaysHaveSpacesAround() throws ParseException {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("i", "arg.size");
		mapping.put("j", "arg.size");
		mapping.put("$t.size", "$t.values.size");
		Node n = parser.parse("i>0 && (cant == 4)&&((j>=0)||($t.size < 20))");
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		
		assertThat(result.getString(), is(equalTo("arg.size > 0 && cant == 4 && (arg.size >= 0 || $t.values.size < 20)")));
	}
	
	@Test
	public void testParentheses() throws ParseException {
		Map<String, String> mapping = new HashMap<String, String>();
		
		Node n = parser.parse("((i>0) && (cant == 4)) && (j >= 0 || ($t.size < 20 || (i == i && i > 0)))");
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), new HashMap<String, String>());
		assertThat(result.getString(), is(equalTo("i > 0 && cant == 4 && (j >= 0 || $t.size < 20 || (i == i && i > 0))")));
		
		n = parser.parse("5/2*x^2+y");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(5/2)*x^2 + y")));
		
		n = parser.parse("2/3/5");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(2/3)/5")));
		
		n = parser.parse("2/(3/5)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("2/(3/5)")));
		
		n = parser.parse("(2/3)/5");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(2/3)/5")));
		
		n = parser.parse("3+(-5)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("3 + -5")));
		
		n = parser.parse("5/(2^3)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5/(2^3)")));
		
		n = parser.parse("5/(4+5)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5/(4 + 5)")));
		
		n = parser.parse("3>=i && i==(10+j^2)*2");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("3 >= i && i == (10 + j^2)*2")));
		
		n = parser.parse("2^3^2");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("2^(3^2)")));
		
		n = parser.parse("2^(3^2)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("2^(3^2)")));
		
		n = parser.parse("(2^3)^2");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(2^3)^2")));
		
		n = parser.parse("3-5+4");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("3 - 5 + 4")));
		
		n = parser.parse("3-(5+4)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("3 - (5 + 4)")));
		
		n = parser.parse("(3-5)+4");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("3 - 5 + 4")));
		
		n = parser.parse("((3-2)-(5-5))+4");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("3 - 2 - (5 - 5) + 4")));
		
		n = parser.parse("5/2%3");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(5/2)%3")));
		
		n = parser.parse("(5/2)%3");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(5/2)%3")));
		
		n = parser.parse("5/(2%3)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5/(2%3)")));
		
		n = parser.parse("5-(4+3)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5 - (4 + 3)")));
		
		n = parser.parse("-5-5");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("-5 - 5")));
		
		n = parser.parse("-(5-5)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("-(5 - 5)")));
		
		n = parser.parse("5%2%2");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(5%2)%2")));
		
		n = parser.parse("5%(2%2)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5%(2%2)")));
		
		n = parser.parse("(5%2)%2");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(5%2)%2")));
		
		n = parser.parse("--(3+4)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("-(-(3 + 4))")));
		
		n = parser.parse("5/2*3");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(5/2)*3")));
		
		n = parser.parse("(5/2)*3");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("(5/2)*3")));
		
		n = parser.parse("5/(2*3)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5/(2*3)")));
		
		n = parser.parse("5*2*3");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5*2*3")));
		
		n = parser.parse("5*(2/2)*3");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5*(2/2)*3")));
		
		n = parser.parse("5+3-3");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), mapping);
		assertThat(result.getString(), is(equalTo("5 + 3 - 3")));
	}
	
	@Test
	public void supporMaxFunction() throws ParseException {
		Node n = parser.parse("max(2,3)");
		ExpressionParsingResult result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), new HashMap<String, String>());
		assertThat(result.getString(), is(equalTo("max(2, 3)")));
		
		n = parser.parse("max(2*n,3+j^2,18)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), new HashMap<String, String>());
		assertThat(result.getString(), is(equalTo("max(2*n, 3 + j^2, 18)")));
		
		n = parser.parse("max(2*n, 3+j^2, -18)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), new HashMap<String, String>());
		assertThat(result.getString(), is(equalTo("max(2*n, 3 + j^2, -18)")));
		
		n = parser.parse("max(2*n, 3+j^2, (-18/7)*n-22)");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), new HashMap<String, String>());
		assertThat(result.getString(), is(equalTo("max(2*n, 3 + j^2, (-18/7)*n - 22)")));
		
		n = parser.parse("18-3*max(2*n, 3+j^2, (-18/7)*n-22)^2");
		result = (ExpressionParsingResult) n.jjtAccept(new ToStringParserVisitor(), new HashMap<String, String>());
		assertThat(result.getString(), is(equalTo("18 - 3*max(2*n, 3 + j^2, (-18/7)*n - 22)^2")));
	}
}
