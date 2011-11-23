package soot.jimple.toolkits.callgraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import soot.MethodOrMethodContext;
import soot.util.queue.ChunkedQueue;
import soot.util.queue.QueueReader;
import ar.uba.dc.soot.EdgeFilter;

/**
 * Implementacion de una clase de soot la cual tuvo que hacerse para poder
 * recortar la generacion del callgraph.
 * 
 * @author testis
 */
public class ReachableMethods {
	
	private static EdgePredicate edgePredicate = null;
	
	public static void setEdgePredicate(EdgePredicate aPredicate) {
		edgePredicate = aPredicate;
	}
	
	private CallGraph cg;
    private Iterator<Edge> edgeSource;
    private final ChunkedQueue<MethodOrMethodContext> reachables = new ChunkedQueue<MethodOrMethodContext>();
    private final Set<MethodOrMethodContext> set = new HashSet<MethodOrMethodContext>();
    private QueueReader<MethodOrMethodContext> unprocessedMethods;
    private final QueueReader<MethodOrMethodContext> allReachables = reachables.reader();
    private Filter filter;
     
    public ReachableMethods( CallGraph graph, Iterator<MethodOrMethodContext> entryPoints ) {
        this( graph, entryPoints, new EdgeFilter(edgePredicate /*new EdgePredicate() {
			public boolean want(Edge e) {
				return true;
			}
		}*/ /*null*/ ) /*null*/);
    }
    public ReachableMethods( CallGraph graph, Iterator<MethodOrMethodContext> entryPoints, Filter filter ) {
        this.filter = filter;
        this.cg = graph;
        addMethods( entryPoints );
        unprocessedMethods = reachables.reader();
        this.edgeSource = graph.listener();
        if( filter != null ) this.edgeSource = filter.wrap( this.edgeSource );
    }
    public ReachableMethods( CallGraph graph, Collection<MethodOrMethodContext> entryPoints ) {
    	this(graph, entryPoints.iterator());
    }
    private void addMethods( Iterator<MethodOrMethodContext> methods ) {
        while( methods.hasNext() )
            addMethod( (MethodOrMethodContext) methods.next() );
    }
    private void addMethod( MethodOrMethodContext m ) {
            if( set.add( m ) ) {
                reachables.add( m );
            }
    }
    /** Causes the QueueReader objects to be filled up with any methods
     * that have become reachable since the last call. */
    public void update() {
        while(edgeSource.hasNext()) {
            Edge e = (Edge) edgeSource.next();
            if( set.contains( e.getSrc() ) ) addMethod( e.getTgt() );
        }
        while(unprocessedMethods.hasNext()) {
            MethodOrMethodContext m = (MethodOrMethodContext) unprocessedMethods.next();
            Iterator<Edge> targets = cg.edgesOutOf( m );
            if( filter != null ) targets = filter.wrap( targets );
            addMethods( new Targets( targets ) );
        }
    }
    /** Returns a QueueReader object containing all methods found reachable
     * so far, and which will be informed of any new methods that are later
     * found to be reachable. */
    public QueueReader<MethodOrMethodContext> listener() {
        return allReachables.clone();
    }
    /** Returns a QueueReader object which will contain ONLY NEW methods
     * which will be found to be reachable, but not those that have already
     * been found to be reachable.
     */
    public QueueReader<MethodOrMethodContext> newListener() {
        return reachables.reader();
    }
    /** Returns true iff method is reachable. */
    public boolean contains( MethodOrMethodContext m ) {
        return set.contains( m );
    }
    /** Returns the number of methods that are reachable. */
    public int size() {
    	return set.size();
    }
}
