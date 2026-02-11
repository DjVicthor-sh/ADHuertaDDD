package dominio;

public class Labor {
    private final Long ID;
    private final Long idHuerto; // Para la relacion con huerto, muchos a uno
    private final String descripcion;
    private final String fechaLimite;

    public Labor(Long ID, Long idHuerto, String descripcion, String fechaLimite){
        this.ID = ID;
        this.idHuerto = idHuerto;

        //descripcion
        if (descripcion == null) {
            this.descripcion = "";
        } else {
            this.descripcion = descripcion.trim();
        }

        //fecha
        if (fechaLimite == null){
            this.fechaLimite = "";
        } else {
            this.fechaLimite = fechaLimite.trim();
        }
    }

    // Cambiado a getID() para seguir el estándar de tus otras clases
    public Long getID() { return ID; }
    public Long getIdHuerto() { return idHuerto; }
    public String getDescripcion() { return descripcion; }
    public String getFechaLimite() { return fechaLimite; }

    @Override
    public String toString() {
        // Corregido el nombre de la clase en el String
        return "Labor{" + "ID=" + ID + ", idHuerto=" + idHuerto +
                ", descripcion='" + descripcion + '\'' + ", fecha='" + fechaLimite + '\'' + '}';
    }
}