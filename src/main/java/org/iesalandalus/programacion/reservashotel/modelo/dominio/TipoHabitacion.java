package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SIMPLE("SIMPLE"),
    DOBLE("DOBLE"),
    TRIPLE("TRIPLE"),
    SUITE("SUITE");

    private final String cadenaAMostrar;

    @Override
    public String toString() {
        return "TipoHabitacion{" +
                "cadenaAMostrar='" + cadenaAMostrar + '}';
    }

    TipoHabitacion(String cadenaAMostrar){
        this.cadenaAMostrar = cadenaAMostrar;
    }
}
