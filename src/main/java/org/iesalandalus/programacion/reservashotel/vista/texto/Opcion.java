package org.iesalandalus.programacion.reservashotel.vista.texto;

import java.text.ParseException;

public enum Opcion {
    SALIR("SALIR") {
        @Override
        public void ejecutar() {
            vista.terminar();
        }
    },
    INSERTAR_HUESPED("INSERTAR HUÉSPED") {
        @Override
        public void ejecutar() {
            vista.insertarHuesped();
        }
    },
    BUSCAR_HUESPED("BUSCAR HUÉSPED") {
        @Override
        public void ejecutar() {
            vista.buscarHuesped();
        }
    } ,
    BORRAR_HUESPED("BORRAR HUÉSPED") {
        @Override
        public void ejecutar() {
            vista.borrarHuesped();
        }
    },
    MOSTRAR_HUESPEDES("MOSTRAR HUÉSPEDES") {
        @Override
        public void ejecutar() {
            vista.mostrarHuespedes();
        }
    },
    INSERTAR_HABITACION("INSERTAR HABITACIÓN") {
        @Override
        public void ejecutar() {
            vista.insertarHabitacion();
        }
    },
    BUSCAR_HABITACION("BUSCAR HABITACIÓN") {
        @Override
        public void ejecutar() {
            vista.buscarHabitacion();
        }
    },
    BORRAR_HABITACION("BORRAR HABITACIÓN") {
        @Override
        public void ejecutar() {
            vista.borrarHabitacion();
        }
    },
    MOSTRAR_HABITACIONES("MOSTRAR HABITACIONES") {
        @Override
        public void ejecutar() {
            vista.mostrarHabitaciones();
        }
    },
    INSERTAR_RESERVA("INSERTAR RESERVA") {
        @Override
        public void ejecutar() {
            vista.insertarReserva();
        }
    },
    ANULAR_RESERVA("ANULAR RESERVA") {
        @Override
        public void ejecutar() throws ParseException {
            vista.anularReserva();
        }
    },
    MOSTRAR_RESERVAS("MOSTRAR RESERVAS") {
        @Override
        public void ejecutar() throws NullPointerException, ParseException {
            vista.mostrarReservas();
        }
    },
    LISTAR_RESERVAS_HUESPED("LISTAR RESERVAS HUÉSPED") {
        @Override
        public void ejecutar() {
            vista.mostrarReservasHuesped();
        }
    },
    LISTAR_RESERVAS_TIPO_HABITACION("LISTAR RESERVAS TIPO HABITACIÓN") {
        @Override
        public void ejecutar() {
            vista.mostrarReservasTipoHabitacion();
        }
    },
    CONSULTAR_DISPONIBILIDAD("CONSULTAR DISPONIBILIDAD") {
        @Override
        public void ejecutar() {
            vista.comprobarDisponibilidad();
        }
    },
    REALIZAR_CHECKIN("REALIZAR CHECKIN") {
        @Override
        public void ejecutar() throws ParseException {
            vista.realizarCheckin();
        }
    },
    REALIZAR_CHECKOUT("REALIZAR CHECKOUT") {
        @Override
        public void ejecutar() throws ParseException {
            vista.realizarCheckout();
        }
    };

    private static VistaTexto vista;

    public static void setVista(VistaTexto vista){
        Opcion.vista = vista;
    }

    public abstract void ejecutar() throws ParseException;

    private final String mensajeAMostrar;
    Opcion(String mensajeAMostrar){
        this.mensajeAMostrar = mensajeAMostrar;
    }

    @Override
    public String toString() {
        return ordinal() + ".- " + mensajeAMostrar + '\n';
    }
}
