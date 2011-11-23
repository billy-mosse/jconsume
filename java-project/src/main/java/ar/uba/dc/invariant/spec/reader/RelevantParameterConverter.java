package ar.uba.dc.invariant.spec.reader;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public class RelevantParameterConverter implements SingleValueConverter {

	public Object fromString(String str) {
		Set<String> set = new TreeSet<String>();
		
		if (StringUtils.isNotBlank(str)) {
			for (String param : str.split(",")) {
				if (StringUtils.isNotBlank(param)) {
					set.add(param.trim());
				}
			}
		}
		
		return set;
	}

	@SuppressWarnings("unchecked")
	public String toString(Object obj) {
		return StringUtils.join((Set<String>) obj, ",");
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class type) {
		return Set.class.isAssignableFrom(type);
	}
}
