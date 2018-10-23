package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CCReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CSReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CallSiteMapInfo;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CreationSiteMapInfo;
import ar.uba.dc.analysis.common.intermediate_representation.IntermediateRepresentationMethod;


public class CSReader{

	Map creationSiteMap;
	Map methodsMap;
	Map varsMap;
	BufferedReader input;
	SortedSet methods; 

	
	public CreationSiteMapInfo getCS(String l)
	{
		return (CreationSiteMapInfo)creationSiteMap.get(l);
	}
	public List getVars(String l)
	{
		return (List)varsMap.get(l);
	}
	public Set getMethods()
	{
		return methodsMap.keySet();
	}
	// Return only the creations sites of the method
	public Set getCSs(String m)
	{
		Set CSs = null; 
		Set allISs =(Set)methodsMap.get(m);
		if(allISs!=null)
		{
			CSs = new HashSet();
			for (Iterator iter = allISs.iterator(); iter.hasNext();) {
				String sCS=(String)iter.next();
				CreationSiteMapInfo cs = getCS(sCS);
				if(cs.getType().equals("CS"))
					CSs.add(cs);
			}
		}
		return CSs;
	}
	
	public Set getCSs(String m, CCReader ccr)
	{
		Set CSs = null; 
		Set allISs =(Set)methodsMap.get(m);
		if(allISs!=null)
		{
			CSs = new HashSet();
			for (Iterator iter = allISs.iterator(); iter.hasNext();) {
				String sCS=(String)iter.next();
				CreationSiteMapInfo cs = getCS(sCS);
				if(cs.getType().equals("CS"))
					CSs.add(cs);
				if(cs.getType().equals("CC"))
				{
					CallSiteMapInfo  ccInfo = ccr.getCallSiteInfo(sCS);
					if(ccInfo!=null) // && ccInfo.methodCallee.indexOf("add(")!=-1)
						CSs.add(cs);
				}
			}
		}
		return CSs;
	}
	
	
	
	String readLine() throws IOException
	{
		String res = "";
		res=input.readLine();
		return res;
	}
	
	protected JsonReader jsonReader;
	
	public void analyze(String fileName) throws FileNotFoundException
	{
		//	input = new BufferedReader (new FileReader(fileName));
			
			
			
			jsonReader = new JsonReader(new FileReader(fileName));
			
			jsonReader.setLenient(true);			
			
			analyzeFile();

	}

	void analyzeFile()
	{
		try {

			this.creationSiteMap = new HashMap();
			// this.methodsMap = (Map) new HashMap();
			this.methodsMap = new TreeMap();
			this.varsMap = new HashMap();
			
			JsonInstrumentationSiteReader reader = new JsonInstrumentationSiteReader();
			
			while(jsonReader.hasNext() && !jsonReader.peek().equals(JsonToken.END_DOCUMENT))
			{
				CreationSiteMapInfo csInfo = reader.read(jsonReader);
				String insSite = csInfo.getInsSite();
				
				creationSiteMap.put(insSite,csInfo);
				
				String className = insSite.substring(0,insSite.lastIndexOf("_"));
				
				Set mSet = (Set)methodsMap.get(className +"_"+csInfo.getMethod()); 
				if (mSet==null)
					mSet = new HashSet();
				mSet.add(insSite);
				
				methodsMap.put(className +"_"+ csInfo.getMethod(),mSet);
				
			}
			

			
			/*while(input.ready())
			{
				String res=readLine();
				// System.out.println(res);
				analyzeLine(res);
			}*/
		}
		catch (IOException e)
		{
		}
	}
	void analyzeLine(String line)
	{
		if(!line.startsWith("Instrumentation ") && !line.startsWith("--"))
		{
			parseInfo(line);
		}
		// else System.out.println(" -> Igore..");
	}
	void parseInfo(String line)
	{
		boolean ignore=true;
		
		//String insSite=line.substring(0,line.indexOf("="));
		//String res = line.substring(line.indexOf("=")+1);
		
		CreationSiteMapInfo csInfo = CreationSiteMapInfo.fromString(line);
		String insSite = csInfo.getInsSite();
				
		creationSiteMap.put(insSite,csInfo);
		
		String className = insSite.substring(0,insSite.lastIndexOf("_"));
		
		Set mSet = (Set)methodsMap.get(className +"_"+csInfo.getMethod()); 
		if (mSet==null)
			mSet = new HashSet();
		mSet.add(insSite);
		
		methodsMap.put(className +"_"+ csInfo.getMethod(),mSet);
		// System.out.println("Jose:"+ className +"_"+ csInfo.getMethod());
		
		
	}
	
	public static void main(String args[]) {
		CSReader ir=new CSReader();
		try {
			ir.analyze("EjemploCC.cs");
		}
		catch (Exception e)
		{
			System.out.println("error:"+e.toString());
		}


	}
}
