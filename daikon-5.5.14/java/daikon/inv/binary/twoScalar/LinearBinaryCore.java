// ***** This file is automatically generated from LinearBinaryCore.java.jpp

package daikon.inv.binary.twoScalar;

import daikon.*;
import daikon.inv.*;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import plume.*;

/*>>>
import org.checkerframework.checker.lock.qual.*;
import org.checkerframework.checker.nullness.qual.*;
import org.checkerframework.dataflow.qual.*;
*/

public final class LinearBinaryCore implements Serializable, Cloneable {
  // We are Serializable, so we specify a version to allow changes to
  // method signatures without breaking serialization.  If you add or
  // remove fields, you should change this number to the current date.
  static final long serialVersionUID = 20030822L;

  /** Debug tracer. */
  public static final Logger debug =
    Logger.getLogger("daikon.inv.binary.twoScalar.LinearBinaryCore");

  /** This invariant represents ax + by + c = 0; the first argument is x, second is y. */
  public double a = 0, b = 0, c = 0;

  public long min_x, min_y, max_x, max_y;
  public double min_a, max_a, min_b, max_b, min_c, max_c;

  public Invariant wrapper;

  // The number of distinct values (not samples) observed.
  public int values_seen = 0;

  // We delay computation of a, b and c until we have seen several pairs so
  // that we can compute a, b and c based on a far-separated pair.  We reduce
  // the likelihood of roundoff error by getting 4 points, then choosing
  // the two that are furthest apart in order to compute a, b and c.
  static final int MINPAIRS = 4;

  public long[] x_cache = new long[MINPAIRS];
  public long[] y_cache = new long[MINPAIRS];

  @SuppressWarnings("fields.uninitialized") // delayed initialization
  public LinearBinaryCore(Invariant wrapper) {
    this.wrapper = wrapper;
  }

  /*@SideEffectFree*/
  public LinearBinaryCore clone(/*>>>@GuardSatisfied LinearBinaryCore this*/) {
    try {
      LinearBinaryCore result = (LinearBinaryCore) super.clone();
      if (x_cache != null) {
        result.x_cache = x_cache.clone();
      }
      if (y_cache != null) {
        result.y_cache = y_cache.clone();
      }
      return result;
    } catch (CloneNotSupportedException e) {
      throw new Error(); // can't happen
    }
  }

//There is no more need for swap because the equation is in standard form.
  public void swap() {
    // was a swap
    if (values_seen < MINPAIRS) {
    } else {
      if (a == 0) {
        // can't swap horizontal line into vertical, but if a was 0,
        // then we might as well falsify ourselves because this is just
        // a constant
        values_seen = Integer.MAX_VALUE;
        a = 0;
        b = 0;
        c = 0;
      } else {
    //since the equation is already in standard form, just guarantee that the x coefficient is positive

        if (b < 0) {
          a = -b;
          b = -a;
          c = -c;
        }
      }
    }

    // swap the value caches
    long[] tmp = x_cache;
    x_cache = y_cache;
    y_cache = tmp;

    // swap min_x/min_y and max_x/max_y
    long tmp_val = max_x;
    max_x = max_y;
    max_y = tmp_val;
    tmp_val = min_x;
    min_x = min_y;
    min_y = tmp_val;
 }

  /**
   * Returns whether or not the invariant is currently active. We become active after MINPAIRS
   * values have been seen and a line calculated. Before that, a and b are uninitialized.
   */
  /*@Pure*/
  public boolean isActive() {
    return (values_seen >= MINPAIRS);
  }

  /**
   * LinearBinary can't be flowed because it keeps samples to build the line. These sample can't be
   * flowed from ppt to ppt (since they probably didn't occur at the lower ppt).
   */
  /*@Pure*/
  public boolean isFlowable() {
    return false;
  }

