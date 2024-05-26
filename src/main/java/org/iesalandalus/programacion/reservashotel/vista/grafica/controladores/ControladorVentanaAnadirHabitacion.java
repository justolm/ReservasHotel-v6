package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;

public class ControladorVentanaAnadirHabitacion {

    @FXML private ComboBox<Integer> cbNumPlanta;
    @FXML private ComboBox<Integer> cbNumPuerta;
    @FXML private ComboBox<String> cbTipoHabitacion;
    @FXML private ComboBox<Integer> cbNumCamasIndividuales;
    @FXML private ComboBox<Integer> cbNumCamasDobles;
    @FXML private ComboBox<Integer> cbNumBanos;
    @FXML private ComboBox<String> cbJacuzzi;
    @FXML private TextField tfPrecio;

    @FXML private Button btnAnadirAHabitacion;
    @FXML private Button btnCancelarAHabitacion;

    private void cargaOpcionesPlanta(){
        ObservableList<Integer> opciones = cbNumPlanta.getItems();
        for (int i = Habitacion.MIN_NUMERO_PLANTA ; i < Habitacion.MAX_NUMERO_PLANTA + 1 ; i++) {
            opciones.add(i);
        }
        cbNumPlanta.setValue(Habitacion.MIN_NUMERO_PLANTA); // Ponemos un valor por defecto.
    }

    private void cargaOpcionesPuerta(){
        ObservableList<Integer> opciones = cbNumPuerta.getItems();
        for (int i = Habitacion.MIN_NUMERO_PUERTA ; i < Habitacion.MAX_NUMERO_PUERTA + 1 ; i++) {
            opciones.add(i);
        }
        cbNumPuerta.setValue(Habitacion.MIN_NUMERO_PUERTA); // Ponemos un valor por defecto.
    }

    private void cargaOpcionesTipoHabitacion(){
        for (TipoHabitacion tipoHabitacion : TipoHabitacion.values()) {
            cbTipoHabitacion.getItems().add(tipoHabitacion.name());
        }
        cbTipoHabitacion.setValue(TipoHabitacion.SIMPLE.name());
    }

    private void cargaOpcionesNumCamasIndividuales(){
    //    ObservableList<Integer> opciones = cbNumCamasIndividuales.getItems();
    //    for (int i = Doble.)
    }


    @FXML
    private void initialize(){
        cargaOpcionesPlanta();
        cargaOpcionesPuerta();
        cargaOpcionesTipoHabitacion();
        // Deshabilitamos por defecto los campos que dependen del tipo de habitación, puesto que seleccionamos por defecto el tipo simple. Inicializamos el resto de campos aunque no se almacenen.
        cbNumCamasIndividuales.setValue(1);
        cbNumCamasIndividuales.setDisable(true);
        cbNumCamasDobles.setValue(0);
        cbNumCamasDobles.setDisable(true);
        cbNumBanos.setValue(1);
        cbNumBanos.setDisable(true);
        cbJacuzzi.setValue("-");
        cbJacuzzi.setDisable(true);
        cbTipoHabitacion.valueProperty().addListener((observableValue, valorAnteriorTipo, valorNuevoTipo) -> muestraOpciones(valorNuevoTipo));
    }

