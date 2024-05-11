package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

public interface IReservas {
    List<Reserva> get() throws ParseException;
    int getTamano();
    void insertar(Reserva reserva) throws OperationNotSupportedException, ParseException;
    Reserva buscar(Reserva reserva) throws ParseException;
    void borrar(Reserva reserva) throws OperationNotSupportedException, ParseException;
    List<Reserva> getReservas(Huesped huesped) throws ParseException;
    List<Reserva> getReservas(TipoHabitacion tipoHabitacion) throws ParseException;
    List<Reserva> getReservas(Habitacion habitacion) throws ParseException;
    List<Reserva> getReservasFuturas(Habitacion habitacion) throws ParseException;
    void realizarCheckin(Reserva reserva, LocalDateTime fecha) throws ParseException;
    void realizarCheckout(Reserva reserva, LocalDateTime fecha) throws ParseException;
    void comenzar();
    void terminar();
}
