package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private List<Habitacion> coleccionHabitaciones;
    private static final String RUTA_FICHERO = "datos/habitaciones.xml";
    private static final String RAIZ = "Habitaciones";
    private static final String HABITACION = "Habitacion";
    private static final String IDENTIFICADOR = "Identificador";
    private static final String PLANTA = "Planta";
    private static final String PUERTA = "Puerta";
    private static final String PRECIO = "Precio";
    private static final String CAMAS_INDIVIDUALES = "CamasIndividuales";
    private static final String CAMAS_DOBLES = "CamasDobles";
    private static final String BANOS = "Banos";
    private static final String JACUZZI = "Jacuzzi";
    private static final String TIPO = "Tipo";
    private static final String SIMPLE = "Simple";
    private static final String DOBLE = "Doble";
    private static final String TRIPLE = "Triple";
    private static final String SUITE = "Suite";
    private static Habitaciones instancia;


    public Habitaciones() {
        coleccionHabitaciones = new ArrayList<>();
        comenzar();
    }

    public List<Habitacion> get() throws NullPointerException, IllegalArgumentException {
        coleccionHabitaciones=copiaProfundaHabitaciones();
        return coleccionHabitaciones;
    }

    public static Habitaciones getInstancia() {
        if (instancia == null) {
            instancia = new Habitaciones();
        }
        return instancia;
    }

    private List<Habitacion> copiaProfundaHabitaciones() throws NullPointerException, IllegalArgumentException {
        if (coleccionHabitaciones == null) {
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía");
        }
        List<Habitacion> copiaProfundaHabitaciones = new ArrayList<>();
        for (int i = 0; i < coleccionHabitaciones.size(); i++) {
            if (coleccionHabitaciones.get(i) instanceof Simple) {
                copiaProfundaHabitaciones.add(new Simple((Simple) coleccionHabitaciones.get(i)));
            }
            else if (coleccionHabitaciones.get(i) instanceof Doble){
                copiaProfundaHabitaciones.add(new Doble(coleccionHabitaciones.get(i).getPlanta(),coleccionHabitaciones.get(i).getPuerta(),coleccionHabitaciones.get(i).getPrecio(),((Doble) coleccionHabitaciones.get(i)).getNumCamasIndividuales(),((Doble) coleccionHabitaciones.get(i)).getNumCamasDobles()));
            }
            else if (coleccionHabitaciones.get(i) instanceof Triple) {
                copiaProfundaHabitaciones.add(new Triple(coleccionHabitaciones.get(i).getPlanta(),coleccionHabitaciones.get(i).getPuerta(),coleccionHabitaciones.get(i).getPrecio(),((Triple) coleccionHabitaciones.get(i)).getNumBanos(),((Triple) coleccionHabitaciones.get(i)).getNumCamasIndividuales(),((Triple) coleccionHabitaciones.get(i)).getNumCamasDobles()));
            }
            else if (coleccionHabitaciones.get(i) instanceof Suite) {
                copiaProfundaHabitaciones.add(new Suite(coleccionHabitaciones.get(i).getPlanta(),coleccionHabitaciones.get(i).getPuerta(),coleccionHabitaciones.get(i).getPrecio(),((Suite) coleccionHabitaciones.get(i)).getNumBanos(),((Suite) coleccionHabitaciones.get(i)).isTieneJacuzzi()));
            }
            else {
                throw new IllegalArgumentException("ERROR: El tipo de habitación es incorrecto.");
            }
        }
        return copiaProfundaHabitaciones;
    }

    public List<Habitacion> get(TipoHabitacion tipoHabitacion) throws NullPointerException{
        if (tipoHabitacion == null){
            throw new NullPointerException("ERROR: El tipo de habitación no puede estar vacío.");
        }
        if (coleccionHabitaciones.isEmpty()){
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía.");
        }
        List<Habitacion> copiaProfundaHabitacionesFiltro = new ArrayList<>();
        if (tipoHabitacion == TipoHabitacion.SIMPLE) {
            for (int i = 0; i < getTamano(); i++) {
                if (coleccionHabitaciones.get(i) instanceof Simple) {
                    copiaProfundaHabitacionesFiltro.add(new Simple(coleccionHabitaciones.get(i).getPlanta(),coleccionHabitaciones.get(i).getPuerta(),coleccionHabitaciones.get(i).getPrecio()));
                }
            }
        }
        else if (tipoHabitacion == TipoHabitacion.DOBLE) {
            for (int i = 0; i < getTamano(); i++) {
                if (coleccionHabitaciones.get(i) instanceof Doble) {
                    copiaProfundaHabitacionesFiltro.add(new Doble(coleccionHabitaciones.get(i).getPlanta(),coleccionHabitaciones.get(i).getPuerta(),coleccionHabitaciones.get(i).getPrecio(),((Doble) coleccionHabitaciones.get(i)).getNumCamasIndividuales(),((Doble) coleccionHabitaciones.get(i)).getNumCamasDobles()));
                }
            }
        }
        else if (tipoHabitacion == TipoHabitacion.TRIPLE) {
            for (int i = 0; i < getTamano(); i++) {
                if (coleccionHabitaciones.get(i) instanceof Triple) {
                    copiaProfundaHabitacionesFiltro.add(new Triple(coleccionHabitaciones.get(i).getPlanta(),coleccionHabitaciones.get(i).getPuerta(),coleccionHabitaciones.get(i).getPrecio(),((Triple) coleccionHabitaciones.get(i)).getNumBanos(),((Triple) coleccionHabitaciones.get(i)).getNumCamasIndividuales(),((Triple) coleccionHabitaciones.get(i)).getNumCamasDobles()));
                }
            }
        }
        else if (tipoHabitacion == TipoHabitacion.SUITE) {
            for (int i = 0 ; i < getTamano() ; i++) {
                if (coleccionHabitaciones.get(i) instanceof Suite) {
                    copiaProfundaHabitacionesFiltro.add(new Suite(coleccionHabitaciones.get(i).getPlanta(),coleccionHabitaciones.get(i).getPuerta(),coleccionHabitaciones.get(i).getPrecio(),((Suite) coleccionHabitaciones.get(i)).getNumBanos(),((Suite) coleccionHabitaciones.get(i)).isTieneJacuzzi()));
                }
            }
        }
        else {
            throw new IllegalArgumentException("ERROR: El tipo de habitación introducido no es válido.");
        }
        return copiaProfundaHabitacionesFiltro;
    }

    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    public void insertar (Habitacion habitacion) throws NullPointerException, OperationNotSupportedException {
        if (habitacion == null){
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) throws NullPointerException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHabitaciones.get(i).equals(habitacion)){
                return coleccionHabitaciones.get(i);
            }
        }
        return null;
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (coleccionHabitaciones.contains(habitacion)){
            coleccionHabitaciones.remove(habitacion);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }

    @Override
    public void comenzar() {
        leerXML();
    }

    @Override
    public void terminar() {
        escribirXML();
    }

    private void leerXML() {
        try {
            Document doc = UtilidadesXML.xmlToDom(RUTA_FICHERO);
            if (doc == null) {
                doc = UtilidadesXML.crearDomVacio(RAIZ);
            }
            NodeList listadoHabitaciones = doc.getElementsByTagName(HABITACION);
            for (int i = 0 ; i < listadoHabitaciones.getLength() ; i++) {
                Element element = (Element) listadoHabitaciones.item(i);
                Habitacion habitacion = elementToHabitacion(element);
                coleccionHabitaciones.add(habitacion);
            }
        } catch (NullPointerException | IllegalArgumentException er) {
            System.out.println(er.getMessage());
        }
    }

    private Habitacion elementToHabitacion(Element element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("ERROR: Se ha introducido un elemento nulo.");
        }
        int identificador = Integer.parseInt(element.getAttribute(IDENTIFICADOR));
        int planta = Integer.parseInt(element.getElementsByTagName(PLANTA).item(0).getTextContent());
        int puerta = Integer.parseInt(element.getElementsByTagName(PUERTA).item(0).getTextContent());
        double precio = Double.parseDouble(element.getElementsByTagName(PRECIO).item(0).getTextContent());
        String tipoHabitacion = element.getAttribute(TIPO);
        switch (tipoHabitacion) {
            case SIMPLE -> {
                return new Simple(planta, puerta, precio);
            }
            case DOBLE -> {
                int camasIndividuales = Integer.parseInt(element.getElementsByTagName(CAMAS_INDIVIDUALES).item(0).getTextContent());
                int camasDobles = Integer.parseInt(element.getElementsByTagName(CAMAS_DOBLES).item(0).getTextContent());
                return new Doble(planta, puerta, precio, camasIndividuales, camasDobles);
            }
            case TRIPLE -> {
                int camasIndividuales = Integer.parseInt(element.getElementsByTagName(CAMAS_INDIVIDUALES).item(0).getTextContent());
                int camasDobles = Integer.parseInt(element.getElementsByTagName(CAMAS_DOBLES).item(0).getTextContent());
                int numBanos = Integer.parseInt(element.getElementsByTagName(BANOS).item(0).getTextContent());
                return new Triple(planta, puerta, precio, numBanos, camasIndividuales, camasDobles);
            }
            case SUITE -> {
                int numBanos = Integer.parseInt(element.getElementsByTagName(BANOS).item(0).getTextContent());
                boolean tieneJacuzzi = Boolean.parseBoolean(element.getElementsByTagName(JACUZZI).item(0).getTextContent());
                return new Suite(planta, puerta, precio, numBanos, tieneJacuzzi);
            }
            default -> {
                return null;
            }
        }
    }

    private Element habitacionToElement(Document doc, Habitacion habitacion) throws NullPointerException {
        if (doc == null || habitacion == null) {
            throw new NullPointerException("ERROR: Se ha introducido un componente nulo.");
        }
        Element elHabitacion = doc.createElement(HABITACION);
        // Identificador
        elHabitacion.setAttribute(IDENTIFICADOR, habitacion.getIdentificador());
        // Tipo
        elHabitacion.setAttribute(TIPO,habitacion.getClass().getSimpleName());
        // Planta
        Element elPlanta = doc.createElement(PLANTA);
        elPlanta.appendChild(doc.createTextNode(String.valueOf(habitacion.getPlanta())));
        elHabitacion.appendChild(elPlanta);
        // Puerta
        Element elPuerta = doc.createElement(PUERTA);
        elPuerta.appendChild(doc.createTextNode(String.valueOf(habitacion.getPuerta())));
        elHabitacion.appendChild(elPuerta);
        // Precio
        Element elPrecio = doc.createElement(PRECIO);
        elPrecio.appendChild(doc.createTextNode(String.valueOf(habitacion.getPrecio())));
        elHabitacion.appendChild(elPrecio);
        // Resto de parámetros específicos del resto de habitaciones
        switch (habitacion.getClass().getSimpleName()){
            case SIMPLE -> {} // No hay que añadir nada específico
            case DOBLE -> {
                // Tipo (hijo)
                Element elTipo = doc.createElement(DOBLE);
                elHabitacion.appendChild(elTipo);
                // Camas individuales
                Element elCamasIndividuales = doc.createElement(CAMAS_INDIVIDUALES);
                elCamasIndividuales.appendChild(doc.createTextNode(String.valueOf(((Doble)habitacion).getNumCamasIndividuales())));
                elTipo.appendChild(elCamasIndividuales);
                // Camas dobles
                Element elCamasDobles = doc.createElement(CAMAS_DOBLES);
                elCamasDobles.appendChild(doc.createTextNode(String.valueOf(((Doble)habitacion).getNumCamasDobles())));
                elTipo.appendChild(elCamasDobles);
            }
            case TRIPLE -> {
                // Tipo (hijo)
                Element elTipo = doc.createElement(TRIPLE);
                elHabitacion.appendChild(elTipo);
                // Camas individuales
                Element elCamasIndividuales = doc.createElement(CAMAS_INDIVIDUALES);
                elCamasIndividuales.appendChild(doc.createTextNode(String.valueOf(((Triple)habitacion).getNumCamasIndividuales())));
                elTipo.appendChild(elCamasIndividuales);
                // Camas dobles
                Element elCamasDobles = doc.createElement(CAMAS_DOBLES);
                elCamasDobles.appendChild(doc.createTextNode(String.valueOf(((Triple)habitacion).getNumCamasDobles())));
                elTipo.appendChild(elCamasDobles);
                // Baños
                Element elBanos = doc.createElement(BANOS);
                elBanos.appendChild(doc.createTextNode(String.valueOf(((Triple)habitacion).getNumBanos())));
                elTipo.appendChild(elBanos);
            }
            case SUITE -> {
                // Tipo (hijo)
                Element elTipo = doc.createElement(SUITE);
                elHabitacion.appendChild(elTipo);
                // Baños
                Element elBanos = doc.createElement(BANOS);
                elBanos.appendChild(doc.createTextNode(String.valueOf(((Suite)habitacion).getNumBanos())));
                elTipo.appendChild(elBanos);
                // Jacuzzi
                Element elJacuzzi = doc.createElement(JACUZZI);
                elJacuzzi.appendChild(doc.createTextNode(String.valueOf(((Suite)habitacion).isTieneJacuzzi())));
                elTipo.appendChild(elJacuzzi);
            }
        }
        return elHabitacion;
    }

    private void escribirXML() {
        try {
            Document doc = UtilidadesXML.crearDomVacio(RAIZ);
            for (Habitacion habitacion : coleccionHabitaciones) {
                Element elHabitacion = habitacionToElement(doc, habitacion);
                doc.getDocumentElement().appendChild(elHabitacion);
            }
            UtilidadesXML.domToXml(doc, RUTA_FICHERO);
        } catch (NullPointerException | IllegalArgumentException er) {
            System.out.println(er.getMessage());
        }
    }
}
