package ar.uba.dc.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
@Retention(RetentionPolicy.RUNTIME)
public @interface CalleeEscapeAnnotation {
/**
 * Asumo que tenemos acceso a la signature del método.
 * Si el return 
 * 
 * */
	
	
	String methodSignature();
    boolean writeConfined(); //owner
    boolean globalAccess(); //
    boolean escapes();
    String returnType(); //void, fresh, any.
}
