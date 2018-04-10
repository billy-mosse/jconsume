package ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ar.uba.dc.analysis.automaticinvariants.instrumentation.daikon.InvariantReader;

public class InvariantReader {

	Map creationSiteMap;

	Map callSiteMap;

	BufferedReader input;
	
	boolean dobleEqual = false;
	
	public InvariantReader(boolean dobleEqual)
	{
		this.dobleEqual = dobleEqual;
	}

	public InvariantReader() {
		// TODO Auto-generated constructor stub
	}

	public String getCallSiteInv(String l) {
		List invs = (List) callSiteMap.get(l);
		if(invs!=null)
			return toStringList(invs);
		return null;
		// return (String) callSiteMap.get(l);
	}

	public Set getCreationsSites() {
		return creationSiteMap.keySet();
	}

	public String getCreationSiteInv(String l) {
		List invs = (List) creationSiteMap.get(l);
		// Diego
		if(invs==null)
			invs = (List) callSiteMap.get(l);
		//
		if(invs!=null)
			return toStringList(invs);
		return null;
		// return (String) creationSiteMap.get(l);
	}

	String readLine() throws IOException {
		String res = "";
		res = input.readLine();
		return res;
	}

	public void analyze(String fileName) throws FileNotFoundException {
		input = new BufferedReader(new FileReader(fileName));
		analyzeFile();

	}
	private String toStringList(List l)
	{
		String res="";
		for (Iterator iter = l.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			
			
				//quiero machear var1[var2]...
		        String patternString = ".+\\[(?!\\[).+\\].*";

		        Pattern pattern = Pattern.compile(patternString);

		        Matcher matcher = pattern.matcher(element);
		        boolean matchesAccessArray = matcher.matches();
		        
			
			//Otro hack horrible: no nos interesan constraints que:
			if(element.contains("has") 
					|| element.contains("\"") //sean sobre un string
					|| element.contains("elementwise") //sean sobre un array
					|| element.contains("contains") //sobre contencion de un vector
					|| element.contains("lexically") //sobre contencion de un vector
					|| element.contains(" elements") //sobre cantidad de elementos de un vector
					|| element.contains(" in ") //sobre pertenencia
					|| element.contains(" reverse ") //sobre array
					|| element.contains(".getClass()") //sobre clase de objeto
					|| element.contains(" one of ") //otro hack que se podria evitar
					|| element.contains("== []") //hack horrible. TODO: ver como hacer para deshabilitar igualdades de arrays!!
					|| element.contains(" null") //sobre nulls. Deberia usar una opcion de daikon pero no la encontre
					|| element.contains("9999999") //Hack temporal para MST
					|| element.contains("sorted by")
					//|| (element.contains("[") && element.contains("]")) //accediendo a un array
					|| matchesAccessArray
					|| containsDouble(element)
					)
			{
				continue;
			}
			
			res+=", ";
			res+=element;
		}
		return res;
	}
	
	
	// "4.234 = A.f" tiene un double
	// "A.f2.f3 = 5" no tiene un double
	public static boolean containsDouble(String element)
	{
		char c;
		for(int i = element.length()-2; i >= 1; i--){
			c = element.charAt(i);
			if(c =='.')
			{
				if(Character.isDigit(element.charAt(i+1)) && (Character.isDigit(element.charAt(i-1))))
					return true;
			}
		}
		
		return false;
	}
	

	void analyzeFile() {
		try {

			this.creationSiteMap = new HashMap();
			this.callSiteMap = new HashMap();

			while (input.ready()) {
				String res = readLine();
				//System.out.println(res);
				analyzeLine(res);
			}
		} catch (IOException e) {
		}
	}

	void analyzeLine(String line) {
		if (!line.startsWith("==") &&  !line.startsWith("--") ) {
			parseInfo(line);
		}
		// else System.out.println(" -> Igore..");
	}

