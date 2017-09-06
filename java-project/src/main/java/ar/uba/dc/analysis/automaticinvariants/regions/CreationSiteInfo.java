package ar.uba.dc.analysis.automaticinvariants.regions;

/**
 * User: JP
 * Date: 25/08/2004
 * Time: 21:10:57
 */
public class CreationSiteInfo {
    private String  _class;
    private String  _method;
    private int     _position;

    public CreationSiteInfo(String clase, String metodo, int position) {
        _class = clase;
        _method = metodo;
        _position = position;
    }

    public String toString(){
        return "class=" + _class + ", method=" + _method + ", line=" + _position;
    }

    public String getClase() {
        return _class;
    }

    public String getClaseNormalizada() {
        String res =  _class;
        if (res.endsWith(".java"))
    		res = res.substring(0, res.length() - 5); //fuente.java -> fuente

        res = res.replaceAll("/", "_");    //paq/fuente -> paq_fuente
        res = res.replaceAll("\\.", "_");  //paq.fuente -> paq_fuente

        return res;
    }

    public String getMetodo() {
        return _method.replaceAll(" ","");
    }

    public int getPosition() {
        return _position;
    }
    
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object arg0) {
		CreationSiteInfo csinfo=(CreationSiteInfo)arg0;
		return this._class.equals(csinfo._class)
			&& this._method.equals(csinfo._method)
			&& this._position==csinfo._position;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return _class.hashCode()+_method.hashCode()+_position;
	}
}
