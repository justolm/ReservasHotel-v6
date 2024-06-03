package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica.getInstancia;

public class ControladorVentanaHabitaciones {
    @FXML private TableView<Habitacion> tvHabitaciones;

    @FXML private TableColumn<Habitacion, String> tcIdentificador;
    @FXML private TableColumn<Habitacion, String> tcPlantaPuerta;
    @FXML private TableColumn<Habitacion, String> tcTipo;
    @FXML private TableColumn<Habitacion, String> tcCamasIndividuales;
    @FXML private TableColumn<Habitacion, String> tcCamasDobles;
    @FXML private TableColumn<Habitacion, String> tcBanos;
    @FXML private TableColumn<Habitacion, String> tcJacuzzi;
    @FXML private TableColumn<Habitacion, String> tcPrecio;

    @FXML private MenuItem miInsertarHabitacion;
    @FXML private MenuItem miEliminarHabitacion;

    @FXML private TextField tfBusquedaIdentificador;
    @FXML private Button btnLimpiarIdentificador;
    @FXML private Button btnAnadirHabitacion;
    @FXML private Button btnBorrarHabitacion;
    @FXML private Button btnVerReservasHabitacion;

    @FXML ControladorVentanaPrincipal controladorVentanaPrincipalenHabitaciones; // Para poder lanzar la búsqueda de reservas por habitación.

    private ObservableList<Habitacion> obsHabitaciones = FXCollections.observableArrayList();

    private List<Habitacion> coleccionHabitaciones = new ArrayList<>();

    private void cargaDatosHabitacion() {
        coleccionHabitaciones.addAll(getInstancia().getControlador().getHabitaciones());
        obsHabitaciones.setAll(coleccionHabitaciones);
    }

