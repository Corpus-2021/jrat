
/*
 * User: jeff
 */
package org.shiftone.jrat.jda.env;



import com.sun.jdi.Bootstrap;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;
import com.sun.jdi.connect.ListeningConnector;

import org.shiftone.jrat.util.log.Log;
import org.shiftone.jrat.util.log.LogFactory;

import java.io.PrintStream;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Class EnvPrinter
 *
 *
 * @author <a href="mailto:jeff@shiftone.org">Jeff Drost</a>
 */
public class EnvPrinter
{

    private static final Log   LOG        = LogFactory.getLogger(EnvPrinter.class);
    public static final String MAIN_TAG   = "jrat-example-connectors";
    public static final String LAUNCH_TAG = "launching-connector";
    public static final String ATTACH_TAG = "attaching-connector";
    public static final String LISTEN_TAG = "listening-connector";

    /**
     * Method rpad
     */
    private String rpad(String str, int length)
    {

        while (str.length() < length)
        {
            str += " ";
        }

        return str;
    }

    /**
     * Method required
     */
    private String required(Connector.Argument arg)
    {

        return arg.mustSpecify()
               ? "required"
               : "optional";
    }

    /**
     * Method dataType
     */
    private String dataType(Connector.Argument arg)
    {

        if (arg instanceof Connector.BooleanArgument)
        {
            return "boolean";
        }
        else if (arg instanceof Connector.IntegerArgument)
        {
            return "integer";
        }
        else if (arg instanceof Connector.StringArgument)
        {
            return "string";
        }
        else if (arg instanceof Connector.SelectedArgument)
        {
            List choices = ((Connector.SelectedArgument) arg).choices();

            return "selected " + choices;
        }
        else
        {
            return "unknown";
        }
    }

    /**
     * Method printConnector
     */
    private void printConnector(String tag, Connector connector, PrintStream out)
    {

        Map      args        = connector.defaultArguments();
        Iterator keyIterator = args.keySet().iterator();

        out.println("<!-- " + connector.description() + " -->");
        out.println("<!-- Transport : " + connector.transport().name() + " -->");
        out.println("<" + tag + " name=\"" + connector.name() + "\">\n");

        while (keyIterator.hasNext())
        {
            String             key   = (String) keyIterator.next();
            Connector.Argument value = (Connector.Argument) args.get(key);

            out.println("\t<!-- " + value.label() + " - " + required(value) + " " + dataType(value) + " -->");
            out.println("\t<!-- " + value.description() + " -->");
            out.print("\t<argument ");
            out.print(rpad("name=\"" + key + "\"", 15));
            out.println("value=\"" + value.value() + "\"/>\n");
        }

        out.println("</" + tag + ">\n");
    }

    /**
     * Method printConnector
     */
    private void printConnector(Connector connector, PrintStream out)
    {

        if (connector instanceof LaunchingConnector)
        {
            printConnector(LAUNCH_TAG, connector, out);
        }

        if (connector instanceof AttachingConnector)
        {
            printConnector(ATTACH_TAG, connector, out);
        }

        if (connector instanceof ListeningConnector)
        {
            printConnector(LISTEN_TAG, connector, out);
        }
    }

    /**
     * Method printAvalibleConfigs
     */
    public void printAvalibleConfigs()
    {

        PrintStream           out        = System.out;
        VirtualMachineManager vmManager  = null;
        List                  connectors = null;

        //BasicConfigurator.configure();
        vmManager  = Bootstrap.virtualMachineManager();
        connectors = vmManager.allConnectors();

        out.println("<" + MAIN_TAG + ">");

        for (int i = 0; i < connectors.size(); i++)
        {
            printConnector((Connector) connectors.get(i), out);
        }

        out.println("</" + MAIN_TAG + ">");
    }

    /**
     * Method main
     */
    public static void main(String[] args)
    {

        EnvPrinter printer = new EnvPrinter();

        printer.printAvalibleConfigs();
    }
}
