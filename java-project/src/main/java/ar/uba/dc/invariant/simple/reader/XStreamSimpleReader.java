package ar.uba.dc.invariant.simple.reader;

import java.io.Reader;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;

import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.simple.SimpleReader;

/**
 * Dado un Reader del XML con la siguiente estructura, devuelve un map de {@link String} a {@link DomainSet}
 * 
 * <invariants>
 * 	<entry>
 *  	<string>ar.uba.dc.BasicTest: void sumNumbersUpToParameterUsingStaticList(java.lang.Integer)-new-1</string>
 *   	<invariant>
 *     		<parameter>upTo</parameter>
 *     		<variable>i</variable>
 *     		<constraints><![CDATA[1 <= i <= upTo]]</constraints>
 *   	</invariant>
 * 	</entry>
 *  <entry>
 * 		...
 * 	</entry>
 * 	...
 * </invariants>
 * 
 * @author testis
 */
public class XStreamSimpleReader implements SimpleReader {

	protected XStream xstream;
	
	public XStreamSimpleReader() {
		xstream = new XStream();
		xstream.processAnnotations(DomainSet.class);
		
		xstream.alias("invariants", Map.class);
		xstream.alias("invariant", DomainSet.class);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, DomainSet> read(Reader reader) {
		Map<String, DomainSet> dic = (Map<String, DomainSet>) xstream.fromXML(reader);
		
		for (DomainSet inv : dic.values()) {
			inv.removeVariable(StringUtils.EMPTY);
			inv.removeParameter(StringUtils.EMPTY);
		}
		
		return dic;
	}

}