  // Note that this method is relatively inconsistent with respect to
  // differing ordering of the points.  It will always give exactly the
  // same answer with points that are delivered in the same order, but
  // it can calculate different a/b and change whether or not it even
  // finds a line with a different order.  Current code always guarantees
  // that the points arrive in the same order (I think).
  public InvariantStatus add_modified(long x, long y, int count) {
    if (values_seen < MINPAIRS) {
      if (debug.isLoggable(Level.FINE)) {
        debug.fine("min point: " + wrapper.ppt.name() + ": " + x + ", " + y);
      }
      // We delay computation of a and b until we have seen several pairs
      // so that we can compute a and b based on a far-separated pair.  If
      // the points in a pair are nearby, then roundoff errors in the
      // computation of the slope can be non-negligible.

      for (int i = 0; i < values_seen; i++)
        if ((x_cache[i] == x) && (y_cache[i] == y)) {
          return InvariantStatus.NO_CHANGE;
        }
      x_cache[values_seen] = x;
      y_cache[values_seen] = y;
      values_seen++;
      if (values_seen == MINPAIRS) {
        // Find the most separated pair.
        int[] sep_indices = maxsep_indices();
        int max_i = sep_indices[0];
        int max_j = sep_indices[1];

        // Set a, b and c based on that pair
        if (max_i == -1) {
          values_seen--;
          wrapper.log("falsified, max_i = -1");
          return InvariantStatus.FALSIFIED;
        }
        // remember the min and max pairs
        if (x_cache[max_i] < x_cache[max_j]) {
          // swap max_i and max_j
          int tmp = max_i;
          max_i = max_j;
          max_j = tmp;
        }
        min_x = x_cache[max_i];
        min_y = y_cache[max_i];
        max_x = x_cache[max_j];
        max_y = y_cache[max_j];

        // calculate a, b and c for this pair and remember them as min/max a,b,c
        if (set_bi_linear(min_x, max_x, min_y, max_y) == InvariantStatus.FALSIFIED) {
          values_seen--;
          wrapper.log("falsified, no set_bi_linear");
          return InvariantStatus.FALSIFIED;
        }
        min_a = a;
        max_a = a;
        min_b = b;
        max_b = b;
        min_c = c;
        max_c = c;
        if (debug.isLoggable(Level.FINE)) {
            debug.fine(
                wrapper.ppt.name() + ": Initial a (" + a + ") and b (" + b + ") and c (" + c + ")");
        }

        // Check all values against a, b, and c.
        if (((a) == ( 0))) {
          wrapper.log("falsified, a == 0");
          values_seen--;
          return InvariantStatus.FALSIFIED;
        }

        for (int i = 0; i < MINPAIRS; i++) {
          // if the point is close enough in either x or y it is close enough
          // The double check is used because lines with different slopes may
          // indicate an unreasonably high x or y diff when the actual point
          // is very close.  It would probably be better to calculate the
          // actual distance from the line, but it would be unclear what to
          // compare this to (average or X and Y?).  This seems like a
          // reasonable compromise
          if ((!((-b * y_cache[i]) == (  a * x_cache[i] + c)))
              && (!((x_cache[i]) == ( ((-b * y_cache[i] - c) / a))))) {
            if (debug.isLoggable(Level.FINE)) {
              debug.fine(
                  "Suppressing LinearBinaryCore ("
                  + wrapper.format()
                  + ") at index "
                  + i
                  + ": "
                  + y_cache[i]
                  + " != "
                  + a
                  + "*"
                  + x_cache[i]
                  + "+"
                  + b);
              debug.fine("    ");
            }
            values_seen--;
            wrapper.log("falsified, point in cache doesn't match");
            return InvariantStatus.FALSIFIED;
          }
        }
      }
    } else {
      // Check the new value against a and b. (see prev explanation for double
      // EQUAL check)
      if ((!((y) == ( (-c - a * x)/b))) && (!((x) == ( (-c - b * y)/ a)))) {
        // see if this new point is off one end or the other
        if (x < min_x) {
          min_x = x;
          min_y = y;
        } else if (x > max_x) {
          max_x = x;
          max_y = y;
        }

        long delta_x = max_x - min_x;
        // should be fixed in the future to permit vertical lines
        if (((delta_x) == ( 0))) {
          return InvariantStatus.FALSIFIED;
        }

        // calculate a new a, b, c possibly using a new end point

        double[] res = new double[3];
        boolean ok = find_bi_linear(min_x, max_x, min_y, max_y, res);
        double new_a = 0;
        double new_b = 0;
        double new_c = 0;
        if (ok) {
          new_a = res[0];
          new_b = res[1];
          new_c = res[2];
        }

        // if the a or b is a new min/max remember it.
        if (new_a < min_a) min_a = new_a;
        if (new_a > max_a) max_a = new_a;
        if (new_b < min_b) min_b = new_b;
        if (new_b > max_b) max_b = new_b;
        if (new_c < min_c) min_c = new_c;
        if (new_c > max_c) max_c = new_c;

        //pick a new a, b, c as the average of their endpoints
        new_a = (min_a + max_a) / 2;
        new_b = (min_b + max_b) / 2;
        new_c = (min_c + max_c) / 2;
        if (debug.isLoggable(Level.FINE)) {
            debug.fine(wrapper.ppt.name() + ": Trying new a (" + new_a
                         + ") and b (" + new_b + ")");
        }

        // if the new a, b, c are 'equal' to min/max a, b, c and
        // this point fits, then this new equation is good enough both
        // for existing points and the new point.

        if (Global.fuzzy.eq(new_a, min_a)
            && Global.fuzzy.eq(new_a, max_a)
            && Global.fuzzy.eq(new_b, min_b)
            && Global.fuzzy.eq(new_b, max_b)
            && Global.fuzzy.eq(new_c, min_c)
            && Global.fuzzy.eq(new_c, max_c)
            && ((-new_b * y) == ( new_a * x + new_c))) {
          a = new_a;
          b = new_b;
          c = new_c;
          if (debug.isLoggable(Level.FINE)) {
            debug.fine(
                wrapper.ppt.name() + ": New a (" + a + ") and b (" + b + ") and c (" + c + ")");
          }
        } else {
          if (debug.isLoggable(Level.FINE)) {
            debug.fine("Suppressing LinearBinaryCore ("
                        + wrapper.format() + ") at new value: "
                        + a + " * x + " + b + " * y + " + c + " != 0");
//                        + y + " != " + a + "*" + x + "+" + b);
          }
          return InvariantStatus.FALSIFIED;
        }
        //??? wrapper.destroyAndFlow();
        //??? return;
      }
    }
    return InvariantStatus.NO_CHANGE;
  }

