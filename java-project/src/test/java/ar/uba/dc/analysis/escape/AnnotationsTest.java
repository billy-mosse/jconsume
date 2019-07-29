package ar.uba.dc.analysis.escape;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.uba.dc.analysis.common.MainRunner;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.summary.io.reader.XMLReader;
import ar.uba.dc.analysis.memory.impl.runner.CommandLineValues;
import ar.uba.dc.analysis.memory.impl.runner.MainSootRunner;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.SootUtils;

public class AnnotationsTest {

	private XMLReader reader;
	
	//HACK
	private String basePath = "results/rinard/escape/"; 
	
	@BeforeClass
	public static void setUpClass() {
		Context ctx = ContextFactory.getContext("test.properties");
		SootUtils.setClasspath(ctx);
	}
	
	@Before
	public void setUp() {
		reader = new XMLReader();
		context = ContextFactory.getContext("config.properties", false);
	}
	
	Context context;
	public void runEscapeAnalysis(String programName)
	{
		String[] args = new String[]{"--config", "config.properties", "--main", "void main(java.lang.String[])", "--ir", "--program", programName};		
		CommandLineValues values = new CommandLineValues(args);
		//MainRunner.main(args);
		MainSootRunner.main(values, context);
	}
	
	@Ignore
	@Test
	public void AnnotationsTest01() throws FileNotFoundException
	{
		
		//Llamo al escape analysis
		
		runEscapeAnalysis("ar.uba.dc.annotations.TestAnnotation01");
		
		//addOne
		String addOneFileName = basePath + "ar.uba.dc.annotations.TestAnnotation01/public_static_void_mainParameters(java.lang.Integer).xml";		
		EscapeSummary summaryAddOne = reader.read(new FileReader(addOneFileName));
		
		assertThat(summaryAddOne.getNodes().size(), is(equalTo(1)));		
		assertThat(summaryAddOne.ptg.getParameterNodes().size(), is(equalTo(1)));
		
		
		ParamNode pAddOne = new ParamNode(0, true);
		assertThat(summaryAddOne.ptg.getParameterNodes().contains(pAddOne), is(equalTo(true)));
		Set<Edge> edgesFromP0AddOne  = summaryAddOne.ptg.edgesOutOf(pAddOne);
		
		assertThat(edgesFromP0AddOne.size(), is(equalTo(1)));
		assertThat(edgesFromP0AddOne.contains(new Edge(pAddOne, "?", pAddOne, true)), is(equalTo(true)));
		assertThat(summaryAddOne.ptg.getReturnedNodes().size(), is(equalTo(0)));
		
			
		
		
		//mainParameters
		
		String mainParametersFileName = basePath + "ar.uba.dc.annotations.TestAnnotation01/public_static_void_addOne(java.lang.Integer).xml";		
		EscapeSummary summaryMainParameters = reader.read(new FileReader(mainParametersFileName));
		
		assertThat(summaryMainParameters.getNodes().size(), is(equalTo(1)));		
		assertThat(summaryMainParameters.ptg.getParameterNodes().size(), is(equalTo(1)));
		
		
		ParamNode pMainParameters = new ParamNode(0, true);
		assertThat(summaryMainParameters.ptg.getParameterNodes().contains(pMainParameters), is(equalTo(true)));
		Set<Edge> edgesFromP0MainParameters  = summaryMainParameters.ptg.edgesOutOf(pMainParameters);
		
		assertThat(edgesFromP0MainParameters.size(), is(equalTo(1)));
		assertThat(edgesFromP0MainParameters.contains(new Edge(pMainParameters, "?", pMainParameters, true)), is(equalTo(true)));
		assertThat(summaryMainParameters.ptg.getReturnedNodes().size(), is(equalTo(0)));
				
	}
	
	@Ignore
	@Test
	public void AnnotationsTest02() throws FileNotFoundException
	{
		//String[] args = new String[]{"--config", "config.properties", "--main", "void main(java.lang.String[])", "--ir", "--program", "ar.uba.dc.annotations.TestAnnotation01"};		
		//MainRunner.main(args);
		
		runEscapeAnalysis("ar.uba.dc.annotations.TestAnnotation02");

		//Llamo al escape analysis
		
		
		XMLReader reader = new XMLReader();
		
		//addOne
		String addOneFileName = basePath + "ar.uba.dc.annotations.TestAnnotation02/public_static_void_addOne(java.lang.Integer,_java.lang.Integer).xml";		
		EscapeSummary summaryAddOne = reader.read(new FileReader(addOneFileName));
		
		
		assertThat(summaryAddOne.getNodes().size(), is(equalTo(2)));		
		assertThat(summaryAddOne.ptg.getParameterNodes().size(), is(equalTo(2)));
		

		ParamNode p0AddOne = new ParamNode(0, true);
		ParamNode p1AddOne = new ParamNode(1, true);

		assertThat(summaryAddOne.ptg.getParameterNodes().contains(p0AddOne), is(equalTo(true)));
		assertThat(summaryAddOne.ptg.getParameterNodes().contains(p1AddOne), is(equalTo(true)));

		Set<Edge> edgesFromP0AddOne  = summaryAddOne.ptg.edgesOutOf(p0AddOne);
		assertThat(edgesFromP0AddOne.size(), is(equalTo(2)));
		assertThat(edgesFromP0AddOne.contains(new Edge(p0AddOne, "?", p0AddOne, true)), is(equalTo(true)));
		assertThat(edgesFromP0AddOne.contains(new Edge(p0AddOne, "?", p1AddOne, true)), is(equalTo(true)));

		
		Set<Edge> edgesFromP1AddOne  = summaryAddOne.ptg.edgesOutOf(p1AddOne);
		assertThat(edgesFromP1AddOne.size(), is(equalTo(0)));

		assertThat(summaryAddOne.ptg.getReturnedNodes().size(), is(equalTo(0)));
		
		
		//mainParameters
		//Primero chequear a mano que tenga sentido el cambio.
		String mainParametersFileName = basePath + "ar.uba.dc.annotations.TestAnnotation02/public_static_void_mainParameters(java.lang.Integer,_java.lang.Integer).xml";		
		EscapeSummary summaryMainParameters = reader.read(new FileReader(mainParametersFileName));
		
		
		assertThat(summaryMainParameters.getNodes().size(), is(equalTo(2)));		
		assertThat(summaryMainParameters.ptg.getParameterNodes().size(), is(equalTo(2)));
		

		ParamNode p0MainParameters = new ParamNode(0, false);
		ParamNode p1MainParameters = new ParamNode(1, false);

		assertThat(summaryMainParameters.ptg.getParameterNodes().contains(p0MainParameters), is(equalTo(true)));
		assertThat(summaryMainParameters.ptg.getParameterNodes().contains(p1MainParameters), is(equalTo(true)));

		Set<Edge> edgesFromP0MainParameters  = summaryMainParameters.ptg.edgesOutOf(p0MainParameters);
		assertThat(edgesFromP0MainParameters.size(), is(equalTo(2)));
		assertThat(edgesFromP0MainParameters.contains(new Edge(p0MainParameters, "?", p0MainParameters, true)), is(equalTo(true)));
		assertThat(edgesFromP0MainParameters.contains(new Edge(p0MainParameters, "?", p1MainParameters, true)), is(equalTo(true)));

		
		Set<Edge> edgesFromP1MainParameters = summaryAddOne.ptg.edgesOutOf(p1MainParameters);
		assertThat(edgesFromP1AddOne.size(), is(equalTo(0)));

		assertThat(summaryMainParameters.ptg.getReturnedNodes().size(), is(equalTo(0)));

	
	
	}
}
