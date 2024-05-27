package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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

    public void borrar (Huesped huesped) throws OperationNotSupportedException, NullPointerException, ParseException, IllegalArgumentException {
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

    public void borrar (Habitacion habitacion) throws OperationNotSupportedException, NullPointerException, ParseException, IllegalArgumentException {
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

    public Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) throws ParseException {
        boolean tipoHabitacionEncontrada = false;
        Habitacion habitacionDisponible = null;
        int numElementos;

        List<Habitacion> habitacionesTipoSolicitado= getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado == null)
            return null;

        for (int i = 0; i < habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado.get(i) != null)
            {
                List<Reserva> reservasFuturas = getReservasFuturas(habitacionesTipoSolicitado.get(i));
                numElementos = reservasFuturas.size();

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que está disponible.
                    switch (tipoHabitacion){
                        case SIMPLE -> habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                        case DOBLE -> habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                        case TRIPLE -> habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                        case SUITE -> habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                    }
                    tipoHabitacionEncontrada = true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                    reservasFuturas.sort(Comparator.comparing(Reserva::getFechaFinReserva).reversed()); //

                    /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                    mostrar(reservasFuturas);*/

                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva()) || fechaInicioReserva.isEqual(reservasFuturas.get(0).getFechaFinReserva())) {
                        switch (tipoHabitacion){
                            case SIMPLE -> habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                            case DOBLE -> habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                            case TRIPLE -> habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                            case SUITE -> habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                        }
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posición 0), quiere decir que la habitación está disponible en las fechas indicadas.

                        reservasFuturas.sort(Comparator.comparing(Reserva::getFechaInicioReserva));

                        /*System.out.println("\n\nMostramos las reservas ordenadas por fecha de inicio de menor a mayor (numelementos="+numElementos+")");
                        mostrar(reservasFuturas);*/

                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva()) || fechaFinReserva.isEqual(reservasFuturas.get(0).getFechaInicioReserva())) {
                            switch (tipoHabitacion){
                                case SIMPLE -> habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                                case DOBLE -> habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                                case TRIPLE -> habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                                case SUITE -> habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                            }
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas están algún hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j = 1; j < reservasFuturas.size() && !tipoHabitacionEncontrada; j++)
                        {
                            if (reservasFuturas.get(j) != null && reservasFuturas.get(j - 1) != null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas.get(j - 1).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {

                                    switch (tipoHabitacion){
                                        case SIMPLE -> habitacionDisponible = new Simple((Simple) habitacionesTipoSolicitado.get(i));
                                        case DOBLE -> habitacionDisponible = new Doble((Doble) habitacionesTipoSolicitado.get(i));
                                        case TRIPLE -> habitacionDisponible = new Triple((Triple) habitacionesTipoSolicitado.get(i));
                                        case SUITE -> habitacionDisponible = new Suite((Suite) habitacionesTipoSolicitado.get(i));
                                    }
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return habitacionDisponible;
    }

    private void setFuenteDatos (IFuenteDatos fuenteDatos) {
        Modelo.fuenteDatos = fuenteDatos;
    }
}
