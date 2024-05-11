package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.texto.Opcion;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

import java.time.DateTimeException;


public class MainApp {

    public static void main(String[] args) {
        System.out.println("Programa para la Gestión de Hoteles IES Al-Ándalus");
        try {
            IModelo modelo = procesarArgumentosFuenteDatos(args);
            VistaTexto vista = new VistaTexto();
            Controlador controlador = new Controlador(modelo, vista);
            Opcion.setVista(vista);
            controlador.comenzar();
        } catch (NullPointerException | IllegalArgumentException | DateTimeException e){
            System.out.println(e.getMessage());
        }
    }

    private static IModelo procesarArgumentosFuenteDatos (String[] argumentos) {
        String arg = argumentos[0];
        String memoria = "-fdmemoria";
        String mongoDB = "-fdmongodb";
        IModelo modelo;
        if (arg.contains(memoria)){
            modelo = new Modelo(FactoriaFuenteDatos.MEMORIA);
        }
        else if (arg.contains(mongoDB)) {
            modelo = new Modelo(FactoriaFuenteDatos.MONGODB);
        }
        // Creo una opción por defecto si no recibe parámetros, que realmente debería de sustituir a su elección.
        else {
            modelo = new Modelo(FactoriaFuenteDatos.MEMORIA);
        }
        return modelo;
    }
}
