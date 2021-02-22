package org.shiftone.jrat.core;

import org.shiftone.jrat.util.StringUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class Accumulator
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.13 $
 */
public class Accumulator {

    private static final Log LOG              = LogFactory.getLogger(Accumulator.class);
    private long             totalEnters      = 0;
    private long             totalExits       = 0;
    private long             totalErrors      = 0;
    private long             totalDuration    = 0; // used for mean
    private long             totalOfSquares   = 0; // used for std dev
    private long             maxDuration      = 0;
    private long             minDuration      = Long.MAX_VALUE;
    private int              concurThreads    = 0;
    private int              maxConcurThreads = 0;

    /**
     * Constructor Accumulator
     */
    public Accumulator() {

    }

    /**
     * Constructor Accumulator
     *
     * @param totalEnters
     * @param totalExits
     * @param totalErrors
     * @param totalDuration
     * @param totalOfSquares
     * @param maxDuration
     * @param minDuration
     * @param maxConcurThreads
     */
    public Accumulator(long totalEnters, long totalExits, long totalErrors, long totalDuration, long totalOfSquares, long maxDuration,
        long minDuration, int maxConcurThreads) {

        setStatistics(totalEnters, totalExits, totalErrors, totalDuration, //
            totalOfSquares, maxDuration, minDuration, maxConcurThreads);

    }

    /**
     * Method setStatistics
     *
     * @param totalEnters .
     * @param totalExits .
     * @param totalErrors .
     * @param totalDuration .
     * @param totalOfSquares .
     * @param maxDuration .
     * @param minDuration .
     * @param maxConcurThreads .
     */
    public void setStatistics(long totalEnters, long totalExits, long totalErrors, long totalDuration, long totalOfSquares, long maxDuration,
        long minDuration, int maxConcurThreads) {

        this.totalEnters          = totalEnters;
        this.totalExits           = totalExits;
        this.totalErrors          = totalErrors;
        this.totalDuration        = totalDuration;
        this.totalOfSquares       = totalOfSquares;
        this.maxDuration          = maxDuration;
        this.minDuration          = minDuration;
        this.maxConcurThreads     = maxConcurThreads;

    }

    /**
     * Method onMethodStart
     */
    public final synchronized void onMethodStart() {

        totalEnters++;
        concurThreads++;

        if (concurThreads > maxConcurThreads) {

            maxConcurThreads = concurThreads;

        }

    }

    /**
     * Method onMethodFinish
     *
     * @param duration .
     * @param success .
     */
    public final synchronized void onMethodFinish(long duration, boolean success) {

        totalExits++;

        totalDuration += duration;
        totalOfSquares += (duration * duration);

        if (!success) {

            totalErrors++;

        }

        if (duration < minDuration) {

            minDuration = duration;

        }

        if (duration > maxDuration) {

            maxDuration = duration;

        }

        concurThreads--;

    }

    /**
     * Method getAverageDuration
     *
     * @return .
     */
    public final Float getAverageDuration() {

        Float average = null;

        if (totalExits > 0) {

            average = new Float((double) totalDuration / (double) totalExits);

        }

        return average;

    }

    /**
     * Method getStdDeviation
     *
     * @return .
     */
    public final Double getStdDeviation() {

        Double stdDeviation = null;

        if (totalExits > 1) {

            double numerator   = totalOfSquares - ((double) (totalDuration * totalDuration) / (double) totalExits);
            double denominator = totalExits - 1.0;

            stdDeviation = new Double(Math.sqrt(numerator / denominator));

        }

        return stdDeviation;

    }

    /**
     * Method getTotalEnters
     *
     * @return .
     */
    public final long getTotalEnters() {

        return totalEnters;

    }

    /**
     * Method getTotalCalls
     *
     * @return .
     */
    public final long getTotalExits() {

        return totalExits;

    }

    /**
     * Method getMinDuration
     *
     * @return .
     */
    public final Long getMinDuration() {

        return (totalExits == 0) ? null : new Long(minDuration);

    }

    /**
     * Method getMaxDuration
     *
     * @return .
     */
    public final Long getMaxDuration() {

        return (totalExits == 0) ? null : new Long(maxDuration);

    }

    /**
     * Method getTotalDuration
     *
     * @return .
     */
    public final long getTotalDuration() {

        return totalDuration;

    }

    /**
     * Method getSumOfSquares
     *
     * @return .
     */
    public final long getSumOfSquares() {

        return totalOfSquares;

    }

    /**
     * Method getTotalErrors
     *
     * @return .
     */
    public final long getTotalErrors() {

        return totalErrors;

    }

    /**
     * Method getMaxConcurrentThreads
     *
     * @return .
     */
    public int getMaxConcurrentThreads() {

        return maxConcurThreads;

    }

    /**
     * Method toCSV
     *
     * @param acc .
     *
     * @return .
     */
    public static String toCSV(Accumulator acc) {

        StringBuffer sb = new StringBuffer(100);

        sb.append(acc.totalEnters); //0
        sb.append(',');
        sb.append(acc.totalExits); //1
        sb.append(',');
        sb.append(acc.totalErrors); //2
        sb.append(',');
        sb.append(acc.totalDuration); //3
        sb.append(',');
        sb.append(acc.totalOfSquares); //4
        sb.append(',');
        sb.append(acc.maxConcurThreads); //5

        if (acc.totalExits != 0) {

            sb.append(',');
            sb.append(acc.maxDuration); //6
            sb.append(',');
            sb.append(acc.minDuration); //7

        }

        return sb.toString();

    }

    /**
     * Method fromCSV
     *
     * @param csv .
     * @param acc .
     *
     * @return .
     *
     * @throws ParseException
     */
    public static Accumulator fromCSV(String csv, Accumulator acc)
        throws ParseException {

        String[] tokens = StringUtil.tokenize(csv, ",", false);

        if ((tokens.length != 6) && (tokens.length != 8)) {

            throw new ParseException("accumulator CSV must contain 6 or 8 tokens : " + tokens.length + " in " + csv);

        }

        if (tokens.length >= 6) {

            acc.totalEnters          = Long.parseLong(tokens[0]);
            acc.totalExits           = Long.parseLong(tokens[1]);
            acc.totalErrors          = Long.parseLong(tokens[2]);
            acc.totalDuration        = Long.parseLong(tokens[3]);
            acc.totalOfSquares       = Long.parseLong(tokens[4]);
            acc.maxConcurThreads     = Integer.parseInt(tokens[5]);

        }

        if (tokens.length == 8) {

            acc.maxDuration     = Long.parseLong(tokens[6]);
            acc.minDuration     = Long.parseLong(tokens[7]);

        }

        return acc;

    }

    /**
     * Method fromCSV
     *
     * @param csv .
     *
     * @return .
     *
     * @throws ParseException
     */
    public static Accumulator fromCSV(String csv) throws ParseException {

        return fromCSV(csv, new Accumulator());

    }

}
