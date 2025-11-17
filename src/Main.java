import dominio.Huerto;
import dominio.Persona;
import dominio.Tamanio;
import repositorios.RepoHuerto;
import repositorios.RepoPersona;



public class Main {

    public static void main(String[] args) {

        System.out.println("--- Prueba simple de repositorios ---");

        // 1. Crear repositorios
        RepoHuerto rh = new RepoHuerto();
        RepoPersona rp = new RepoPersona();

        // 2. Empezar de cero (borra los CSV)
        rh.deleteAll();
        rp.deleteAll();
        System.out.println("Ficheros limpios.");

        // 3. Crear una Persona y guardarla
        try {
            Persona p1 = new Persona(1L, "Paco", "Garcia");
            rp.save(p1);
            System.out.println("Persona guardada: " + p1.getNombre());
        } catch (Exception e) {
            System.out.println("ERROR al guardar persona: " + e.getMessage());
        }

        // 4. Crear un Huerto y guardarlo
        try {
            // (Usa 20.5f para que sea float)
            Huerto h1 = new Huerto(101L, 1L, "Patata", "Madrid", new Tamanio(20f,"m2"));
            rh.save(h1);
            System.out.println("Huerto guardado: " + h1.getCultivo());
        } catch (Exception e) {
            System.out.println("ERROR al guardar huerto: " + e.getMessage());
        }

        // 5. Comprobar el conteo
        System.out.println("--- Conteo Final ---");
        System.out.println("Total Personas en CSV: " + rp.count());
        System.out.println("Total Huertos en CSV: " + rh.count());
    }
}