package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IModelo {
    void comenzar() throws IllegalArgumentException, NullPointerException;
    void terminar();
    void insertar (Huesped huesped) throws OperationNotSupportedException, NullPointerException, ParseException;
    Huesped buscar (Huesped huesped) throws IllegalArgumentException, NullPointerException, ParseException;
    void borrar (Huesped huesped) throws OperationNotSupportedException, NullPointerException, ParseException;
    List<Huesped> getHuespedes() throws ParseException;
    void insertar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException;
    Habitacion buscar (Habitacion habitacion) throws IllegalArgumentException, NullPointerException;
    void borrar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException, ParseException;
    List<Habitacion> getHabitaciones();
    List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) throws NullPointerException;
    void insertar (Reserva reserva) throws OperationNotSupportedException, NullPointerException, ParseException;
    void borrar (Reserva reserva) throws OperationNotSupportedException, NullPointerException, ParseException;
    Reserva buscar (Reserva reserva) throws NullPointerException, ParseException;
    List<Reserva> getReservas() throws ParseException;
    List<Reserva> getReservas(Huesped huesped) throws NullPointerException, ParseException;
    List<Reserva> getReservas(TipoHabitacion tipoHabitacion) throws NullPointerException, ParseException;
    List<Reserva> getReservas(Habitacion habitacion) throws NullPointerException, ParseException;
    List<Reserva> getReservasFuturas(Habitacion habitacion) throws NullPointerException, ParseException;
    void realizarCheckin (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException, ParseException;
    void realizarCheckout (Reserva reserva, LocalDateTime fecha) throws IllegalArgumentException, NullPointerException, ParseException;
    Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) throws ParseException;
}
