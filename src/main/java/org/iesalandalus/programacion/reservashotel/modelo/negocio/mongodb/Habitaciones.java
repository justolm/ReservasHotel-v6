package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private static final String COLECCION = "habitaciones";
    private MongoCollection<Document> coleccionHabitaciones;

    public Habitaciones() {
        coleccionHabitaciones = MongoDB.getBD().getCollection(COLECCION);
    }

    public List<Habitacion> get() throws NullPointerException, IllegalArgumentException {
        List<Habitacion> listadoHabitaciones = new ArrayList<>();
        for (Document document : coleccionHabitaciones.find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR))){
            listadoHabitaciones.add(MongoDB.getHabitacion(document));
        }
        return listadoHabitaciones;
    }

    public List<Habitacion> get(TipoHabitacion tipoHabitacion) throws NullPointerException{
        if (tipoHabitacion==null){
            throw new NullPointerException("ERROR: El tipo de habitación no puede estar vacío.");
        }
        if (coleccionHabitaciones == null){
            throw new NullPointerException("ERROR: No es posible copiar una colección vacía.");
        }
        List<Habitacion> copiaColHabitaciones = get();
        List<Habitacion> listadoHabitacionesFiltro = new ArrayList<>();
        if (tipoHabitacion == TipoHabitacion.SIMPLE) {
            for (Habitacion habitacion : copiaColHabitaciones) {
                if (habitacion instanceof Simple) {
                    listadoHabitacionesFiltro.add(new Simple(habitacion.getPlanta(),habitacion.getPuerta(),habitacion.getPrecio()));
                }
            }
        }
        else if (tipoHabitacion == TipoHabitacion.DOBLE) {
            for (Habitacion habitacion : copiaColHabitaciones) {
                if (habitacion instanceof Doble) {
                    listadoHabitacionesFiltro.add(new Doble(habitacion.getPlanta(),habitacion.getPuerta(),habitacion.getPrecio(),((Doble) habitacion).getNumCamasIndividuales(),((Doble) habitacion).getNumCamasDobles()));
                }
            }
        }
        else if (tipoHabitacion == TipoHabitacion.TRIPLE) {
            for (Habitacion habitacion : copiaColHabitaciones) {
                if (habitacion instanceof Triple) {
                    listadoHabitacionesFiltro.add(new Triple(habitacion.getPlanta(),habitacion.getPuerta(),habitacion.getPrecio(),((Triple) habitacion).getNumBanos(),((Triple) habitacion).getNumCamasIndividuales(),((Triple) habitacion).getNumCamasDobles()));
                }
            }
        }
        else if (tipoHabitacion == TipoHabitacion.SUITE) {
            for (Habitacion habitacion : copiaColHabitaciones) {
                if (habitacion instanceof Suite) {
                    listadoHabitacionesFiltro.add(new Suite(habitacion.getPlanta(),habitacion.getPuerta(),habitacion.getPrecio(),((Suite) habitacion).getNumBanos(),((Suite) habitacion).isTieneJacuzzi()));
                }
            }
        }
        else {
            throw new IllegalArgumentException("ERROR: El tipo de habitación introducido no es válido.");
        }
        return listadoHabitacionesFiltro;
    }

    public int getTamano() {
        return (int) coleccionHabitaciones.countDocuments();
    }

    public void insertar (Habitacion habitacion) throws NullPointerException, OperationNotSupportedException {
        if (habitacion == null){
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (buscar(habitacion) != null){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
    }

    public Habitacion buscar(Habitacion habitacion) throws NullPointerException{
        if (habitacion == null) {
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        }
        Document docHabitacion = coleccionHabitaciones.find(Filters.eq(MongoDB.IDENTIFICADOR,habitacion.getIdentificador())).first();
        if (docHabitacion != null){
            return MongoDB.getHabitacion(docHabitacion);
        }
        return null;
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException {
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        Document docHabitacion = coleccionHabitaciones.find(Filters.eq(MongoDB.IDENTIFICADOR,habitacion.getIdentificador())).first();
        if (docHabitacion != null){
            coleccionHabitaciones.deleteOne(docHabitacion);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }

    @Override
    public void comenzar() {
        MongoDatabase database = MongoDB.getBD();
        coleccionHabitaciones = database.getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }
}
