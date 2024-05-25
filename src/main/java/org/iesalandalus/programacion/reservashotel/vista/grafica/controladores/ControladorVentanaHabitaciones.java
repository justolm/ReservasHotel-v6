package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;

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

    @FXML private TextField tfBusquedaIdentificador;
    @FXML private Button btnLimpiarIdentificador;

    private ObservableList<Habitacion> obsHabitaciones = FXCollections.observableArrayList();

    private List<Habitacion> coleccionHabitaciones = new ArrayList<>();

    private void cargaDatosHabitacion() {
        coleccionHabitaciones.addAll(getInstancia().getControlador().getHabitaciones());
        obsHabitaciones.setAll(coleccionHabitaciones);
    }

    @FXML
    private void inicializarHabitaciones() {
        cargaDatosHabitacion();
        tfBusquedaIdentificador.textProperty().addListener(((observableValue, valorViejo, valorNuevo) -> buscarDirectoHabitacion(valorNuevo)));
    }


    @FXML
    void buscarDirectoHabitacion(String busqueda) {
        List<Habitacion> coleccionHabitacionesFiltrada = new ArrayList<>();
        if (tfBusquedaIdentificador.getText().isBlank() || tfBusquedaIdentificador.getText().isEmpty()) {
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
}
