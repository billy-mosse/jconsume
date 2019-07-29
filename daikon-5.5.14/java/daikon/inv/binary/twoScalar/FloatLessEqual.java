// ***** This file is automatically generated from IntComparisons.java.jpp

package daikon.inv.binary.twoScalar;

import daikon.*;
import daikon.derive.binary.*;
import daikon.derive.unary.*;
import daikon.inv.*;
import daikon.inv.binary.twoScalar.*;
import daikon.inv.binary.twoSequence.*;
import daikon.inv.unary.scalar.*;
import daikon.inv.unary.sequence.*;
import daikon.inv.unary.string.*;
import daikon.suppress.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import plume.*;

/*>>>
import org.checkerframework.checker.interning.qual.*;
import org.checkerframework.checker.lock.qual.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
import typequals.*;
*/

/**
 * Represents an invariant of &le; between two double scalars. Prints as {@code x <= y}.
 */
public final class FloatLessEqual extends TwoFloat {

  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20030822L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /** Boolean. True iff FloatLessEqual invariants should be considered. */
  public static boolean dkconfig_enabled = Invariant.invariantEnabledDefault;

  public static final Logger debug = Logger.getLogger("daikon.inv.binary.twoScalar.FloatLessEqual");

  protected FloatLessEqual(PptSlice ppt) {
   super(ppt);
  }

  protected /*@Prototype*/ FloatLessEqual() {
   super();
  }

  private static /*@Prototype*/ FloatLessEqual proto = new /*@Prototype*/ FloatLessEqual();

  /** Returns the prototype invariant for FloatLessEqual */
  public static /*@Prototype*/ FloatLessEqual get_proto() {
    return proto;
  }

  /** Returns whether or not this invariant is enabled */
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /** Returns whether or not the specified var types are valid for FloatLessEqual */
  public boolean instantiate_ok(VarInfo[] vis) {

    if (!valid_types(vis)) {
      return false;
    }

      return true;
  }

  /** Instantiate an invariant on the specified slice */
  protected FloatLessEqual instantiate_dyn(/*>>> @Prototype FloatLessEqual this,*/ PptSlice slice) {

    return new FloatLessEqual(slice);
  }

  protected Invariant resurrect_done_swapped() {

      // we have no non-static member data, so we only need care about our type
      // As of now, the constructor chain is side-effect free;
      // let's hope it stays that way.
      FloatGreaterEqual result = new FloatGreaterEqual(ppt);
      return result;
  }

  /** Returns the class that corresponds to this class with its variable order swapped. */
  public static Class<? extends Invariant> swap_class() {
    return FloatGreaterEqual.class;
  }

  // JHP: this should be removed in favor of checks in PptTopLevel
  // such as is_equal, is_lessequal, etc.
  // Look up a previously instantiated FloatLessEqual relationship.
  // Should this implementation be made more efficient?
  public static /*@Nullable*/ FloatLessEqual find(PptSlice ppt) {
    assert ppt.arity() == 2;
    for (Invariant inv : ppt.invs) {
      if (inv instanceof FloatLessEqual) {
        return (FloatLessEqual) inv;
      }
    }

    // If the invariant is suppressed, create it
    if ((suppressions != null) && suppressions.suppressed(ppt)) {
      FloatLessEqual inv = proto.instantiate_dyn(ppt);
      // System.out.printf ("%s is suppressed in ppt %s%n", inv.format(), ppt.name());
      return inv;
    }

    return null;
  }

  public String repr(/*>>>@GuardSatisfied FloatLessEqual this*/) {
    return "FloatLessEqual" + varNames();
  }

  /*@SideEffectFree*/
  public String format_using(/*>>>@GuardSatisfied FloatLessEqual this,*/ OutputFormat format) {

    String var1name = var1().name_using(format);
    String var2name = var2().name_using(format);

    if ((format == OutputFormat.DAIKON) || (format == OutputFormat.ESCJAVA)) {
      String comparator = "<=";
      return var1name + " " + comparator + " " + var2name;
    }

    if (format == OutputFormat.CSHARPCONTRACT) {

        String comparator = "<=";
        return var1name + " " + comparator + " " + var2name;
    }

    if (format.isJavaFamily()) {

        return Invariant.formatFuzzy("lte", var1(), var2(), format);

    }

    if (format == OutputFormat.SIMPLIFY) {

        String comparator = "<=";

      return "("
          + comparator
          + " "
          + var1().simplifyFixup(var1name)
          + " "
          + var2().simplifyFixup(var2name)
          + ")";
    }

    return format_unimplemented(format);
  }

