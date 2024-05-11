package org.iesalandalus.programacion.reservashotel.vista.grafica;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class VistaGrafica extends Vista {
    VistaGrafica instancia;

    public VistaGrafica getInstancia() {
        if (instancia == null){
            instancia = new VistaGrafica();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        LanzadorVentanaPrincipal.comenzar();
    }

    @Override
    public void terminar() {
        getControlador().terminar();
    }
}
