package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public abstract class Habitacion {
    public static final double MIN_PRECIO_HABITACION = 40.0;
    public static final double MAX_PRECIO_HABITACION = 150.0;
    public static final int MIN_NUMERO_PUERTA = 0;
    public static final int MAX_NUMERO_PUERTA = 14;
    public static final int MIN_NUMERO_PLANTA = 1;
    public static final int MAX_NUMERO_PLANTA = 3;
    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;

    public Habitacion(int planta, int puerta, double precio) throws IllegalArgumentException {
        if (planta<MIN_NUMERO_PLANTA||planta>MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");
        }
        else if (puerta<MIN_NUMERO_PUERTA||puerta>MAX_NUMERO_PUERTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 0 ni mayor que 14.");
        }
        else if (precio<MIN_PRECIO_HABITACION||precio>MAX_PRECIO_HABITACION) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");
        }
        else{
            setPlanta(planta);
            setPuerta(puerta);
            setPrecio(precio);
            setIdentificador();
        }
    }

    public Habitacion(Habitacion habitacion) throws NullPointerException {
        if(habitacion==null) {
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        }
        this.identificador = habitacion.identificador;
        this.planta = habitacion.planta;
        this.puerta = habitacion.puerta;
        this.precio = habitacion.precio;
    }

    public abstract int getNumeroMaximoPersonas ();

    public String getIdentificador() {
        return String.valueOf(this.planta+""+this.puerta);
    }

    private void setIdentificador() {
        this.identificador = String.valueOf(planta+""+puerta);
    }

    private void setIdentificador(String identificador) throws NullPointerException {
        if(identificador==null)
            throw new NullPointerException("ERROR: Identificador nulo");
        this.identificador = identificador;
    }

    public int getPlanta() {
        return planta;
    }

    private void setPlanta(int planta) throws IllegalArgumentException {
        if(planta<MIN_NUMERO_PLANTA||planta>MAX_NUMERO_PLANTA)
            throw new IllegalArgumentException("ERROR: planta errónea");
        this.planta = planta;
    }

    public int getPuerta() {
        return puerta;
    }

    private void setPuerta(int puerta) throws IllegalArgumentException {
        if (puerta<MIN_NUMERO_PUERTA||puerta>MAX_NUMERO_PUERTA){
            throw new IllegalArgumentException("ERROR: Puerta incorrecta.");
            }
        this.puerta = puerta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) throws IllegalArgumentException {
        if (precio<MIN_PRECIO_HABITACION||precio>MAX_PRECIO_HABITACION){
            throw new IllegalArgumentException("ERROR: Precio incorrecto.");
        }
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitacion that)) return false;
        return Objects.equals(getIdentificador(), that.getIdentificador());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentificador());
    }

    @Override
    public String toString() {
        return "identificador=" + identificador + " (" + planta + '-' + puerta +')' +
                ", precio habitación=" + precio;
    }
}
