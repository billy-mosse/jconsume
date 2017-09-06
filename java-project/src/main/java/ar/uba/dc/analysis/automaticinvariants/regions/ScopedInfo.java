package ar.uba.dc.analysis.automaticinvariants.regions;

import java.util.*;

/**
 * User: JP
 * Date: 09/07/2004
 * Time: 15:08:14
 */
public class ScopedInfo {

    private Map _regionesPorClase = new HashMap();
    private Map _regionesPorStmt = new HashMap();
    private List _callSites = new Vector();
    private List _regiones = new Vector();
    private String _claseMain = null;

    /**
     * Directorio ra�z de los fuentes
     */
    private String _srcPath = null;

    /**
     * Lista de fuentes
     */
    private Set _fuentes = new HashSet();

    
    /**
     * Por cada callSite (ie: invocaci�n a un m�todo) se guarda el n�mero de 
     * l�nea
     */
    private Map _numeroDeLineaPorCallSiteMap = new HashMap();
    
    /**
     * Identifica al fuente/bytecode/Jitter/etc al que corresponde
     * esta informaci�n
     */
    private String _id = null;

    public ScopedInfo(String id) {
        _id = id;
    }

    public String getId(){
        return _id;
    }

    
    public long getNumeroDeLinea(String callSiteId){
        long res = 0;
        Object o = _numeroDeLineaPorCallSiteMap.get(callSiteId);
        if (o != null){
            res = Long.parseLong(o.toString());
        }                                              
        return res;
    }


    //Nueva version de carga/des:
    public void addCallSite(CallSite callSite) {
        _callSites.add(callSite);
    }

    public void addRegion(RegionInfo ri) {
        _regiones.add(ri);
        String clase = ri.getClase();

        addFuente(clase);
        addRegionesPorClase(clase, ri);
    }

    public List getRegiones() {
        return _regiones;
    }

    public String getClaseMain() {
        return _claseMain;
    }

    public void setClaseMain(String pClaseMain) {
        _claseMain = pClaseMain;
    }

    //Ejemplo:  clase=p.prueba.EjemploCC4 metodo=m0(int)
    public RegionInfo getRegion(String clase, String metodo){
        RegionInfo res = null;
        List regiones = getRegionesPorClase(clase);
        int i = 0;
        if(regiones!=null)
        {
	        while( i < regiones.size() && res == null) {
	            RegionInfo ri = (RegionInfo) regiones.get(i);
	            String met2 = ri.getMetodo();
	            if (metodo.equals(met2))
	                res = ri;
	            i++;
	        }
        }
        return res;
    }

    public Set getCallChainNodes(String clase, int linea) {
    	if (clase.endsWith(".java"))
    		clase = clase.substring(0, clase.length());
        clase = clase.replaceAll("/", "_");
        clase = clase.replaceAll("\\.", "_");
        Iterator it = _callSites.iterator();
        while (it.hasNext()) {
            CallSite cs = (CallSite) it.next();
            if (cs.getClaseNormalizada().equals(clase) && cs.getLinea() == linea)
                return cs.getCreationSites();
        }
        return null;
    }

    public List getCallSites(String clase){
        List res = new Vector();
        if (clase.endsWith(".java"))
    		clase = clase.substring(0, clase.length() - 5); //fuente.java -> fuente

        clase = clase.replaceAll("/", "_");    //paq/fuente -> paq_fuente
        clase = clase.replaceAll("\\.", "_");  //paq.fuente -> paq_fuente


        Iterator it = _callSites.iterator();

        while (it.hasNext()) {
            CallSite cs = (CallSite) it.next();

            String claseNormalizada = cs.getClaseNormalizada();
            if (claseNormalizada.equals(clase))
                res.add(cs);
        }
        return res;
    }
    //fin nueva version de carga/des

    public String getSrcPath() {
        return _srcPath;
    }

    public void setSrcPath(String srcPath) {
        _srcPath = srcPath;
    }

    public Set getFuentes() {
        return _fuentes;
    }

    public void addFuente(String src){
        _fuentes.add(src);
    }

    //Devuelve las keys de _regionesPorClase
    public String[] getClasesDeRegiones(){
        Object[] os = _regionesPorClase.keySet().toArray();
        String[] res = new String[os.length];
        for (int i = 0; i < os.length; i++) {
            Object o = os[i];
            res[i] = (String)o;
        }
        return res;
    }

    public List getRegionesPorClase(String clase){
        List res = (List) _regionesPorClase.get(clase);

        return res;
    }

    public void addRegionesPorClase(String clase, List regiones){
        _regionesPorClase.put(clase, regiones);
    }
    public void addRegionesPorClase(String clase, RegionInfo ri){
        List regs = getRegionesPorClase(clase);
        if (regs == null){
            regs = new Vector();
            _regionesPorClase.put(clase, regs);
        }
        regs.add(ri);
    }

    public String toString(){
        StringBuffer res = new StringBuffer();
        res.append("Source path = " + _srcPath);
        res.append('\n' + "Fuentes: ");
        Iterator ite = _fuentes.iterator();
        while (ite.hasNext()) {
            String s = (String) ite.next();
            res.append('\n' + "    " + s);
        }
        res.append('\n');

//        res.append("Call site: ");
//        Iterator it = _callSites.iterator();
//        while (it.hasNext()) {
//            CallSite cs = (CallSite) it.next();
//
//            String l = "<" + cs.toString() + ">";
//            Set s = (Set) cs.getCreationSites();
//            String ns = "{ ";
//            Iterator it2 = s.iterator();
//            while (it2.hasNext()) {
//                CreationSiteInfo n = (CreationSiteInfo) it2.next();
//                ns += n.toString();
//                if (it2.hasNext())
//                    ns += ", ";
//            }
//            ns += "}";
//            res.append('\n' + "    " + l + " = " + ns);
//        }

        //regiones
        res.append("Regiones:" + '\n');
        for (int i = 0; i <_regiones.size(); i++) {
            RegionInfo ri = (RegionInfo)_regiones.get(i);
            res.append("        Regi�n: " + ri.getMetodo());
            res.append(" start=" + ri.getStart());
            res.append(" end=" + ri.getEnd() + '\n');

            Iterator it = ri.getCreationSites().iterator();
            while (it.hasNext()) {
                CreationSiteInfo cs = (CreationSiteInfo) it.next();
                res.append("            " + cs.toString() +  '\n');
            }

        }

        return res.toString();
    }

    public void print(){
        System.out.println(toString());
    }


//    private void log(String x) {
//        FileOutputStream fos = null;
//        try {
//            String a = "C:\\Visitor.log";
//            File f = new File(a);
//            fos = new FileOutputStream(f, true);
//            fos.write("\n".getBytes());
//            fos.write(x.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        finally{
//            if (fos != null)
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//                }
//        }
//    }
}


