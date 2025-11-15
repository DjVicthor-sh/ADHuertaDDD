package repositorios;

import dominio.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoPersona implements IRepositorioExtend<Persona, Long> {

    // Constantes para el fichero, igual que tu compañero usa SEPARATOR
    private static final String FILE_NAME = "Persona.csv";
    private static final String SEPARATOR = ",";

    // Un constructor para asegurar que el fichero existe
    public RepoPersona() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al crear el fichero CSV: " + e.getMessage());
        }
    }

    // --- Métodos Privados ---

    /**
     * Convierte un objeto Persona en una línea de texto CSV.
     * Formato: ID,nombre,apellido
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
        // Nuestro formato CSV es ID[0], nombre[1], apellido[2]
        return new Persona(Long.parseLong(partes[0]), partes[1], partes[2]);
    }
    /**
     * MÉTODO CLAVE: Escribe una lista completa de Personas en el fichero,
     * SOBRESCRIBIENDO el contenido. Esto es necesario para save() y deleteById().
     */
    private void escribirTodas(List<Persona> personas) {
        BufferedWriter bw = null;
        try {
            // 'false' en FileWriter para SOBRESCRIBIR el fichero
            bw = new BufferedWriter(new FileWriter(FILE_NAME, false));
            for (Persona p : personas) {
                bw.write(personaToCsv(p));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el fichero: " + e.getMessage());
        } finally {
            // Cerramos manualmente, al estilo de tu compañero
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // --- Implementación de IRepositorioExtend ---

    /**
     * Devuelve todas las instancias de Persona del fichero.
     * (Estilo idéntico a findAll() de RepoHuerto)
     */
    @Override
    public List<Persona> findAll() {
        List<Persona> personas = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(FILE_NAME));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) { // Evitar líneas vacías
                    personas.add(csvToPersona(linea));
                }
            }
        } catch (FileNotFoundException e) {
            // No es un error, el fichero puede estar vacío al principio
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

        // 1. LEER TODO
        List<Persona> todas = findAll();

        // 2. MODIFICAR EN MEMORIA
        //    Eliminamos la versión "vieja" de esta persona (si existe)
        todas.removeIf(p -> p.getID().equals(entity.getID()));
        //    Añadimos la entidad (nueva o actualizada) a la lista
        todas.add(entity);

        // 3. VOLVER A ESCRIBIR TODO
        escribirTodas(todas);

        return entity;
    }

    /**
     * Borra la entidad con el identificador dado.
     * Esta es la implementación COMPLETA.
     */
    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        // 1. LEER TODO
        List<Persona> todas = findAll();

        // 2. MODIFICAR EN MEMORIA
        todas.removeIf(p -> p.getID().equals(id));

        // 3. VOLVER A ESCRIBIR TODO
        escribirTodas(todas);
    }

    /**
     * Borra todas las entidades del repositorio.
     */
    @Override
    public void deleteAll() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(FILE_NAME));
            bw.write("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Devuelve el número de entidades.
     */
    @Override
    public long count() {
        // Es más eficiente reusar findAll() que volver a leer el fichero
        return findAll().size();
    }

    /**
     * Devuelve la entidad con el ID dado.
     */
    @Override
    public Persona findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        for (Persona p : findAll()) {
            if (p.getID().equals(id)) {
                return p;
            }
        }
        return null; // No se encuentra
    }

    /**
     * Devuelve true si existe una entidad con el ID dado.
     */
    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return findById(id) != null;
    }

    @Override
    public Optional<Persona> findByIdOptional(Long id) {
        return Optional.ofNullable(findById(id));
    }
}