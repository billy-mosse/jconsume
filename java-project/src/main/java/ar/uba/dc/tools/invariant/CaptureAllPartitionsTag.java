package ar.uba.dc.tools.invariant;

import soot.tagkit.StringTag;

public class CaptureAllPartitionsTag extends StringTag {

	public CaptureAllPartitionsTag(){
        super("Captura all partitions");
    }
    
    /** Returns the tag name. */
    public String getName() {
        return "CaptureAllPartitionsTag";
    }
}
