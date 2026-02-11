package dominio;

public class Labor {
    private final Long ID;
    private final Long idHuerto; // Para la relacion con huerto, muchos a uno
    private final String descripcion;
    private final String fechaLimite;

    public Labor(Long ID, Long idHuerto, String descripcion, String fechaLimite){
        this.ID = ID;
        this.idHuerto = idHuerto;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
    }

    public Long getId() { return ID; }
    public Long getIdHuerto() { return idHuerto; }
    public String getDescripcion() { return descripcion; }
    public String getFechaLimite() { return fechaLimite; }

    @Override
    public String toString() {
        return "Tarea{" + "ID=" + ID + ", idHuerto=" + idHuerto +
                ", descripcion='" + descripcion + '\'' + ", fecha='" + fechaLimite + '\'' + '}';
    }
}
