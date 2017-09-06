package ar.uba.dc.analysis.automaticinvariants.regions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;

/**
 * User: JP
 * Date: 30/08/2004
 * Time: 23:27:38
 */
public class RegionInfo {
    private String start = null;
    private String end = null;
    private String metodo = null;
    private String clase = null;
    private Set creationSites = null;
    private Map IsToCsMap = null;


    public RegionInfo(String nombre, String start, String end) {
        //nombre es de la forma: p.prueba.EjemploCC4.m2(int,p.prueba.RefO)
        int i = nombre.indexOf("(");
        String tem = nombre.substring(0, i);   //p.prueba.EjemploCC4.m2
        int j = tem.lastIndexOf(".");

        this.clase = tem.substring(0, j);
        this.metodo = nombre.substring(j + 1);
        this.start = start;
        this.end = end;
        creationSites = new HashSet();
        IsToCsMap = new HashMap();
    }

    public void addCreationSite(InstrumentationSite is, CreationSiteInfo cs){
    	// System.out.println("Entre!!"+is);
    	Set CSs = (Set) IsToCsMap.get(is);
    	if(CSs==null)
    	{
    		CSs = new HashSet();
    		IsToCsMap.put(is,CSs);
    	}
    	CSs.add(cs);
        creationSites.add(cs);
    }
    public void addCreationSite(CreationSiteInfo cs){
        creationSites.add(cs);
    }
    public Set getCreationSites(InstrumentationSite is){
        return (Set)IsToCsMap.get(is);
    }
    public Set getCreationSites(){
        return creationSites;
    }
    public Set getInstrumentationSites()
    {
    	return (Set)IsToCsMap.keySet();
    }
	
    public boolean constainsCreationSite(String clase, String metodo, int position)
    {
    	CreationSiteInfo csInfo = new CreationSiteInfo(clase, metodo, position);
    	return getCreationSites().contains(csInfo); 
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getClase() {
        return clase;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		
		return clase+"."+metodo+" "+getInstrumentationSites();
	}
}