    @FXML
    private void initialize() {
        String error = "";
        try {
            cargaDatosHabitacion();
            tvHabitaciones.setPlaceholder(new Label("No hay habitaciones para mostrar."));
            if (tfBusquedaIdentificador.getText() != null) {
                tfBusquedaIdentificador.textProperty().addListener((observable, valorViejo, valorNuevo) -> buscarDirectoHabitacion(valorNuevo));
            }
            tcIdentificador.setCellValueFactory(habitacion -> new SimpleStringProperty(habitacion.getValue().getIdentificador()));
            tcPlantaPuerta.setCellValueFactory(habitacion -> new SimpleStringProperty(habitacion.getValue().getPlanta() + " - " + habitacion.getValue().getPuerta()));
            tcTipo.setCellValueFactory(habitacion -> new SimpleStringProperty(habitacion.getValue().getClass().getSimpleName()));
            tcCamasIndividuales.setCellValueFactory(habitacion -> switch (habitacion.getValue().getClass().getSimpleName()) {
                case "Simple" -> new SimpleStringProperty("1");
                case "Doble" -> new SimpleIntegerProperty(((Doble) habitacion.getValue()).getNumCamasIndividuales()).asString();
                case "Triple" -> new SimpleIntegerProperty(((Triple) habitacion.getValue()).getNumCamasIndividuales()).asString();
                case "Suite" -> new SimpleStringProperty("2");
                default -> throw new IllegalStateException("Tipo de habitación incorrecto: " + habitacion.getValue().getClass().getSimpleName());
            });
            tcCamasDobles.setCellValueFactory(habitacion -> switch (habitacion.getValue().getClass().getSimpleName()){
                case "Simple" -> new SimpleStringProperty("-");
                case "Doble" -> new SimpleIntegerProperty(((Doble) habitacion.getValue()).getNumCamasDobles()).asString();
                case "Triple" -> new SimpleIntegerProperty(((Triple) habitacion.getValue()).getNumCamasDobles()).asString();
                case "Suite" -> new SimpleStringProperty("1");
                default -> throw new IllegalStateException("Tipo de habitación incorrecto: " + habitacion.getValue().getClass().getSimpleName());
            });
            tcBanos.setCellValueFactory(habitacion -> switch (habitacion.getValue().getClass().getSimpleName()){
                case "Simple", "Doble" -> new SimpleStringProperty("1");
                case "Triple" -> new SimpleIntegerProperty(((Triple) habitacion.getValue()).getNumBanos()).asString();
                case "Suite" -> new SimpleIntegerProperty(((Suite) habitacion.getValue()).getNumBanos()).asString();
                default -> throw new IllegalStateException("Tipo de habitación incorrecto: " + habitacion.getValue().getClass().getSimpleName());
            });
            tcJacuzzi.setCellValueFactory(habitacion -> switch (habitacion.getValue().getClass().getSimpleName()){
                case "Simple", "Doble", "Triple" ->  new SimpleStringProperty("-");
                case "Suite" -> {
                    String tieneJacuzzi;
                    if (((Suite) habitacion.getValue()).isTieneJacuzzi()) {
                        tieneJacuzzi = "Sí";
                    }
                    else {
                        tieneJacuzzi = "No";
                    }
                    yield new SimpleStringProperty(tieneJacuzzi);
                }
                default -> throw new IllegalStateException("Tipo de habitación incorrecto: " + habitacion.getValue().getClass().getSimpleName());
            });
            tcPrecio.setCellValueFactory(habitacion -> new SimpleDoubleProperty(habitacion.getValue().getPrecio()).asString());
            tvHabitaciones.getSelectionModel().selectedItemProperty().addListener((observableValue, valorViejo, valorNuevo) -> muestraHabitacionSeleccionada(valorNuevo));
            tvHabitaciones.setItems(obsHabitaciones);
        } catch (IllegalArgumentException | NullPointerException e) {
            error=String.valueOf(e.getMessage());
            System.out.println(e.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Habitaciones", error);
        }
    }

    private void muestraHabitacionSeleccionada(Habitacion valorNuevo) {
        System.out.println(valorNuevo);
    }

    @FXML
    void eliminarHabitacion() {
        String error = "";
        try {
            Habitacion habitacion = tvHabitaciones.getSelectionModel().getSelectedItem();
            if (habitacion != null && Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Borrar habitación","¿Está seguro de eliminar la habitación seleccionada?")) {
                VistaGrafica.getInstancia().getControlador().borrar(habitacion);
                coleccionHabitaciones.remove(habitacion);
                obsHabitaciones.setAll(coleccionHabitaciones);
                Dialogos.mostrarDialogoInformacion("Hotel Al-Andalus - Borrar habitación", "Habitación eliminada correctamente");
            }
            if (habitacion == null) {
                Dialogos.mostrarDialogoAdvertencia("Hotel Al-Andalus - Borrar habitación", "Debe seleccionar la habitación que desee eliminar.");
            }
        } catch (OperationNotSupportedException | ParseException | IllegalArgumentException | NullPointerException e) {
            error=String.valueOf(e.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Borrar habitación", error);
        }
    }

    @FXML
    void anadirHabitacion(ActionEvent event){
        FXMLLoader fxmlLoader2 = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaAnadirHabitacion.fxml"));
        ControladorVentanaHabitaciones c = fxmlLoader2.getController();
        String error = "";
        try {
            Parent raiz = fxmlLoader2.load();
            Scene escenaAH = new Scene(raiz,600,400);
            Stage escenarioAnadirHabitacion = new Stage();
            escenarioAnadirHabitacion.setResizable(false);
            escenarioAnadirHabitacion.setScene(escenaAH);
            escenarioAnadirHabitacion.initModality(Modality.APPLICATION_MODAL);
            escenarioAnadirHabitacion.setTitle("Hotel Al-Andalus - Añadir Habitación");
            escenarioAnadirHabitacion.showAndWait();
            obsHabitaciones.clear();
            coleccionHabitaciones.clear();
            cargaDatosHabitacion();
        } catch (IOException e) {
            error=String.valueOf(e.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Insertar habitación", error);
        }
    }

    @FXML
    public void verReservas(){
        Habitacion habitacion = tvHabitaciones.getSelectionModel().getSelectedItem();
        if (habitacion != null && Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Reservas habitación","¿Quiere buscar las reservas de la habitación seleccionada?")) {
            controladorVentanaPrincipalenHabitaciones.filtrarPorHabitacion(habitacion.getIdentificador());
            ((Stage) btnLimpiarIdentificador.getScene().getWindow()).close();
        }
    }

    @FXML
    void buscarDirectoHabitacion(String busqueda) {
        List<Habitacion> coleccionHabitacionesFiltrada = new ArrayList<>();
        if (tfBusquedaIdentificador.getText().isBlank() && tfBusquedaIdentificador.getText().isEmpty()) {
            obsHabitaciones.setAll(coleccionHabitaciones);
        }
        else {
            for (Habitacion habitacion : coleccionHabitaciones) {
                if (habitacion.getIdentificador().contains(busqueda)) {
                    coleccionHabitacionesFiltrada.add(habitacion);
                }
            }
            obsHabitaciones.setAll(coleccionHabitacionesFiltrada);
        }
    }

    @FXML
    void limpiarIdentificador () {
        tfBusquedaIdentificador.clear();
        obsHabitaciones.setAll(coleccionHabitaciones);
    }

    @FXML
    public void recibeControladorHabitaciones(ControladorVentanaPrincipal controladorVentanaPrincipal) {
        controladorVentanaPrincipalenHabitaciones = controladorVentanaPrincipal;
    }
}
