package repositorios;

import dominio.Persona; // Importamos la clase Persona

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio para la entidad Persona.
 * Esta clase usa un fichero "Persona.csv" para la persistencia.
 */
public class RepoPersona implements IRepositorioExtend<Persona, Long> {

    // Definimos el nombre del fichero y el separador como constantes
    private static final String FILE_NAME = "Persona.csv";
    private static final String SEPARATOR = ",";

    public RepoPersona() {
        // Al crear el repositorio, nos aseguramos de que el fichero exista
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el fichero CSV: " + e.getMessage());
        }
    }

    // --- Métodos Privados (Ayudantes) ---

    /**
     * Convierte un objeto Persona en una línea de texto CSV.
     * El formato será: ID,nombre,apellido
     */
    private String personaToCsv(Persona persona) {
        return persona.getID() + SEPARATOR +
                persona.getNombre() + SEPARATOR +
                persona.getApellido();
    }

    /**
     * Convierte una línea de texto CSV en un objeto Persona.
     */
    private Persona csvToPersona(String csvLine) {
        String[] partes = csvLine.split(SEPARATOR);
        // OJO: El constructor de Persona pide (apellido, nombre, ID)
        // Nuestro formato CSV es ID[0], nombre[1], apellido[2]
        return new Persona(partes[2], partes[1], Long.parseLong(partes[0]));
    }

    /**
     * Escribe una lista completa de Personas en el fichero, sobrescribiendo el contenido.
     */
    private void escribirTodas(List<Persona> personas) {
        // Usamos 'false' en FileWriter para SOBRESCRIBIR el fichero
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (Persona p : personas) {
                bw.write(personaToCsv(p));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el fichero: " + e.getMessage());
        }
    }


    // --- Implementación de IRepositorioExtend ---

    /**
     * Devuelve todas las instancias de Persona del fichero.
     */
    @Override
    public List<Persona> findAll() {
        List<Persona> personas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) { // Evitar líneas vacías
                    personas.add(csvToPersona(linea));
                }
            }
        } catch (FileNotFoundException e) {
            // No es un error, el fichero puede no existir al principio
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero: " + e.getMessage());
        }
        return personas;
    }

    /**
     * Guarda una entidad (la añade o la actualiza si ya existe).
     */
    @Override
    public <S extends Persona> S save(S entity) {
        if (entity == null || entity.getID() == null) {
            throw new IllegalArgumentException("La entidad o su ID no pueden ser nulos");
        }

        // Leemos todas las personas que ya existen
        List<Persona> todas = findAll();

        // Eliminamos la versión "vieja" de esta persona, si existe
        // Esto permite que 'save' sirva también para ACTUALIZAR
        todas.removeIf(p -> p.getID().equals(entity.getID()));

        // Añadimos la entidad (nueva o actualizada) a la lista
        todas.add(entity);

        // Sobrescribimos el fichero con la lista actualizada
        escribirTodas(todas);

        return entity;
    }

    /**
     * Devuelve la entidad con el ID dado, envuelta en un Optional.
     */
    @Override
    public Optional<Persona> findByIdOptional(Long id) {
        // Reutilizamos findById
        return Optional.ofNullable(findById(id));
    }

    /**
     * Devuelve el número de entidades.
     */
    @Override
    public long count() {
        // Reutilizamos findAll y contamos el tamaño
        return findAll().size();
    }

    /**
     * Borra la entidad con el identificador dado.
     */
    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        // Leemos todas
        List<Persona> todas = findAll();

        // Eliminamos solo la que tenga el ID especificado
        todas.removeIf(p -> p.getID().equals(id));

        // Sobrescribimos el fichero solo con las que quedan
        escribirTodas(todas);
    }

    /**
     * Borra todas las entidades del repositorio.
     */
    @Override
    public void deleteAll() {
        // Escribimos una lista vacía, lo que borra el contenido
        escribirTodas(new ArrayList<>());
    }

    /**
     * Devuelve true si existe una entidad con el ID dado.
     */
    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        // Reutilizamos findById
        return findById(id) != null;
    }

    /**
     * Devuelve la entidad con el ID dado.
     */
    @Override
    public Persona findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        // Leemos todas y buscamos
        for (Persona p : findAll()) {
            if (p.getID().equals(id)) {
                return p;
            }
        }
        // Si no se encuentra, devuelve null
        return null;
    }
}