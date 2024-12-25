// XMLParser.java
package com.example.istyleapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParser {

    public static ArrayList<Product> parseProductosXML(String xml) {
        ArrayList<Product> productos = new ArrayList<>();
        try {
            // Crear el parser para el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            doc.getDocumentElement().normalize();

            // Obtener todos los nodos <usuario> dentro del XML
            NodeList nodeList = doc.getElementsByTagName("producto");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Obtener los datos de cada <usuario>
                    String nombre = element.getElementsByTagName("nombre_producto").item(0).getTextContent();
                    double precio = Double.parseDouble(element.getElementsByTagName("precio").item(0).getTextContent());
                    String categoria = element.getElementsByTagName("nombre_categoria").item(0).getTextContent();
                    String imgPath = element.getElementsByTagName("imagen").item(0).getTextContent();

                    // Crear el objeto Producto y agregarlo a la lista
                    Product product = new Product(nombre, precio, categoria, imgPath);
                    productos.add(product);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    public static ArrayList<Review> parseReviewsXML(String xml, String productoFiltrado) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("valoracion");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String producto = element.getElementsByTagName("nombre_producto").item(0).getTextContent();
                    if (producto.equals(productoFiltrado)) {
                        float rating = Float.parseFloat(element.getElementsByTagName("rating").item(0).getTextContent());
                        String descripcion = element.getElementsByTagName("descripcion").item(0).getTextContent();
                        String fecha = element.getElementsByTagName("fecha").item(0).getTextContent();
                        String email = element.getElementsByTagName("email_usuario").item(0).getTextContent();

                        reviews.add(new Review(email, descripcion, fecha, rating));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
