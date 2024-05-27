package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import javax.naming.OperationNotSupportedException;
import java.text.ParseException;
import java.util.List;
import java.util.ArrayList;

public class Huespedes implements IHuespedes {
    private static final String COLECCION = "huespedes";
    private MongoCollection<Document> coleccionHuespedes;

    public Huespedes () {
        coleccionHuespedes = MongoDB.getBD().getCollection(COLECCION);
    }

    public List<Huesped> get() throws ParseException {
        List<Huesped> listadoHuespedes = new ArrayList<>();
        FindIterable<Document> listaHuespedesOrdenada;
        listaHuespedesOrdenada = coleccionHuespedes.find().sort(Sorts.ascending(MongoDB.DNI));
        for (Document document : listaHuespedesOrdenada) {
            Huesped huesped = MongoDB.getHuesped(document);
            listadoHuespedes.add(huesped);
        }
        return listadoHuespedes;
    }

    public int getTamano() throws NullPointerException {
        return (int) coleccionHuespedes.countDocuments();
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException, NullPointerException, ParseException {
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        else if (buscar(huesped) != null){
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
        coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
    }

    public Huesped buscar(Huesped huesped) throws NullPointerException, IllegalArgumentException, ParseException {
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        Document docHuesped = coleccionHuespedes.find(Filters.eq(MongoDB.DNI,huesped.getDni())).first();
        if (docHuesped != null){
            return MongoDB.getHuesped(docHuesped);
        }
        return null;
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException, NullPointerException, ParseException, IllegalArgumentException {
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        Document docHuesped = null;
        if (coleccionHuespedes.countDocuments() > 0) { // Comprueba que existan huéspedes antes de intentar filtrarlas.
            docHuesped = coleccionHuespedes.find(Filters.eq(MongoDB.DNI,huesped.getDni())).first();
        }
        if (!VistaGrafica.getInstancia().getControlador().getReservas(huesped).isEmpty()) {
            throw new IllegalArgumentException("ERROR: No se puede eliminar un huésped que tenga reservas.");
        }
        if (docHuesped != null){
            coleccionHuespedes.deleteOne(docHuesped);
        }
        else {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
    }

    @Override
    public void comenzar() {
        MongoDatabase database = MongoDB.getBD();
        coleccionHuespedes = database.getCollection(COLECCION);
    }

    @Override
    public void terminar() {
        MongoDB.cerrarConexion();
    }
}
