package ar.uba.dc.tools.invariant;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import soot.Body;
import soot.BodyTransformer;
import soot.PackManager;
import soot.Transform;
import ar.uba.dc.analysis.memory.code.MethodBody;
import ar.uba.dc.analysis.memory.code.Statement;
import ar.uba.dc.analysis.memory.code.impl.DefaultMethodDecorator;
import ar.uba.dc.barvinok.expression.DomainSet;
import ar.uba.dc.config.Context;
import ar.uba.dc.config.ContextFactory;
import ar.uba.dc.invariant.InvariantProvider;

public class InvariantTagger extends BodyTransformer {
	
	private static Log log = LogFactory.getLog(InvariantTagger.class);
	
	protected static InvariantProvider provider;
	
	public static void main(String[] args) {
		Transform t = new Transform("jap.ita", InvariantTagger.v());
		/* adds the transformer. */
        PackManager.v().getPack("jap").add(t);
        
        Context context = ContextFactory.getContext(args[1], false);
                
        provider = context.getFactory().getInvariantProvider();
        
        /* invokes Soot */
        log.info("Exec Soot");
        soot.Main.main(new String[] {
        	"-main-class", args[0],
        	"-soot-classpath", context.getString(Context.APPLICATION_CLASSPATH),
        	"-f", "J",
        	"-print-tags",
        	//"-app",
        	"-d", context.getString(Context.OUTPUT_FOLDER),
        	args[0]
        });
        log.info("Soot Finished");
	}

	public static InvariantTagger v() { return new InvariantTagger(); }

	@SuppressWarnings("unchecked")
	@Override
	protected void internalTransform(Body b, String phaseName, Map options) {
		MethodBody abstraction = new DefaultMethodDecorator().decorate(b.getMethod());
		for (Statement stmt : abstraction.getStatements()) {
			DomainSet invariant = provider.getInvariant(stmt);
			if (!invariant.getConstraints().isEmpty()) {
				stmt.getStatement().addTag(new InvariantTag(invariant.toString()));
			}
			
			if (provider.isLoopInvariant(stmt)) {
				stmt.getStatement().addTag(new LoopInvariantTag());
			}
			
			if (provider.captureAllPartitions(stmt)) {
				stmt.getStatement().addTag(new CaptureAllPartitionsTag());
			}
		}
	}
}