package ar.uba.dc.tools.invariant;

import soot.tagkit.StringTag;

public class LoopInvariantTag extends StringTag {

	public LoopInvariantTag(){
        super("Is loop invariant");
    }
    
    /** Returns the tag name. */
    public String getName() {
        return "LoopInvariantTag";
    }
}
