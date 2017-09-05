package ar.uba.dc.analysis.automaticinvariants.regions;

import org.jdom.Element;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.DataConversionException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScopedInfoReader {
    private final static String RAIZ_ID = "id";
    private final static String SRC_PATH = "srcPath";
    private final static String TAG_CAPTURE = "Stmt";
    private final static String TAG_REGIONS = "Regions";
    private final static String TAG_REGION = "Region";
    private final static String AT_REGION_NAME = "scope";
    private final static String AT_REGION_DESDE = "lineFrom";
    private final static String AT_REGION_HASTA = "lineTo";
    private final static String TAG_CREATION_SITE = "CreationSite";
    private final static String AT_CREATION_SITE_CLASE = "class";
    private final static String AT_CREATION_SITE_METODO = "method";
    private final static String AT_CREATION_SITE_POSTITION = "position";


    public static ScopedInfo parse(String path)
            throws IOException, JDOMException {
        return parse(new File(path));
    }

    public static ScopedInfo parseDirectorio(String path)
                throws IOException, JDOMException {
        return parseDirectorio(new File(path));
    }


    /**
     * @param pFile es el xml que tiene la clase con main
     *
     * Adem�s de ese archivo parsea todos los xml del directorio
     *
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static ScopedInfo parseDirectorio(File pFile)
                throws IOException, JDOMException {
        ScopedInfo res = null;
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(pFile);
        Element root = doc.getRootElement();
        String siId = root.getAttribute(RAIZ_ID).getValue();
        res = new ScopedInfo(siId);
        res.setClaseMain(siId);
        String srcPath = root.getAttribute(SRC_PATH).getValue();
        res.setSrcPath(srcPath);

        parseRegiones(res, root);
        //Parseo los xml del directorio
        File dir = pFile.getParentFile();
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            String name = f.getName();
            if (!name.equals(pFile.getName())    //si no es el de claseMain
                && name.endsWith(".xml")){       //y termina en '.xml'
                doc = builder.build(f);
                root = doc.getRootElement();
                parseRegiones(res, root);
            }
        }
        return res;
    }

    public static ScopedInfo parse(File file)
                throws IOException, JDOMException {
        ScopedInfo res = null;
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(file);
        Element root = doc.getRootElement();
        String siId = root.getAttribute(RAIZ_ID).getValue();
        res = new ScopedInfo(siId);
        res.setClaseMain(siId);
        String srcPath = root.getAttribute(SRC_PATH).getValue();
        res.setSrcPath(srcPath);

        parseRegiones(res, root);

        return res;
    }

    private static void parseRegiones(ScopedInfo res, Element root) {
        Element regionsElem = root.getChild(TAG_REGIONS);

        if (regionsElem != null){
            List regiones = regionsElem.getChildren(TAG_REGION);
            //lista de <Region id = "p.prueba.EjemploCC4/m0" scope = "p.prueba.EjemploCC4.m0(int)" lineFrom = "4" lineTo

            RegionInfo region = null;
            for (int i = 0; i < regiones.size(); i++) {
                Element regElem = (Element) regiones.get(i);
                String ini = regElem.getAttribute(AT_REGION_DESDE).getValue();
                String fin = regElem.getAttribute(AT_REGION_HASTA).getValue();
                String nombre = regElem.getAttribute(AT_REGION_NAME).getValue();
                nombre = nombre.replaceAll(" ","");
                region = new RegionInfo(nombre, ini, fin);
                res.addRegion(region);
                // Element capture = regElem.getChild(TAG_CAPTURE);
                List capturesStmt = regElem.getChildren(TAG_CAPTURE);
                // System.out.println("Capturo:"+capturesStmt);
                for (int jS = 0; jS < capturesStmt.size(); jS++) {
                	int posS;
                    Element captElemS = (Element) capturesStmt.get(jS);
                    try {
                        posS = captElemS.getAttribute(AT_CREATION_SITE_POSTITION).getIntValue();
                    } catch (DataConversionException e) {
                        e.printStackTrace();
                        posS = -6;
                    }
                    String classS=nombre.substring(0,nombre.lastIndexOf(".")-1);
                    String methodS=nombre.substring(nombre.lastIndexOf(".")-1);
                    InstrumentationSite is = new InstrumentationSite(classS, methodS, posS);

	                //Agrego los capture(CreationSite) de la region
	                CreationSiteInfo creationS = null;
	                List captures = captElemS.getChildren(TAG_CREATION_SITE);
	                // lista de <CreationSite file ="" lineNumber=...
	
	                for (int j = 0; j < captures.size(); j++) {
	                    Element captElem = (Element) captures.get(j);
	
	                    int pos = 0;
	                    try {
	                        pos = captElem.getAttribute(AT_CREATION_SITE_POSTITION).getIntValue();
	                    } catch (DataConversionException e) {
	                        e.printStackTrace();
	                        pos = -6;
	                    }
	                    String cl = captElem.getAttribute(AT_CREATION_SITE_CLASE).getValue();
	                    String me = captElem.getAttribute(AT_CREATION_SITE_METODO).getValue();
	                    me = me.replaceAll(" ","");
	
	                    creationS = new CreationSiteInfo(cl, me, pos);
	                    region.addCreationSite(creationS);
	                    region.addCreationSite(is,creationS);
	                }
                }
            }
        }
    }


    public static void main(String[] args) throws IOException, JDOMException {
//        String path = "F:\\eclipse\\code\\JScope\\src\\p\\prueba\\p.prueba.EjemploCC3.xml";
//        String path = "C:\\IntelliJ-IDEA-4.0\\Pablo\\te\\src\\p\\prueba\\p.prueba.EjemploCC5.xml";
        String path = "F:\\eclipse\\code\\JScope\\src\\p\\prueba\\p.prueba.EjemploCC5.xml";
        ScopedInfo si = ScopedInfoReader.parse(path);
        si.print();
    }
}
