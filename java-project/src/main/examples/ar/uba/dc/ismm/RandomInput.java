package ar.uba.dc.ismm;

import java.util.Date;
import java.util.Random;

public class RandomInput {

	static int MAX_SIZE = 20;
	
	public static void main(String[] args) {
		int mSize = 0;
		StringBuffer ls = new StringBuffer();
		Date date = new Date();
		Random random = new Random(date.getTime());
		ls.append("\"");
		int lSize = random.nextInt(MAX_SIZE);
		for(int i=0;i<lSize;i++) {
			int subListSize = random.nextInt(MAX_SIZE);
			mSize = subListSize > mSize ? subListSize : mSize;
			if(i!= 0)
				ls.append(" ");
			ls.append(subListSize);
		}
		ls.append("\"");
		ls.append(",");
		ls.append(Boolean.toString(random.nextBoolean()));
		System.out.println(ls.toString());
		System.out.println("lS=" + lSize);
		System.out.println("mS=" + mSize);
			
	}
	
}
