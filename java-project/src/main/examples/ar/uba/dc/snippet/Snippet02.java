package ar.uba.dc.snippet;

/**
 * @author martin
 *
 */
public class Snippet02 {

	public static void main(String[] args) {
		int size = Integer.parseInt(args[0]);
		Snippet02 snippet = new Snippet02();
		snippet.geometric(size);
	}
	
	
	public void geometric(int size) {
		for(int i=0; i<size;i++) {
			for(int j=0;j<i;j++) {
				Resource resource = new Resource();
				resource.use();
			}
		}
		
		new Resource();
	}
	
	private class Resource {
		
		public void use() {
			
		}
	}
}
