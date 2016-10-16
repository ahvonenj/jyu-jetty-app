package com.mycompany.task2_jetty_fixed;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class Temperature extends HttpServlet
{

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/xml");
        
        if(req.getParameter("temperature") == null || 
           req.getParameter("fromunit") == null ||
           req.getParameter("tounit") == null)
        {
            String result = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
                "<Result><Error>" + 
                "getTemperature requires temperature, " +
                "fromunit, and tounit -parameters!" +
                "</Error></Result>";
            
            PrintWriter out = resp.getWriter();
            out.print(result);
            out.flush();
            out.close();
        }
        
        String temperature = req.getParameter("temperature");        
        String fromunit = req.getParameter("fromunit");
        String tounit = req.getParameter("tounit");
        
        try
        {
            SOAPMessage temperatureResult = convertTemperature(temperature, fromunit, tounit);
            temperatureResult.writeTo(resp.getOutputStream());
        }
        catch(SOAPException e)
        {
            PrintWriter out = resp.getWriter();
            out.print("Error: SOAPException");
            out.flush();
            out.close();
        }   
        catch(Exception e)
        {
            PrintWriter out = resp.getWriter();
            out.print("Error: Exception");
            out.flush();
            out.close();
        }
    }

    @WebMethod(operationName = "convertTemperature",
               action = "action",
               exclude = false)
    @WebResult(name = "TemperatureResult")
    private SOAPMessage convertTemperature(
            String temperature_p,
            String fromunit_p,
            String tounit_p
    ) throws SOAPException, Exception
    {
        String url = "http://www.webserviceX.NET/";
        String endpoint = "ConvertTemp";
        String action = "ConvertTemperature";
        Map<String, String> parameters = new HashMap<String, String>();

        parameters.put("Temperature", temperature_p);
        parameters.put("FromUnit", fromunit_p);
        parameters.put("ToUnit", tounit_p);
        
        SOAPMessage m = SOAPHelper.DoSOAP(url, endpoint, action, parameters);
        
        return m;
    }
}
