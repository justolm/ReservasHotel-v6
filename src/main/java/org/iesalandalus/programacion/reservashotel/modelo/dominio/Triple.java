package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Triple extends Habitacion {
    private static final int NUM_MAXIMO_PERSONAS = 3;
    public static final int MIN_NUM_BANOS = 0;
    public static final int MAX_NUM_BANOS = 2;
    public static final int MIN_NUM_CAMAS_INDIVIDUALES = 1;
    public static final int MAX_NUM_CAMAS_INDIVIDUALES = 3;
    public static final int MIN_NUM_CAMAS_DOBLES = 0;
    public static final int MAX_NUM_CAMAS_DOBLES = 1;
    private int numBanos, numCamasIndividuales, numCamasDobles;

    public Triple (int planta, int puerta, double precio, int numBanos, int numCamasIndividuales, int numCamasDobles) {
        super(planta, puerta, precio);
        setNumBanos(numBanos);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumCamas();
    }

    public Triple (Triple habitacionTriple) {
        super(habitacionTriple);
        setNumBanos(habitacionTriple.numBanos);
        if (habitacionTriple.numCamasIndividuales == 0 && habitacionTriple.numCamasDobles == 0){
            habitacionTriple.numCamasIndividuales = MAX_NUM_CAMAS_INDIVIDUALES;
        }
        setNumCamasIndividuales(habitacionTriple.numCamasIndividuales);
        setNumCamasDobles(habitacionTriple.numCamasDobles);
        validaNumCamas();
    }

    public int getNumBanos () {
        return numBanos;
    }

    public void setNumBanos (int numBanos) throws IllegalArgumentException {
        if (numBanos < MIN_NUM_BANOS || numBanos > MAX_NUM_BANOS) {
            throw new IllegalArgumentException("ERROR: El número de baños no puede ser inferior a " + MIN_NUM_BANOS + " ni superior a " + MAX_NUM_BANOS);
        }
        this.numBanos = numBanos;
    }

    public int getNumCamasIndividuales () {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) throws IllegalArgumentException {
        if (numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES){
            throw new IllegalArgumentException("ERROR: El número de camas individuales de una habitación triple no puede ser inferior a " + MIN_NUM_CAMAS_INDIVIDUALES + " ni mayor que " + MAX_NUM_CAMAS_INDIVIDUALES);
        }
        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) throws IllegalArgumentException {
        if (numCamasDobles < MIN_NUM_CAMAS_DOBLES || numCamasDobles > MAX_NUM_CAMAS_DOBLES) {
            throw new IllegalArgumentException("ERROR: El número de camas dobles de una habitación triple no puede ser inferior a " + MIN_NUM_CAMAS_DOBLES + " ni mayor que " + MAX_NUM_CAMAS_DOBLES);
        }
        this.numCamasDobles = numCamasDobles;
    }

    private void validaNumCamas () throws IllegalArgumentException {
        if (numCamasDobles == 1 && numCamasIndividuales != 1) {
            throw new IllegalArgumentException("ERROR: La distribución de camas en una habitación triple tiene que ser " + MAX_NUM_CAMAS_INDIVIDUALES + " camas individuales y " + MIN_NUM_CAMAS_DOBLES + " doble o " + MIN_NUM_CAMAS_INDIVIDUALES + " cama individual y " + MAX_NUM_CAMAS_DOBLES + " doble");
        }
        if (numCamasDobles == 0 && numCamasIndividuales != MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: La distribución de camas en una habitación triple tiene que ser " + MAX_NUM_CAMAS_INDIVIDUALES + " camas individuales y " + MIN_NUM_CAMAS_DOBLES + " doble o " + MIN_NUM_CAMAS_INDIVIDUALES + " cama individual y " + MAX_NUM_CAMAS_DOBLES + " doble");
        }
        if (numCamasDobles < MIN_NUM_CAMAS_DOBLES || numCamasDobles > MAX_NUM_CAMAS_DOBLES || numCamasIndividuales < MIN_NUM_CAMAS_INDIVIDUALES || numCamasIndividuales > MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: Se ha introducido un número de camas fuera de los parámetros aceptados.");
        }
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return super.toString()+", habitación triple, capacidad=" + NUM_MAXIMO_PERSONAS + " personas, baños=" + numBanos + ", camas individuales=" + numCamasIndividuales + ", camas dobles=" + numCamasDobles;
    }
}
