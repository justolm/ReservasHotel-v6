package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Huespedes implements IHuespedes {
    private List<Huesped> coleccionHuespedes;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final String RUTA_FICHERO = "datos/huespedes.xml";
    private static final String RAIZ = "Huespedes";
    private static final String HUESPED = "Huesped";
    private static final String NOMBRE = "Nombre";
    private static final String DNI = "Dni";
    private static final String CORREO = "Correo";
    private static final String TELEFONO = "Telefono";
    private static final String FECHA_NACIMIENTO = "FechaNacimiento";
    private static Huespedes instancia;

    public Huespedes() {
        coleccionHuespedes = new ArrayList<>();
    }

    public static Huespedes getInstancia() {
        if (instancia == null){
            instancia = new Huespedes();
        }
        return instancia;
    }

    public List<Huesped> get() {
        coleccionHuespedes=new ArrayList<>(copiaProfundaHuespedes());
        coleccionHuespedes.sort(Comparator.comparing(Huesped::getNombre));
        return coleccionHuespedes;
    }

    private List<Huesped> copiaProfundaHuespedes() throws NullPointerException {
        if (coleccionHuespedes == null) {
            throw new NullPointerException("ERROR: No se puede copiar una colección vacía.");
        }
        List<Huesped> copiaProfundaHuespedes = new ArrayList<>();
        for (int i = 0; i < getTamano(); i++) {
            copiaProfundaHuespedes.add(coleccionHuespedes.get(i));
        }
        return copiaProfundaHuespedes;
    }

    public int getTamano() throws NullPointerException {
        return coleccionHuespedes.size();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException, NullPointerException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if (coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.add(new Huesped(huesped));
    }

    public Huesped buscar(Huesped huesped) throws NullPointerException, IllegalArgumentException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }
        for(int i = 0; i < getTamano(); i++) {
            if (coleccionHuespedes.get(i).equals(huesped)) {
                return coleccionHuespedes.get(i);
            }
        }
        return null;
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException, NullPointerException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
        if (coleccionHuespedes.contains(huesped)) {
            coleccionHuespedes.remove(huesped);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
    }

    @Override
    public void comenzar() {
        leerXML();
    }

    private void leerXML() {
        try {
            Document doc = UtilidadesXML.xmlToDom(RUTA_FICHERO);
            if (doc == null) {
                doc = UtilidadesXML.crearDomVacio(RAIZ);
            }
            NodeList listadoHuespedes = doc.getElementsByTagName(HUESPED);
            for (int i = 0 ; i < listadoHuespedes.getLength() ; i++) {
                Element element = (Element) listadoHuespedes.item(i);
                Huesped huesped = elementToHuesped(element);
                coleccionHuespedes.add(huesped);
            }
        } catch (NullPointerException | IllegalArgumentException er) {
            System.out.println(er.getMessage());
        }
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    private Huesped elementToHuesped(Element element){
        String nombre = element.getElementsByTagName(NOMBRE).item(0).getTextContent();
        String Dni = element.getAttribute(DNI);
        String correo = element.getElementsByTagName(CORREO).item(0).getTextContent();
        String telefono = element.getElementsByTagName(TELEFONO).item(0).getTextContent();
        String fechaNacimiento = element.getElementsByTagName(FECHA_NACIMIENTO).item(0).getTextContent();
        return new Huesped(nombre,Dni,correo,telefono, LocalDate.parse(fechaNacimiento,FORMATO_FECHA));
    }

    private Element huespedToElement(Document doc, Huesped huesped) throws NullPointerException{
        if (doc == null || huesped == null){
            throw new NullPointerException("ERROR: Se ha introducido un componente nulo.");
        }
        Element elHuesped = doc.createElement(HUESPED);
        // DNI
        elHuesped.setAttribute(DNI, huesped.getDni());
        // Nombre
        Element elNombre = doc.createElement(NOMBRE);
        elNombre.appendChild(doc.createTextNode(huesped.getNombre()));
        elHuesped.appendChild(elNombre);
        // Correo
        Element elCorreo = doc.createElement(CORREO);
        elCorreo.appendChild(doc.createTextNode(huesped.getCorreo()));
        elHuesped.appendChild(elCorreo);
        // Teléfono
        Element elTelefono = doc.createElement(TELEFONO);
        elTelefono.appendChild(doc.createTextNode(huesped.getTelefono()));
        elHuesped.appendChild(elTelefono);
        // Fecha de nacimiento
        Element elFechaNacimiento = doc.createElement(FECHA_NACIMIENTO);
        elFechaNacimiento.appendChild(doc.createTextNode(huesped.getFechaNacimiento().format(FORMATO_FECHA)));
        elHuesped.appendChild(elFechaNacimiento);
        return elHuesped;
    }

    private void escribirXML(){
        try {
            Document doc = UtilidadesXML.crearDomVacio(RAIZ);
            for (Huesped huesped : coleccionHuespedes){
                Element elHuesped = huespedToElement(doc, huesped);
                doc.getDocumentElement().appendChild(elHuesped);
            }
            UtilidadesXML.domToXml(doc, RUTA_FICHERO);
        } catch (NullPointerException | IllegalArgumentException er) {
            System.out.println(er.getMessage());
        }
    }
}