  public InvariantStatus check_modified(double v1, double v2, int count) {
    if (!((Global.fuzzy.lte(v1, v2)))) {
      return InvariantStatus.FALSIFIED;
    }
    return InvariantStatus.NO_CHANGE;
  }

  public InvariantStatus add_modified(double v1, double v2, int count) {
    if (logDetail() || debug.isLoggable(Level.FINE)) {
      log(
          debug,
          "add_modified (" + v1 + ", " + v2 + ",  ppt.num_values = " + ppt.num_values() + ")");
    }
    if ((logOn() || debug.isLoggable(Level.FINE))
        && check_modified(v1, v2, count) == InvariantStatus.FALSIFIED)
      log(debug, "destroy in add_modified (" + v1 + ", " + v2 + ",  " + count + ")");

    return check_modified(v1, v2, count);
  }

  // This is very tricky, because whether two variables are equal should
  // presumably be transitive, but it's not guaranteed to be so when using
  // this method and not dropping out all variables whose values are ever
  // missing.
  protected double computeConfidence() {
    // Should perhaps check number of samples and be unjustified if too few
    // samples.

      // // The reason for this multiplication is that there might be only a
      // // very few possible values.  Example:  towers of hanoi has only 6
      // // possible (pegA, pegB) pairs.
      // return 1 - (Math.pow(.5, ppt.num_values())
      //             * Math.pow(.99, ppt.num_mod_samples()));
      return 1 - Math.pow(.5, ppt.num_samples());
  }

  // For Comparison interface
  public double eq_confidence() {
    if (isExact()) {
      return getConfidence();
    } else {
      return Invariant.CONFIDENCE_NEVER;
    }
  }

  /*@Pure*/
  public boolean isExact() {

      return false;
  }

  // // Temporary, for debugging
  // public void destroy() {
  //   if (debug.isLoggable(Level.FINE)) {
  //     System.out.println("FloatLessEqual.destroy(" + ppt.name() + ")");
  //     System.out.println(repr());
  //     (new Error()).printStackTrace();
  //   }
  //   super.destroy();
  // }

  public InvariantStatus add(
      /*@Interned*/ Object v1, /*@Interned*/ Object v2, int mod_index, int count) {
    if (debug.isLoggable(Level.FINE)) {
      debug.fine(
          "FloatLessEqual"
          + ppt.varNames()
          + ".add("
          + v1
          + ","
          + v2
          + ", mod_index="
          + mod_index
          + "), count="
          + count
          + ")");
    }
    return super.add(v1, v2, mod_index, count);
  }

  /*@Pure*/
  public boolean isSameFormula(Invariant other) {
    return true;
  }

  /*@Pure*/
  public boolean isExclusiveFormula(Invariant other) {

    // Also ought to check against LinearBinary, etc.

      if (other instanceof FloatGreaterThan) {
        return true;
      }

    return false;
  }

  @Override
  public /*@Nullable*/ DiscardInfo isObviousStatically(VarInfo[] vis) {
    final VarInfo var1 = vis[0];
    final VarInfo var2 = vis[1];

    if (var1.aux.hasValue(VarInfoAux.MAXIMUM_VALUE)
        && var2.aux.hasValue(VarInfoAux.MINIMUM_VALUE)) {
      @SuppressWarnings("keyfor")   // needs EnsuresQualifier
      int maxA = var1.aux.getInt(VarInfoAux.MAXIMUM_VALUE),
          minB = var2.aux.getInt(VarInfoAux.MINIMUM_VALUE);

      if (maxA <= minB) {
        return new DiscardInfo(
            this,
            DiscardCode.obvious,
            var1.name() + " DISCARD_OP " + var2.name() + " is already known");
      }
    }

    return super.isObviousStatically(vis);
  }