	void parseInfo(String line) {
		List invariant = new Vector();
		boolean ignore = true;
		if (line.endsWith(":::ENTER"))
			ignore = false;
//		if(line.length()==0)
//			return; 
		// String invariant = "";
		System.out.println(line);
		//InstrumentedMethod.ar_uba_dc_paper_Program1Test_CC_00005(java.lang.String, java.lang.String[]):::ENTER
		
		String[] splitee = line.split("\\(");
		int i = splitee[0].lastIndexOf(".");
		int j = line.indexOf("(");
		//String insSite = line.substring(line.lastIndexOf(".") + 1, line
				//.indexOf("("));
		String insSite = line.substring(i + 1, j);
				
				String tipo = "";
		if (!ignore) {
			// tipo = insSite.substring(insSite.indexOf("_") + 1);
			tipo = insSite.substring(insSite.lastIndexOf("_") -2 );
			
			if (!(tipo.indexOf("CS") != -1 || tipo.indexOf("CC") != -1)) {
				ignore = true;
			}
		}

		try {
			String res = readLine();
			while (input.ready() && !res.startsWith("==")) {
				if (!ignore && !res.startsWith("(!")  &&  !res.startsWith("--")  
					&& res.indexOf(">>")==-1 && res.indexOf("|")==-1
					&& res.indexOf("==>")==-1
					&& res.indexOf("!=")==-1
					&& res.indexOf(" & ")==-1
					&& res.indexOf("min(")==-1
					&& res.indexOf("max(")==-1
					&& res.indexOf("gcd(")==-1
					&& res.indexOf("is a bitwise")==-1
					&& !esModuloVariables(res))				
				{
					if(res.indexOf("/")!=-1)
					{
						res= adaptdivision(res);
					}
					else
					{
						if(!dobleEqual) res = replaceEqual(res);
						invariant.add(res);
					}
					// invariant = invariant + replaceEqual(res) + ", ";
				}
				res = readLine();
			}
			/*
			if (invariant.endsWith(", "))
				invariant = invariant.substring(0, invariant.length() - 2);
			*/

			// insSite=insSite.substring(0,insSite.indexOf("."))+insSite.substring(insSite.indexOf(".")+3);
			// ESTE ESTABA insSite = insSite.substring(0, insSite.indexOf("_")) + "_"+ insSite.substring(insSite.indexOf("_") + 4);
			insSite = insSite.substring(0, insSite.lastIndexOf("_")-3) + "_"+ insSite.substring(insSite.lastIndexOf("_")+1 );
			if (tipo.indexOf("CS") != -1) {
				//System.out.println("Creation Site:"+insSite);
				this.creationSiteMap.put(insSite, invariant);
			} else if (tipo.indexOf("CC") != -1) {
				//System.out.println("Call Site:"+insSite);

				this.callSiteMap.put(insSite, invariant);
			}
			// System.out.println(invariant);
		} catch (IOException e) {
		}

	}

	/**
	 * @param res
	 * @return
	 */
	private String adaptdivision(String res) {
		String resAdapted="";
		String op="";
		if(res.indexOf(">=")!=-1)
		{
			op=">=";
		}
		else if(res.indexOf("<=")!=-1)
		{
			op="<=";
		}
		else op="==";
		String[] terminos = res.split(op);
		String pd = terminos[1];
		String [] pdTerms = pd.trim().substring(1,pd.length()-2).split("/");
		resAdapted = terminos[0]+" * " + pdTerms[1] + " " + op + " " + pdTerms[0];
		return resAdapted;
	}

	/**
	 * @param res
	 * @return
	 */
	private boolean esModuloVariables(String res) {
		String res2 = res.replaceAll(" ","");
		if(res2.indexOf("%")!=-1)
		{
			String[] args = res2.split("%");
			return !Character.isDigit(args[1].charAt(0)); 
		}
		return false;
	}

	private static String replaceEqual(String conditionStr) {
		String res = "";
		int pos1 = 0;
		int pos2 = conditionStr.length();
		while (conditionStr.indexOf("==", pos1) != -1) {
			pos2 = conditionStr.indexOf("==", pos1);
			res = res + conditionStr.substring(pos1, pos2) + "= ";
			pos1 = pos2 + 2;
		}
		res = res + conditionStr.substring(pos1);
		return res.replace('$','_');
	}

	public static void main(String args[]) {
		InvariantReader ir = new InvariantReader();
		try {
			ir.analyze("inv.txt");
		} catch (Exception e) {
			System.out.println("error:" + e.toString());
		}

	}
}
