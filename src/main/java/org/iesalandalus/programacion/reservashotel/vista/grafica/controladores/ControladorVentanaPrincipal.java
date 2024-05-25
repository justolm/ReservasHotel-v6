package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica.getInstancia;

public class ControladorVentanaPrincipal {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML private TableView<Reserva> tvReservas;

    @FXML private TableColumn<Reserva, String> tcHuesped;
    @FXML private TableColumn<Reserva, String> tcHabitacion;
    @FXML private TableColumn<Reserva, String> tcImporte;
    @FXML private TableColumn<Reserva, String> tcFechaInicio;
    @FXML private TableColumn<Reserva, String> tcFechaFin;
    @FXML private TableColumn<Reserva, String> tcCheckIn;
    @FXML private TableColumn<Reserva, String> tcCheckOut;

    @FXML private TextField tfNombre;
    @FXML private TextField tfDNI;
    @FXML private TextField tfCorreo;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfFechaNacimiento;

    @FXML private TextField tfIdentificador;
    @FXML private TextField tfTipoHabitacion;
    @FXML private TextField tfCamasIndividuales;
    @FXML private TextField tfCamasDobles;
    @FXML private TextField tfBanos;
    @FXML private TextField tfJacuzzi;

    @FXML private Button btnFiltrarPorHuesped;
    @FXML private Button btnFiltrarPorHabitacion;

    // Para pasar información entre ventanas
    @FXML ControladorVentanaPrincipal controladorVentanaPrincipal;

    private final ObservableList<Reserva> obsReservas = FXCollections.observableArrayList();

    private final List<Reserva> coleccionReservas = new ArrayList<>();

    private void cargaDatosReservas() throws ParseException {
        coleccionReservas.addAll(VistaGrafica.getInstancia().getControlador().getReservas());
        obsReservas.setAll(coleccionReservas);
    }


