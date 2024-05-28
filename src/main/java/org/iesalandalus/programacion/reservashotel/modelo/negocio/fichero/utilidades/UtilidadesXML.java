package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UtilidadesXML {

    private UtilidadesXML(){
        // Para evitar que se creen instancias.
    }

    public static Document xmlToDom(String CaminoAlArchivoXml) {
        Document documento = null;
        try {
            // 1º Creamos una nueva instancia de una fábrica de constructores de documentos.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 2º A partir de la instancia anterior, fabricamos un constructor de documentos, que procesará el XML.
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 3º Procesamos el documento (almacenado en un archivo) y lo convertimos en un árbol DOM.
            documento=db.parse(CaminoAlArchivoXml);
            documento.getDocumentElement().normalize();
        } catch (Exception er) {
            System.out.println("¡Error! No se ha podido cargar el documento XML.");
        }
        return documento;
    }

    public static void domToXml(Document doc, String CaminoAlArchivoXML) {
        try {
            // 1º Creamos una instancia de la clase File para acceder al archivo donde guardaremos el XML.
            File f=new File(CaminoAlArchivoXML);
            //2º Creamos una nueva instancia del transformador a través de la fábrica de transformadores.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            //3º Establecemos algunas opciones de salida, como por ejemplo, la codificación de salida.
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            //4º Creamos el StreamResult, que intermediará entre el transformador y el archivo de destino.
            StreamResult result = new StreamResult(f);
            //5º Creamos el DOMSource, que intermediará entre el transformador y el árbol DOM.
            DOMSource source = new DOMSource(doc);
            //6º Realizamos la transformación.
            transformer.transform(source, result);
        } catch (TransformerException er) {
            System.out.println("¡Error! No se ha podido llevar a cabo la transformación.");
        }
    }

    public static Document crearDomVacio(String etiquetaRaiz) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document d = null ;
        try {
            db = dbf.newDocumentBuilder() ;
            d = db.newDocument() ;
            d.appendChild(d.createElement(etiquetaRaiz));
            return d;
        } catch (ParserConfigurationException er) {
            Logger.getLogger(UtilidadesXML.class.getName()).log(Level.SEVERE, null, er);
        }
        return d ;
    }
}
