package ar.uba.dc.analysis.automaticinvariants.regions;

import java.util.Set;
import java.util.HashSet;

/**
 * User: JP
 * Date: 25/08/2004
 * Time: 21:11:35
 */
public class CallSite{
    private String claseNormalizada;
    private String clase;
    private String metodo;
    private int linea;
    private Set creationSites;

    public CallSite(String clase, String metodo, int linea){
        this.clase = clase;
        if (clase.endsWith(".java"))
            clase = clase.substring(0, clase.length() - 5);
        clase = clase.replaceAll("\\.", "_");
        this.metodo = metodo;
        this.linea = linea;
        this.claseNormalizada = clase;
        creationSites = new HashSet();
    }

    public void addCreationSite(CreationSiteInfo cs){
        creationSites.add(cs);
    }
    public Set getCreationSites(){
        return creationSites;
    }

    public String getClaseNormalizada() {
        return claseNormalizada;
    }

    public String getClase() {
        return clase;
    }

    public String getMetodo() {
        return metodo;
    }

    public int getLinea() {
        return linea;
    }

    public String toString(){
        String res = clase;
        res += "_" + linea;
        return res;
    }
}
