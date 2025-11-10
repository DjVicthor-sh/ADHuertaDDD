package dominio;

/**
 * Tenemos que crear las variables que usaran la Entidad (ID, Nombre , Apellido )
 */

public class Persona {

    private Long ID;
    private String nombre;
    private String apellido;

    public Persona(String apellido, String nombre, Long ID) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.ID = ID;
    }

    // Getters y Setters

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * ======================================
     * Aqui pondremos las funciones necesarias.
     * ======================================
     *
     *
     *
     */
    //Funciones

    //Fin Funciones
    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", ID=" + ID +
                ", apellido='" + apellido + '\'' +
                '}';
    }
}
