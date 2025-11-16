package dominio;

public class Tamanio {

    private final float tamanio;
    private final String unidad;

    public Tamanio(float tamanio, String unidad) {

        // --- Validación ---
        if (tamanio <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser positivo.");
        }
        if (unidad == null || unidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La unidad no puede estar vacía.");
        }
        // --- Fin Validación ---

        this.tamanio = tamanio;
        this.unidad = unidad.trim();
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