  /**
   * Returns a 2-element int array of the indices of the most separated pair of points between the
   * points represented by x_array and y_array. Requires that x_array and y_array are the same
   * length.
   */
  static int[] maxsep_point(long[] x_array, long[] y_array) {
    // Find the most separated pair.
    // Do I really need to check in two dimensions, or would one be enough?

    assert x_array.length == y_array.length;

    // indices of the most-separated pair of points
    int max_i = -1;
    int max_j = -1;
    // (square of the) distance between the most separated pair
    double max_separation = 0;
    for (int i = 0; i < x_array.length - 1; i++) {
      for (int j = i + 1; j < x_array.length; j++) {
        // not long, lest we get wraparound
        double xsep = x_array[i] - x_array[j];
        double ysep = y_array[i] - y_array[j];
        double separation = xsep * xsep + ysep * ysep;

        if (separation > max_separation) {
          max_separation = separation;
          max_i = i;
          max_j = j;
        }
      }
    }
    return new int[] {max_i, max_j};
  }
  /**
   * Returns a 2-element int array of the indices of the most separated pair of points between the
   * points within this.
   */
  int[] maxsep_indices() {
    return maxsep_point(x_cache, y_cache);
  }

  //  /**
  //   * Given ((x0,y0),(x1,y1)), finds a and b such that y = ax + b.
  //   * Places a and b (if they exist) into result at indexes 0 and 1
  //   * respectively.
  //   * @return true if such an a and b exist.
  //   */
  /**
   * Given ((x0,y0),(x1,y1)), finds a,b and c such that ax + by + c = 0. Places a, b and c (if they
   * exist) into result at indexes 0, 1 and 2 respectively.
   *
   * @return true if such an a and b exist
   */
  private static boolean find_bi_linear(
      long x0, long x1, long y0, long y1, double[] result) {
    if (x1 - x0 == 0) {         // not "x0 == x1", due to roundoff
      // x being constant would have been discovered elsewhere (and this
      // invariant would not have been instantiated).
      return false;
    }
    if (y1 - y0 == 0) {         // not "y0 == y1", due to roundoff
      // y being constant would have been discovered elsewhere (and this
      // invariant would not have been instantiated).
      return false;
    }

    long i_var = y1 - y0;
    long j_var = x0 - x1;
    long Stand_const = y0 * x1 - x0 * y1;

    // Put the coefficients in lowest terms by dividing by the gcd.
    // If the gcd is 1, no harm done -- divide by 1.
    // Type is double to ensure floating-point division.
    double myGCD = MathMDE.gcd(MathMDE.gcd(i_var, j_var), Stand_const);
    double standard_i_var = i_var / myGCD;
    double standard_j_var = j_var / myGCD;
    double standard_Stand_const = Stand_const / myGCD;

    // Ensure postive x coefficient by negating if "x" term is negative.
    if (i_var < 0) {
      standard_i_var = -standard_i_var;
      standard_j_var = -standard_j_var;
      standard_Stand_const = -standard_Stand_const;
    }

    result[0] = standard_i_var;
    result[1] = standard_j_var;
    result[2] = standard_Stand_const;

    return true;
  }

