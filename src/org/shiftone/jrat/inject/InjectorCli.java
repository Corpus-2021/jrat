package org.shiftone.jrat.inject;

import java.io.PrintStream;


/**
 * Class InjectorCli
 *
 * @author $Author: jeffdrost $
 * @version $Revision: 1.4 $
 */
public class InjectorCli {

    private static PrintStream OUT = System.out;

    /**
     * Method main
     *
     * @param args .
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        if (args.length == 0) {

            OUT.println("usage : ");
            OUT.println("\tinject [target]");
            OUT.println("\tinject [source] [target]");

        } else {

            Injector injector = new Injector();

            if (args.length == 1) {

                injector.setForceOverwrite(true);
                injector.inject(args[0]);

            } else if (args.length == 2) {

                injector.setForceOverwrite(true);
                injector.inject(args[0], args[1]);

            }

        }

    }

}
