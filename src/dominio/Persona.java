package dominio;

/**
 * Tenemos que crear las variables que usaran la Entidad (ID, Nombre , Apellido )
 */

public class Persona {

    private final Long ID;
    private final String nombre;
    private final String apellido;

    public Persona(Long ID, String nombre, String apellido) {

        // Validar
        if (ID == null || ID <= 0) {
            throw new IllegalArgumentException("El ID no puede ser nulo o negativo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }

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

    /**
     * ======================================
     * Aqui pondremos las funciones necesarias.
     * ======================================
     *
     * CAMBIO
     *
     */
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
