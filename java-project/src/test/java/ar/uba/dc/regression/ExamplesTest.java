package ar.uba.dc.regression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.options.Options;
import soot.toolkits.graph.Orderer;
import soot.toolkits.graph.PseudoTopologicalOrderer;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.ProgramInstrumentatorForDaikonMain;
import ar.uba.dc.analysis.common.SummaryRepository;
import ar.uba.dc.analysis.memory.InterproceduralAnalysis;
import ar.uba.dc.analysis.memory.IntraproceduralAnalysis;
import ar.uba.dc.analysis.memory.summary.MemorySummary;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.soot.DirectedCallGraph;
import ar.uba.dc.soot.SootUtils;

@Ignore
@SuppressWarnings("unused")
public class ExamplesTest {
	
	@Before
	public void setUp() {

	}
	
	@Test
	public void paperProgramsInvariants() {
		String programName = "ar.uba.dc.paper.Program1";
		String numberOfExecutions = "5";
		Map<String,Element> expectedOutputs;
		
		
		programName = "ar.uba.dc.paper.Program1"; 
		expectedOutputs = new HashMap();
		
		Element elem = new Element(Tag.valueOf("td"), "");
		elem.text("((1/2))*r_init + ((3/2))*r_init^2: r_init > 0");
			
		expectedOutputs.put("mainParameters", elem);
		testProgram(programName, expectedOutputs);
		

		programName = "ar.uba.dc.jolden.mst.MST"; 
		expectedOutputs = new HashMap();
		
		Element ul = new Element(Tag.valueOf("ul"), "");
		
		Element row1 = new Element(Tag.valueOf("li"), "");
		row1.text("max(6 + 3*pVertices_init + ((9/4))*pVertices_init^2, -1 + 4*pVertices_init + ((9/4))*pVertices_init^2): 4*floor([pVertices_init/4]) == pVertices_init && pVertices_init >= 4");
		row1.appendTo(ul);
		
		Element row2 = new Element(Tag.valueOf("li"), "");
		row2.text("max(6 + 3*pVertices_init + 2*pVertices_init^2, -1 + 4*pVertices_init + 2*pVertices_init^2): pVertices_init >= 4 && 4*floor([pVertices_init/4]) < pVertices_init");
		row2.appendTo(ul);
		
		Element row3 = new Element(Tag.valueOf("li"), "");
		row3.text("max(6 + 3*pVertices_init + 2*pVertices_init^2, -1 + 4*pVertices_init + 2*pVertices_init^2): 2 <= pVertices_init <= 3");
		row3.appendTo(ul);
		
		Element row4 = new Element(Tag.valueOf("li"), "");
		row4.text("9: pVertices_init == 1");
		row4.appendTo(ul);
		
		Element row5 = new Element(Tag.valueOf("li"), "");
		row5.text("6: pVertices_init <= 0");
		row5.appendTo(ul);
		
		elem = new Element(Tag.valueOf("td"), "");
		ul.appendTo(elem);		
		
		expectedOutputs.put("mainParameters", elem);
		
		testProgram(programName, expectedOutputs);
		
		
	}
	
	
	
	String numberOfExecutions = "10";
	String homeDir = System.getenv("HOME");
	void testProgram(String programName, Map expectedOutputs)
	{
		String[] cmd = { homeDir + "/Projects/git/jconsume/java-project/full_analysis.sh", programName, numberOfExecutions};
		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);
			
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}
					process.waitFor();
					String dir = homeDir + "/Projects/git/jconsume/java-project/results/rinard/report/index.html";
					
					String content = new Scanner(new File(dir)).useDelimiter("\\Z").next();
					
					Document doc = Jsoup.parse(content);
					Elements titles = doc.select("h4");
					for(Element title: titles)
					{
						Iterator it = expectedOutputs.entrySet().iterator();
					    while (it.hasNext()) {
					        Map.Entry pair = (Map.Entry)it.next();
							if (title.toString().contains(pair.getKey().toString()))
							{
								Element e1 = title.nextElementSibling();
								Element e2 = e1.child(0);
								Element e3 = e2.child(0).child(2).child(1);
								String val1 = e3.toString();
								String val2 = pair.getValue().toString();
								assertThat(val1, is(equalTo(val2)));
							}
						}
					}
		} catch (IOException e) {
			
			e.printStackTrace();
			assert(false);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
			assert(false);
		}
		
		
	}

}