package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica.getInstancia;

public class ControladorVentanaHuespedes {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML private TableView<Huesped> tvHuespedes;

    @FXML private TableColumn<Huesped, String> tcNombre;
    @FXML private TableColumn<Huesped, String> tcDNI;
    @FXML private TableColumn<Huesped, String> tcCorreo;
    @FXML private TableColumn<Huesped, String> tcTelefono;
    @FXML private TableColumn<Huesped, String> tcFechaNacimiento;

    @FXML private TextField tfBusquedaNombre;
    @FXML private TextField tfBusquedaDNI;
    @FXML private Button btnBuscarHuesped;
    @FXML private Button btnLimpiarHuesped;

    // Ventana para añadir un huésped:
    @FXML private Button btnAnadirAHuesped;
    @FXML private Button btnCancelarAHuesped;
    @FXML private TextField tfNombreAHuesped;
    @FXML private TextField tfDNIAHuesped;
    @FXML private TextField tfCorreoAHuesped;
    @FXML private TextField tfTelefonoAHuesped;
    @FXML private DatePicker dpFechaNacimientoAHuesped;

    private ObservableList<Huesped> obsHuespedes = FXCollections.observableArrayList();

    private List<Huesped> coleccionHuespedes = new ArrayList<>();

    private void cargaDatosHuesped() throws ParseException {
        coleccionHuespedes.addAll(getInstancia().getControlador().getHuespedes());
        obsHuespedes.setAll(coleccionHuespedes);
    }

    @FXML
    private void inicializar() {
        try {
            cargaDatosHuesped();
            tcNombre.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getNombre()));
            tcDNI.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getDni()));
            tcCorreo.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getCorreo()));
            tcTelefono.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getTelefono()));
            tcFechaNacimiento.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(FORMATO_FECHA).toString()));
            tvHuespedes.getSelectionModel().selectedItemProperty().addListener((observableValue, valorAnterior, valorNuevo) -> muestraHuespedSeleccionado(valorNuevo));
            tvHuespedes.setItems(obsHuespedes);
        } catch (ParseException e) {
            System.out.println(e.getMessage());;
        }

    }

    private void muestraHuespedSeleccionado(Huesped valorNuevo) {
        System.out.println(valorNuevo);
    }

    @FXML
    void buscar(ActionEvent event) {
        List<Huesped> coleccionHuespedesFiltrada = new ArrayList<>();
        if (tfBusquedaNombre.getText().isBlank() || tfBusquedaNombre.getText().isEmpty() || tfBusquedaDNI.getText().isBlank() || tfBusquedaDNI.getText().isEmpty()) {
            obsHuespedes.setAll(coleccionHuespedes);
        }
        else if (tfBusquedaDNI.getText().isBlank() || tfBusquedaDNI.getText().isEmpty()){
            String cadenaFiltradoNombre = tfBusquedaNombre.getText().toLowerCase();
            for (Huesped huesped : coleccionHuespedes) {
                if (huesped.getNombre().toLowerCase().contains(cadenaFiltradoNombre)) {
                    coleccionHuespedesFiltrada.add(huesped);
                }
            }
            obsHuespedes.setAll(coleccionHuespedesFiltrada);
        }
        else if (tfBusquedaNombre.getText().isBlank() || tfBusquedaNombre.getText().isEmpty()){
            String cadenaFiltradoDNI = tfBusquedaDNI.getText().toLowerCase();
            for (Huesped huesped : coleccionHuespedes) {
                if (huesped.getDni().toLowerCase().contains(cadenaFiltradoDNI)) {
                    coleccionHuespedesFiltrada.add(huesped);
                }
            }
            obsHuespedes.setAll(coleccionHuespedesFiltrada);
        }
        else {
            String cadenaFiltradoNombre = tfBusquedaNombre.getText().toLowerCase();
            String cadenaFiltradoDNI = tfBusquedaDNI.getText().toLowerCase();
            for (Huesped huesped : coleccionHuespedes) {
                if (huesped.getNombre().toLowerCase().contains(cadenaFiltradoNombre)) {
                    if (huesped.getDni().toLowerCase().contains(cadenaFiltradoDNI)) {
                        coleccionHuespedesFiltrada.add(huesped);
                    }
                }
            }
            obsHuespedes.setAll(coleccionHuespedesFiltrada);
        }
    }

    @FXML
    void limpiar (ActionEvent event) {
        tfBusquedaDNI.clear();
        tfBusquedaNombre.clear();
    }

    @FXML
    void eliminarHuesped(ActionEvent event) {
        Huesped huesped = tvHuespedes.getSelectionModel().getSelectedItem();
        if (huesped != null && Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Borrar huésped","¿Está seguro de eliminar al huésped seleccionado?")) {
            coleccionHuespedes.remove(huesped);
            obsHuespedes.setAll(coleccionHuespedes);
            Dialogos.mostrarDialogoInformacion("Hotel Al-Andalus - Borrar huésped", "Huésped eliminado correctamente");
        }
        if (huesped == null) {
            Dialogos.mostrarDialogoAdvertencia("Hotel Al-Andalus - Borrar huésped", "Debe de seleccionar el huésped que desee eliminar.");
        }
    }

    @FXML
    void anadirHuesped(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/VentanaAnadirHuesped.fxml"));
        ControladorVentanaHuespedes c = fxmlLoader.getController();
        try {
            Parent raiz = fxmlLoader.load();
            Scene escena = new Scene(raiz,600,400);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setTitle("Hotel Al-Andalus - Añadir Huésped");
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    // Parte correspondiente a la ventana para añadir huéspedes:
    @FXML
    void anadirAHuesped(ActionEvent event) {
        Huesped huesped = new Huesped(tfNombreAHuesped.getText(),tfDNIAHuesped.getText(),tfCorreoAHuesped.getText(),tfTelefonoAHuesped.getText(),dpFechaNacimientoAHuesped.getValue());
        if (!coleccionHuespedes.contains(huesped)) {
            coleccionHuespedes.add(huesped);
            obsHuespedes.setAll(coleccionHuespedes);
            Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Insertar huésped", "Huésped insertado correctamente.");
        }
        else {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Insertar huésped", "El huésped introducido ya se encuentra en el listado.");
        }
        ((Stage) btnAnadirAHuesped.getScene().getWindow()).close();
    }

    @FXML
    void cancelarAHuesped(ActionEvent event) {
        ((Stage) btnCancelarAHuesped.getScene().getWindow()).close();
    }

}
