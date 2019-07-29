package ar.uba.dc.analysis.memory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java_cup.internal_error;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;
import ar.uba.dc.analysis.common.MainRunner;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootUtils;
import org.jsoup.nodes.Document;

import com.google.gson.JsonObject;
import com.sun.tools.example.debug.expr.ParseException;

public class PaperExamplesTest {		
	@Before
	public void setUp() {

	}
	
	
	
	public List<String> convertToList(JSONArray array)
	{
		List<String> ret = new ArrayList<String>();
		for(int i = 0; i < array.size(); i++)
		{
			ret.add((String)array.get(i));
		}
		return ret;
	}
	
	
	@Ignore
	@Test
	public void Program01()  {
		//String[] args = {"--config", "config.properties", "--main", "void main(java.lang.String[])", "--program", "ar.uba.dc.paper.Program1", "--ir", "--memory"};
		//MainRunner.main(args);
		//assertThat(true, is(equalTo(true)));
		
		
		try {
			Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","sh full_analysis.sh \"ar.uba.dc.paper.Program1 10\""});
			BufferedReader input =  
			        new BufferedReader  
			          (new InputStreamReader(p.getInputStream()));  
			      String line;
				while ((line = input.readLine()) != null) {  
			        System.out.println(line);  
			      }  
			      input.close();  
		    p.waitFor();
		    
		    JSONParser jsonParser = new JSONParser();
	         
		    
	       Map< String,SimpleMemoryResult> program1Map =  
                       new HashMap< String,SimpleMemoryResult>(); 
          SimpleMemoryResult program1Main = new SimpleMemoryResult();

          
          program1Main.parameters = "[args_init__f__size]";
          program1Main.residualPieces = Arrays.asList("0");
          program1Main.memorySummaryPieces = Arrays.asList("infty: args_init__f__size >= 0", "infty: args_init__f__size < 0");          
          program1Map.put("main", program1Main);

          SimpleMemoryResult program1MainParameters = new SimpleMemoryResult();

          program1MainParameters.parameters = "[r_init]";
          program1MainParameters.residualPieces = Arrays.asList("0");
          program1MainParameters.memorySummaryPieces = Arrays.asList("((1/2))*r_init + ((3/2))*r_init^2: r_init > 0");          
          program1Map.put("mainParameters", program1MainParameters);

          SimpleMemoryResult program1Line = new SimpleMemoryResult();

          program1Line.parameters = "[a_init__f__size, m_init]";
          program1Line.residualPieces = Arrays.asList("m_init: m_init > 0");
          program1Line.memorySummaryPieces = Arrays.asList("m_init: m_init > 0");          
          program1Map.put("line", program1Line);
          
          
          SimpleMemoryResult program1Triangle= new SimpleMemoryResult();

          program1Triangle.parameters = "[a_init__f__size, n_init]";
          program1Triangle.residualPieces = Arrays.asList("((1/2))*n_init + ((1/2))*n_init^2: n_init > 0");
          program1Triangle.memorySummaryPieces = Arrays.asList("((1/2))*n_init + ((1/2))*n_init^2: n_init > 0");          
          program1Map.put("triangle", program1Triangle);
          
          
          
          
		    
	        try (FileReader reader = new FileReader("results/rinard/historical_reports/report_ar.uba.dc.paper.Program1/report/report.json"))
	        {
	            //Read JSON file
	            Object obj = jsonParser.parse(reader);
	            JSONArray methods = (JSONArray) obj;
	            for(int i = 0; i < methods.size(); i++)
	            {	            	
	            	JSONObject method = (JSONObject) methods.get(i);
	            	if(((String)method.get("className")).equals("ar.uba.dc.paper.Program1"))
	            	{	            		
	            		JSONArray methodSummaries = (JSONArray) method.get("jsonMethodSummaries");
	            		for(int j = 0; j < methodSummaries.size(); j++)
	    	            {
	            			JSONObject methodSummary = (JSONObject) methodSummaries.get(j);
	            			String methodName = (String) methodSummary.get("methodName");
	            			
	            			String parameters = (String) methodSummary.get("parameters");
	            			List<String> residualPieces = convertToList((JSONArray)methodSummary.get("residualPieces"));
	            			List<String> memorySummaryPieces = convertToList((JSONArray)methodSummary.get("memorySummaryPieces"));
	            			
	            			
	            			SimpleMemoryResult simpleMemoryResult = program1Map.get(methodName);
	            			
	            			if(simpleMemoryResult != null)
	            			{
	            				assertThat(simpleMemoryResult.parameters, is(equalTo(parameters)));
		            			assertThat(simpleMemoryResult.residualPieces, is(equalTo(residualPieces)));
		            			assertThat(simpleMemoryResult.memorySummaryPieces, is(equalTo(memorySummaryPieces)));
	            			}
	            			
	    	            }
	            	}
            	}
	            
	            //Iterate over employee array
	 
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    }	
}