  /*@Pure*/
  public /*@Nullable*/ DiscardInfo isObviousDynamically(VarInfo[] vis) {

    // JHP: We might consider adding a check over bounds.   If
    // x < c and y > c then we know that x < y.  Similarly for
    // x > c and y < c.  We could also substitute oneof for
    // one or both of the bound checks.

    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null) {
      return super_result;
    }

    VarInfo var1 = vis[0];
    VarInfo var2 = vis[1];

    DiscardInfo di = null;

      // Check for the same invariant over enclosing arrays
      di = pairwise_implies(vis);
      if (di != null) {
        return di;
      }

    { // Sequence length tests
      SequenceLength sl1 = null;
      if (var1.isDerived() && (var1.derived instanceof SequenceLength)) {
        sl1 = (SequenceLength) var1.derived;
      }
      SequenceLength sl2 = null;
      if (var2.isDerived() && (var2.derived instanceof SequenceLength)) {
        sl2 = (SequenceLength) var2.derived;
      }

      // "size(a)-1 cmp size(b)-1" is never even instantiated;
      // use "size(a) cmp size(b)" instead.

      // This might never get invoked, as equality is printed out specially.
      VarInfo s1 = (sl1 == null) ? null : sl1.base;
      VarInfo s2 = (sl2 == null) ? null : sl2.base;
      if ((s1 != null) && (s2 != null) && (s1.equalitySet == s2.equalitySet)) {
        // lengths of equal arrays being compared
        String n1 = var1.name();
        String n2 = var2.name();
        return new DiscardInfo(
            this,
            DiscardCode.obvious,
            n1 + " and " + n2 + " are equal arrays, so equal size is implied");
      }

    }

    return null;
  } // isObviousDynamically

  /**
   * If both variables are subscripts and the underlying arrays have the same invariant, then this
   * invariant is implied:
   *
   * <pre>(x[] op y[]) ^ (i == j) &rArr; (x[i] op y[j])</pre>
   */
  private /*@Nullable*/ DiscardInfo pairwise_implies(VarInfo[] vis) {

    VarInfo v1 = vis[0];
    VarInfo v2 = vis[1];

    // Make sure v1 and v2 are SequenceFloatSubscript with the same shift
    if (!v1.isDerived() || !(v1.derived instanceof SequenceFloatSubscript)) {
      return null;
    }
    if (!v2.isDerived() || !(v2.derived instanceof SequenceFloatSubscript)) {
      return null;
    }
    @SuppressWarnings("nullness") // checker bug in flow
    /*@NonNull*/ SequenceFloatSubscript der1 = (SequenceFloatSubscript) v1.derived;
    @SuppressWarnings("nullness") // checker bug in flow
    /*@NonNull*/ SequenceFloatSubscript der2 = (SequenceFloatSubscript) v2.derived;
    if (der1.index_shift != der2.index_shift) {
      return null;
    }

    // Make sure that the indices are equal
    if (!ppt.parent.is_equal(der1.sclvar().canonicalRep(), der2.sclvar().canonicalRep())) {
      return null;
    }

    // See if the same relationship holds over the arrays
    Invariant proto = PairwiseFloatLessEqual.get_proto();
    DiscardInfo di = ppt.parent.check_implied_canonical(this, der1.seqvar(), der2.seqvar(), proto);
    return di;
  }

  /** NI suppressions, initialized in get_ni_suppressions() */
  private static /*@Nullable*/ NISuppressionSet suppressions = null;

  /** Returns the non-instantiating suppressions for this invariant. */
  /*@Pure*/
  public /*@NonNull*/ NISuppressionSet get_ni_suppressions() {
    if (suppressions == null) {

        NISuppressee suppressee = new NISuppressee(FloatLessEqual.class, 2);

      // suppressor definitions (used in suppressions below)

      NISuppressor v1_eq_v2 = new NISuppressor(0, 1, FloatEqual.class);

      NISuppressor v1_lt_v2 = new NISuppressor(0, 1, FloatLessThan.class);

      suppressions =
          new NISuppressionSet(
              new NISuppression[] {

                  // v1 == v2 => v1 <= v2
                  new NISuppression(v1_eq_v2, suppressee),
                  // v1 < v2 => v1 <= v2
                  new NISuppression(v1_lt_v2, suppressee),

              });
    }
    return suppressions;
  }

}
