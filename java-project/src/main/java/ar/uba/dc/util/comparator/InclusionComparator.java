package ar.uba.dc.util.comparator;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

/**
 * Comparador de string que ordena segun inclusion. Las cadenas que no incluyen 
 * a otras son más chicas (quedan primeras en orden) que las que si lo hacen.
 * @author testis
 */
public class InclusionComparator implements Comparator<String> {

	public int compare(String o1, String o2) {
		
		if (!o1.equals(o2)) {
			if (StringUtils.contains(o1, o2)) {
				return -1;
			} else if (StringUtils.contains(o2, o1)) {
				return 1;
			}
		}
		
		return 0;
	}

}
