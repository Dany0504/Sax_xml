package org.example;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class SalesReportSAX extends DefaultHandler {

    private double totalSales = 0.0;
    private boolean inSales = false;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, proporciona el archivo XML como argumento.");
            return;
        }

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            SalesReportSAX handler = new SalesReportSAX();
            File inputFile = new File(args[0]);
            saxParser.parse(inputFile, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Iniciando análisis del archivo XML...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("sales")) {
            inSales = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inSales) {
            String salesValue = new String(ch, start, length).trim();
            try {
                double sales = Double.parseDouble(salesValue);
                totalSales += sales;
                System.out.println("Venta: $" + sales);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir el valor de ventas: " + salesValue);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("sales")) {
            inSales = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Análisis finalizado. Ventas totales: $" + totalSales);
    }
}
