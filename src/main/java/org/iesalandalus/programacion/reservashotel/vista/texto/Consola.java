package org.iesalandalus.programacion.reservashotel.vista.texto;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consola {
    private Consola(){}

    public static void mostrarMenu(){
        System.out.println("* - * - MENÚ - * - *");
        for (Opcion opcion1 : Opcion.values()){
            System.out.print(opcion1);
        }
    }

    public static Opcion elegirOpcion(){
        Opcion elegido;
        int elegidoInt, numOpciones;
        numOpciones = Opcion.values().length-1;
        do{
            System.out.print("Elija la opción deseada (0 - "+numOpciones+"): ");
            elegidoInt=Entrada.entero();
        }while (elegidoInt<0 || elegidoInt>numOpciones);
        elegido = Opcion.values()[elegidoInt];
        return elegido;
    }

    public static Huesped leerHuesped() throws NullPointerException, IllegalArgumentException, ParseException {
        String nombre, dni, correo, telefono, fecha;
        DateTimeFormatter formatoFechaNac = DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);
        LocalDate fechaNacimiento=null;
        System.out.println("Introduzca los datos del huésped. ");
        System.out.print("Nombre: ");
        nombre=Entrada.cadena();
        System.out.print("DNI: ");
        dni=Entrada.cadena();
        System.out.print("Correo electrónico: ");
        correo=Entrada.cadena();
        System.out.print("Teléfono: ");
        telefono=Entrada.cadena();
        System.out.print("Fecha de nacimiento (DD/MM/AAAA): ");
        fecha=Entrada.cadena();
        if (fecha.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No se puede introducir una fecha vacía.");
        }
        try {
            fechaNacimiento = LocalDate.parse(fecha, formatoFechaNac);
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
        }
        return (new Huesped(nombre, dni, correo, telefono, fechaNacimiento));
    }

    public static Huesped getHuespedPorDni() throws NullPointerException, IllegalArgumentException {
        String dni;
        System.out.println("Introduzca el DNI del huésped: ");
        dni=Entrada.cadena();
        dni=dni.toUpperCase();
        if (dni.isEmpty()){
            throw new NullPointerException("ERROR: No se puede buscar un DNI vacío.");
        }
        return new Huesped("nombre",dni,"correo@falso.es","950000000", LocalDate.of(2000,1,1));
    }

    public static LocalDate leerFecha(String mensaje) throws NullPointerException{
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_RESERVA);
        if (mensaje == null) {
            throw new NullPointerException("ERROR: La fecha está vacía.");
        }
        try {
            return LocalDate.parse(mensaje, formatoFecha);
        } catch (DateTimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static LocalDateTime leerFechaHora (String mensaje) throws IllegalArgumentException {
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA);
        if (mensaje.isEmpty()){
            throw new IllegalArgumentException("ERROR: La fecha/hora está vacía.");
        }
        try {
            return LocalDateTime.parse(mensaje, formatoFechaHora);
        } catch (DateTimeException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Habitacion leerHabitacion() throws NullPointerException, IllegalArgumentException {
        int planta=1, puerta=0, precio=0;
        TipoHabitacion tipoHabitacion;
        Habitacion habitacion=null;
        System.out.println("Introduzca los datos de la habitación. ");
        do{
            if (planta != 1){
                System.out.print("La planta tiene que ser un número entre "+ Habitacion.MIN_NUMERO_PLANTA +" y " + Habitacion.MAX_NUMERO_PLANTA + ". ");
            }
            System.out.print("Planta ("+ Habitacion.MIN_NUMERO_PLANTA + '-' + Habitacion.MAX_NUMERO_PLANTA +"): ");
            planta=Entrada.entero();
        } while (planta<Habitacion.MIN_NUMERO_PLANTA || planta > Habitacion.MAX_NUMERO_PLANTA);
        do{
            if (puerta != 0){
                System.out.print("La puerta tiene que ser un número entre "+ Habitacion.MIN_NUMERO_PUERTA +" y " + Habitacion.MAX_NUMERO_PUERTA + ". ");
            }
            System.out.print("Puerta (" + Habitacion.MIN_NUMERO_PUERTA + '-' + Habitacion.MAX_NUMERO_PUERTA + "): ");
            puerta=Entrada.entero();
        } while (puerta < Habitacion.MIN_NUMERO_PUERTA || puerta > Habitacion.MAX_NUMERO_PUERTA);
        do {
            if (precio != 0){
                System.out.print("El precio tiene que ser un número entero entre " + Habitacion.MIN_PRECIO_HABITACION + " y " + Habitacion.MAX_PRECIO_HABITACION + ". ");
            }
            System.out.print("Introduzca el precio (" + Habitacion.MIN_PRECIO_HABITACION + " - " + Habitacion.MAX_PRECIO_HABITACION + "): ");
            precio=Entrada.entero();
        } while (precio < Habitacion.MIN_PRECIO_HABITACION || precio > Habitacion.MAX_PRECIO_HABITACION);
        tipoHabitacion=leerTipoHabitacion();
        if (tipoHabitacion.equals(TipoHabitacion.SIMPLE)){
            habitacion = new Simple(planta, puerta, precio);
        }
        else if (tipoHabitacion.equals(TipoHabitacion.DOBLE)) {
            int numCamasInd, numCamasDob;
            do {
                System.out.println("Introduzca el número de camas individuales (" + Doble.MIN_NUM_CAMAS_INDIVIDUALES + " - " + Doble.MAX_NUM_CAMAS_INDIVIDUALES + "): ");
                numCamasInd = Entrada.entero();
                System.out.println("Introduzca el número de camas dobles (" + Doble.MIN_NUM_CAMAS_DOBLES + " - " + Doble.MAX_NUM_CAMAS_DOBLES + "): ");
                numCamasDob = Entrada.entero();
            } while (numCamasInd < Doble.MIN_NUM_CAMAS_INDIVIDUALES || numCamasInd > Doble.MAX_NUM_CAMAS_INDIVIDUALES || numCamasDob < Doble.MIN_NUM_CAMAS_DOBLES || numCamasDob > Doble.MAX_NUM_CAMAS_DOBLES);
            habitacion = new Doble(planta, puerta, precio, numCamasInd, numCamasDob);
        }
        else if (tipoHabitacion.equals(TipoHabitacion.TRIPLE)) {
            int numCamasInd, numCamasDob, numBanos;
            do {
                System.out.println("Introduzca el número de camas individuales (" + Triple.MIN_NUM_CAMAS_INDIVIDUALES + " - " + Triple.MAX_NUM_CAMAS_INDIVIDUALES + "): ");
                numCamasInd = Entrada.entero();
                System.out.println("Introduzca el número de camas dobles (" + Triple.MIN_NUM_CAMAS_DOBLES + " - " + Triple.MAX_NUM_CAMAS_DOBLES + "): ");
                numCamasDob = Entrada.entero();
                System.out.println("Introduzca el número de baños (" + Triple.MIN_NUM_BANOS + " - " + Triple.MAX_NUM_BANOS + "): ");
                numBanos = Entrada.entero();
            } while (numCamasInd < Triple.MIN_NUM_CAMAS_INDIVIDUALES || numCamasInd > Triple.MAX_NUM_CAMAS_INDIVIDUALES || numCamasDob < Triple.MIN_NUM_CAMAS_DOBLES || numCamasDob > Triple.MAX_NUM_CAMAS_DOBLES || numBanos < Triple.MIN_NUM_BANOS || numBanos > Triple.MAX_NUM_BANOS);
            habitacion = new Triple(planta, puerta, precio, numBanos, numCamasInd, numCamasDob);
        }
        else if (tipoHabitacion.equals(TipoHabitacion.SUITE)) {
            int numBanos;
            String hayJacuzzi;
            do {
                System.out.println("Introduzca el número de baños (" + Suite.MIN_NUM_BANOS + " - " + Suite.MAX_NUM_BANOS + "): ");
                numBanos = Entrada.entero();
                System.out.println("¿Quiere Jacuzzi (S/N): ");
                hayJacuzzi = Entrada.cadena();
                if (hayJacuzzi.equalsIgnoreCase("S") || hayJacuzzi.equalsIgnoreCase("1")) { // también acepto "1" como entrada válida para indicar que quiere jacuzzi
                    hayJacuzzi="true";
                }
            } while (numBanos < Suite.MIN_NUM_BANOS || numBanos > Suite.MAX_NUM_BANOS);
            habitacion = new Suite(planta, puerta, precio, numBanos, Boolean.parseBoolean(hayJacuzzi));
        }
        return habitacion;
    }

    public static Habitacion leerHabitacionPorIdentificador() throws IllegalArgumentException {
        int planta, puerta;
        Habitacion habitacion;
        System.out.println("Introduzca los datos de la habitación. ");
        System.out.print("Planta (" + Habitacion.MIN_NUMERO_PLANTA + " - " + Habitacion.MAX_NUMERO_PLANTA + "): ");
        planta=Entrada.entero();
        if (planta<Habitacion.MIN_NUMERO_PLANTA || planta>Habitacion.MAX_NUMERO_PLANTA){
            throw new IllegalArgumentException("ERROR: El número de planta es incorrecto.");
        }
        System.out.print("Puerta (" + Habitacion.MIN_NUMERO_PUERTA + " - " + Habitacion.MAX_NUMERO_PUERTA + "): ");
        puerta=Entrada.entero();
        if (puerta<Habitacion.MIN_NUMERO_PUERTA || puerta>Habitacion.MAX_NUMERO_PUERTA){
            throw new IllegalArgumentException("ERROR: El número de puerta es incorrecto.");
        }
        habitacion = new Simple(planta,puerta,40);
        return habitacion;
    }

    public static TipoHabitacion leerTipoHabitacion() {
        int tipoHab=0;
        TipoHabitacion tipoHabitacion;
        do {
            if (tipoHab != 0){
                System.out.print("Escriba la opción deseada (número entre 0 y " + TipoHabitacion.values().length);
            }
            System.out.println("Elija el tipo de habitación: ");
            for (TipoHabitacion tipoHabitacion1 : TipoHabitacion.values()){
                System.out.println(tipoHabitacion1.ordinal() + " - " + tipoHabitacion1.name());
            }
            tipoHab=Entrada.entero();
        } while (tipoHab<0 || tipoHab >=TipoHabitacion.values().length);
        tipoHabitacion=TipoHabitacion.values()[tipoHab];
        return tipoHabitacion;
    }

    public static Regimen leerRegimen() {
        Regimen regimen;
        int numReg = 0;
        do {
            if (numReg != 0){
                System.out.print("Escriba la opción deseada (número entre 0 y " + Regimen.values().length);
            }
            System.out.println("Elija el régimen deseado: ");
            for (Regimen regimen1 : Regimen.values()){
                System.out.println(regimen1.ordinal() + " - " + regimen1.name() + " .");
            }
            numReg=Entrada.entero();
        } while (numReg<0 || numReg>Regimen.values().length);
        regimen=Regimen.values()[numReg];
        return regimen;
    }

    public static Reserva leerReserva() throws IllegalArgumentException, NullPointerException, OperationNotSupportedException {
        Reserva reserva;
        Huesped huesped;
        Regimen regimen;
        TipoHabitacion tipoHabitacion;
        Habitacion habitacion=null;
        LocalDate fechaInicioReserva, fechaFinReserva;
        String entrada, salida;
        int numeroPersonas,maximoPersonas;
        huesped = new Huesped(getHuespedPorDni());
        tipoHabitacion = leerTipoHabitacion();
        switch (tipoHabitacion){
            case SIMPLE -> habitacion = new Simple(3,14,50);
            case DOBLE -> habitacion = new Doble(3,14,50,0,1);
            case TRIPLE -> habitacion = new Triple(3,14,50,1,1,1);
            case SUITE -> habitacion = new Suite(3,14,50,1,true);
        }
        maximoPersonas = habitacion.getNumeroMaximoPersonas();
        do{
            System.out.print("Introduzca el número de personas: ");
            numeroPersonas=Entrada.entero();
        }while (numeroPersonas<0 || numeroPersonas>maximoPersonas);
        regimen = leerRegimen();
        System.out.print("Introduzca la fecha de inicio de reserva (dd/mm/aaaa): ");
        entrada = Entrada.cadena();
        fechaInicioReserva = leerFecha(entrada);
        System.out.print("Introduzca la fecha de fin de reserva (dd/mm/aaaa): ");
        salida = Entrada.cadena();
        fechaFinReserva = leerFecha(salida);
        reserva=new Reserva(huesped,habitacion,regimen,fechaInicioReserva,fechaFinReserva,numeroPersonas);
        return reserva;
    }
}
