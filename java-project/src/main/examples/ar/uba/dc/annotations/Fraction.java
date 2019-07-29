package ar.uba.dc.annotations;

public class Fraction {
	public Integer n;
	public Integer m;
	public Fraction(Integer n, Integer m)
	{
		this.n = n;
		this.m = m;
	}
	
	Fraction multiply(Fraction b)
	{
		Integer new_numerator = new Integer(n*b.n);
		Integer new_denominator = new Integer(m*b.m);
		Fraction result = new Fraction(new_numerator, new_denominator); 
		return result;
	}
	
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