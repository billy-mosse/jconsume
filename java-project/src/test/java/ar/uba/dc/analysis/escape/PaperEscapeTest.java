package ar.uba.dc.analysis.escape;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import ar.uba.dc.analysis.common.MainRunner;
import ar.uba.dc.analysis.memory.PaperInterproceduralAnalysis;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import uk.co.datumedge.hamcrest.json.SameJSONAs;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONObjectAs; 
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONArrayAs; 
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


//TODO
@Ignore
@RunWith(Theories.class)
@SuppressWarnings("unused")
public class PaperEscapeTest {

	//Hack feo
	String basePath = "/home/billy/Projects/git/jconsume/java-project/target/test-classes/escape/summary/";

	//TODO que al terminar destruya los archivos
	@Before
	public void setUp() {
		
	
	}
	
	  @Rule
	  public TemporaryFolder folder= new TemporaryFolder();
	
	
	public void testProgram(int i)
	{
		String[] args = {"--program", "ar.uba.dc.paper.Program" + i , "--config", "config.properties", "--main", "\"void main(java.lang.String[])\"", "--ir"};
		MainRunner.main(args);
		
		String loc = "/home/billy/Projects/git/jconsume/java-project/results/rinard/escape/json/ar.uba.dc.paper.Program" + i;
		
		List<File> files = new ArrayList<File>();
		
		listf(loc,files);
		
		ListIterator fileIt = files.listIterator();
		
		JSONArray o1 = new JSONArray();
		try
		{
			while (fileIt.hasNext()) {
				JSONParser parser  = new JSONParser();
				String fileEntry = (String) fileIt.next().toString();
				Object object = parser.parse(new FileReader(fileEntry));
				
		        String content = new String(Files.readAllBytes(Paths.get(fileEntry)));
		        //JSONObject object = new JSONObject(content);
		        
		        
				JSONObject jsonObject = (JSONObject) object;
				o1.put(object);
			}
			
		}
		catch(FileNotFoundException e)
		{
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String loc2 = "/home/billy/Projects/git/jconsume/java-project/target/test-classes/escape/summary/json/ar.uba.dc.paper.Program" + i;
		
		List<File> files2 = new ArrayList<File>();
		
		listf(loc2,files2);
		
		ListIterator fileIt2 = files2.listIterator();
		
		JSONArray  o2 = new JSONArray();
		
		try
		{
			while (fileIt2.hasNext()) {
				JSONParser parser  = new JSONParser();
				File fileEntry = (File) fileIt2.next();
				Object object = parser.parse(new FileReader(fileEntry));
				JSONObject jsonObject = (JSONObject) object;
				o2.put(jsonObject);
			}
			
		}
		catch(FileNotFoundException e)
		{
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//assertThat(o1, is(sameJSONObjectAs(o2)));
		//assertTrue(b);
		
		assertEquals(o1.toString(),o2.toString());
		
	}
	
	
	
	
	
	@Test
	public void Program1() {
		
		//testProgram_i(1);
		
		/*final Context context = ContextFactory.getContext("config.properties", false);
		PaperInterproceduralAnalysis aPaperMemoryAnalysis = context.getFactory().getPaperMemoryAnalysis();

		aPaperMemoryAnalysis.setMainClass("ar.uba.dc.paper.Program1");
		aPaperMemoryAnalysis.getIrMethods("/home/billy/Projects/git/jconsume/java-project/target/test-classes/escape/");
		Map<String, IntermediateRepresentationMethod> methods1 = aPaperMemoryAnalysis.getMethods();
		
		
		PaperInterproceduralAnalysis iAna2 = new PaperInterproceduralAnalysis();
		aPaperMemoryAnalysis.setMainClass("ar.uba.dc.paper.Program1");
		aPaperMemoryAnalysis.getIrMethods("/home/billy/Projects/git/jconsume/java-project/results/rinard/escape/");
		Map<String, IntermediateRepresentationMethod> methods2 = aPaperMemoryAnalysis.getMethods();
		
		
		//assertTrue(CollectionUtils.isEqualCollection(methods1.values(), methods2.values()));
		
		System.out.println(methods1);
		System.out.println(methods2);*/
		
	
		//assertThat(o1, is(sameJSONObjectAs(o2)));
		//boolean b = o1.sameJSONArrayAs(o2);
		//assertTrue(b);
		
		
		/*assertThat(o1,
				SameJSONAs.sameJSONArrayAs(o2)
		                  /*.allowingExtraUnexpectedFields()*/
		//                  .allowingAnyArrayOrdering());
	}

	@Test
	public void Program2() {
		
		//testProgram_i(2);
	}
	
	@Test
	public void Program3() {
		
		//testProgram_i(3);
	}
	
	@Test
	public void Program4() {
		
		testProgram(4);
	}
	
	@Test
	public void Program5() {
		
		//testProgram_i(5);
	}
	
	@Test
	public void Program6() {
		
		//testProgram_i(6);
	}
				
	public void listf(String directoryPath, List<File> files) {

		File directory = new File(directoryPath);
	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
	}

}
