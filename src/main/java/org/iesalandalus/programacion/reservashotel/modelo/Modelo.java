package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

public class Modelo implements IModelo {
    private static IHabitaciones habitaciones;
    private static IReservas reservas;
    private static IHuespedes huespedes;
    private static IFuenteDatos fuenteDatos;

    public Modelo (FactoriaFuenteDatos factoriaFuenteDatos) throws NullPointerException {
        if (factoriaFuenteDatos == null) {
            throw new NullPointerException("ERROR: No se ha indicado el modelo de datos a usar.");
        }
        setFuenteDatos(factoriaFuenteDatos.crear());
        comenzar();
    }

    public void comenzar() throws IllegalArgumentException, NullPointerException {
        huespedes = fuenteDatos.crearHuespedes();
        habitaciones = fuenteDatos.crearHabitaciones();
        reservas = fuenteDatos.crearReservas();
    }

    public void terminar(){
        huespedes.terminar();
        habitaciones.terminar();
        reservas.terminar();
        System.out.println("El modelo ha finalizado.");
    }

    public void insertar (Huesped huesped) throws OperationNotSupportedException, NullPointerException, ParseException {
        huespedes.insertar(huesped);
    }

    public Huesped buscar (Huesped huesped) throws IllegalArgumentException, NullPointerException, ParseException {
        return huespedes.buscar(huesped);
    }

    public void borrar (Huesped huesped) throws OperationNotSupportedException, NullPointerException {
        huespedes.borrar(huesped);
    }

    public List<Huesped> getHuespedes() throws ParseException {
        return huespedes.get();
    }

    public void insertar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException {
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscar (Habitacion habitacion) throws NullPointerException {
        return habitaciones.buscar(habitacion);
    }

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException {
        habitaciones.borrar(habitacion);
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones.get();
    }

    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) throws NullPointerException {
        return habitaciones.get(tipoHabitacion);
    }

    public void insertar (Reserva reserva) throws OperationNotSupportedException, NullPointerException, ParseException {
        reservas.insertar(reserva);
    }

    public Reserva buscar (Reserva reserva) throws NullPointerException, ParseException {
        return reservas.buscar(reserva);
    }

    public void borrar (Reserva reserva) throws OperationNotSupportedException, NullPointerException, ParseException {
        reservas.borrar(reserva);
    }

    public List<Reserva> getReservas() throws ParseException {
        return reservas.get();
    }

    public List<Reserva> getReservas(Huesped huesped) throws NullPointerException, ParseException {
        return reservas.getReservas(huesped);
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) throws NullPointerException, ParseException {
        return reservas.getReservas(tipoHabitacion);
    }

    public List<Reserva> getReservas(Habitacion habitacion) throws NullPointerException, ParseException {
        return reservas.getReservas(habitacion);
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion) throws NullPointerException, ParseException {
        return reservas.getReservasFuturas(habitacion);
    }

    public void realizarCheckin (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException, ParseException {
        reservas.realizarCheckin(reserva, fecha);
    }

    public void realizarCheckout (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException, ParseException {
        reservas.realizarCheckout(reserva, fecha);
    }

    private void setFuenteDatos (IFuenteDatos fuenteDatos) {
        Modelo.fuenteDatos = fuenteDatos;
    }
}
