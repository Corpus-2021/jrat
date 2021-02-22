package org.shiftone.jrat.ui.inject;



import java.awt.Frame;
import java.io.File;
import java.lang.reflect.Method;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.shiftone.jrat.inject.Injector;
import org.shiftone.jrat.ui.UIConstants;
import org.shiftone.jrat.util.GlobClassLoader;
import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;


/**
 * Class InjectionManager
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 * @version $Revision: 1.4 $
 */
public class InjectionManager implements UIConstants
{

    private static final Log         LOG            = LogFactory.getLogger(InjectionManager.class);
    private static final ClassLoader PARENT_LOADER  = InjectionManager.class.getClassLoader();
    private static final String      INJECTOR_CLASS = Injector.class.getName();
    private Frame                    parentFrame    = null;
    private Object                   injector       = null;
    private Class                    injectorClass  = null;

    /**
     * Constructor InjectionManager
     *
     *
     * @param parentFrame
     */
    public InjectionManager(Frame parentFrame)
    {
        this.parentFrame = parentFrame;
    }

    /**
     * Method injectJar
     *
     * @throws java.lang.Exception
     *
     * @throws Exception
     */
    public void inject(final File file) throws Exception
    {

        final Method method;

        LOG.info("inject " + file);

        if (canProceed())
        {
            method = injectorClass.getMethod("inject", new Class[]{ File.class });

            try
            {
                method.invoke(injector, new Object[]{ file });
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method setBcelJar
     */
    public boolean setInjector(ClassLoader loader)
    {

        Boolean   success   = Boolean.FALSE;
        Method    method    = null;
        Object    ret       = null;
        Exception exception = null;

        LOG.info("setInjector()");

        try
        {
            injectorClass = loader.loadClass(INJECTOR_CLASS);
            injector      = injectorClass.newInstance();

            //
            method = injectorClass.getMethod("setForceOverwrite", new Class[]{ Boolean.TYPE });
            ret    = method.invoke(injector, new Object[]{ Boolean.TRUE });

            //
            method  = injectorClass.getMethod("isBCELPresent", new Class[]{});
            ret     = method.invoke(injector, new Object[]{});
            success = (Boolean) ret;
        }
        catch (Exception e)
        {
            LOG.info("error creating Processor", e);

            exception = e;
        }

        if (success.booleanValue() == false)
        {
            injectorClass = null;
            injector      = null;

            if (exception == null)
            {
                exception = new Exception("Processor is unable to access BCEL");
            }
        }

        return success.booleanValue();
    }

    /**
     * Method setBcelJar
     */
    public boolean setInjectorWithJarFile(File bcelJar)
    {

        boolean     result = false;
        ClassLoader loader;

        try
        {
            LOG.info("setWithJarFile");

            //loader = new ChainClassLoader(bcelJar, PARENT_LOADER);
            loader = new GlobClassLoader(bcelJar, "org.shiftone.jrat.inject.*", PARENT_LOADER);
            result = setInjector(loader);
        }
        catch (Exception e)
        {
            LOG.info("setInjectorWithJarFile", e);
        }

        return result;
    }

    /**
     * Method setWithDefaultClassLoader
     */
    public boolean setInjectorWithDefaultClassLoader()
    {

        LOG.info("setWithDefaultClassLoader");

        return setInjector(PARENT_LOADER);
    }

    /**
     * Method userSelect
     */
    public boolean setInjectorWithUserSelection()
    {

        File         file    = null;
        JFileChooser chooser = new JFileChooser();

        chooser.addChoosableFileFilter(BCEL_FILE_FILTER);

        if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(parentFrame))
        {
            file = chooser.getSelectedFile();

            LOG.info(file.getAbsolutePath());

            if (setInjectorWithJarFile(file))
            {
                SETTINGS.setBcelJarFile(file);

                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(    //
                    parentFrame,                  //
                    BAD_BCEL_MSG,                 //
                    BAD_BCEL_TITLE,               //
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }

    /**
     * Method assertReady
     */
    public boolean canProceed()
    {

        File jarFile = SETTINGS.getBcelJarFile();

        if ((injectorClass != null) && (injector != null))
        {
            return true;
        }
        else if (setInjectorWithDefaultClassLoader())
        {
            return true;
        }
        else if ((jarFile != null) && (setInjectorWithJarFile(jarFile)))
        {
            return true;
        }
        else
        {
            int choice = JOptionPane.showOptionDialog(    //
                parentFrame,                              //
                PICK_BCEL_MSG,                            //
                PICK_BCEL_TITLE,                          //
                JOptionPane.YES_NO_OPTION,                //
                JOptionPane.QUESTION_MESSAGE,             //
                null,                                     //
                new Object[]{ YES, NO },                  //
                null);

            if (choice == 0)                              // yes
            {
                return setInjectorWithUserSelection();
            }

            return false;
        }
    }
}
