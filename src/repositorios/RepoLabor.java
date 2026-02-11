package repositorios;

import dominio.Labor;
import gestorCSV.GestorCSV;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepoLabor implements IRepositorioExtend<Labor, Long> {
    // Nombre del archivo donde se guardarán las labores
    private static final String FILE_NAME = "Labor.csv";
    private static final String SEPARADOR = ",";

    // Pasa un objeto Labor a una línea de texto para el CSV
    private String laborToCsv(Labor l) {
        return l.getID() + SEPARADOR +
                l.getIdHuerto() + SEPARADOR +
                l.getDescripcion() + SEPARADOR +
                l.getFechaLimite();
    }

    // Pasa una línea del CSV a un objeto Labor
    private Labor csvToLabor(String linea) {
        String[] partes = linea.split(SEPARADOR);
        return new Labor(
                Long.parseLong(partes[0]),
                Long.parseLong(partes[1]),
                partes[2],
                partes[3]
        );
    }


    // Implementación de la Interfaz
    @Override
    public List<Labor> findAll() {
        // Usamos Streams para leer y convertir todas las líneas
        return GestorCSV.leerLineas(FILE_NAME).stream()
                .map(this::csvToLabor)
                .collect(Collectors.toList());
    }

    @Override
    public <S extends Labor> S save(S entity) {
        List<Labor> todas = findAll();
        // Borramos la versión vieja si existe para actualizar
        todas.removeIf(l -> l.getID().equals(entity.getID()));
        todas.add(entity);

        // Guardamos toddo de nuevo convirtiendo a texto con Streams
        List<String> lineas = todas.stream()
                .map(this::laborToCsv)
                .collect(Collectors.toList());

        GestorCSV.escribirTodasLasLineas(FILE_NAME, lineas);
        return entity;
    }

    @Override
    public Labor findById(Long id) {
        // Buscamos usando Stream para cumplir con el requisito
        return findAll().stream()
                .filter(l -> l.getID().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        List<Labor> todas = findAll();
        if (todas.removeIf(l -> l.getID().equals(id))) {
            List<String> lineas = todas.stream()
                    .map(this::laborToCsv)
                    .collect(Collectors.toList());
            GestorCSV.escribirTodasLasLineas(FILE_NAME, lineas);
        }
    }

    @Override
    public void deleteAll() {
        GestorCSV.escribirTodasLasLineas(FILE_NAME, new ArrayList<>());
    }

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    @Override
    public Optional<Labor> findByIdOptional(Long id) {
        return Optional.ofNullable(findById(id));
    }

    // Method propio busca todas las labores de un huerto concreto
    public List<Labor> findByHuerto(Long idHuerto) {
        return findAll().stream()
                .filter(l -> l.getIdHuerto().equals(idHuerto))
                .collect(Collectors.toList());
    }
}