// ***** This file is automatically generated from PairwiseLinearBinary.java.jpp

package daikon.inv.binary.twoSequence;

import daikon.*;
import daikon.Quantify.QuantFlags;
import daikon.inv.*;
import daikon.inv.DiscardCode;
import daikon.inv.DiscardInfo;
import daikon.inv.binary.twoScalar.*;
import java.util.*;

/*>>>
import org.checkerframework.checker.interning.qual.*;
import org.checkerframework.checker.lock.qual.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
import typequals.*;
*/

/**
 * Represents a linear invariant (i.e., {@code y = ax + b}) between the corresponding elements
 * of two sequences of double values. Each {@code (x[i], y[i])} pair is examined. Thus,
 * {@code x[0]} is compared to {@code y[0]}, {@code x[1]} to {@code y[1]} and so
 * forth. Prints as {@code y[] = a * x[] + b}.
 */
public class PairwiseLinearBinaryFloat extends TwoSequenceFloat {
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20030822L;

  // Variables starting with dkconfig_ should only be set via the
  // daikon.config.Configuration interface.
  /** Boolean. True iff PairwiseLinearBinary invariants should be considered. */
  public static boolean dkconfig_enabled = Invariant.invariantEnabledDefault;

  public LinearBinaryCoreFloat core;

  @SuppressWarnings("nullness") // circular initialization
  protected PairwiseLinearBinaryFloat(PptSlice ppt) {
    super(ppt);
    core = new LinearBinaryCoreFloat(this);
  }

  @SuppressWarnings("nullness") // circular initialization
  protected /*@Prototype*/ PairwiseLinearBinaryFloat() {
    super();
    // Do we need a core in a prototype invariant?
    core = new LinearBinaryCoreFloat(this);
  }

  private static /*@Prototype*/ PairwiseLinearBinaryFloat proto = new /*@Prototype*/ PairwiseLinearBinaryFloat();

  /** Returns the prototype invariant for PairwiseLinearBinaryFloat */
  public static /*@Prototype*/ PairwiseLinearBinaryFloat get_proto() {
    return proto;
  }

  /** Returns whether or not this invariant is enabled */
  public boolean enabled() {
    return dkconfig_enabled;
  }

  /** Instantiates the invariant on the specified slice */
  protected PairwiseLinearBinaryFloat instantiate_dyn(/*>>> @Prototype PairwiseLinearBinaryFloat this,*/ PptSlice slice) {
    return new PairwiseLinearBinaryFloat(slice);
  }

  /*@SideEffectFree*/
  public PairwiseLinearBinaryFloat clone(/*>>>@GuardSatisfied PairwiseLinearBinaryFloat this*/) {
    PairwiseLinearBinaryFloat result = (PairwiseLinearBinaryFloat) super.clone();
    result.core = core.clone();
    result.core.wrapper = result;
    return result;
  }

  protected Invariant resurrect_done_swapped() {
    core.swap();
    return this;
  }

  public String repr(/*>>>@GuardSatisfied PairwiseLinearBinaryFloat this*/) {
    return "PairwiseLinearBinaryFloat" + varNames() + ": falsified=" + falsified
      + "; " + core.repr();
  }

  /*@SideEffectFree*/
  public String format_using(/*>>>@GuardSatisfied PairwiseLinearBinaryFloat this,*/ OutputFormat format) {
    if (core.a == 0 && core.b == 0 && core.c == 0) {
      return format_too_few_samples(format, null);
    }

    if (format == OutputFormat.DAIKON) return format_daikon();
    //if (format == OutputFormat.JML) return format_jml();
    if (format == OutputFormat.SIMPLIFY) return format_simplify();
    if (format == OutputFormat.CSHARPCONTRACT) return format_csharp();

    return format_unimplemented(format);
  }

  public String format_daikon(/*>>>@GuardSatisfied PairwiseLinearBinaryFloat this*/) {
    return core.format_using(OutputFormat.DAIKON, var1().name(),
                             var2().name());
  }

