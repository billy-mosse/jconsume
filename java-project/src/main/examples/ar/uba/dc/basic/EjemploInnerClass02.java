package ar.uba.dc.basic;


/**
 * La idea es ver si bancamos bien las innerclass anonimas
 * 
 */
@SuppressWarnings({"unused"})
public class EjemploInnerClass02 {

	/**
	 * 
	 * @temporal 2
	 * @residual 0
	 */
	public void main(String[] args) {
		  IInteger i = new IInteger() {
			  /**
				 * 
				 * @temporal 0
				 * @residual 1
				 */
             public Integer getInteger() {
                     return new Integer(1); 
             }
         }; //tempLocal = 1
         
         Integer temp = i.getInteger(); //tempCall = 1
	}

}
