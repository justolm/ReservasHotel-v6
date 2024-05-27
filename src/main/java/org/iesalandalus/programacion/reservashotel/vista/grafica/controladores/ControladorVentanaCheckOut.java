package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ControladorVentanaCheckOut {
    @FXML private DatePicker dpFechaCheckOut;
    @FXML private TextField tfHoraCheckOut;
    @FXML private TextField tfMinutoCheckOut;
    @FXML private Button btnAceptarCheckOut;
    @FXML private Button btnCancelarCheckOut;
    @FXML private Slider sHoraCheckOut;
    @FXML private Slider sMinutoCheckOut;

    @FXML ControladorVentanaPrincipal controladorVentanaPrincipalenCheckOut;

    @FXML
    public void initialize(){
        dpFechaCheckOut.setValue(LocalDate.now());
        tfHoraCheckOut.setText(String.valueOf(sHoraCheckOut.getValue()));
        tfHoraCheckOut.textProperty().bindBidirectional(sHoraCheckOut.valueProperty(), NumberFormat.getCompactNumberInstance());
        tfMinutoCheckOut.setText(String.valueOf(sMinutoCheckOut.getValue()));
        tfMinutoCheckOut.textProperty().bindBidirectional(sMinutoCheckOut.valueProperty(), NumberFormat.getCompactNumberInstance());
    }

    @FXML
    void aceptarCheckOut() {
        String error = "";
        try {
            System.out.println(dpFechaCheckOut.getValue());
            System.out.println((int) sHoraCheckOut.getValue());
            System.out.println((int) sMinutoCheckOut.getValue());
            LocalDateTime fechaHoraCheckOut = LocalDateTime.of(dpFechaCheckOut.getValue(), LocalTime.of((int) sHoraCheckOut.getValue(), (int) sMinutoCheckOut.getValue()));
            System.out.println(fechaHoraCheckOut);
            controladorVentanaPrincipalenCheckOut.realizarCheckOut(fechaHoraCheckOut);
            ((Stage)btnAceptarCheckOut.getScene().getWindow()).close();
        } catch (NullPointerException | IllegalArgumentException er) {
            error = String.valueOf(er.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Insertar CheckOut", error);
        }
    }

    @FXML
    void cancelarCheckOut() {
        ((Stage) btnCancelarCheckOut.getScene().getWindow()).close();
    }

    @FXML
    public void recibeControladorCheckOut(ControladorVentanaPrincipal controladorVentanaPrincipal) {
        controladorVentanaPrincipalenCheckOut = controladorVentanaPrincipal;
    }
}
