module org.iesalandalus.programacion.reservashotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires java.desktop;
    //requires entrada;

    opens org.iesalandalus.programacion.reservashotel to javafx.fxml;
    exports org.iesalandalus.programacion.reservashotel;
    exports org.iesalandalus.programacion.reservashotel.vista.grafica;
    opens org.iesalandalus.programacion.reservashotel.vista.grafica.controladores to javafx.fxml;
    exports org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;
    exports org.iesalandalus.programacion.reservashotel.modelo.dominio;
    opens org.iesalandalus.programacion.reservashotel.modelo.dominio to javafx.fxml;
}