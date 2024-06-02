package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Reservas implements IReservas {
    private static final String COLECCION = "reservas";
    private MongoCollection<Document> coleccionReservas;

    public Reservas () {
        coleccionReservas = MongoDB.getBD().getCollection(COLECCION);
    }

    public List<Reserva> get() throws ParseException {
        List<Reserva> listadoReservas = new ArrayList<>();
        FindIterable<Document> listaReservasOrdenada;
        listaReservasOrdenada = coleccionReservas.find().sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));
        for (Document document : listaReservasOrdenada) {
            Reserva reserva = MongoDB.getReserva(document);
            listadoReservas.add(reserva);
        }
        return listadoReservas;
    }

    public int getTamano() {
        return (int) coleccionReservas.countDocuments();
    }

    public void insertar (Reserva reserva) throws OperationNotSupportedException, NullPointerException, ParseException {
        if (reserva == null){
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (buscar(reserva) != null){
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }
        coleccionReservas.insertOne(MongoDB.getDocumento(reserva));
    }

    public Reserva buscar (Reserva reserva) throws NullPointerException, ParseException {
        if (reserva == null){
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        }
        Document docReserva = null;
        if (coleccionReservas.countDocuments() > 0) { // Comprueba que existan reservas antes de intentar filtrarlas.
            docReserva = coleccionReservas.find().filter(and(eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA)),eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()))).first();
        }
        if (docReserva != null){
            return MongoDB.getReserva(docReserva);
        }
        return null;
    }

    public void borrar (Reserva reserva) throws OperationNotSupportedException, NullPointerException, ParseException {
        if (reserva == null){
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        Reserva reservaBorrar = buscar(reserva);
        if (reservaBorrar != null){
            coleccionReservas.deleteOne(MongoDB.getDocumento(reserva));
        }
        else throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
    }

    public List<Reserva> getReservas (Huesped huesped) throws NullPointerException, ParseException {
        List<Reserva> listadoReservasHuesped = new ArrayList<>();
        List<Reserva> copiaColReservas = get();
        if (huesped == null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");
        }
        for (Reserva reserva : copiaColReservas){
            if (reserva.getHuesped().equals(huesped)){
                listadoReservasHuesped.add(new Reserva(reserva));
            }
        }
        return listadoReservasHuesped;
    }

    public List<Reserva> getReservas (TipoHabitacion tipoHabitacion) throws NullPointerException, ParseException {
        if (tipoHabitacion == null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        List<Reserva> listadoReservasTipoHab = new ArrayList<>();
        List<Reserva> copiaColReservas = get();
        for (Reserva reserva : copiaColReservas){
            if (reserva.getHabitacion() instanceof Simple && tipoHabitacion==TipoHabitacion.SIMPLE){
                listadoReservasTipoHab.add(new Reserva(reserva));
            }
            else if (reserva.getHabitacion() instanceof Doble && tipoHabitacion==TipoHabitacion.DOBLE) {
                listadoReservasTipoHab.add(new Reserva(reserva));
            }
            else if (reserva.getHabitacion() instanceof Triple && tipoHabitacion==TipoHabitacion.TRIPLE) {
                listadoReservasTipoHab.add(new Reserva(reserva));
            }
            else if (reserva.getHabitacion() instanceof Suite && tipoHabitacion==TipoHabitacion.SUITE) {
                listadoReservasTipoHab.add(new Reserva(reserva));
            }
        }
        return listadoReservasTipoHab;
    }

    public List<Reserva> getReservas (Habitacion habitacion) throws NullPointerException, ParseException {
        if (habitacion == null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        List<Reserva> listadoReservasHabitacion = new ArrayList<>();
        List<Reserva> copiaColReservas = get();
        for (Reserva reserva : copiaColReservas){
            if (reserva.getHabitacion().equals(habitacion)){
                listadoReservasHabitacion.add(new Reserva(reserva));
            }
        }
        if (listadoReservasHabitacion.isEmpty()) {
            System.out.println("No existen reservas para la habitación indicada.");
        }
        return listadoReservasHabitacion;
    }

    public List<Reserva> getReservasFuturas (Habitacion habitacion) throws NullPointerException, ParseException {
        if (habitacion == null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        }
        List<Reserva> listadoReservasFuturasHab = new ArrayList<>();
        List<Reserva> copiaColReservas = get();
        for (Reserva reserva : copiaColReservas){
            if (reserva.getHabitacion().equals(habitacion)){
                if (reserva.getFechaFinReserva().isAfter(LocalDate.now())){
                    listadoReservasFuturasHab.add(new Reserva(reserva));
                }
            }
        }
        return listadoReservasFuturasHab;
    }

    public void realizarCheckin (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException, ParseException {
        if (reserva == null){
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        else if (fecha == null){
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
        Document docReserva = coleccionReservas.find().filter(and(eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA)),eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()))).first();
        if (docReserva != null) {
            reserva.setCheckIn(fecha);
            coleccionReservas.updateOne(Filters.and(eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA)),eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador())), Updates.set(MongoDB.CHECKIN,fecha.format(MongoDB.FORMATO_DIA_HORA)));
            System.out.println("CheckIn añadido a la reserva.");
        }
    }

    public void realizarCheckout (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException, ParseException {
        if (reserva == null){
            throw new NullPointerException("ERROR: La reserva no puede ser nula.");
        }
        else if (fecha == null){
            throw new NullPointerException("ERROR: La fecha no puede ser nula");
        }
        else if (fecha.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("ERROR: La fecha no puede ser posterior a la actual.");
        }
        else if (reserva.getCheckIn() == null || reserva.getCheckIn().isAfter(fecha)){
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckOut sin un CheckIn previo.");
        }
        else if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusDays(1))) {
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckOut en una fecha posterior al final de la reserva.");
        }
        else if (fecha.isAfter(reserva.getFechaFinReserva().atStartOfDay().plusHours(12))) {
            throw new IllegalArgumentException("ERROR: No se puede realizar el CheckOut después de las 12.");
        }
        Document docReserva = coleccionReservas.find().filter(and(eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA)),eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()))).first();
        if (docReserva != null) {
            reserva.setCheckIn(fecha);
            coleccionReservas.updateOne(Filters.and(eq(MongoDB.FECHA_INICIO_RESERVA,reserva.getFechaInicioReserva().format(MongoDB.FORMATO_DIA)),eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador())), Updates.set(MongoDB.CHECKOUT, fecha.format(MongoDB.FORMATO_DIA_HORA)));
            System.out.println("CheckOut añadido a la reserva.");
        }
    }

    @Override
    public void comenzar() {
        MongoDatabase database = MongoDB.getBD();
        coleccionReservas = database.getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }
}
