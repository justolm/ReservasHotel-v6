package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    //Array para la validación del DNI
    private static final List<Character> ER_DNI = Arrays.asList('T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E');
    private static final String ER_TELEFONO = "[0-9]{9}";
    private static final String ER_CORREO = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";

    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento) throws NullPointerException, IllegalArgumentException {
        if (nombre==null){
            throw new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");
        }
        else if (dni==null){
            throw new NullPointerException("ERROR: El dni de un huésped no puede ser nulo.");
        }
        else if (correo==null){
            throw new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");
        }
        else if (telefono==null){
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        }
        else if (fechaNacimiento==null){
            throw new NullPointerException("ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        }
        else {
            setNombre(nombre);
            setDni(dni.toUpperCase());
            setCorreo(correo);
            setTelefono(telefono);
            setFechaNacimiento(fechaNacimiento);
        }
    }

    // Constructor copia
    public Huesped(Huesped huesped) throws NullPointerException, IllegalArgumentException{
        if(huesped==null)
            throw new NullPointerException("ERROR: No es posible copiar un huésped nulo.");
        setNombre(huesped.getNombre());
        setDni(huesped.getDni());
        setCorreo(huesped.getCorreo());
        setTelefono(huesped.getTelefono());
        setFechaNacimiento(huesped.getFechaNacimiento());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws NullPointerException,IllegalArgumentException {
       if(nombre==null)
           throw new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");

       if (nombre.isBlank()||nombre.isEmpty())
           throw new IllegalArgumentException("ERROR: El nombre de un huésped no puede estar vacío.");

       else{
           this.nombre = formateaNombre(nombre);
       }
    }

    private String formateaNombre(String nombre){

        //Eliminamos los espacios dobles
        String espacioMultiple = "\\s+"; // patrón de 2 o más espacios en blanco
        String espacioUnico = " ";
        nombre = nombre.toLowerCase();
        nombre = nombre.replaceAll(espacioMultiple, espacioUnico);

        // Corregimos las mayúsculas y minúsculas
        String nombreFormateado = "";
        boolean inicioNombre = true;

        String[] palabras = nombre.split(" ");

        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                if (inicioNombre) {
                    nombreFormateado = Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1);
                    inicioNombre = false;
                } else {
                    nombreFormateado = nombreFormateado + ' ' + Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1);
                }
            }
        }
        nombre=nombreFormateado;
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) throws IllegalArgumentException,NullPointerException
    {
        if(telefono==null)
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");

        if (telefono.isBlank()||telefono.isEmpty())
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");

        else {
            if (telefono.matches(ER_TELEFONO)) {
                this.telefono = telefono;
            } else {
                throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
            }
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) throws NullPointerException, IllegalArgumentException {
        if(correo==null)
            throw new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");

        if (correo.isBlank()||correo.isEmpty())
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");

        else {
            Pattern pattern = Pattern.compile(ER_CORREO);
            Matcher matcher = pattern.matcher(correo);
            if (matcher.matches()) {
                this.correo = correo;
            } else {
                throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
            }
        }
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) throws IllegalArgumentException {
        if (comprobarLetraDni(dni.toUpperCase())){
            this.dni = dni;
        }
        else {
            throw new IllegalArgumentException("ERROR: La letra del dni del huésped no es correcta.");
        }
    }

    private static boolean comprobarLetraDni(String dni) throws IllegalArgumentException {
        if (dni == null || dni.length() != 9) {
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        }

        // Partimos el DNI en dos, separando números y letra
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letra = dni.charAt(8);

        if (numero < 0 || numero > 99999999) {
            return false;
        }

        int resto = numero % 23;
        return letra == ER_DNI.get(resto);
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    private void setFechaNacimiento(LocalDate fechaNacimiento) throws NullPointerException, IllegalArgumentException {
        if (fechaNacimiento == null){
            throw new NullPointerException("ERROR: La fecha de nacimiento no puede estar vacía.");
        }
        if (fechaNacimiento.isBefore(LocalDate.now())){
            this.fechaNacimiento = fechaNacimiento;
        }
        else {
            throw new IllegalArgumentException("ERROR: La fecha de nacimiento no puede ser posterior a la actual.");
        }
    }

    private String getIniciales(){
        if (nombre == null || nombre.isEmpty()) {
            return "";
        }

        String iniciales = "";
        String[] partes = nombre.split(" "); // parte el nombre por "palabras"

        for (String parte : partes) {
            if (!parte.isEmpty()) {
                iniciales = iniciales.concat(String.valueOf(parte.charAt(0))); // añade a la cadena la primera letra de cada parte
            }
        }

        return iniciales.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Huesped huesped)) return false;
        return Objects.equals(dni, huesped.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "nombre=" + nombre + " ("+getIniciales()+")" +
                ", DNI=" + dni  +
                ", correo=" + correo +
                ", teléfono=" + telefono +
                ", fecha nacimiento=" + fechaNacimiento;
    }
}
