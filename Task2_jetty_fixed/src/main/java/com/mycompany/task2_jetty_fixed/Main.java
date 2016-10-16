package com.mycompany.task2_jetty_fixed;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        ServletHandler handler = new ServletHandler();

        
        handler.addServletWithMapping(Temperature.class, "/getTemperature");
        handler.addServletWithMapping(RandomServlet.class, "/getRandom");
        
        
        //Create a new Server, add the handler to it and start
        Server server = new Server(8080);
        server.setHandler(handler);
        server.start();
        
        
        //this dumps a lot of debug output to the console.
        server.dumpStdErr();
        server.join();
    }
}
