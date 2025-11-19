import dominio.Huerto;
import dominio.Persona;
import dominio.Tamanio;
import repositorios.RepoHuerto;
import repositorios.RepoPersona;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INICIO DE PRUEBAS (2 Entidades: Persona y Huerto) ===");

        // 1. Instanciar Repositorios
        RepoPersona repoPersona = new RepoPersona();
        RepoHuerto repoHuerto = new RepoHuerto();

        // 2. Limpieza inicial (deleteAll) para empezar de cero
        repoPersona.deleteAll();
        repoHuerto.deleteAll();
        System.out.println("[OK] Ficheros limpiados.");

        // 3. Guardar Personas (save)
        System.out.println("\n--- Guardando Personas ---");
        Persona p1 = new Persona(1L, "Victor", "Huerta");
        Persona p2 = new Persona(2L, "Ana", "Lopez");

        repoPersona.save(p1);
        repoPersona.save(p2);

        System.out.println("Personas guardadas: " + repoPersona.count()); // Debe salir 2

        // 4. Guardar Huertos (save) - Relacionados con Persona 1 y 2
        System.out.println("\n--- Guardando Huertos ---");
        // Huerto 100 asignado a Victor (ID 1)
        Huerto h1 = new Huerto(100L, 1L, "Tomates", "Valencia", new Tamanio(50f, "m2"));
        // Huerto 101 asignado a Ana (ID 2)
        Huerto h2 = new Huerto(101L, 2L, "Patatas", "Madrid", new Tamanio(100f, "Ha"));

        repoHuerto.save(h1);
        repoHuerto.save(h2);

        System.out.println("Huertos guardados: " + repoHuerto.count()); // Debe salir 2

        // 5. Pruebas de Búsqueda Estándar (findById / existsById)
        System.out.println("\n--- Pruebas CRUD Estándar ---");
        System.out.println("¿Existe Persona 1?: " + repoPersona.existsById(1L));

        Huerto hRecuperado = repoHuerto.findById(100L);
        if (hRecuperado != null) {
            System.out.println("Huerto recuperado por ID 100: " + hRecuperado.getCultivo());
        }

        // 6. PRUEBA DE MÉTODOS SEMÁNTICOS (Tus métodos propios)
        System.out.println("\n--- Pruebas de Métodos Propios (Semánticos) ---");

        // A) RepoPersona: findByApellido
        System.out.println("> Buscando personas con apellido 'Huerta':");
        List<Persona> listaPersonas = repoPersona.findByApellido("Huerta");
        for (Persona p : listaPersonas) {
            System.out.println("  Encontrado: " + p.getNombre() + " " + p.getApellido());
        }

        // B) RepoHuerto: findByCultivo
        System.out.println("> Buscando huertos de 'Patatas':");
        List<Huerto> listaHuertos = repoHuerto.findByCultivo("Patatas");
        for (Huerto h : listaHuertos) {
            System.out.println("  Encontrado Huerto ID " + h.getID() + " en " + h.getLocalizacion());
        }

        // 7. Prueba de Borrado (deleteById)
        System.out.println("\n--- Prueba de Borrado ---");
        repoHuerto.deleteById(100L); // Borramos los tomates
        System.out.println("Huertos restantes tras borrar uno: " + repoHuerto.count());

        System.out.println("\n=== PRUEBAS FINALIZADAS ===");
    }
}