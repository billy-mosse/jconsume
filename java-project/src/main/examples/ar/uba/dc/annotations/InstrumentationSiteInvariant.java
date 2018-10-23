package ar.uba.dc.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
@Retention(RetentionPolicy.RUNTIME)
public @interface InstrumentationSiteInvariant {
    boolean isCallSite(); //false hace que sea creationsite
    int index();
    String[] constraints();
    String[] newRelevantParameters();
    String[] newInductives();
    String[] newVariables();
}