  public String format_simplify(/*>>>@GuardSatisfied PairwiseLinearBinaryFloat this*/) {
    String[] form = VarInfo.simplify_quantify(QuantFlags.element_wise(),
                                               var1(), var2());
    return form[0] + LinearBinaryCoreFloat.format_simplify(form[1], form[2],
                                               core.a, core.b, core.c)
      + form[3];
  }

  public String format_csharp(/*>>>@GuardSatisfied PairwiseLinearBinaryFloat this*/) {

    String[] split1 = var1().csharp_array_split();
    String[] split2 = var2().csharp_array_split();

    return "Contract.ForAll(0, " + split1[0]
      + ".Count(), i => "
      + core.format_using(OutputFormat.CSHARPCONTRACT, split1[0] + "[i]" + split1[1], split2[0] + "[i]" + split2[1])
      + ")";
  }

  public InvariantStatus check_modified(double /*@Interned*/ [] x_arr, double /*@Interned*/ [] y_arr, int count) {
    return clone().add_modified(x_arr, y_arr, count);
  }

  public InvariantStatus add_modified(double /*@Interned*/ [] x_arr, double /*@Interned*/ [] y_arr, int count) {
    if (x_arr.length != y_arr.length) {
      return InvariantStatus.FALSIFIED;
    }
    int len = x_arr.length;
    // int len = Math.min(x_arr.length, y_arr.length);

    for (int i = 0; i < len; i++) {
      double x = x_arr[i];
      double y = y_arr[i];

      if (core.add_modified(x, y, count) == InvariantStatus.FALSIFIED) {
        return InvariantStatus.FALSIFIED;
      }
    }
    return InvariantStatus.NO_CHANGE;
  }

  protected double computeConfidence() {
    return core.computeConfidence();
  }

  /*@Pure*/
  public boolean isSameFormula(Invariant other) {
    return core.isSameFormula(((PairwiseLinearBinaryFloat) other).core);
  }

  /*@Pure*/
  public boolean isExclusiveFormula(Invariant other) {
    if (other instanceof PairwiseLinearBinaryFloat) {
      return core.isExclusiveFormula(((PairwiseLinearBinaryFloat) other).core);
    }
    return false;
  }

  /*@Pure*/
  public /*@Nullable*/ DiscardInfo isObviousDynamically(VarInfo[] vis) {
    DiscardInfo super_result = super.isObviousDynamically(vis);
    if (super_result != null) {
      return super_result;
    }

    if (core.a == 0) {
      return new DiscardInfo(this, DiscardCode.obvious, var2().name() + " is constant");
    }
    if (core.b == 0) {
      return new DiscardInfo(this, DiscardCode.obvious, var1().name() + " is constant");
    }
//    if (core.a == 1 && core.b == 0) {
//      return new DiscardInfo(this, DiscardCode.obvious, "Variables are equal");
//    }
    if (core.a == -core.b && core.c == 0) {
     return new DiscardInfo(this, DiscardCode.obvious, "Variables are equal");
    }
    return null;
  }

  /*@Pure*/
  public boolean isActive() {
    return core.isActive();
  }

  public boolean mergeFormulasOk() {
    return (core.mergeFormulasOk());
  }

  /**
   * Merge the invariants in invs to form a new invariant. Each must be a PairwiseLinearBinaryFloat invariant. The
   * work is done by the LinearBinary core
   *
   * @param invs list of invariants to merge. They should all be permuted to match the variable
   *     order in parent_ppt.
   * @param parent_ppt slice that will contain the new invariant
   */
  public /*@Nullable*/ Invariant merge(List<Invariant> invs, PptSlice parent_ppt) {

    // Create a matching list of cores
    List<LinearBinaryCoreFloat> cores = new ArrayList<LinearBinaryCoreFloat>();
    for (Invariant inv : invs) {
      cores.add(((PairwiseLinearBinaryFloat) inv).core);
    }

    // Merge the cores and build a new invariant containing the merged core
    PairwiseLinearBinaryFloat result = new PairwiseLinearBinaryFloat(parent_ppt);
    LinearBinaryCoreFloat newcore = core.merge(cores, result);
    if (newcore == null) {
      return null;
    }
    result.core = newcore;
    return result;
  }
}
