package ar.uba.dc.simple;

public class PetExample
{	
	public static void main(String[] args)
	{
		Animal animal;		
		
		if(args.length == 4)
			animal = new Dog();
		else
			animal = new Cat();
		
		
		Integer a = a1(animal);
	}
	
	public static Integer a1(Animal animal)
	{
		Integer n = a2();
		
		n+=1;

		return animal.getLives();
	}
	
	public static Integer a2()
	{
		Integer n = new Integer(4);
		Integer m = new Integer(8);
		
		Integer l = Integer.valueOf(n);
		
		
		return l;
	}
}