    private void muestraOpciones(String opcionTipoElegida) {
        if (opcionTipoElegida.equals(TipoHabitacion.SIMPLE.name())) {
            // Al seleccionar el tipo simple, establecemos los mismos datos que por defecto.
            cbNumCamasIndividuales.setValue(1);
            cbNumCamasIndividuales.setDisable(true);
            cbNumCamasDobles.setValue(0);
            cbNumCamasDobles.setDisable(true);
            cbNumBanos.setValue(1);
            cbNumBanos.setDisable(true);
            cbJacuzzi.setValue("-");
            cbJacuzzi.setDisable(true);
        }
        else if (opcionTipoElegida.equals(TipoHabitacion.DOBLE.name())) {
            // Al establecer una habitación doble, habilitamos la opción del número de camas, que acepta un número par de camas individuales o una cama doble.
            cbNumCamasIndividuales.setDisable(false);
            cbNumCamasIndividuales.getItems().clear();
            cbNumCamasDobles.getItems().clear();
            ObservableList<Integer> opcionesCamIndD = cbNumCamasIndividuales.getItems();
            for (int i = Doble.MIN_NUM_CAMAS_INDIVIDUALES ; i < Doble.MAX_NUM_CAMAS_INDIVIDUALES +1 ; i = i+2) {
                opcionesCamIndD.add(i);
            }
            cbNumCamasDobles.setDisable(false);
            ObservableList<Integer> opcionesCamDobD = cbNumCamasDobles.getItems();
            for (int i = Doble.MIN_NUM_CAMAS_DOBLES ; i < Doble.MAX_NUM_CAMAS_DOBLES + 1 ; i++) {
                opcionesCamDobD.add(i);
            }
            // Al seleccionar las camas individuales, modifica las dobles (y viceversa posteriormente)
            cbNumCamasIndividuales.setOnAction(event -> {
                if (cbNumCamasIndividuales.getValue().equals(Doble.MAX_NUM_CAMAS_INDIVIDUALES)){
                    cbNumCamasDobles.setValue(Doble.MIN_NUM_CAMAS_DOBLES);
                }
                else {
                    cbNumCamasDobles.setValue(Doble.MAX_NUM_CAMAS_DOBLES);
                }
            });
            cbNumCamasDobles.setOnAction(event -> {
                if (cbNumCamasDobles.getValue().equals(Doble.MAX_NUM_CAMAS_DOBLES)){
                    cbNumCamasIndividuales.setValue(Doble.MIN_NUM_CAMAS_INDIVIDUALES);
                }
                else {
                    cbNumCamasIndividuales.setValue(Doble.MAX_NUM_CAMAS_INDIVIDUALES);
                }
            });
            cbNumCamasIndividuales.setValue(Doble.MAX_NUM_CAMAS_INDIVIDUALES);
            cbNumBanos.setValue(1);
            cbNumBanos.setDisable(true);
            cbJacuzzi.setValue("-");
            cbJacuzzi.setDisable(true);
        }
        else if (opcionTipoElegida.equals(TipoHabitacion.TRIPLE.name())) {
            // Al establecer una habitación doble, habilitamos la opción del número de camas, que acepta un número par de camas individuales o una cama doble.
            cbNumCamasIndividuales.setDisable(false);
            cbNumCamasIndividuales.getItems().clear();
            cbNumCamasDobles.getItems().clear();
            ObservableList<Integer> opcionesCamIndT = cbNumCamasIndividuales.getItems();
            for (int i = Triple.MIN_NUM_CAMAS_INDIVIDUALES; i < Triple.MAX_NUM_CAMAS_INDIVIDUALES +1 ; i = i+2) {
                opcionesCamIndT.add(i);
            }
            cbNumCamasDobles.setDisable(false);
            ObservableList<Integer> opcionesCamDobT = cbNumCamasDobles.getItems();
            for (int i = Triple.MIN_NUM_CAMAS_DOBLES ; i < Triple.MAX_NUM_CAMAS_DOBLES + 1 ; i++) {
                opcionesCamDobT.add(i);
            }
            // Al seleccionar las camas individuales, modifica las dobles (y viceversa posteriormente)
            cbNumCamasIndividuales.setOnAction(event -> {
                if (cbNumCamasIndividuales.getValue().equals(Triple.MAX_NUM_CAMAS_INDIVIDUALES)){
                    cbNumCamasDobles.setValue(Triple.MIN_NUM_CAMAS_DOBLES);
                }
                else {
                    cbNumCamasDobles.setValue(Triple.MAX_NUM_CAMAS_DOBLES);
                }
            });
            cbNumCamasDobles.setOnAction(event -> {
                if (cbNumCamasDobles.getValue().equals(Triple.MAX_NUM_CAMAS_DOBLES)){
                    cbNumCamasIndividuales.setValue(Triple.MIN_NUM_CAMAS_INDIVIDUALES);
                }
                else {
                    cbNumCamasIndividuales.setValue(Triple.MAX_NUM_CAMAS_INDIVIDUALES);
                }
            });
            cbNumCamasIndividuales.setValue(Triple.MAX_NUM_CAMAS_INDIVIDUALES);
            cbNumBanos.getItems().clear();
            cbNumBanos.setDisable(false);
            ObservableList<Integer> opcionesNumBanos = cbNumBanos.getItems();
            for (int i = Triple.MIN_NUM_BANOS ; i < Triple.MAX_NUM_BANOS + 1 ; i++) {
                opcionesNumBanos.add(i);
            }
            cbNumBanos.setValue(Triple.MAX_NUM_BANOS);
            cbJacuzzi.setValue("-");
            cbJacuzzi.setDisable(true);
        }
        else if (opcionTipoElegida.equals(TipoHabitacion.SUITE.name())) {
            cbNumCamasIndividuales.setValue(2);
            cbNumCamasIndividuales.setDisable(true);
            cbNumCamasDobles.setValue(1);
            cbNumCamasDobles.setDisable(true);
            cbNumBanos.setDisable(false);
            cbNumBanos.getItems().clear();
            ObservableList<Integer> opcionesNumBanos = cbNumBanos.getItems();
            for (int i = Suite.MIN_NUM_BANOS; i < Suite.MAX_NUM_BANOS + 1 ; i++) {
                opcionesNumBanos.add(i);
            }
            cbNumBanos.setValue(Suite.MAX_NUM_BANOS);
            cbJacuzzi.setDisable(false);
            cbJacuzzi.getItems().clear();
            cbJacuzzi.getItems().addAll("Sí","No");
            cbJacuzzi.setValue("Sí");
        }
    }


