package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
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

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.text.ParseException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ControladorVentanaHuespedes {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML private TableView<Huesped> tvHuespedes;

    @FXML private TableColumn<Huesped, String> tcNombre;
    @FXML private TableColumn<Huesped, String> tcDNI;
    @FXML private TableColumn<Huesped, String> tcCorreo;
    @FXML private TableColumn<Huesped, String> tcTelefono;
    @FXML private TableColumn<Huesped, String> tcFechaNacimiento;

    @FXML private MenuItem miInsertarHuesped;
    @FXML private MenuItem miEliminarHuesped;

    @FXML private TextField tfBusquedaNombre;
    @FXML private TextField tfBusquedaDNI;
    @FXML private Button btnBuscarHuesped;
    @FXML private Button btnLimpiarHuesped;
    @FXML private Button btnVerReservasHuesped;
    @FXML private Button btnAnadirHuesped;
    @FXML private Button btnBorrarHuesped;

    @FXML ControladorVentanaPrincipal controladorVentanaPrincipalenHuespedes; // Para poder lanzar la búsqueda de reservas de un huésped de vuelta.

    private ObservableList<Huesped> obsHuespedes = FXCollections.observableArrayList();

    List<Huesped> coleccionHuespedes = new ArrayList<>();

    private void cargaDatosHuesped() throws ParseException {
        coleccionHuespedes.addAll(VistaGrafica.getInstancia().getControlador().getHuespedes());
        obsHuespedes.setAll(coleccionHuespedes);
    }

    @FXML
    private void initialize() {
        String error = "";
        try {
            cargaDatosHuesped();
            tvHuespedes.setPlaceholder(new Label("No hay huéspedes para mostrar."));
            if(tfBusquedaNombre.getText() != null) {
                tfBusquedaNombre.textProperty().addListener((observable, valorViejo, valorNuevo) -> buscarDirecto(valorNuevo));
            }
            tcNombre.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getNombre()));
            tcDNI.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getDni()));
            tcCorreo.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getCorreo()));
            tcTelefono.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getTelefono()));
            tcFechaNacimiento.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(FORMATO_FECHA).toString()));
            tvHuespedes.getSelectionModel().selectedItemProperty().addListener((observableValue, valorAnterior, valorNuevo) -> muestraHuespedSeleccionado(valorNuevo));
            tvHuespedes.setItems(obsHuespedes);
        } catch (ParseException | IllegalArgumentException | NullPointerException e) {
            error=String.valueOf(e.getMessage());
            System.out.println(e.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Huéspedes", error);
        }
    }

    private void muestraHuespedSeleccionado(Huesped valorNuevo) {
        System.out.println(valorNuevo);
    }

    @FXML
    void buscar(ActionEvent event) {
        List<Huesped> coleccionHuespedesFiltrada = new ArrayList<>();
        if ((tfBusquedaNombre.getText().isBlank() || tfBusquedaNombre.getText().isEmpty()) && (tfBusquedaDNI.getText().isBlank() || tfBusquedaDNI.getText().isEmpty())) {
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
    void buscarDirecto(String busqueda) {
        List<Huesped> coleccionHuespedesFiltroDirecto = new ArrayList<>();
        if (tfBusquedaNombre.getText().isBlank() && tfBusquedaNombre.getText().isEmpty()){
            obsHuespedes.setAll(coleccionHuespedes);
        }
        else {
            busqueda = tfBusquedaNombre.getText().toLowerCase();
            for (Huesped huesped : coleccionHuespedes) {
                if (huesped.getNombre().toLowerCase().contains(busqueda)) {
                    coleccionHuespedesFiltroDirecto.add(huesped);
                }
            }
            obsHuespedes.clear();
            obsHuespedes.setAll(coleccionHuespedesFiltroDirecto);
        }
    }

    @FXML
    void limpiar (ActionEvent event) {
        tfBusquedaDNI.clear();
        tfBusquedaNombre.clear();
        obsHuespedes.setAll(coleccionHuespedes);
    }

    @FXML
    void eliminarHuesped(ActionEvent event) {
        String error = "";
        try {
            Huesped huesped = tvHuespedes.getSelectionModel().getSelectedItem();
            if (huesped != null && Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Borrar huésped","¿Está seguro de eliminar al huésped seleccionado?")) {
                VistaGrafica.getInstancia().getControlador().borrar(huesped);
                coleccionHuespedes.remove(huesped);
                obsHuespedes.setAll(coleccionHuespedes);
                Dialogos.mostrarDialogoInformacion("Hotel Al-Andalus - Borrar huésped", "Huésped eliminado correctamente");
            }
            if (huesped == null) {
                Dialogos.mostrarDialogoAdvertencia("Hotel Al-Andalus - Borrar huésped", "Debe seleccionar el huésped que desee eliminar.");
            }
        } catch (OperationNotSupportedException | ParseException | IllegalArgumentException | NullPointerException e) {
            error=String.valueOf(e.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Borrar huésped", error);
        }
    }

    @FXML
    void anadirHuesped(ActionEvent event) {
        FXMLLoader fxmlLoader2 = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaAnadirHuesped.fxml"));
        ControladorVentanaHuespedes c = fxmlLoader2.getController();
        String error = "";
        try {
            Parent raiz = fxmlLoader2.load();
            Scene escenaAH = new Scene(raiz,600,400);
            Stage escenarioAnadirHuesped = new Stage();
            escenarioAnadirHuesped.setResizable(false);
            escenarioAnadirHuesped.setScene(escenaAH);
            escenarioAnadirHuesped.initModality(Modality.APPLICATION_MODAL);
            escenarioAnadirHuesped.setTitle("Hotel Al-Andalus - Añadir Huésped");
            escenarioAnadirHuesped.showAndWait();
            obsHuespedes.clear();
            coleccionHuespedes.clear();
            cargaDatosHuesped();
        } catch (IOException | ParseException e) {
            error=String.valueOf(e.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Insertar huésped", error);
        }
    }

    public void verReservas(ActionEvent event) {
        Huesped huesped = tvHuespedes.getSelectionModel().getSelectedItem();
        if (huesped != null && Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Reservas huésped","¿Quiere buscar las reservas del huésped seleccionado?")){
            controladorVentanaPrincipalenHuespedes.filtrarPorHuesped(huesped.getDni());
            ((Stage) btnBuscarHuesped.getScene().getWindow()).close();
        }
    }

    // Recibo el controlador de la ventana principal para poder enviar el filtrado por huésped.
    @FXML
    public void recibeControladorHuespedes(ControladorVentanaPrincipal controladorVentanaPrincipal) {
        controladorVentanaPrincipalenHuespedes = controladorVentanaPrincipal;
    }
}