    @FXML
    private void initialize() {
        controladorVentanaPrincipal=this;
        try {
            cargaDatosReservas();
            tcHuesped.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre().concat(" - ").concat(reserva.getValue().getHuesped().getDni())));
            tcHabitacion.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador().concat(" - ").concat(reserva.getValue().getHabitacion().getClass().getSimpleName())));
            tcImporte.setCellValueFactory(reserva -> new SimpleStringProperty(Double.toString(reserva.getValue().getPrecio())));
            tcFechaInicio.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(FORMATO_FECHA).toString()));
            tcFechaFin.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(FORMATO_FECHA).toString()));
            tcCheckIn.setCellValueFactory(reserva -> {
                if(reserva.getValue().getCheckIn() != null) {
                    return  new SimpleStringProperty(reserva.getValue().getCheckIn().format(FORMATO_FECHA_HORA).toString());
                }
                else return new SimpleStringProperty(" - ");
            });
            tcCheckOut.setCellValueFactory(reserva -> {
                if(reserva.getValue().getCheckOut() != null) {
                    return  new SimpleStringProperty(reserva.getValue().getCheckOut().format(FORMATO_FECHA_HORA).toString());
                }
                else return new SimpleStringProperty(" - ");
            });
            tvReservas.getSelectionModel().selectedItemProperty().addListener(((observableValue, valorAnteriorReserva, valorNuevoReserva) -> muestraReservaSeleccionada(valorNuevoReserva)));
            tvReservas.setItems(obsReservas);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    // Para mostrar los datos al seleccionar una reserva
    private void muestraReservaSeleccionada(Reserva valorNuevoReserva) {
        System.out.println(valorNuevoReserva); // Sirve para mostrar en la consola la reserva seleccionada.

        // Mostramos los datos del huésped correspondiente a la reserva:
        tfNombre.setText(valorNuevoReserva.getHuesped().getNombre());
        tfDNI.setText(valorNuevoReserva.getHuesped().getDni());
        tfCorreo.setText(valorNuevoReserva.getHuesped().getCorreo());
        tfTelefono.setText(valorNuevoReserva.getHuesped().getTelefono());
        tfFechaNacimiento.setText(valorNuevoReserva.getHuesped().getFechaNacimiento().format(FORMATO_FECHA));

        // Mostramos los datos de la habitación correspondiente a la reserva:
        tfIdentificador.setText(valorNuevoReserva.getHabitacion().getIdentificador());
        switch (valorNuevoReserva.getHabitacion().getClass().getSimpleName()){
            case "Simple":
                tfTipoHabitacion.setText("Simple");
                tfCamasIndividuales.setText("1");
                tfCamasDobles.setText("-");
                tfBanos.setText("1");
                tfJacuzzi.setText("-");
                break;
            case "Doble":
                tfTipoHabitacion.setText("Doble");
                tfCamasIndividuales.setText(String.valueOf(((Doble)valorNuevoReserva.getHabitacion()).getNumCamasIndividuales()));
                tfCamasDobles.setText(String.valueOf(((Doble)valorNuevoReserva.getHabitacion()).getNumCamasDobles()));
                tfBanos.setText("1");
                tfJacuzzi.setText("-");
                break;
            case "Triple":
                tfTipoHabitacion.setText("Triple");
                tfCamasIndividuales.setText(String.valueOf(((Triple)valorNuevoReserva.getHabitacion()).getNumCamasIndividuales()));
                tfCamasDobles.setText(String.valueOf(((Triple)valorNuevoReserva.getHabitacion()).getNumCamasDobles()));
                tfBanos.setText(String.valueOf(((Triple)valorNuevoReserva.getHabitacion()).getNumBanos()));
                tfJacuzzi.setText("-");
                break;
            case "Suite":
                String tieneJacuzzi = "No";
                tfTipoHabitacion.setText("Suite");
                tfCamasIndividuales.setText("2");
                tfCamasDobles.setText("1");
                tfBanos.setText(String.valueOf(((Suite)valorNuevoReserva.getHabitacion()).getNumBanos()));
                if (((Suite)valorNuevoReserva.getHabitacion()).isTieneJacuzzi()) {
                    tieneJacuzzi = "Sí";
                }
                tfJacuzzi.setText(tieneJacuzzi);
                break;
        }
    }

    @FXML
    void filtrarPorHuesped(ActionEvent event) {
        List<Reserva> colecccionReservasFiltradaHuesped = new ArrayList<>();
        if (tfDNI.getText().isEmpty() || tfDNI.getText().isBlank()){
            obsReservas.setAll(coleccionReservas);
        }
        else {
            String filtroHuesped = tfDNI.getText().toLowerCase();
            for (Reserva reserva : coleccionReservas) {
                if (reserva.getHuesped().getDni().toLowerCase().contains(filtroHuesped)) {
                    colecccionReservasFiltradaHuesped.add(reserva);
                }
            }
            obsReservas.setAll(colecccionReservasFiltradaHuesped);
        }
    }

    @FXML
    void filtrarPorHuesped(String dni) {
        List<Reserva> colecccionReservasFiltradaHuesped = new ArrayList<>();
        if (dni.isEmpty() || dni.isBlank()){
            obsReservas.setAll(coleccionReservas);
        }
        else {
            String filtroHuesped = dni.toLowerCase();
            for (Reserva reserva : coleccionReservas) {
                if (reserva.getHuesped().getDni().toLowerCase().contains(filtroHuesped)) {
                    colecccionReservasFiltradaHuesped.add(reserva);
                }
            }
            obsReservas.setAll(colecccionReservasFiltradaHuesped);
        }
    }

    @FXML
    void filtrarPorHabitacion(ActionEvent event) {
        List<Reserva> colecccionReservasFiltradaHabitacion = new ArrayList<>();
        if (tfIdentificador.getText().isEmpty() || tfIdentificador.getText().isBlank()){
            obsReservas.setAll(coleccionReservas);
        }
        else {
            String filtroHabitacion = tfIdentificador.getText();
            for (Reserva reserva : coleccionReservas) {
                if (reserva.getHabitacion().getIdentificador().contains(filtroHabitacion)) {
                    colecccionReservasFiltradaHabitacion.add(reserva);
                }
            }
            obsReservas.setAll(colecccionReservasFiltradaHabitacion);
        }
    }

    @FXML
    void LimpiarFiltro(ActionEvent event) throws ParseException {
        obsReservas.setAll(coleccionReservas);
    }

    @FXML
    void anadirHuespedMenu(ActionEvent event) {
        new ControladorVentanaHuespedes().anadirHuesped(new ActionEvent());
    }

    @FXML
    void acercaDe(ActionEvent event) {
        Dialogos.mostrarDialogoInformacion("Hotel Al-Andalus - Acerca de...", "Esta tarea ha sido realizada por Justo del Saliente López Martínez.");
    }

    @FXML
    void salir(ActionEvent event) {
        if (Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus","¿Confirma que desea cerrar la aplicación?")) {
            getInstancia().terminar();
        }
        else {
            event.consume();
        }
    }

    @FXML
    void abrirVentanaHuespedes(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaHuespedes.fxml"));
        ControladorVentanaHuespedes c = fxmlLoader.getController();
        try {
            Parent raiz = fxmlLoader.load();
            Scene escena = new Scene(raiz,900,600);
            Stage escenarioHuespedes = new Stage();
            escenarioHuespedes.setResizable(false);
            escenarioHuespedes.setScene(escena);
            escenarioHuespedes.initModality(Modality.APPLICATION_MODAL);
            escenarioHuespedes.setTitle("Hotel Al-Andalus - Huéspedes");
            ControladorVentanaHuespedes controladorVentanaHuespedesInstancia=(ControladorVentanaHuespedes)fxmlLoader.getController();
            controladorVentanaHuespedesInstancia.recibeControlador(controladorVentanaPrincipal);
            escenarioHuespedes.showAndWait();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void cargaHuespedBusqueda(String dni) {
        filtrarPorHuesped(dni);
        System.out.println(dni);
    }

    @FXML
    void abrirVentanaHabitaciones(ActionEvent event){
    }
}
