package ar.uba.dc.basic;


/**
 * La idea es ver si bancamos bien las innerclass declaradas
 * 
 */
@SuppressWarnings({"unused"})
public class EjemploInnerClass01 {
	
	/**
	 * 
	 * @temporal 2
	 * @residual 0
	 */
	public void main(String[] args) {
		  class Interna implements IInteger{
			  /**
				 * 
				 * @temporal 0
				 * @residual 1
				 */
             public Integer getInteger() {
                     return new Integer(1); 
             }
         };
         
         IInteger i = new Interna(); //tempLocal = 1
         Integer temp = i.getInteger(); //tempCall = 1
	}

}
