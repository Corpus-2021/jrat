package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class GlobMatcher
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.8 $
 */
public class GlobMatcher {

    private static final Log        LOG          = LogFactory.getLogger(GlobMatcher.class);
    public static final GlobMatcher INCLUDE_ALL  = new GlobMatcher("*");
    private char[][]                patternParts = null;

    /**
     * Constructor GlobMatcher
     *
     * @param pattenString initializes the matcher with the glob patterm.
     */
    public GlobMatcher(String pattenString) {

        Assert.assertNotNull("pattenString", pattenString);
        patternParts = getPatternParts(pattenString);

    }

    /**
     * Method isMatch
     *
     * @param inputString .
     *
     * @return .
     */
    public boolean isMatch(String inputString) {

        Assert.assertNotNull("inputString", inputString);

        return isMatch(inputString, patternParts);

    }

    /**
     * Method getPatternParts
     *
     * @param pattenString .
     *
     * @return .
     */
    private static char[][] getPatternParts(String pattenString) {

        String[] sections     = StringUtil.tokenize(pattenString, "*", true);
        char[][] patternParts = new char[sections.length][];

        for (int i = 0; i < sections.length; i++) {

            patternParts[i] = sections[i].toCharArray();

        }

        return patternParts;

    }

    /**
     * <b>used by unit tests only</b>
     *
     * @param inputString .
     * @param pattenString .
     *
     * @return .
     */
    public static boolean isMatch(String inputString, String pattenString) {

        return isMatch(inputString, getPatternParts(pattenString));

    }

    /**
     * Method isMatch
     *
     * @param input .
     * @param patternParts .
     *
     * @return .
     */
    public static boolean isMatch(String input, char[][] patternParts) {

        char[]  in           = input.toCharArray();
        boolean canSkip      = false;
        int     currentIndex = 0;

        for (int i = 0; i < patternParts.length; i++) {

            if (patternParts[i][0] == '*') {

                canSkip = true;

            } else {

                if (canSkip == false) {

                    if (!matchesFixed(in, currentIndex, patternParts[i])) {

                        return false;

                    }

                } else {

                    int m = nextFixedMatch(in, currentIndex, patternParts[i]);

                    if (m == -1) {

                        return false;

                    } else {

                        currentIndex = m + patternParts[i].length;

                    }

                }

                canSkip = false;

            }

        }

        return true;

    }

    /**
     * <b>used by unit tests only</b>
     *
     * @param cs .
     * @param offSet .
     * @param ps .
     *
     * @return .
     */
    public static int nextFixedMatch(String cs, int offSet, String ps) {

        return nextFixedMatch(cs.toCharArray(), offSet, ps.toCharArray());

    }

    /**
     * Method nextFixedMatch
     *
     * @param cs .
     * @param offSet .
     * @param ps .
     *
     * @return .
     */
    public static int nextFixedMatch(char[] cs, int offSet, char[] ps) {

        int endIndex = cs.length - ps.length + 1;

        for (int i = offSet; i < endIndex; i++) {

            if (matchesFixed(cs, i, ps)) {

                return i;

            }

        }

        return -1;

    }

    /**
     * <b>used by unit tests only</b>
     *
     * @param cs .
     * @param offSet .
     * @param ps .
     *
     * @return .
     */
    public static boolean matchesFixed(String cs, int offSet, String ps) {

        return matchesFixed(cs.toCharArray(), offSet, ps.toCharArray());

    }

    /**
     * Method matchesFixed
     *
     * @param cs .
     * @param offSet .
     * @param ps .
     *
     * @return .
     */
    public static boolean matchesFixed(char[] cs, int offSet, char[] ps) {

        for (int i = 0; i < ps.length; i++) {

            int c = i + offSet;

            if (c >= cs.length) {

                return false;

            }

            if ((cs[c] != ps[i]) && (ps[i] != '?')) {

                return false;

            }

        }

        return true;

    }

}
