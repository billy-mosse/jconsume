package ar.uba.dc.tools.invariant;

import soot.tagkit.StringTag;


/** Represents a tag that just has a string to be printed with the code.
 */

public class InvariantTag extends StringTag
{

    public InvariantTag(String s){
        super(s);
    }
    
    /** Returns the tag name. */
    public String getName() {
        return "InvariantTag";
    }
}