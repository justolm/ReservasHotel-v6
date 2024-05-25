package org.iesalandalus.programacion.reservashotel.vista.texto;

import org.bson.codecs.configuration.CodecConfigurationException;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class VistaTexto extends Vista {
    private boolean datosInicializados = false; // Para comprobar si ya se han inicializado los datos previamente.

    public VistaTexto(){}

    @Override
    public void setControlador(Controlador controlador) {
        if (getControlador() == null) {
            System.out.println("ERROR: El getControlador() no puede ser nulo.");
        }
        super.setControlador(getControlador());
    }

    @Override
    public void comenzar() {
        try{
            Opcion opcion = null;
            do{
                if (opcion != null){
                    System.out.println("Presione ENTER para continuar...");
                    Entrada.cadena();
                }
                Consola.mostrarMenu();
                opcion = Consola.elegirOpcion();
                opcion.ejecutar();
            }while (opcion != Opcion.SALIR);
            System.out.println("Hasta luego!!!!");
        } catch (NullPointerException | IllegalArgumentException | DateTimeException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void terminar() {
        System.out.println("Final de la ejecución.");
        getControlador().terminar();
    }

    public void insertarHuesped() {
        Huesped huesped;
        try {
            huesped = Consola.leerHuesped();
            getControlador().insertar(huesped);
            System.out.println("Huésped insertado correctamente.");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buscarHuesped() {
        Huesped huesped, huespedEncontrado;
        try {
            if (!getControlador().getHuespedes().isEmpty()){
                if (!datosInicializados){
                    inicializarDatos(); // Método creado para generar datos válidos para realizar pruebas.
                }
            }
            huesped = Consola.getHuespedPorDni();
            huespedEncontrado = getControlador().buscar(huesped);
            if (huespedEncontrado != null){
                System.out.println("Huésped encontrado.");
                System.out.println(huespedEncontrado);
            }
            else {
                System.out.println("El huésped indicado no existe.");
            }
        } catch (NullPointerException | IllegalArgumentException | ParseException e) {
            System.out.println(e.getMessage());
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void borrarHuesped() {
        Huesped huesped;
        try {
            huesped = Consola.getHuespedPorDni();
            getControlador().borrar(huesped);
            System.out.println("Huésped eliminado.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarHuespedes() {
        try {
            if (!getControlador().getHuespedes().isEmpty()){
                List<Huesped> listadoHuespedes = getControlador().getHuespedes();
                for (int i = 0 ; i < listadoHuespedes.size() ; i++){
                    System.out.println(listadoHuespedes.get(i));
                }
            }
            else {
                System.out.println("No hay huéspedes para mostrar.");
            }
        } catch (NullPointerException | IllegalArgumentException | ParseException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarHabitacion() {
        try {
            Habitacion habitacion;
            habitacion = Consola.leerHabitacion();
            getControlador().insertar(habitacion);
            System.out.println("Habitación insertada correctamente.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    public void buscarHabitacion() {
        Habitacion habitacion, habitacionEncontrada;
        try {
            habitacion = Consola.leerHabitacionPorIdentificador();
            habitacionEncontrada = getControlador().buscar(habitacion);
            if (habitacionEncontrada != null){
                System.out.println("Habitación encontrada.");
                System.out.println(habitacionEncontrada);
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void borrarHabitacion() {
        Habitacion habitacion;
        try {
            habitacion = Consola.leerHabitacionPorIdentificador();
            getControlador().borrar(habitacion);
            System.out.println("Habitación borrada.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println(e.getMessage());
        }
    }

    public void mostrarHabitaciones() {
        try {
            if(!getControlador().getHabitaciones().isEmpty()){
                List<Habitacion> ListadoHabitaciones = getControlador().getHabitaciones();
                ListadoHabitaciones.sort(Comparator.comparing(Habitacion::getPlanta).thenComparing(Habitacion::getPuerta));
                for (Habitacion habitacion : ListadoHabitaciones){
                    System.out.println(habitacion);
                }
            }
            else {
                System.out.println("No hay habitaciones para mostrar.");
            }
        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void insertarReserva() {
        Reserva reserva;
        Habitacion habitacion;
        TipoHabitacion tipoHabitacion = null;
        try {
            reserva = Consola.leerReserva();
            if (reserva.getHabitacion()instanceof Simple){
                tipoHabitacion = TipoHabitacion.SIMPLE;
            }
            if (reserva.getHabitacion()instanceof Doble){
                tipoHabitacion = TipoHabitacion.DOBLE;
            }
            if (reserva.getHabitacion()instanceof Triple){
                tipoHabitacion = TipoHabitacion.TRIPLE;
            }
            if (reserva.getHabitacion()instanceof Suite){
                tipoHabitacion = TipoHabitacion.SUITE;
            }
            habitacion = consultarDisponibilidad(tipoHabitacion, reserva.getFechaInicioReserva(), reserva.getFechaFinReserva());
            if (habitacion != null){
                Huesped huesped = getControlador().buscar(reserva.getHuesped());
                Regimen regimen = reserva.getRegimen();
                LocalDate fechaInicio = reserva.getFechaInicioReserva();
                if (fechaInicio.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");
                }
                LocalDate fechaFin = reserva.getFechaFinReserva();
                if (fechaFin.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");
                }
                int numPersonas = reserva.getNumeroPersonas();
                getControlador().insertar(new Reserva(huesped,habitacion,regimen,fechaInicio,fechaFin,numPersonas));
                System.out.println("Reserva insertada correctamente.");
            }
            else {
                System.out.println("ERROR: No hay habitaciones disponibles para esa fecha.");
            }
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException | ParseException |
                 CodecConfigurationException e){
            System.out.println(e.getMessage());
        }
    }

    public void mostrarReservasHuesped() {
        Huesped huesped;
        try {
            huesped = Consola.getHuespedPorDni();
            listarReservas(huesped);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarReservas (Huesped huesped) {
        if (huesped == null){
            System.out.println("ERROR: No se pueden listar reservas de un huésped nulo.");
        }
        else {
            try {
                if (!getControlador().getReservas(huesped).isEmpty()){
                    List<Reserva> listadoReservasHuesped = getControlador().getReservas(huesped);
                    listadoReservasHuesped.sort(Comparator.comparing(Reserva::getFechaInicioReserva).reversed().thenComparing(reserva -> reserva.getHabitacion().getPlanta()).thenComparing(reserva -> reserva.getHabitacion().getPuerta()));
                    for(Reserva reserva : listadoReservasHuesped){
                        System.out.println(reserva);
                    }
                }
                else  {
                    System.out.println("No existen reservas para el huésped indicado.");
                }
            } catch (NullPointerException | IllegalArgumentException | ParseException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void mostrarReservasTipoHabitacion() {
        Habitacion habitacionIden, habitacion;
        TipoHabitacion tipoHabitacion;
        habitacionIden = Consola.leerHabitacionPorIdentificador();
        habitacion = getControlador().buscar(habitacionIden);
        if (habitacion instanceof Simple) {
            tipoHabitacion = TipoHabitacion.SIMPLE;
        }
        else if (habitacion instanceof Doble) {
            tipoHabitacion = TipoHabitacion.DOBLE;
        }
        else if (habitacion instanceof Triple) {
            tipoHabitacion = TipoHabitacion.TRIPLE;
        }
        else {
            tipoHabitacion = TipoHabitacion.SUITE;
        }
        listarReservas(tipoHabitacion);
    }

    public void comprobarDisponibilidad() {
        LocalDate fechaEntrada, fechaSalida;
        Habitacion habitacion;
        TipoHabitacion tipoHabitacion;
        try {
            tipoHabitacion = Consola.leerTipoHabitacion();
            System.out.println("Introduzca la fecha de inicio de reserva (dd/mm/aa): ");
            fechaEntrada = Consola.leerFecha(Entrada.cadena());
            System.out.println("Introduzca la fecha de fin de reserva (dd/mm/aa): ");
            fechaSalida = Consola.leerFecha(Entrada.cadena());
            /*if (reserva.getHabitacion() instanceof Simple) {
                tipoHabitacion = TipoHabitacion.SIMPLE;
            }
            else if (reserva.getHabitacion() instanceof Doble) {
                tipoHabitacion = TipoHabitacion.DOBLE;
            }
            else if (reserva.getHabitacion() instanceof Triple) {
                tipoHabitacion = TipoHabitacion.TRIPLE;
            }
            else {
                tipoHabitacion = TipoHabitacion.SUITE;
            }*/
            habitacion = consultarDisponibilidad(tipoHabitacion, fechaEntrada, fechaSalida);
            if (habitacion != null) {
                System.out.println(habitacion);
            }
            else {
                System.out.println("No se ha encontrado ninguna habitación disponible del tipo " + tipoHabitacion + " disponible entre el " + fechaEntrada + " y el " + fechaSalida + '.');
            }
        } catch (NullPointerException | IllegalArgumentException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listarReservas (TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null){
            System.out.println("ERROR: No se pueden listar reservas de un tipo de habitación nulo.");
        }
        else {
            try {
                if (!getControlador().getReservas(tipoHabitacion).isEmpty()){
                    System.out.println("Reservas del tipo de habitación " + tipoHabitacion.name() + ": ");
                    List<Reserva> ListadoReservasTipoHabitacion = getControlador().getReservas(tipoHabitacion);
                    ListadoReservasTipoHabitacion.sort(Comparator.comparing(Reserva::getFechaInicioReserva).reversed().thenComparing(reserva -> reserva.getHuesped().getNombre()));
                    for (Reserva reserva : ListadoReservasTipoHabitacion){
                            System.out.println(reserva);
                    }
                }
                else {
                    System.out.println("No existen reservas para el tipo de habitación indicado.");
                }
            } catch (NullPointerException | IllegalArgumentException | ParseException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Reserva> getReservasAnulables(List<Reserva> reservasAAnular) {
        if (reservasAAnular == null){
            return null;
        }
        List<Reserva> reservasAnulables = new ArrayList<>();
        for (Reserva reserva : reservasAAnular) {
            if (reserva != null) {
                if (reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                    reservasAnulables.add(new Reserva(reserva));
                }
            }
        }
        return reservasAnulables;
    }

    public void anularReserva() throws NullPointerException, IllegalArgumentException, ParseException {
        int reservasAnulables = 0, reservaIncorrecta=0, numEleccion;
        String eleccion;
        Huesped huesped = new Huesped(Consola.getHuespedPorDni());
        List<Reserva> reservas1 = getReservasAnulables(getControlador().getReservas(huesped));
        if (reservas1 == null){
            throw new NullPointerException("ERROR: No hay reservas anulables para ese cliente.");
        } else if (reservas1.size() == 1) {
            do {
                System.out.println("¿Confirma que desea eliminar la reserva (S/N): " + reservas1.get(0).toString() + " ?");
                eleccion = Entrada.cadena();
                if (eleccion.equalsIgnoreCase("S")) {
                    try {
                        getControlador().borrar(reservas1.get(0));
                        System.out.println("Reserva eliminada.");
                    } catch (OperationNotSupportedException | NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } while (!eleccion.equalsIgnoreCase("s")&&!eleccion.equalsIgnoreCase("n"));
        } else {
            System.out.println("Se han encontrado varias reservas para el huésped indicado. Elija la que desea eliminar: ");
            for (Reserva reserva : reservas1){
                if (reserva != null){
                    System.out.println(reservasAnulables + ": " + reserva);
                    reservasAnulables++;
                }
            }
            try {
                do {
                    if(reservaIncorrecta != 0){
                        reservaIncorrecta = reservasAnulables-1;
                        System.out.println("Elija un valor entre 0 y " + reservaIncorrecta);
                    }
                    numEleccion = Entrada.entero();
                    reservaIncorrecta = 1;
                } while (numEleccion > reservasAnulables-1 || numEleccion < 0);
                getControlador().borrar(reservas1.get(numEleccion));
                System.out.println("Reserva eliminada.");
            } catch (OperationNotSupportedException | NullPointerException e) {
                System.out.println("ERROR:" + e.getMessage());
            }
        }
    }

    public void mostrarReservas() throws NullPointerException, ParseException {
        List<Reserva> reservas1 = getControlador().getReservas();
        int numReservas = 0;
        if (reservas1.isEmpty()){
            throw new NullPointerException("ERROR: No hay reservas almacenadas.");
        }
        reservas1.sort(Comparator.comparing(Reserva::getFechaInicioReserva).reversed().thenComparing(reserva -> reserva.getHabitacion().getPlanta()).thenComparing(reserva -> reserva.getHabitacion().getPuerta()));
        for (Reserva reserva2 : reservas1){
            System.out.println(reserva2.toString());
            numReservas++;
        }
        if (numReservas == 0){
            System.out.println("No hay reservas para mostrar.");
        }
    }

    public Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva) throws ParseException {
        boolean tipoHabitacionEncontrada = false;
        Habitacion habitacionDisponible = null;
        int numElementos;

        List<Habitacion> habitacionesTipoSolicitado= getControlador().getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado == null)
            return null;

        for (int i = 0; i < habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado.get(i) != null)
            {
                List<Reserva> reservasFuturas = getControlador().getReservasFuturas(habitacionesTipoSolicitado.get(i));
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

    public void realizarCheckin() throws NullPointerException, IllegalArgumentException, ParseException, DateTimeException {
        List<Reserva> reservas;
        Reserva reserva = null;
        LocalDateTime fecha;
        Huesped huesped;
        String confirmacion;
        int numOpcion, contador = 0;
        huesped=Consola.getHuespedPorDni();
        reservas=getControlador().getReservas(huesped);
        if (reservas == null){
            throw new NullPointerException("ERROR: El cliente no tiene reservas.");
        }
        else if (reservas.size() == 1) {
            do {
                System.out.println("¿Confirma que desea realizar el CheckIn para la reserva (S/N): " + reservas.get(0).toString() + " ?");
                confirmacion = Entrada.cadena();
                if (confirmacion.equalsIgnoreCase("S")) {
                    reserva = reservas.get(0);
                }
            } while (!confirmacion.equalsIgnoreCase("s") && !confirmacion.equalsIgnoreCase("n"));
        } else {
            System.out.println("Se han encontrado varias reservas para el huésped indicado. Elija para la que quiere hacer el CheckIn: ");
            for (Reserva reservaFor : reservas) {
                if (reservaFor != null) {
                    System.out.println(contador + ": " + reservaFor);
                    contador++;
                }
            }
            do {
                numOpcion = Entrada.entero();
            } while (numOpcion < 0 || numOpcion > contador);
            reserva = reservas.get(numOpcion);
        }
        do{
            System.out.println("Introduzca la fecha y hora de entrada (dd/mm/aaaa HH:mm): ");
            fecha = Consola.leerFechaHora(Entrada.cadena());
        } while (Objects.requireNonNull(fecha).isBefore(LocalDate.now().atStartOfDay()));
        if (reserva == null){
            throw new NullPointerException("ERROR: No se puede introducir una reserva nula.");
        }
        if (reserva.getCheckIn() != null) {
            throw new IllegalArgumentException("ERROR: No se puede modificar un CheckIn.");
        }
        else {
            try {
                getControlador().realizarCheckin(reserva, fecha);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void realizarCheckout() throws NullPointerException, IllegalArgumentException, ParseException, DateTimeException {
        List<Reserva> reservas;
        Reserva reserva = null;
        LocalDateTime fecha;
        Huesped huesped;
        String confirmacion;
        int numOpcion, contador=0;
        huesped = Consola.getHuespedPorDni();
        reservas = getControlador().getReservas(huesped);
        if (reservas == null){
            throw new NullPointerException("ERROR: El cliente no tiene reservas.");
        }
        else if (reservas.size() == 1) {
            do {
                System.out.println("¿Confirma que desea realizar el CheckOut para la reserva (S/N): " + reservas.get(0).toString() + " ?");
                confirmacion = Entrada.cadena();
                if (confirmacion.equalsIgnoreCase("S")) {
                    reserva = reservas.get(0);
                }
            } while (!confirmacion.equalsIgnoreCase("s")&&!confirmacion.equalsIgnoreCase("n"));
        } else {
            System.out.println("Se han encontrado varias reservas para el huésped indicado. Elija para la que quiere hacer el CheckOut: ");
            for (Reserva reservaFor : reservas) {
                if (reservaFor != null) {
                    System.out.println(contador + ": " + reservaFor);
                    contador++;
                }
            }
            do {
                numOpcion = Entrada.entero();
            } while (numOpcion < 0 || numOpcion > contador);
            reserva = reservas.get(numOpcion);
        }
        do{
            System.out.println("Introduzca la fecha y hora de salida (dd/mm/aaaa HH:mm): ");
            fecha = Consola.leerFechaHora(Entrada.cadena());
        } while (Objects.requireNonNull(fecha).isBefore(LocalDate.now().atStartOfDay()));
        if(reserva == null){
            throw new NullPointerException("ERROR: No se puede introducir una reserva nula.");
        }
        try {
            getControlador().realizarCheckout(reserva, fecha);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // método creado para inicializar algunos datos al trabajar con memoria y así optimizar las pruebas
    private void inicializarDatos () throws NullPointerException, IllegalArgumentException, OperationNotSupportedException, ParseException {
        Huesped huesped = new Huesped("Justo Lopez", "45596798b", "justolm@gmail.com", "666619806", LocalDate.of(1980, 11, 19));
        Huesped huesped1 = new Huesped("Noe Lilla", "11111111h", "noe@lilla.es", "650476674", LocalDate.of(1982, 11, 7));
        Habitacion habitacion = new Simple(1,1,45.0);
        Habitacion habitacion1 = new Suite(3,13,80,2,true);
        Habitacion habitacion2 = new Doble(2,2,80,0,1);
        Reserva reserva = new Reserva(huesped, habitacion1, Regimen.MEDIA_PENSION, LocalDate.now(),LocalDate.now().plusDays(2),2);
        Reserva reserva1 = new Reserva(huesped, habitacion, Regimen.SOLO_ALOJAMIENTO, LocalDate.of(2024, 5, 13), LocalDate.of(2024, 5, 15), 1);
        getControlador().insertar(huesped);
        getControlador().insertar(huesped1);
        getControlador().insertar(habitacion);
        getControlador().insertar(habitacion1);
        getControlador().insertar(habitacion2);
        getControlador().insertar(reserva);
        getControlador().insertar(reserva1);
        System.out.println("Datos inicializados. ");
        datosInicializados = true;
    }
}
