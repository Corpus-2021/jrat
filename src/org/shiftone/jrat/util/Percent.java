package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.text.DecimalFormat;


/**
 * Class Percent
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class Percent extends Number {

    private static final Log     LOG               = LogFactory.getLogger(Percent.class);
    private static DecimalFormat pctDecimalFormat  = new DecimalFormat("#,###.0");
    public static final double   POSITIVE_INFINITY = 1.0 / 0.0;
    public static final double   NEGATIVE_INFINITY = -1.0 / 0.0;
    public static final double   NAN               = 0.0d / 0.0;
    public static final double   MAX_VALUE         = 1.7976931348623157e+308;
    public static final double   MIN_VALUE         = 4.9e-324;
    private double               value;

    /**
     * Constructor Percent
     *
     * @param value
     */
    public Percent(double value) {

        this.value = value;

    }

    /**
     * Constructor Percent
     *
     * @param s
     *
     * @throws NumberFormatException
     */
    public Percent(String s) throws NumberFormatException {

        // REMIND: this is inefficient
        this(valueOf(s).doubleValue());

    }

    /**
     * Method toString
     *
     * @param d .
     *
     * @return .
     */
    public static String toString(double d) {

        return Double.toString(d);

    }

    /**
     * Method valueOf
     *
     * @param s .
     *
     * @return .
     *
     * @throws NumberFormatException
     */
    public static Percent valueOf(String s) throws NumberFormatException {

        Assert.assertNotNull("string value", s);

        return new Percent(Double.parseDouble(s));

    }

    /**
     * Method parseDouble
     *
     * @param s .
     *
     * @return .
     *
     * @throws NumberFormatException
     */
    public static double parseDouble(String s) throws NumberFormatException {

        Assert.assertNotNull("string value", s);

        return Double.parseDouble(s);

    }

    /**
     * Method isNaN
     *
     * @param v .
     *
     * @return .
     */
    public static boolean isNaN(double v) {

        return (v != v);

    }

    /**
     * Method isInfinite
     *
     * @param v .
     *
     * @return .
     */
    public static boolean isInfinite(double v) {

        return (v == POSITIVE_INFINITY) || (v == NEGATIVE_INFINITY);

    }

    /**
     * Method isNaN
     *
     * @return .
     */
    public boolean isNaN() {

        return isNaN(value);

    }

    /**
     * Method isInfinite
     *
     * @return .
     */
    public boolean isInfinite() {

        return isInfinite(value);

    }

    /**
     * Method toString
     *
     * @return .
     */
    public String toString() {

        synchronized (pctDecimalFormat) {

            return pctDecimalFormat.format(value);

        }

        //return String.valueOf(value);
    }

    /**
     * Method byteValue
     *
     * @return .
     */
    public byte byteValue() {

        return (byte) value;

    }

    /**
     * Method shortValue
     *
     * @return .
     */
    public short shortValue() {

        return (short) value;

    }

    /**
     * Method intValue
     *
     * @return .
     */
    public int intValue() {

        return (int) value;

    }

    /**
     * Method longValue
     *
     * @return .
     */
    public long longValue() {

        return (long) value;

    }

    /**
     * Method floatValue
     *
     * @return .
     */
    public float floatValue() {

        return (float) value;

    }

    /**
     * Method doubleValue
     *
     * @return .
     */
    public double doubleValue() {

        return (double) value;

    }

    /**
     * Method hashCode
     *
     * @return .
     */
    public int hashCode() {

        long bits = Double.doubleToLongBits(value);

        return (int) (bits ^ (bits >>> 32));

    }

    /**
     * Method equals
     *
     * @param obj .
     *
     * @return .
     */
    public boolean equals(Object obj) {

        return ((obj instanceof Number) && ((((Number) obj).doubleValue()) == value));

    }

    /**
     * Method compareTo
     *
     * @param anotherPercent .
     *
     * @return .
     */
    public int compareTo(Percent anotherPercent) {

        Assert.assertNotNull("anotherPercent", anotherPercent);

        return Double.compare(value, anotherPercent.value);

    }

    /**
     * Method compareTo
     *
     * @param o .
     *
     * @return .
     */
    public int compareTo(Object o) {

        return compareTo((Percent) o);

    }

}
