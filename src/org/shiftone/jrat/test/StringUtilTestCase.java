package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.shiftone.jrat.util.StringUtil;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.Serializable;


/**
 * Class StringUtilTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.13 $
 */
public class StringUtilTestCase extends TestCase implements Serializable {

    private static final Log LOG = LogFactory.getLogger(StringUtilTestCase.class);

    /**
     * Method testParsePropertiesString
     */
    public void testParsePropertiesString() {

        StringUtil.parsePropertiesString("a=b|c=d|e=f");
        StringUtil.parsePropertiesString("a=b|vvv|c=d|e=f");

    }

    /**
     * Method testDurationToString
     */
    public void testDurationToString() {

        LOG.info(StringUtil.durationToString(5432));
        LOG.info(StringUtil.durationToString(5432234));
        LOG.info(StringUtil.durationToString(543253556));
        LOG.info(StringUtil.durationToString(543267678));
        LOG.info(StringUtil.durationToString(5432335689052434L));
        LOG.info(StringUtil.durationToString(2356543233568952434L));

    }

}
