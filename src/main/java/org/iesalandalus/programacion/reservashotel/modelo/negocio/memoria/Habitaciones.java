package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class Habitaciones implements IHabitaciones {
    private List<Habitacion> coleccionHabitaciones;

    public Habitaciones() {
        coleccionHabitaciones = new ArrayList<>();
    }

    public List<Habitacion> get() throws NullPointerException, IllegalArgumentException {
        coleccionHabitaciones=copiaProfundaHabitaciones();
        return coleccionHabitaciones;
    }

    private List<Habitacion> copiaProfundaHabitaciones() throws NullPointerException, IllegalArgumentException {
        if (coleccionHabitaciones.isEmpty()){
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
        if (tipoHabitacion==null){
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
        if (habitacion==null){
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        }
        if (coleccionHabitaciones.contains(habitacion)){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.add(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) throws NullPointerException{
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        for(int i = 0; i < getTamano(); i++){
            if (coleccionHabitaciones.get(i).equals(habitacion)){
                return coleccionHabitaciones.get(i);
            }
        }
        return null;
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException {
        if (habitacion==null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (coleccionHabitaciones.contains(habitacion)){
            coleccionHabitaciones.remove(habitacion);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        }
    }

    @Override
    public void comenzar() {}

    @Override
    public void terminar() {}
}
