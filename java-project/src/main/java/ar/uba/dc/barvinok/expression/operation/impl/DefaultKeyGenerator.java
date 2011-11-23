package ar.uba.dc.barvinok.expression.operation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ar.uba.dc.barvinok.expression.operation.KeyGenerator;

/**
 * Genera claves usando la secuencia "a, b, c, d, ..., z, za, zb, ..."
 * 
 * @author testis
 */
public class DefaultKeyGenerator implements KeyGenerator {

	public String getInitialKey(Map<String, String> mapping) {
		String initialKey = StringUtils.EMPTY;
		
		if (!mapping.isEmpty()) {
			List<String> keys = new ArrayList<String>(mapping.keySet());
			Collections.sort(keys);
			initialKey = keys.get(keys.size() - 1);
		}
		
		return initialKey;
	}

	public String getNextKey(String actualKey) {
		if (StringUtils.isEmpty(actualKey)) {
			return "a";
		} 
			
		String nextKey = StringUtils.chop(actualKey);
		char lastChar = actualKey.charAt(actualKey.length() -1);
		String suffix = "za";
		if (lastChar < 'z') {
			suffix = Character.toString(++lastChar);
		}
		
		nextKey = nextKey + suffix;
		
		return nextKey;
	}

}