  /** Given ((x0,y0),(x1,y1)), set a, b and c such that ax + by + c = 0. */
  private InvariantStatus set_bi_linear(long x0, long x1, long y0, long y1) {
    double[] AandBandC = new double[3];
    if (!find_bi_linear(x0, x1, y0, y1, AandBandC)) {
      if (debug.isLoggable(Level.FINE)) {
        debug.fine("Suppressing LinearBinaryCore due to equal x values: (" + x0 + "," + y0 + "), (" + x1 + "," + y1 + ")");
      }
      return InvariantStatus.FALSIFIED;
    }
    a = AandBandC[0];
    b = AandBandC[1];
    c = AandBandC[2];
    wrapper.log("x0=%s, x1=%s, y0=%s, y1=%s", x0, x1, y0, y1);
    wrapper.log("a=%s, b=%s, c=%s", a, b, c);
    return InvariantStatus.NO_CHANGE;
  }

  public boolean enoughSamples(/*>>>@GuardSatisfied LinearBinaryCore this*/) {
    return (values_seen >= MINPAIRS);
  }

  public double computeConfidence() {
    return Invariant.conf_is_ge(values_seen, MINPAIRS);
  }

  public String repr(/*>>>@GuardSatisfied LinearBinaryCore this*/) {
    return "LinearBinaryCore" + wrapper.varNames() + ": a=" + a
      + ",b=" + b
      + ",c=" + c
      + ",values_seen=" + values_seen;
  }

  // Format one term of an equation.
  // Variable "first" indicates whether this is the leading term
  // Variable "varname" is the name of the variable; may be null
  // for the constant term.
  public static String formatTerm(double coeff, /*@Nullable*/ String varname, boolean first) {
    double ncoeff = coeff;

    if (ncoeff == 0) {
      return "";
    }
    String sign;
    if (ncoeff < 0) {
      if (first) {
        sign = "- ";
      } else {
        sign = " - ";
      }
    } else if (first) {
      sign = "";
    } else {
      sign = " + ";
    }

    //we want to ensure that the positive value is used because the sign has already been determined
    String coeff_string =
        (ncoeff == (int)ncoeff) ? "" + Math.abs((int)ncoeff) : "" + Math.abs(ncoeff);
    if (varname == null) {
      return sign + coeff_string;
    }
    if (ncoeff == 1 || ncoeff == -1) {
      return sign + varname;
    } else {
      return sign + coeff_string + " * " + varname;
    }
  }

  /*@SideEffectFree*/
  public String format_using(
      OutputFormat format, String vix, String viy, double u, double v, double w) {

    //recall that a, b, c in the linear binary equation means ax + by + c = 0;
    if (u == 0 && v == 0 && w == 0) {
      return wrapper.format_too_few_samples(format, "0 * " + vix + "+ 0 * " + viy + " + 0 == 0");
    }

    if (format.isJavaFamily()) {

        int a = (int) u;
        int b = (int) v;
        int c = (int) w;
        return formatTerm(a, vix, true)
          + formatTerm(b, viy, false)
          + formatTerm(c, null, false)
          + " == 0";

    }

    if ((format == OutputFormat.DAIKON)
        || (format == OutputFormat.ESCJAVA)
        || (format == OutputFormat.CSHARPCONTRACT)) {
      String eq = " == ";
      return formatTerm(u, vix, true)
        + formatTerm(v, viy, false)
        + formatTerm(w, null, false)
        + eq
        + "0";
    }

    if (format == OutputFormat.SIMPLIFY) {
      return format_simplify(vix, viy, u, v, w);
    }
    throw new Error("Unknown OutputFormat: " + format);
  }