    @FXML
    void anadirAHabitacion(ActionEvent event) {
        String error = "";
        try {
            Habitacion habitacion;
            double precio = Double.parseDouble(tfPrecio.getText());
            boolean tieneJacuzzi = false;
            switch (cbTipoHabitacion.getValue()) {
                case "SIMPLE" -> habitacion = new Simple(cbNumPlanta.getValue(),cbNumPuerta.getValue(),precio);
                case "DOBLE" -> habitacion = new Doble(cbNumPlanta.getValue(),cbNumPuerta.getValue(),precio,cbNumCamasIndividuales.getValue(),cbNumCamasDobles.getValue());
                case "TRIPLE" -> habitacion = new Triple(cbNumPlanta.getValue(),cbNumPuerta.getValue(),precio,cbNumBanos.getValue(),cbNumCamasIndividuales.getValue(),cbNumCamasDobles.getValue());
                case "SUITE" -> {
                    if (cbJacuzzi.equals("Sí")) {
                        tieneJacuzzi=true;
                    }
                    habitacion = new Suite(cbNumPlanta.getValue(),cbNumPuerta.getValue(),precio,cbNumBanos.getValue(),tieneJacuzzi);
                }
                default -> habitacion = null;
            }
            VistaGrafica.getInstancia().getControlador().insertar(habitacion);
            Dialogos.mostrarDialogoConfirmacion("Hotel Al-Andalus - Insertar habitación", "Habitación insertada correctamente.");
            ((Stage) btnAnadirAHabitacion.getScene().getWindow()).close();
        } catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException er) {
            error = String.valueOf(er.getMessage());
        }
        if (!error.isEmpty()) {
            Dialogos.mostrarDialogoError("Hotel Al-Andalus - Insertar habitación", error);
        }
    }

    @FXML
    void cancelarAHabitacion(ActionEvent event) {
        ((Stage) btnCancelarAHabitacion.getScene().getWindow()).close();
    }

}
