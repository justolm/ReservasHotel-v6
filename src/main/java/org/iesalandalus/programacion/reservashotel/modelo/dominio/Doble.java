package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion {
    private static final int NUM_MAXIMO_PERSONAS = 2;
    public static final int MIN_NUM_CAMAS_INDIVIDUALES = 0;
    public static final int MAX_NUM_CAMAS_INDIVIDUALES = 2;
    public static final int MIN_NUM_CAMAS_DOBLES = 0;
    public static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numCamasIndividuales, numCamasDobles;

    public Doble (int planta, int puerta, double precio, int numCamasIndividuales, int numCamasDobles) {
        super(planta, puerta, precio);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumCamas();
    }

    public Doble (Doble habitacionDoble) {
        super(habitacionDoble);
        if (habitacionDoble.numCamasIndividuales == 0 && habitacionDoble.numCamasDobles == 0){
            habitacionDoble.numCamasIndividuales=MAX_NUM_CAMAS_INDIVIDUALES;
        }
        setNumCamasIndividuales(habitacionDoble.numCamasIndividuales);
        setNumCamasDobles(habitacionDoble.numCamasDobles);
        validaNumCamas();
    }

    public int getNumCamasIndividuales () {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales (int numCamasIndividuales) throws IllegalArgumentException {
        if (numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: El número de camas individuales de una habitación doble no puede ser inferior a " + MIN_NUM_CAMAS_INDIVIDUALES + " ni mayor que " + MAX_NUM_CAMAS_INDIVIDUALES);
        }
        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles () {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        if (numCamasDobles > MAX_NUM_CAMAS_DOBLES || numCamasDobles < MIN_NUM_CAMAS_DOBLES) {
            throw new IllegalArgumentException("ERROR: El número de camas dobles de una habitación doble no puede ser inferior a " + MIN_NUM_CAMAS_DOBLES + " ni mayor que " + MAX_NUM_CAMAS_DOBLES);
        }
        this.numCamasDobles = numCamasDobles;
    }

    private void validaNumCamas () throws IllegalArgumentException {
        if (numCamasDobles == 1 && numCamasIndividuales ==2) {
            throw new IllegalArgumentException("ERROR: La distribución de camas en una habitación doble tiene que ser " + MAX_NUM_CAMAS_INDIVIDUALES + " camas individuales y " + MIN_NUM_CAMAS_DOBLES + " doble o " + MIN_NUM_CAMAS_INDIVIDUALES + " camas individuales y " + MAX_NUM_CAMAS_DOBLES + " doble");
        }
        else if (numCamasIndividuales == 1) {
            throw new IllegalArgumentException("ERROR: La distribución de camas en una habitación doble tiene que ser " + MAX_NUM_CAMAS_INDIVIDUALES + " camas individuales y " + MIN_NUM_CAMAS_DOBLES + " doble o " + MIN_NUM_CAMAS_INDIVIDUALES + " camas individuales y " + MAX_NUM_CAMAS_DOBLES + " doble");
        }
        else if (numCamasDobles == 0 && numCamasIndividuales == 0) {
            System.out.println("ERROR: Hay que seleccionar una cama doble o dos individuales para la habitación doble.");
        }
        else if (numCamasDobles < MIN_NUM_CAMAS_DOBLES || numCamasDobles > MAX_NUM_CAMAS_DOBLES || numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: Se ha introducido un número de camas fuera de los parámetros aceptados.");
        }
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return super.toString()+", habitación doble, capacidad=" + NUM_MAXIMO_PERSONAS + " personas, camas individuales=" + numCamasIndividuales + ", camas dobles=" + numCamasDobles;
    }
}
