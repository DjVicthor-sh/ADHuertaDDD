package dominio;


/**
 * Tenemos que crear las variables que usaran la Entidad (ID, IDPersona , Cultivo, Localizacion, Tamaño )
 */
public class Huerto {

    private Long ID;
    private Long idPersona;
    private String cultivo;
    private String localizacion;
    private Tamanio tamanio;

    //Constructor
    public Huerto(Long ID, Long idPersona, String cultivo, String localizacion, Tamanio tamanio) {
        this.ID = ID;
        this.idPersona = idPersona;
        this.cultivo = cultivo;
        this.localizacion = localizacion;
        this.tamanio = tamanio;
    }

    //Getter y Setter
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getCultivo() {
        return cultivo;
    }

    public void setCultivo(String cultivo) {
        this.cultivo = cultivo;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Tamanio getTamanio() {
        return tamanio;
    }

    public void setTamanio(Tamanio tamanio) {
        this.tamanio = tamanio;
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
