package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Reservas implements IReservas {
    private List<Reserva> coleccionReservas;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String RUTA_FICHERO = "datos/reservas.xml";
    private static final String RAIZ = "Reservas";
    private static final String RESERVA = "Reserva";
    private static final String DNI_HUESPED = "Dni";
    private static final String PLANTA_HABITACION = "Planta";
    private static final String PUERTA_HABITACION = "Puerta";
    private static final String FECHA_INICIO_RESERVA = "FechaInicioReserva";
    private static final String FECHA_FIN_RESERVA = "FechaFinReserva";
    private static final String REGIMEN = "Regimen";
    private static final String NUMERO_PERSONAS = "Personas";
    private static final String CHECKIN = "FechaCheckin";
    private static final String CHECKOUT = "FechaCheckout";
    private static final String PRECIO = "Precio";
    private static Reservas instancia;

    public Reservas() {
        coleccionReservas=new ArrayList<>();
    }

    public List<Reserva> get() {
        coleccionReservas=copiaProfundaReservas();
        return coleccionReservas;
    }

    public static Reservas getInstancia() {
        if (instancia == null) {
            instancia = new Reservas();
        }
        return instancia;
    }

    private List<Reserva> copiaProfundaReservas() {
        List<Reserva> copiaProfundaReservas=new ArrayList<>();
        copiaProfundaReservas.addAll(coleccionReservas);
        return copiaProfundaReservas;
    }

    public int getTamano() {
        return coleccionReservas.size();
    }

    public void insertar (Reserva reserva) throws OperationNotSupportedException, NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (coleccionReservas.contains(reserva)){
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        coleccionReservas.add(new Reserva(reserva));
    }

    public Reserva buscar (Reserva reserva) throws NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        for (int i=0 ; i < getTamano() ; i++){
            if (coleccionReservas.get(i).equals(reserva)){
                return reserva;
            }
        }
        return null;
    }

    public void borrar (Reserva reserva) throws OperationNotSupportedException, NullPointerException {
        if (reserva==null){
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        if (coleccionReservas.contains(reserva)){
            coleccionReservas.remove(reserva);
        }
        else throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
    }

    public List<Reserva> getReservas (Huesped huesped) throws NullPointerException{
        List<Reserva> copiaProfundaHabitacionesHuesped = new ArrayList<>();
        for (int i=0 ; i < getTamano() ; i++){
            if (huesped==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
            }
            if (coleccionReservas.get(i).getHuesped().equals(huesped)){
                copiaProfundaHabitacionesHuesped.add(new Reserva(coleccionReservas.get(i)));
            }
        }
        return copiaProfundaHabitacionesHuesped;
    }

    public List<Reserva> getReservas (TipoHabitacion tipoHabitacion) throws NullPointerException{
        List<Reserva> copiaProfundaHabitacionesTipoHabitacion = new ArrayList<>();
        if (tipoHabitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        for (Reserva reserva : coleccionReservas) {
            if (reserva.getHabitacion() instanceof Simple && tipoHabitacion == TipoHabitacion.SIMPLE) {
                copiaProfundaHabitacionesTipoHabitacion.add(new Reserva(reserva));
            } else if (reserva.getHabitacion() instanceof Doble && tipoHabitacion == TipoHabitacion.DOBLE) {
                copiaProfundaHabitacionesTipoHabitacion.add(new Reserva(reserva));
            } else if (reserva.getHabitacion() instanceof Triple && tipoHabitacion == TipoHabitacion.TRIPLE) {
                copiaProfundaHabitacionesTipoHabitacion.add(new Reserva(reserva));
            } else if (reserva.getHabitacion() instanceof Suite && tipoHabitacion == TipoHabitacion.SUITE) {
                copiaProfundaHabitacionesTipoHabitacion.add(new Reserva(reserva));
            }
        }
        return copiaProfundaHabitacionesTipoHabitacion;
    }

    public List<Reserva> getReservas (Habitacion habitacion) throws NullPointerException {
        List<Reserva> copiaProfundaHabitacionesReservasHabitacion = new ArrayList<>();
        for (int i=0 ; i < getTamano() ; i++){
            if (habitacion==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
            }
            if (coleccionReservas.get(i).getHabitacion().equals(habitacion)){
                copiaProfundaHabitacionesReservasHabitacion.add(new Reserva(coleccionReservas.get(i)));
            }
        }
        return copiaProfundaHabitacionesReservasHabitacion;
    }

    public List<Reserva> getReservasFuturas (Habitacion habitacion) throws NullPointerException {
        List<Reserva> copiaProfundaHabitacionesReservasFuturas = new ArrayList<>();
        for (int i=0 ; i < getTamano() ; i++){
            if (habitacion==null){
                throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
            }
            if (coleccionReservas.get(i).getHabitacion().equals(habitacion)){
                if (coleccionReservas.get(i).getFechaFinReserva().isAfter(LocalDate.now())){
                    copiaProfundaHabitacionesReservasFuturas.add(new Reserva(coleccionReservas.get(i)));
                }
            }
        }
        return copiaProfundaHabitacionesReservasFuturas;
    }

    public void realizarCheckin (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException{
        if (reserva==null){
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        else if (fecha==null){
            throw new NullPointerException("ERROR: La fecha no puede ser nula");
        }
        else if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("ERROR: La fecha no puede ser posterior a la actual.");
        }
        else if (fecha.isBefore(reserva.getFechaInicioReserva().atStartOfDay())){
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckIn en una fecha anterior a la reservada.");
        }
        else if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusDays(1))) {
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckIn en una fecha posterior al final de la reserva.");
        }
        for (Reserva coleccionReserva : coleccionReservas) {
            if (coleccionReserva.equals(reserva)) {
                coleccionReserva.setCheckIn(fecha);
                System.out.println("CheckIn añadido a la reserva.");
            }
        }
    }

    public void realizarCheckout (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException{
        if (reserva==null){
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        else if (fecha==null){
            throw new NullPointerException("ERROR: La fecha no puede ser nula");
        }
        else if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("ERROR: La fecha no puede ser posterior a la actual.");
        }
        else if (reserva.getCheckIn()==null || reserva.getCheckIn().isAfter(fecha)){
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckOut sin un CheckIn previo.");
        }
        else if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusDays(1))) {
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckOut en una fecha posterior al final de la reserva.");
        }
        for (Reserva coleccionReserva : coleccionReservas) {
            if (coleccionReserva.equals(reserva)) {
                coleccionReserva.setCheckOut(fecha);
                System.out.println("CheckOut añadido a la reserva.");
            }
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

    private void leerXML(){
        try {
            Document doc = UtilidadesXML.xmlToDom(RUTA_FICHERO);
            if (doc == null) {
                doc = UtilidadesXML.crearDomVacio(RAIZ);
            }
            NodeList listadoReservas = doc.getElementsByTagName(RESERVA);
            for (int i = 0 ; i < listadoReservas.getLength() ; i++) {
                Element element = (Element) listadoReservas.item(i);
                Reserva reserva = elementToReserva(element);
                coleccionReservas.add(reserva);
            }
        } catch (NullPointerException | IllegalArgumentException er) {
            System.out.println(er.getMessage());
        }
    }

    private Reserva elementToReserva(Element element){
        String dni = element.getAttribute(DNI_HUESPED);
        int planta = Integer.parseInt(element.getAttribute(PLANTA_HABITACION));
        int puerta = Integer.parseInt(element.getAttribute(PUERTA_HABITACION));
        Regimen regimen = Regimen.valueOf(element.getElementsByTagName(REGIMEN).item(0).getTextContent());
        String fechaInicioReserva = element.getElementsByTagName(FECHA_INICIO_RESERVA).item(0).getTextContent();
        String fechaFinReserva = element.getElementsByTagName(FECHA_FIN_RESERVA).item(0).getTextContent();
        int personas = Integer.parseInt(element.getElementsByTagName(NUMERO_PERSONAS).item(0).getTextContent());
        String fechaCheckin = element.getElementsByTagName(CHECKIN).item(0).getTextContent();
        String fechaCheckout = element.getElementsByTagName(CHECKOUT).item(0).getTextContent();
        Huesped huespedDni = new Huesped("Nombre", dni, "Correo@falso.es", "600600600",LocalDate.of(1980,11,19));
        Huesped huesped = Huespedes.getInstancia().buscar(huespedDni);
        Habitacion habitacionIdentificador = new Simple(planta, puerta, Simple.MIN_PRECIO_HABITACION);
        Habitacion habitacion = Habitaciones.getInstancia().buscar(habitacionIdentificador);
        Reserva reserva = new  Reserva(huesped, habitacion, regimen, LocalDate.parse(fechaInicioReserva,FORMATO_FECHA), LocalDate.parse(fechaFinReserva,FORMATO_FECHA), personas);
        if (fechaCheckin != null) {
            reserva.setCheckIn(LocalDateTime.parse(fechaCheckin,FORMATO_FECHA_HORA));
            if (fechaCheckout != null) {
                reserva.setCheckOut(LocalDateTime.parse(fechaCheckout,FORMATO_FECHA_HORA));
            }
        }
        return reserva;
    }

    private Element reservaToElement(Document doc, Reserva reserva) {
        if (doc == null || reserva == null) {
            throw new NullPointerException("ERROR: Se ha introducido un componente nulo.");
        }
        Element elReserva = doc.createElement(RESERVA);
        // DNI
        elReserva.setAttribute(DNI_HUESPED, reserva.getHuesped().getDni());
        // Planta y puerta
        elReserva.setAttribute(PLANTA_HABITACION, String.valueOf(reserva.getHabitacion().getPlanta()));
        elReserva.setAttribute(PUERTA_HABITACION, String.valueOf(reserva.getHabitacion().getPuerta()));
        // Regimen
        Element elRegimen = doc.createElement(RESERVA);
        elRegimen.appendChild(doc.createTextNode(String.valueOf(reserva.getRegimen())));
        elReserva.appendChild(elRegimen);
        // Fecha inicio reserva
        Element elFechaInicio = doc.createElement(FECHA_INICIO_RESERVA);
        elFechaInicio.appendChild(doc.createTextNode(String.valueOf(reserva.getFechaInicioReserva())));
        elReserva.appendChild(elFechaInicio);
        // Fecha fin reserva
        Element elFechaFin = doc.createElement(FECHA_FIN_RESERVA);
        elFechaFin.appendChild(doc.createTextNode(String.valueOf(reserva.getFechaFinReserva())));
        elReserva.appendChild(elFechaFin);
        // Número de personas
        Element elPersonas = doc.createElement(NUMERO_PERSONAS);
        elPersonas.appendChild(doc.createTextNode(String.valueOf(reserva.getNumeroPersonas())));
        elReserva.appendChild(elPersonas);
        // CheckIn
        Element elCheckin = doc.createElement(CHECKIN);
        elCheckin.setAttribute(CHECKIN, String.valueOf(reserva.getCheckIn()));
        elReserva.appendChild(elCheckin);
        // CheckOut
        Element elCheckout = doc.createElement(CHECKOUT);
        elCheckout.setAttribute(CHECKOUT, String.valueOf(reserva.getCheckOut()));
        elReserva.appendChild(elCheckout);
        // Precio
        Element elPrecio = doc.createElement(PRECIO);
        elPrecio.appendChild(doc.createTextNode(String.valueOf(reserva.getPrecio())));
        elReserva.appendChild(elPrecio);
        return elReserva;
    }

    private void escribirXML(){
        try {
            Document doc = UtilidadesXML.crearDomVacio(RAIZ);
            for (Reserva reserva : coleccionReservas) {
                Element elReserva = reservaToElement(doc, reserva);
                doc.getDocumentElement().appendChild(elReserva);
            }
            UtilidadesXML.domToXml(doc, RUTA_FICHERO);
        } catch (NullPointerException | IllegalArgumentException er) {
            System.out.println(er.getMessage());
        }
    }
}
