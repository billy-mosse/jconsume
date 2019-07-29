
public class EltWithAmount implements Comparable{
	Integer number;
	int amount;
	
	public EltWithAmount(int num, int amount)
	{
		this.number = num;
		this.amount = amount;
	}

	@Override
	public int compareTo(Object o) {
		if(this.amount < ((EltWithAmount)o).amount)
    	{
    		return 0;
    	}
    	else
    		return 1;
	}
	
	public String toString()
	{
		return "Number: " + number + "| Amount: " + amount;
	}
}
