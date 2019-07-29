package ar.uba.dc.analysis.memory;

import java.util.List;

public class SimpleMemoryResult {
	public List<String> residualPieces;
	public List<String> memorySummaryPieces;
	public String parameters;
	
 
    // Overriding equals() to compare two Complex objects 
    @Override
    public boolean equals(Object o) { 
  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof SimpleMemoryResult)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        SimpleMemoryResult s = (SimpleMemoryResult) o; 
          
        // Compare the data members and return accordingly  
        return s.residualPieces.equals(this.residualPieces) && s.memorySummaryPieces.equals(this.memorySummaryPieces) && s.parameters.equals(this.parameters);
    }  

}
