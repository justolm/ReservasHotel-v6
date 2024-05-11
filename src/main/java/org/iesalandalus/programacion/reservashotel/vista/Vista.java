package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;

public abstract class Vista {
    private Controlador controlador;

    public void setControlador(Controlador controlador) throws NullPointerException {
        if (controlador == null){
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public Controlador getControlador() {
        return controlador;
    }

    public abstract void comenzar();

    public abstract void terminar();

}
