package ar.uba.dc.annotations;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
 
@Retention(RetentionPolicy.RUNTIME)
public @interface EscapeAnnotation {
/**
 * Asumo que tenemos acceso a la signature del método.
 * Si el return 
 * 
 * */
	CalleeEscapeAnnotation[] methods();
	
	
}
