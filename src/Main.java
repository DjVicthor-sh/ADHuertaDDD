import dominio.Huerto;
import dominio.Labor;
import dominio.Persona;
import dominio.Tamanio;
import repositorios.RepoHuerto;
import repositorios.RepoLabor;
import repositorios.RepoPersona;

import java.sql.SQLOutput;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INICIO DE PRUEBAS (2 Entidades: Persona y Huerto) ===");

        // 1. Instanciar Repositorios
        RepoPersona repoPersona = new RepoPersona();
        RepoHuerto repoHuerto = new RepoHuerto();
        RepoLabor repoLabor = new RepoLabor();

        // 2. Limpieza inicial (deleteAll) para empezar de cero
        repoPersona.deleteAll();
        repoHuerto.deleteAll();
        repoLabor.deleteAll();
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

        // LABORES
        Labor l1 = new Labor(500L, 100L, "Regar toamtes", "2026-02-20");
        Labor l2 = new Labor(501L, 100L, "Limpiar yerbajos", "2026-05-11");

        repoLabor.save(l1);
        repoLabor.save(l2);
        System.out.println("Labores Guardadas: " + repoLabor.count());

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

        // C) RepoLabor: findByHuerto
        System.out.println("> Buscando labores del huerto de Victor");
        List<Labor> laboresHuerto = repoLabor.findByHuerto(100L);
        for (Labor l : laboresHuerto){
            System.out.println(" - " + l.getDescripcion() + " (Hacer antes de : " + l.getFechaLimite() + ")" );
        }

        // 7. Prueba de Borrado (deleteById)
        System.out.println("\n--- Prueba de Borrado ---");
        repoHuerto.deleteById(100L); // Borramos los tomates
        System.out.println("Huertos restantes tras borrar uno: " + repoHuerto.count());

        // Labores
        repoLabor.deleteById(500L);
        System.out.println("Eliminando la labor --> " + l1.getDescripcion());
        System.out.println("Ahora te quedan... \n" + repoLabor.count() + " -- labor mas" );
        System.out.println("\n=== PRUEBAS FINALIZADAS ===");
    }
}