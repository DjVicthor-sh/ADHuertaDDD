package dominio;

/**
 * Tenemos que crear las variables que usaran la Entidad (ID, Nombre , Apellido )
 */

public class Persona {

    private final Long ID;
    private final String nombre;
    private final String apellido;

    public Persona(Long ID, String nombre, String apellido) {
        this.ID = ID;
        this.nombre = nombre.trim(); // Guardamos sin espacios extra
        this.apellido = apellido.trim();
    }

    // Getters

    public Long getID() {
        return ID;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    //Funciones

    @Override
    public String toString() {
        return "Persona{" +
                "ID=" + ID +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
