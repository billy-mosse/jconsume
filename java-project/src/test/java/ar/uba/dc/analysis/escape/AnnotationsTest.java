package ar.uba.dc.analysis.escape;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.uba.dc.analysis.common.MainRunner;
import ar.uba.dc.analysis.escape.graph.Edge;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.analysis.escape.graph.node.ParamNode;
import ar.uba.dc.analysis.escape.summary.io.reader.XMLReader;
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
	}
	
	
	@Test
	public void AnnotationsTest01() throws FileNotFoundException
	{
		//String[] args = new String[]{"--config", "config.properties", "--main", "void main(java.lang.String[])", "--ir", "--program", "ar.uba.dc.annotations.TestAnnotation01"};		
		//MainRunner.main(args);
		final Context context = ContextFactory.getContext("config.properties", false);
		
		//Llamo al escape analysis
		
		
		XMLReader reader = new XMLReader();
		
		//addOne
		String addOneFileName = basePath + "ar.uba.dc.annotations.TestAnnotation01/public_static_void_addOne(java.lang.Integer).xml";		
		EscapeSummary summaryAddOne = reader.read(new FileReader(addOneFileName));
		
		assertThat(summaryAddOne.getNodes().size(), is(equalTo(1)));		
		assertThat(summaryAddOne.ptg.getParameterNodes().size(), is(equalTo(1)));
		
		for(Node p : summaryAddOne.ptg.getParameterNodes())
		{
			assertThat(p.isOmega(), is(equalTo(true)));
			assertThat(!p.isFresh(), is(equalTo(true)));
			
			for(Edge e : summaryAddOne.ptg.edgesOutOf(p))
			{
				assertThat(e.getTarget().equals(p), is(equalTo(true)));
				assertThat(e.getField(), is(equalTo("?")));
			}
		}

		assertThat(summaryAddOne.ptg.getReturnedNodes().size(), is(equalTo(0)));
		
		
		//mainParameters
		
		
		String mainParametersFileName = basePath + "ar.uba.dc.annotations.TestAnnotation01/public_static_void_mainParameters(java.lang.Integer).xml";		
		EscapeSummary summaryMainParameters = reader.read(new FileReader(mainParametersFileName));
		
		assertThat(summaryMainParameters.getNodes().size(), is(equalTo(1)));		
		assertThat(summaryMainParameters.ptg.getParameterNodes().size(), is(equalTo(1)));
		
		for(Node p : summaryMainParameters.ptg.getParameterNodes())
		{
			assertThat(p.isOmega(), is(equalTo(true)));
			assertThat(!p.isFresh(), is(equalTo(true)));
			for(Edge e : summaryAddOne.ptg.edgesOutOf(p))
			{
				assertThat(e.getTarget().equals(p), is(equalTo(true)));
				assertThat(e.getField(), is(equalTo("?")));
			}
		}
		
		assertThat(summaryMainParameters.ptg.getReturnedNodes().size(), is(equalTo(0)));		
	}
}
