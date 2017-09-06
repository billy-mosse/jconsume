/*
 * Created on Dec 28, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InductiveVariablesInfo;

/**
 * @author diegog
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class InductivesReader {

	Map csMap = new HashMap();;

	BufferedReader input;


	public InductiveVariablesInfo getiInfo(String l)
	{
		return (InductiveVariablesInfo)csMap.get(l);
	}
	
	
	String readLine() throws IOException
	{
		String res = "";
		res=input.readLine();
		return res;
	}
	public void analyze(String fileName) throws FileNotFoundException
	{
			input = new BufferedReader (new FileReader(fileName));
			analyzeFile();

	}

	void analyzeFile()
	{
		try {

			this.csMap = new HashMap();

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
		if(! ( line.startsWith("Inductives") || line.startsWith("--") || line.trim().isEmpty() ) )
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
		
		
		
		InductiveVariablesInfo IVInfo  = InductiveVariablesInfo.fromString(res); 
		
		csMap.put(normalizeInsSite(insSite),IVInfo);
		// System.out.println("Joos: "+ insSite+"="+IVInfo);

	}
	
	private String normalizeInsSite(String is)
	{
		String res = is;
		int pos_c = is.lastIndexOf("c");
		int pos_dash = is.lastIndexOf("_");
		
		int line = -1;
		int bc = -1;
		if(pos_c != -1 && pos_c > pos_dash)
		{
			line = Integer.parseInt(is.substring(pos_dash +1 , pos_c));
			bc = Integer.parseInt(is.substring(pos_c+1));
			res = is.substring(0,is.lastIndexOf("_")+1)+String.format("%05d",line)+"c"+String.format("%05d",bc);
		}
		else
		{
			line = Integer.parseInt(is.substring(pos_dash +1));
			res = is.substring(0,is.lastIndexOf("_")+1)+String.format("%05d",line);
		}
		
		
		return res;
	}
	
	
	public static void main(String[] args) {
	}
}
