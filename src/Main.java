import dominio.Huerto;
import dominio.Labor;
import dominio.Persona;
import dominio.Tamanio;
import repositorios.RepoHuertoJDBC;
import repositorios.RepoLaborJDBC;
import repositorios.RepoPersonaJDBC;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INICIO DE PRUEBAS (2 Entidades: Persona y Huerto) ===");

        // 1. Instanciar Repositorios
        RepoPersonaJDBC repoPersonaJDBC = new RepoPersonaJDBC();
        RepoHuertoJDBC repoHuertoJDBC = new RepoHuertoJDBC();
        RepoLaborJDBC repoLaborJDBC = new RepoLaborJDBC();

        // 2. Limpieza inicial (deleteAll) para empezar de cero
        repoPersonaJDBC.deleteAll();
        repoHuertoJDBC.deleteAll();
        repoLaborJDBC.deleteAll();
        System.out.println("[OK] Ficheros limpiados.");

        // 3. Guardar Personas (save)
        System.out.println("\n--- Guardando Personas ---");
        Persona p1 = new Persona(1L, "Victor", "Huerta");
        Persona p2 = new Persona(2L, "Ana", "Lopez");

        repoPersonaJDBC.save(p1);
        repoPersonaJDBC.save(p2);

        System.out.println("Personas guardadas: " + repoPersonaJDBC.count()); // Debe salir 2

        // 4. Guardar Huertos (save) - Relacionados con Persona 1 y 2
        System.out.println("\n--- Guardando Huertos ---");
        // Huerto 100 asignado a Victor (ID 1)
        Huerto h1 = new Huerto(100L, 1L, "Tomates", "Valencia", new Tamanio(50f, "m2"));
        // Huerto 101 asignado a Ana (ID 2)
        Huerto h2 = new Huerto(101L, 2L, "Patatas", "Madrid", new Tamanio(100f, "Ha"));

        repoHuertoJDBC.save(h1);
        repoHuertoJDBC.save(h2);

        System.out.println("Huertos guardados: " + repoHuertoJDBC.count()); // Debe salir 2

        // LABORES
        Labor l1 = new Labor(500L, 100L, "Regar toamtes", "2026-02-20");
        Labor l2 = new Labor(501L, 100L, "Limpiar yerbajos", "2026-05-11");

        repoLaborJDBC.save(l1);
        repoLaborJDBC.save(l2);
        System.out.println("Labores Guardadas: " + repoLaborJDBC.count());

        // 5. Pruebas de Búsqueda Estándar (findById / existsById)
        System.out.println("\n--- Pruebas CRUD Estándar ---");
        System.out.println("¿Existe Persona 1?: " + repoPersonaJDBC.existsById(1L));

        Huerto hRecuperado = repoHuertoJDBC.findById(100L);
        if (hRecuperado != null) {
            System.out.println("Huerto recuperado por ID 100: " + hRecuperado.getCultivo());
        }



        // 6. PRUEBA DE MÉTODOS SEMÁNTICOS (Tus métodos propios)
        System.out.println("\n--- Pruebas de Métodos Propios (Semánticos) ---");

        // A) RepoPersonaJDBC: findByApellido
        System.out.println("> Buscando personas con apellido 'Huerta':");
        List<Persona> listaPersonas = repoPersonaJDBC.findByApellido("Huerta");
        for (Persona p : listaPersonas) {
            System.out.println("  Encontrado: " + p.getNombre() + " " + p.getApellido());
        }

        // B) RepoHuertoJDBC: findByCultivo
        System.out.println("> Buscando huertos de 'Patatas':");
        List<Huerto> listaHuertos = repoHuertoJDBC.findByCultivo("Patatas");
        for (Huerto h : listaHuertos) {
            System.out.println("  Encontrado Huerto ID " + h.getID() + " en " + h.getLocalizacion());
        }

        // C) RepoLaborJDBC: findByHuerto
        System.out.println("> Buscando labores del huerto de Victor");
        List<Labor> laboresHuerto = repoLaborJDBC.findByHuerto(100L);
        for (Labor l : laboresHuerto){
            System.out.println(" - " + l.getDescripcion() + " (Hacer antes de : " + l.getFechaLimite() + ")" );
        }

        // 7. Prueba de Borrado (deleteById)
        System.out.println("\n--- Prueba de Borrado ---");
        repoHuertoJDBC.deleteById(100L); // Borramos los tomates
        System.out.println("Huertos restantes tras borrar uno: " + repoHuertoJDBC.count());

        // Labores
        repoLaborJDBC.deleteById(500L);
        System.out.println("Eliminando la labor --> " + l1.getDescripcion());
        System.out.println("Ahora te quedan... \n" + repoLaborJDBC.count() + " -- labor mas" );
        System.out.println("\n=== PRUEBAS FINALIZADAS ===");
    }
}