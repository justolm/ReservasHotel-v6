package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.util.List;

public interface IHuespedes {
    List<Huesped> get() throws ParseException;
    int getTamano();
    void insertar(Huesped huesped) throws OperationNotSupportedException, ParseException;
    Huesped buscar(Huesped huesped) throws ParseException;
    void borrar(Huesped huesped) throws OperationNotSupportedException, ParseException;
    void comenzar();
    void terminar();
}
