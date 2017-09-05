package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CCReader;
import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.CallSiteMapInfo;

public class CCReader{

	Map csMap;
	Map methodParamsMap;

	BufferedReader input;


	public CallSiteMapInfo getCallSiteInfo(String l)
	{
		return (CallSiteMapInfo)csMap.get(l);
	}
	public List getMethodParams(String m)
	{
		return (List)methodParamsMap.get(m);
	}

	String readLine() throws IOException
	{
		String res = "";
		res=input.readLine();
		return res;
	}
	public void analyze(String fileName) throws FileNotFoundException
	{
			FileReader fr = new FileReader(fileName);
			input = new BufferedReader (fr);
			analyzeFile();

	}

	void analyzeFile()
	{
		try {

			this.csMap = new HashMap();
			this.methodParamsMap = new HashMap();

			while(input.ready())
			{
				String res=readLine();
				// System.out.println(res);
				analyzeLine(res);
			}
		}
		catch (IOException e)
		{
		}
	}
	void analyzeLine(String line)
	{
		if(!line.startsWith("Call"))
		{
			parseInfo(line);
		}
		// else System.out.println(" -> Igore..");
	}
	void parseInfo(String line)
	{
		boolean ignore=true;

		String insSite=line.substring(0,line.indexOf("="));
		String res = line.substring(line.indexOf("=")+1);
		
		CallSiteMapInfo ccInfo = CallSiteMapInfo.fromString(res);
		
		csMap.put(insSite,ccInfo);
		// Diego: Uso los "init
		methodParamsMap.put(ccInfo.methodCallee, ccInfo.paramsInit);
		// System.out.println(insSite+"="+ccInfo);

	}
	
	public static void main(String args[]) {
		CCReader cc=new CCReader();
		try {
			cc.analyze(args[0]);
		}
		catch (Exception e)
		{
			System.out.println("error:"+e.toString());
		}
	}
}
