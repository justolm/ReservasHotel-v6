package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;

public class ControladorVentanaAnadirHuesped {
    @FXML private Button btnAnadirAHuesped;
    @FXML private Button btnCancelarAHuesped;
    @FXML private TextField tfNombreAHuesped;
    @FXML private TextField tfDNIAHuesped;
    @FXML private TextField tfCorreoAHuesped;
    @FXML private TextField tfTelefonoAHuesped;
    @FXML private DatePicker dpFechaNacimientoAHuesped;

    @FXML
    void anadirAHuesped() {
        String error = "";
        try {
            Huesped huesped = new Huesped(tfNombreAHuesped.getText(),tfDNIAHuesped.getText(),tfCorreoAHuesped.getText(),tfTelefonoAHuesped.getText(),dpFechaNacimientoAHuesped.getValue());
            VistaGrafica.getInstancia().getControlador().insertar(huesped);
            Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Insertar huésped", "Huésped insertado correctamente.");
            ((Stage) btnAnadirAHuesped.getScene().getWindow()).close();
        } catch (OperationNotSupportedException | ParseException | IllegalArgumentException | NullPointerException er) {
            error = String.valueOf(er.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Insertar huésped", error);
        }
    }

    @FXML
    void cancelarAHuesped() {
        ((Stage) btnCancelarAHuesped.getScene().getWindow()).close();
    }
}
