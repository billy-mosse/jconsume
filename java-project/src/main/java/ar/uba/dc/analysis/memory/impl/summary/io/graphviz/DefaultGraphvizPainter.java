package ar.uba.dc.analysis.memory.impl.summary.io.graphviz;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.SootClass;
import soot.jimple.Stmt;
import ar.uba.dc.analysis.escape.graph.Node;
import ar.uba.dc.soot.StatementId;
import ar.uba.dc.util.collections.CircularStack;

public class DefaultGraphvizPainter implements GraphvizPainter {

	private static Log log = LogFactory.getLog(DefaultGraphvizPainter.class);
	
	protected Map<Stmt, String> backgroundMapping; // call -> color
	protected Map<Stmt, Map<SootClass, String>> borderMapping; // call -> (implementation -> color)
	
	protected static String[] colorScheme = new String[] { 
		"aquamarine2", "azure4", "bisque3", "brown", "burlywood1", "cadetblue3", "chartreuse2", "chocolate", "coral", "cornflowerblue",
		"crimson", "cyan3", "blueviolet", "cyan4", "darkgoldenrod1", "darkgreen", "deeppink2", "dimgrey", "gold", "gray",
		"gray50", "green2", "greenyellow", "indianred1", "khaki3", "lightpink4", "lightskyblue3", "palegreen3", "plum4", "red3",
		"rosybrown3", "salmon", "seagreen4", "sienna3", "yellow", "yellowgreen", "steelblue", "violetred4", "turquoise4", "wheat4",
		"navajowhite1", "navajowhite2", "navajowhite3", "navajowhite4", "navy", "peru", "pink", "pink1", "pink2", "pink3"
	};
	
	protected Integer currentBackground;
	protected Map<Stmt, Integer> currentBorder;
	
	
	@Override
	public void init() {
		backgroundMapping = new HashMap<Stmt, String>();
		borderMapping = new HashMap<Stmt, Map<SootClass,String>>();
		currentBackground = null;
		currentBorder = new HashMap<Stmt, Integer>();
	}
	
	@Override
	public String getColor(Node node) {
		String result = StringUtils.EMPTY;
		
		if (comeFromCall(node)) {
			Stmt call = node.getContext().peek().getId();
			Map<SootClass, String> mapping = borderMapping.get(call);
			
			if (mapping == null) {
				mapping = new HashMap<SootClass, String>();
				borderMapping.put(call, mapping);
			}
			
			SootClass impl = getImplementation(node);
			
			result = mapping.get(impl);
			if (result == null) {
				Integer color = nextColor(currentBorder.get(call), colorScheme.length-1,  -1);
				currentBorder.put(call, color);
				result = colorScheme[color];
				mapping.put(impl, result);
			}
		}
		
		return result;
	}

	@Override
	public String getFillColor(Node node) {
		String result = StringUtils.EMPTY;
		
		if (comeFromCall(node)) {
			Stmt call = node.getContext().peek().getId();
			
			result = backgroundMapping.get(call);
			if (result == null) {
				currentBackground = nextColor(currentBackground, 0, 1);
				result = colorScheme[currentBackground];
				backgroundMapping.put(call, result);
			}
			
		}
		
		return result;
	}
	
	private Integer nextColor(Integer value, Integer init, int inc) {
		if (value == null) {
			return init;
		}
		
		value += inc;
		if (value < 0) {
			value = 0;
			log.warn("To much colors where used. Please add more to de colorscheme");
		}
		
		if (value >= colorScheme.length) {
			value = 0;
			log.warn("To much colors where used. Please add more to de colorscheme");
		}
		
		return value;
	}

	protected SootClass getImplementation(Node node) {
		CircularStack<StatementId> ctx = node.getContext();
		SootClass impl = null;
		
		// El stack tiene como primer elemento al metodo invocado por el metodo al cual pertenece el summary 
		// que estamos procesando. Si el nodo procede de otro call (el metodo invocado invoco a otro call), la implementacion
		// podemos obtenerla de dicho call. Si no hay mas calls, eso quiere decir que la impl la obtenemos de la clase a la que
		// pertenece el nodo (la que creo el nodo).
		if (ctx.size() > 1) {
			ctx.pop();
			impl = ctx.peek().getMethodOfId().getDeclaringClass();
		} else {
			impl = node.belongsTo().getDeclaringClass();
		}
		
		return impl;
	}

	protected boolean comeFromCall(Node node) {
		return node.isInside() && node.getContext().size() > 0;
	}
}
