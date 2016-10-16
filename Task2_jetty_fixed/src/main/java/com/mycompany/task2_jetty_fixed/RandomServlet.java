package com.mycompany.task2_jetty_fixed;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RandomServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/xml");
        
        if(req.getParameter("min") == null || req.getParameter("max") == null)
        {
            String result = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
                "<Result><Error>getRandom requires min and max -parameters!</Error></Result>";
            
            PrintWriter out = resp.getWriter();
            out.print(result);
            out.flush();
            out.close();
            
            return;
        }
        
        int min = Integer.parseInt(req.getParameter("min"));        
        int max = Integer.parseInt(req.getParameter("max"));
        
        String random = String.valueOf(Rand.getRand(min, max));
        
        String result = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
                "<Result><RandomValue>" + random + "</RandomValue></Result>";
        
        PrintWriter out = resp.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }
}
