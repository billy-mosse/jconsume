package ar.uba.dc.analysis.automaticinvariants;

public class VarTest
{

    public static int test()
    {
        return 0;
    }

    public VarTest()
    {
        super();

        return;
    }

    public static int sizeCollection(java.util.Collection c)
    {
        int res;

        res = 0;

        if (c != null)
        {
            res = c.size();
        }

        return res;
    }

    public static int valueInteger(java.lang.Integer i)
    {
    	return i.intValue();
    }
    public static int sizeString(java.lang.String s)
    {
        int res;

        res = 0;

        if (s != null)
        {
            res = s.length();
        }

        return res;
    }

    public static int sizeArray(java.lang.Object[] a)
    {
        int res;

        res = 0;

        if (a != null)
        {
            res = a.length;
        }

        return res;
    }
    public static int toInt(double i)
    {
    	return (int)i;
    }
    public static int toInt(long i)
    {
    	return (int)i;
    }
    public static int toInt(byte i)
    {
    	return i;
    }

    
    public static void cartelito()
	{
    	System.out.println("Hola");
	}
	
}
