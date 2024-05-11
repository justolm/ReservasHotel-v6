package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {
    SOLO_ALOJAMIENTO("SOLO_ALOJAMIENTO",0),
    ALOJAMIENTO_DESAYUNO("ALOJAMIENTO_DESAYUNO",15),
    MEDIA_PENSION("MEDIA_PENSION",30),
    PENSION_COMPLETA("PENSION_COMPLETA",50);

    private final String cadenaAMostrar;
    private final double incrementoPrecio;

    private Regimen(String cadenaAMostrar, double incrementoPrecio) {
        this.cadenaAMostrar = cadenaAMostrar;
        this.incrementoPrecio = incrementoPrecio;
    }

    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }

    @Override
    public String toString() {
        return "Regimen{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                ", incrementoPrecio=" + incrementoPrecio +
                '}';
    }
}
