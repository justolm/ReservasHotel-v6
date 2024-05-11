package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

public class LanzadorVentanaPrincipal extends Application {

    public static void comenzar() {
        launch();
    }

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
        Parent raiz=fxmlLoader.load();
        Scene scene = new Scene(raiz, 900, 600);
        escenarioPrincipal.setTitle("Reservas Hotel IES Al-Andalus");
        escenarioPrincipal.setScene(scene);
        escenarioPrincipal.setOnCloseRequest(e->confirmarSalida(escenarioPrincipal,e));
        escenarioPrincipal.show();
    }

    private void confirmarSalida(Stage escenario, WindowEvent e)
    {
        if (Dialogos.mostrarDialogoConfirmacion("Reservas Hotel", "¿Estas seguro que quieres salirte de la aplicación?")) {
            escenario.close();
        }
        else
            e.consume();

    }
}
