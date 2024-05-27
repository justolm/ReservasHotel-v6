package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.time.LocalDate;

public class ControladorVentanaAnadirReserva {

    @FXML private Button btnAnadirAReserva;
    @FXML private Button btnCancelarAReserva;
    @FXML private ChoiceBox<String> chbTipoHabitacion;
    @FXML private ChoiceBox<String> chbTipoRegimen;
    @FXML private DatePicker dpFechaEntrada;
    @FXML private DatePicker dpFechaSalida;
    @FXML private TextField tfDniHuesped;
    @FXML private TextField tfNumeroHuespedes;

    private void cargaOpcionesTipoHabitacion() {
        for (TipoHabitacion tipoHabitacion : TipoHabitacion.values()) {
            chbTipoHabitacion.getItems().add(tipoHabitacion.name());
        }
        chbTipoHabitacion.setValue(TipoHabitacion.SIMPLE.name());
    }

    private void cargaOpcionesRegimen() {
        for (Regimen regimen : Regimen.values()) {
            chbTipoRegimen.getItems().add(regimen.name());
        }
        chbTipoRegimen.setValue(Regimen.SOLO_ALOJAMIENTO.name());
    }

    @FXML
    private void initialize() {
        cargaOpcionesTipoHabitacion();
        cargaOpcionesRegimen();
    }

    @FXML
    void anadirAReserva() {
        String error = "";
        try {
            Huesped huespedDNI = new Huesped("Falso", tfDniHuesped.getText(),"correo@falso.es", "963852741", LocalDate.of(1980,11,19));
            Huesped huespedReal = VistaGrafica.getInstancia().getControlador().buscar(huespedDNI);
            Habitacion habitacion = VistaGrafica.getInstancia().getControlador().consultarDisponibilidad(TipoHabitacion.valueOf(chbTipoHabitacion.getValue()),dpFechaEntrada.getValue(),dpFechaSalida.getValue());
            Reserva reserva = new Reserva(huespedReal,habitacion,Regimen.valueOf(chbTipoRegimen.getValue()),dpFechaEntrada.getValue(),dpFechaSalida.getValue(),Integer.parseInt(tfNumeroHuespedes.getText()));
            VistaGrafica.getInstancia().getControlador().insertar(reserva);
            System.out.println(reserva);
            Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Añadir reserva", "Reserva insertada correctamente.");
            ((Stage) btnAnadirAReserva.getScene().getWindow()).close();
        } catch (ParseException | NullPointerException | IllegalArgumentException | OperationNotSupportedException er) {
            error = String.valueOf(er.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Añadir reserva", error);
        }
    }

    @FXML
    void cancelarAReserva() {
        ((Stage) btnCancelarAReserva.getScene().getWindow()).close();
    }

}

