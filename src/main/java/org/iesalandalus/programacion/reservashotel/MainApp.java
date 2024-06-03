package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.FactoriaVista;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;
import org.iesalandalus.programacion.reservashotel.vista.texto.Opcion;


import java.time.DateTimeException;


public class MainApp {

    public static void main(String[] args) {
        System.out.println("Programa para la Gestión de Hoteles IES Al-Ándalus");
        try {
            IModelo modelo = procesarArgumentosFuenteDatos(args);
            Vista vista = procesarArgumentosVista(args).crear();
            Controlador controlador = new Controlador(modelo, vista);
            if (args[1].contains("-vTexto")){
                Opcion.setVista((VistaTexto) vista);
            }
            controlador.comenzar();
        } catch (NullPointerException | IllegalArgumentException | DateTimeException e){
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException er) { // Para que, en caso de no tener parámetros, inicie igualmente sin dar error.
            System.out.println(er.getMessage());
            main(new String[]{"-fdmongodb", "-vGrafica"});
        }
    }

    private static IModelo procesarArgumentosFuenteDatos (String[] argumentos) {
        String arg = argumentos[0];
        String memoria = "-fdmemoria";
        String mongoDB = "-fdmongodb";
        String fichero = "-fdfichero";
        IModelo modelo;
        if (arg.contains(memoria)){
            modelo = new Modelo(FactoriaFuenteDatos.MEMORIA);
        }
        else if (arg.contains(mongoDB)) {
            modelo = new Modelo(FactoriaFuenteDatos.MONGODB);
        }
        else if (arg.contains(fichero)) {
            modelo = new Modelo(FactoriaFuenteDatos.FICHERO);
        }
        // Creo una opción por defecto si no recibe parámetros, que realmente debería de sustituir a su elección.
        else {
            modelo = new Modelo(FactoriaFuenteDatos.MONGODB);
        }
        return modelo;
    }

    private static FactoriaVista procesarArgumentosVista (String[] argumentos) {
        String arg = argumentos[1];
        String texto = "-vTexto";
        String grafica = "-vGrafica";
        FactoriaVista vista;
        if (arg.contains(texto)) {
            vista = FactoriaVista.TEXTO;
        } else if (arg.contains(grafica)) {
            vista = FactoriaVista.GRAFICA;
        }
        // Creo una opción por defecto si no recibe parámetros, que realmente debería de sustituir a su elección.
        else {
            vista = FactoriaVista.GRAFICA;
        }
        return vista;
    }
}
