package org.shiftone.jrat.util;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * Class GlobClassLoader
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.7 $
 */
public class GlobClassLoader extends URLClassLoader {

    private static final Log LOG         = LogFactory.getLogger(GlobClassLoader.class);
    private ClassLoader      parent      = null;
    private GlobMatcher      globMatcher = null;

    /**
     * Constructor GlobClassLoader
     *
     * @param urls
     * @param globPattern
     * @param parent
     */
    public GlobClassLoader(URL[] urls, String globPattern, ClassLoader parent) {
        super(urls, parent);

        this.parent          = parent;
        this.globMatcher     = new GlobMatcher(globPattern);

    }

    /**
     * Constructor GlobClassLoader
     *
     * @param file
     * @param globPattern
     * @param parent
     *
     * @throws MalformedURLException
     */
    public GlobClassLoader(File file, String globPattern, ClassLoader parent)
        throws MalformedURLException {
        this(new URL[] { file.toURL() }, globPattern, parent);

    }

    /**
     * Constructor GlobClassLoader
     *
     * @param urls
     * @param globPattern
     */
    public GlobClassLoader(URL[] urls, String globPattern) {
        super(urls);

        globMatcher = new GlobMatcher(globPattern);

    }

    /**
     * Method loadClass
     *
     * @param name .
     * @param resolve .
     *
     * @return .
     *
     * @throws ClassNotFoundException
     */
    protected synchronized Class loadClass(String name, boolean resolve)
        throws ClassNotFoundException {

        Class klass = null;

        if (globMatcher.isMatch(name)) {

            klass = loadClassHere(name);

        } else {

            klass = super.loadClass(name, resolve);

        }

        return klass;

    }

    /**
     * Method loadClassHere
     *
     * @param name .
     *
     * @return .
     *
     * @throws ClassNotFoundException
     */
    private Class loadClassHere(String name) throws ClassNotFoundException {

        Class  klass        = null;
        String resourceName = null;
        byte[] bytes        = null;

        LOG.debug("loadClassHere(" + name + ")");

        try {

            resourceName     = name.replace('.', '/').concat(".class");
            bytes            = loadClassData(resourceName);
            klass            = defineClass(name, bytes, 0, bytes.length);

        } catch (Exception e) {

            throw new ClassNotFoundException("not found :" + name, e);

        }

        return klass;

    }

    /**
     * Method loadClassData
     *
     * @param resourceName .
     *
     * @return .
     *
     * @throws IOException
     */
    private byte[] loadClassData(String resourceName) throws IOException {

        ClassLoader           loader = null;
        ByteArrayOutputStream out = null;
        InputStream           in  = null;

        in = getResourceAsStream(resourceName);

        if (in == null) {

            throw new IOException("class resource not found : " + resourceName);

        }

        out = new ByteArrayOutputStream();

        IOUtil.copy(in, out);

        return out.toByteArray();

    }

}
