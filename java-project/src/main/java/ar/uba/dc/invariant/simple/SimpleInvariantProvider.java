package ar.uba.dc.invariant.simple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootClass;
import soot.SootMethod;
import ar.uba.dc.analysis.common.code.CallStatement;
import ar.uba.dc.analysis.common.code.NewStatement;
import ar.uba.dc.analysis.common.code.Statement;
import ar.uba.dc.analysis.common.code.StatementVisitor;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.invariant.InvariantProvider;
import ar.uba.dc.util.location.ClassLocationStrategy;

/**
 * Levanta los invariantes de un unico archivo. El formato del archivo es el siguiente
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
 * como podemos ver la clave de cada entrada es <full class name>: <method signature>-<type>-<offset>
 * donde type puede tomar valor "call" o "new" y el offset es el numero relativo a la aparicion de la instruccion del tipo 
 * especificado (comenzando en 0). En el ejemplo, es el 2do new dentro del codigo. 
 * 
 * El offset es relativo a cada tipo. En otras palabras, si el codigo es "new, new, call" el call tiene offset 0.
 * 
 * @author testis
 */
public class SimpleInvariantProvider implements InvariantProvider, StatementVisitor<Boolean> {

	protected static Log log = LogFactory.getLog(SimpleInvariantProvider.class);
	
	protected static Map<String, DomainSet> dic = new HashMap<String, DomainSet>();
	
	protected ClassLocationStrategy locationStrategy;
	protected String lastFileUsed;
	
	protected SimpleReader reader = null;
		
	public DomainSet getInvariantWithBinding(Statement stmt, Operation operation) {
		return getInvariant(stmt);
	}

	public DomainSet getInvariant(Statement stmt) {
			// Obtenemos la clase para la cual hay que buscar el invariante
		loadInvariants(stmt.belongsTo().getDeclaringClass());		
		
		DomainSet d = new DomainSet("");
		String key = "";
		
		Boolean isNew = stmt.apply(this);
		
		if (isNew) {
			key = getKey(stmt, "new");
		} else {
			key = getKey(stmt, "call");
		}
		
		if (key.length() > 0 && dic.containsKey(key)) {
			d = dic.get(key);
		}
		
		return d;
	}

	protected void loadInvariants(SootClass sootClass) {
			// Recuperamos el archivo donde estan los invariantes de esa clase
		String invFile = locationStrategy.getLocation(sootClass);
		
			// Es el mismo archivo que ya tenemos cargado?
		if (lastFileUsed == null || !lastFileUsed.equals(invFile)) {
			lastFileUsed = invFile;
			try {
					// Es otro archivo asi que lo cargamos 
				dic = reader.read(new FileReader(new File(invFile)));
			} catch (FileNotFoundException e) {
				log.error("No fue posible recuperar los invariantes del archivo [" + invFile + "]: " + e.getMessage());
				throw new RuntimeException(e);
			}
		}
	}
	
	public Set<String> getRelevantParameters(SootMethod method) {
		Set<String> parameters = new TreeSet<String>();
		
		loadInvariants(method.getDeclaringClass());
		
		String keyPreffix = getKeyPreffix(method);
		for (Entry<String, DomainSet> entry : dic.entrySet()) {
			if (entry.getKey().startsWith(keyPreffix)) {
				parameters.addAll(entry.getValue().getParameters());
			}
		}
		
		return parameters;
	}

	public DomainSet getRequeriments(SootMethod method) {
		return null;
	}
	
	public boolean isLoopInvariant(Statement stmt) {
		return false;
	}

	public boolean captureAllPartitions(Statement stmt) {
		return false;
	}

	protected String getKey(Statement stmt, String type) {
		return getKeyPreffix(stmt.belongsTo()) + type + "-" + stmt.getCounter();
	}
	
	protected String getKeyPreffix(SootMethod method) {
		return method.getDeclaringClass().getName() + ": " + method.getSubSignature() + "-";
	}

	public Boolean visit(CallStatement stmt) {
		return false;
	}

	public Boolean visit(NewStatement stmt) {
		return true;
	}

	public void setLocationStrategy(ClassLocationStrategy locationStrategy) {
		this.locationStrategy = locationStrategy;
	}

	public void setReader(SimpleReader reader) {
		this.reader = reader;
	}
}