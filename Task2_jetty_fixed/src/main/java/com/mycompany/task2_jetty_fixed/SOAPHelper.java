package com.mycompany.task2_jetty_fixed;

import java.util.Map;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SOAPHelper
{

    private static SOAPConnectionFactory soapConnectionFactory;
    private static SOAPConnection soapConnection;

    public static SOAPMessage DoSOAP(String serverurl, String endpoint, String action, Map<String, String> parameters) throws SOAPException, Exception
    {
        soapConnectionFactory = SOAPConnectionFactory.newInstance();
        soapConnection = soapConnectionFactory.createConnection();

        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(serverurl, endpoint, action, parameters), serverurl + action + ".asmx");

        //System.out.print("Response SOAP Message:");
        //soapResponse.writeTo(System.out);

        return soapResponse;
    }

    private static SOAPMessage createSOAPRequest(String serverurl, String endpoint, String action, Map<String, String> parameters) throws Exception
    {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        

        String serverURI = serverurl;

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("soap", serverURI);
        //soapMessage.getSOAPHeader().getParentNode().removeChild(soapMessage.getSOAPHeader());

        //soapMessage.getSOAPPart().getEnvelope().setPrefix("soap12");
        //soapMessage.getSOAPHeader().setPrefix("soap12");
        //soapMessage.getSOAPBody().setPrefix("soap12");
        soapMessage.setProperty(SOAPMessage.WRITE_XML_DECLARATION, "true");
        
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();

        SOAPElement afterBody = soapBody.addChildElement(endpoint);
        afterBody.setAttribute("xmlns", serverURI);

        for (Map.Entry<String, String> entry : parameters.entrySet())
        {
            SOAPElement soapElem = afterBody.addChildElement(entry.getKey());
            soapElem.addTextNode(entry.getValue());
        }

        MimeHeaders headers = soapMessage.getMimeHeaders();

        headers.addHeader("SOAPAction", "\"" + serverURI + endpoint + "\"");
        headers.addHeader("Content-Type", "application/soap+xml; charset=utf-8");
        //headers.addHeader("Content-Length", String.valueOf(soapMessage.getSOAPBody().toString().getBytes().length));

        soapMessage.saveChanges();

        /* Print the request message */
        //System.out.print("Request SOAP Message:");
        //soapMessage.writeTo(System.out);
        //System.out.println();

        return soapMessage;
    }

}