  public static String format_simplify(String str_x, String str_y, double a, double b, double c) {

      int ia = (int) a;
      int ib = (int) b;
      int ic = (int) c;

      String str_a, str_b, str_c;
      if (ia == a && ib == b && ic == c) {
        // integer
        str_a = Invariant.simplify_format_long(ia);
        str_b = Invariant.simplify_format_long(ib);
        str_c = Invariant.simplify_format_long(ic);
      } else {
        // floating point; probably not very useful
        str_a = Invariant.simplify_format_double(a);
        str_b = Invariant.simplify_format_double(b);
        str_c = Invariant.simplify_format_double(c);
      }

      // ax + by + c = 0

      String str_ax = (a == 1.0) ? str_x : "(* " + str_a + " " + str_x + ")";
      String str_by = (b == 1.0) ? str_y : "(* " + str_b + " " + str_y + ")";
      String str_axby = "(+ " +  str_ax + " " + str_by + ")";
      String str_axpc = (c == 0.0) ? str_axby : "(+ " + str_axby + " " + str_c + ")";

      return "(EQ 0 " + str_axpc + ")";
  }

  /*@SideEffectFree*/
  public String format_using(OutputFormat format, String xname, String yname) {
    String result = format_using(format, xname, yname, a, b, c);
    if (result != null) {
      return result;
    } else {
      return wrapper.format_unimplemented(format);
    }
  }

  // Format as "x = cy+d" instead of as "y = ax+b". [now that we print in standard format, is
  // there a point to this method?
  public String format_reversed_using(OutputFormat format, String xname, String yname) {
    assert a == 1 || a == -1;
    return format_using(format, yname, xname, b, a, c);
  }

  /**
   * In general, we can't merge formulas, but we can merge invariants with too few samples to have
   * formed a line with invariants with enough samples. And those will appear to have different
   * formulas.
   */
  public boolean mergeFormulasOk() {
    return true;
  }

  /**
   * Merge the linear binary cores in cores to form a new core. Any core in the list that has seen
   * enough points to define a line, must define the same line. Any cores that have not yet seen
   * enough points, will have each of their points applied to that invariant. The merged core is
   * returned. Null is returned if the cores don't describe the same line
   *
   * @param cores list of LinearBinary cores to merge. They should all be permuted to match the
   *     variable order in ppt.
   */
  public /*@Nullable*/ LinearBinaryCore merge(List<LinearBinaryCore> cores, Invariant wrapper) {

    // Look for any active lines.  All must define the same line
    LinearBinaryCore result = null;
    for (LinearBinaryCore c : cores) {
      if (!c.isActive()) {
        continue;
      }
      if (result == null) {
        result = c.clone();
      } else {
        if (!Global.fuzzy.eq(result.a, c.a)
            || !Global.fuzzy.eq(result.b, c.b)
            || !Global.fuzzy.eq(result.c, c.c)) {
          return null;
        }
      }
    }

    // If no active lines were found, created an empty core
    if (result == null) {
      result = new LinearBinaryCore(wrapper);
    } else {
      result.wrapper = wrapper;
    }

    // Merge in any points from non-active cores
    for (LinearBinaryCore c : cores) {
      if (c.isActive()) {
        continue;
      }
      for (int j = 0; j < c.values_seen; j++) {
        if (result.add_modified(c.x_cache[j], c.y_cache[j], 1) == InvariantStatus.FALSIFIED) {
          //        if (wrapper.falsified)
          return null;
        }
      }
    }

    return result;
  }

  /*@Pure*/
  public boolean isSameFormula(LinearBinaryCore other) {
    boolean thisMeaningless = values_seen < MINPAIRS;
    boolean otherMeaningless = other.values_seen < MINPAIRS;

    if (thisMeaningless && otherMeaningless) {
      return true;
    } else {
      return (values_seen >= MINPAIRS)
        && (other.values_seen >= MINPAIRS)
        && (a == other.a)
        && (b == other.b)
        && (c == other.c);
    }
  }

  /*@Pure*/
  public boolean isExclusiveFormula(LinearBinaryCore other) {
    if ((values_seen < MINPAIRS) || (other.values_seen < MINPAIRS)) {
      return false;
    }

      return ((-a / b == -other.a / other.b) && (-c / b != -other.c / other.b));
  }
}
