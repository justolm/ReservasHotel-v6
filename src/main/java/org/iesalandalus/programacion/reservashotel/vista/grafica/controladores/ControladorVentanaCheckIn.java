package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ControladorVentanaCheckIn {
    @FXML private DatePicker dpFechaCheckIn;
    @FXML private TextField tfHoraCheckIn;
    @FXML private TextField tfMinutoCheckIn;
    @FXML private Button btnAceptarCheckIn;
    @FXML private Button btnCancelarCheckIn;
    @FXML private Slider sHoraCheckIn;
    @FXML private Slider sMinutoCheckIn;
    @FXML private Reserva reservaCheckIn;

    @FXML
    public void initialize(){
        dpFechaCheckIn.setValue(LocalDate.now());
        tfHoraCheckIn.setText(String.valueOf(sHoraCheckIn.getValue()));
        tfHoraCheckIn.textProperty().bindBidirectional(sHoraCheckIn.valueProperty(), NumberFormat.getCompactNumberInstance());
        tfMinutoCheckIn.setText(String.valueOf(sMinutoCheckIn.getValue()));
        tfMinutoCheckIn.textProperty().bindBidirectional(sMinutoCheckIn.valueProperty(), NumberFormat.getCompactNumberInstance());
    }

    @FXML
    void recibirReservaCheckIn(Reserva reserva) {
        reservaCheckIn = reserva;
        System.out.println(reserva);
    }

    @FXML
    void AceptarCheckIn() {
        String error = "";
        try {
            System.out.println(reservaCheckIn);
            LocalDateTime fechaHoraCheckIn = LocalDateTime.of(dpFechaCheckIn.getValue(), LocalTime.of((int) sHoraCheckIn.getValue(), (int) sMinutoCheckIn.getValue()));
            System.out.println(fechaHoraCheckIn);
            VistaGrafica.getInstancia().getControlador().realizarCheckin(reservaCheckIn,fechaHoraCheckIn);
            System.out.println(reservaCheckIn);
            Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Realizar CheckIn", "CheckIn registrado correctamente.");
            ((Stage)btnAceptarCheckIn.getScene().getWindow()).close();
        } catch (NullPointerException | IllegalArgumentException | ParseException er) {
            error = String.valueOf(er.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Realizar CheckIn", error);
        }
    }

    @FXML
    void CancelarCheckIn() {
        ((Stage) btnCancelarCheckIn.getScene().getWindow()).close();
    }
}
