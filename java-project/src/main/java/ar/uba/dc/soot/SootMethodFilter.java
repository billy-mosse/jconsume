package ar.uba.dc.soot;

import soot.SootMethod;

/**
 * Allows specifing which SootMethod you want to analyse in a
 * AbstractInterproceduralAnalysis.
 *
 * You will need a way to provide a summary for unanalysed methods that
 * are used by analysed code!
 */
public interface SootMethodFilter {

    public boolean want(SootMethod method, String mainClass);
    public void build(String mainClass);

}