package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Huespedes implements IHuespedes {
    private List<Huesped> coleccionHuespedes;

    public Huespedes () {
        coleccionHuespedes = new ArrayList<>();
    }

    public List<Huesped> get() {
        coleccionHuespedes=new ArrayList<>(copiaProfundaHuespedes());
        coleccionHuespedes.sort(Comparator.comparing(Huesped::getNombre));
        return coleccionHuespedes;
    }

    private List<Huesped> copiaProfundaHuespedes() throws NullPointerException {
        if (coleccionHuespedes == null) {
            throw new NullPointerException("ERROR: No se puede copiar una colección vacía.");
        }
        List<Huesped> copiaProfundaHuespedes = new ArrayList<>();
        for (int i = 0; i < getTamano(); i++) {
            copiaProfundaHuespedes.add(coleccionHuespedes.get(i));
        }
        return copiaProfundaHuespedes;
    }

    public int getTamano() throws NullPointerException {
        return coleccionHuespedes.size();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException, NullPointerException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        }
        if (coleccionHuespedes.contains(huesped)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.add(new Huesped(huesped));
    }

    public Huesped buscar(Huesped huesped) throws NullPointerException, IllegalArgumentException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        }
        for(int i = 0; i < getTamano(); i++) {
            if (coleccionHuespedes.get(i).equals(huesped)) {
                return coleccionHuespedes.get(i);
                // Para el test requiere que se devuelva el huesped introducido "return huesped;"
            }
        }
        return null;
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException, NullPointerException {
        if (huesped == null) {
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
        if (coleccionHuespedes.contains(huesped)) {
            coleccionHuespedes.remove(huesped);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
    }

    @Override
    public void comenzar() {}

    @Override
    public void terminar() {}
}
