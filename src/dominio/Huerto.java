package dominio;


/**
 * Tenemos que crear las variables que usaran la Entidad (ID, IDPersona , Cultivo, Localizacion, Tamaño )
 */
public class Huerto {

    private final Long ID;
    private final Long idPersona;
    private final String cultivo;
    private final String localizacion;
    private final Tamanio tamanio;

    //Constructor
    public Huerto(Long ID, Long idPersona, String cultivo, String localizacion, Tamanio tamanio) {
        this.ID = ID;
        this.idPersona = idPersona;
        this.cultivo = cultivo;
        this.localizacion = localizacion;
        this.tamanio = tamanio;
    }

    //Getters
    public Long getID() {
        return ID;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public String getCultivo() {
        return cultivo;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    //Fin Getter y Setter

    /**
     * ======================================
     * Aqui pondremos las funciones necesarias.
     * ======================================
     * <p>
     * Se me ocurre una para añadirle el dueño
     *
     */
    //Funciones

    //Fin Funciones

    //Funcion toString
    @Override
    public String toString() {
        return "Huerto{" +
                "ID=" + ID +
                ", idPersona=" + idPersona +
                ", cultivo='" + cultivo + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", tamaño=" + tamanio +
                '}';
    }
}
