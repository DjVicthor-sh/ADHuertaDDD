package dominio;

public class Tamanio {

    private final float tamanio;
    private final String unidad;

    public Tamanio(float tamanio, String unidad) {
        this.tamanio = tamanio;
        this.unidad = unidad;
    }

    public float getTamanio() {
        return tamanio;
    }

    public String getUnidad() {
        return unidad;
    }

    @Override
    public String toString() {
        return "Tamanio{" +
                "tamanio=" + tamanio +
                ", unidad='" + unidad + '\'' +
                '}';
    }
}
