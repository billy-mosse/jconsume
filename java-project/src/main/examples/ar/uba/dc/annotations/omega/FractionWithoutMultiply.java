package ar.uba.dc.annotations.omega;

public class FractionWithoutMultiply {
	public Integer n;
	public Integer m;
	public FractionWithoutMultiply(Integer n, Integer m)
	{
		this.n = n;
		this.m = m;
	}
	
	/*FractionWithoutMultiply multiply(FractionWithoutMultiply b)
	{
		Integer new_numerator = new Integer(n*b.n);
		Integer new_denominator = new Integer(m*b.m);
		FractionWithoutMultiply result = new FractionWithoutMultiply(new_numerator, new_denominator); 
		return result;
	}*/
	
	Integer getNumeratorIfGreaterThanDenominator()
	{
		Integer ret;
		if (this.n > this.m)
			ret = this.n;
		else
			ret = this.m;
		
		return ret;
	}	
}
