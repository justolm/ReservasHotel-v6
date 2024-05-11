package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

public enum FactoriaVista {
    TEXTO {
        @Override
        public Vista crear() {
            return new VistaTexto();
        }
    },
    GRAFICA {
        @Override
        public Vista crear() {
            return new VistaGrafica();
        }
    };

    public abstract Vista crear();
}
