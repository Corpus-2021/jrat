package org.shiftone.jrat.test;

import junit.framework.TestCase;

import org.apache.bcel.Repository;

import org.apache.bcel.classfile.JavaClass;

import org.shiftone.jrat.inject.criteria.ClassCriterion;
import org.shiftone.jrat.inject.criteria.InjectionCriteria;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class InjectionCriteriaTestCase
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class InjectionCriteriaTestCase extends TestCase {

    private static final Log LOG = LogFactory.getLogger(InjectionCriteriaTestCase.class);

    /**
     * Method testIncludeAllClasses
     *
     * @throws Exception
     */
    public void testIncludeAllClasses() throws Exception {

        InjectionCriteria criteria  = new InjectionCriteria();
        JavaClass         javaClass = null;

        javaClass = Repository.lookupClass("java.lang.Long");

        assertTrue(criteria.isMatch(javaClass));

        javaClass = Repository.lookupClass("java.util.HashMap");

        assertTrue(criteria.isMatch(javaClass));

        javaClass = Repository.lookupClass(this.getClass().getName());

        assertTrue(criteria.isMatch(javaClass));

    }

    /**
     * Method testIncludeJavaDotLang
     *
     * @throws Exception
     */
    public void testIncludeJavaDotLang() throws Exception {

        InjectionCriteria criteria  = new InjectionCriteria();
        ClassCriterion    criterion = null;
        JavaClass         javaClass = null;

        criterion = criteria.createIncludeClass();

        criterion.setClass("java.lang.*");

        javaClass = Repository.lookupClass("java.lang.Long");

        assertTrue(criterion.isMatch(javaClass));
        assertTrue(criteria.isMatch(javaClass));

        javaClass = Repository.lookupClass("java.util.HashMap");

        assertFalse(criterion.isMatch(javaClass));
        assertFalse(criteria.isMatch(javaClass));

        javaClass = Repository.lookupClass("java.lang.reflect.Proxy");

        assertTrue(criterion.isMatch(javaClass));
        assertTrue(criteria.isMatch(javaClass));

    }

    /**
     * Method testIncludeClass
     *
     * @throws Exception
     */
    public void testIncludeExcludeClass() throws Exception {

        InjectionCriteria criteria         = new InjectionCriteria();
        ClassCriterion    includeCriterion = null;
        ClassCriterion    excludeCriterion = null;
        JavaClass         javaClass        = null;

        includeCriterion     = criteria.createIncludeClass();
        excludeCriterion     = criteria.createExcludeClass();

        includeCriterion.setClass("java.lang.*");
        excludeCriterion.setClass("java.lang.reflect.*");

        javaClass = Repository.lookupClass("java.lang.Long");

        assertTrue(includeCriterion.isMatch(javaClass));
        assertTrue(criteria.isMatch(javaClass));

        javaClass = Repository.lookupClass("java.util.HashMap");

        assertFalse(includeCriterion.isMatch(javaClass));
        assertFalse(criteria.isMatch(javaClass));

        javaClass = Repository.lookupClass("java.lang.reflect.Proxy");

        assertTrue(includeCriterion.isMatch(javaClass));
        assertTrue(excludeCriterion.isMatch(javaClass));
        assertFalse(criteria.isMatch(javaClass));

    }

}
