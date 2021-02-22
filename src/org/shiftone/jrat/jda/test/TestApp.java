package org.shiftone.jrat.jda.test;



/**
 * Class TestApp
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class TestApp
{

    /**
     * Method log
     */
    private void log(String message)
    {
        System.out.println(">" + message);
    }

    int c = 0;

    /**
     * Constructor TestApp
     *
     */
    public TestApp() {}

    /**
     * Method a
     */
    public void a()
    {

        log("a");
        sleep(100);
        b();
    }

    /**
     * Method b
     */
    public void b()
    {

        log("b");
        sleep(200);
        c();
    }

    /**
     * Method c
     */
    public void c()
    {
        log("c");
    }

    /**
     * Method sleep
     */
    private void sleep(int t)
    {

        try
        {
            Thread.sleep(100);
        }
        catch (Exception e) {}

        c++;

        log("c = " + c);
    }

    /**
     * Method go
     *
     * @throws Exception
     */
    public void go() throws Exception
    {

        log("go");

        for (int i = 0; i < 2; i++)
        {
            a();
            sleep(100);
        }
    }

    /**
     * Method main
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {

        try
        {
            TestApp testApp1 = new TestApp();

            testApp1.go();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            throw e;
        }
    }
}
