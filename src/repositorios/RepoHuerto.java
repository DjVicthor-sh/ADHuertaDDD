package repositorios;

import dominio.Huerto;

import dominio.Tamanio;
import gestorCSV.GestorCSV;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoHuerto implements IRepositorioExtend<Huerto, Long> {

    BufferedWriter bw = null;
    BufferedReader br = null;

    private static final String SEPARADOR = ",";
    private static final String FILE_NAME = "Huerto.csv";

    private String huertoToCSV(Huerto huerto) {
        // "Aplanamos" el objeto Tamanio en sus dos propiedades
        return huerto.getID() + SEPARADOR +
                huerto.getIdPersona() + SEPARADOR +
                huerto.getCultivo() + SEPARADOR +
                huerto.getLocalizacion() + SEPARADOR +
                huerto.getTamanio().getTamanio() + SEPARADOR + // <-- [4] Valor del tamaño
                huerto.getTamanio().getUnidad();            // <-- [5] Unidad del tamaño
    }

    private Huerto csvToHueto(String csvLine) {

        String[] parte = csvLine.split(SEPARADOR);

        // 1. "Re-hidratamos" el Objeto Valor Tamanio leyendo las dos últimas columnas
        float tamanioValor = Float.parseFloat(parte[4]);
        String tamanioUnidad = parte[5];
        Tamanio tamanio = new Tamanio(tamanioValor, tamanioUnidad);

        // 2. Creamos el Huerto con el objeto Tamanio
        return new Huerto(
                Long.parseLong(parte[0]),
                Long.parseLong(parte[1]),
                parte[2],
                parte[3],
                tamanio // <-- Le pasamos el objeto completo
        );
    }

    private void escribirTodas(List<Huerto> huerto) {
        // 1. El Repositorio "traduce" las personas a líneas
        List<String> lineas = new ArrayList<>();
        for (Huerto h : huerto) {
            lineas.add(huertoToCSV(h));
        }

        // 2. El gestorCSV.GestorCSV escribe las líneas
        GestorCSV.escribirTodasLasLineas(FILE_NAME, lineas);
    }


    @Override
    public Optional<Huerto> findByIdOptional(Long id) {
        return Optional.empty();
    }

    //Suma 1 por cada linea, cada lina es una entidad distinta
    @Override
    public long count() {
        // Mucho más simple: solo cuenta la lista de findAll()
        return findAll().size();
    }

    @Override
    public void deleteById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        // 1. LEER TODO
        List<Huerto> lista = findAll();

        // 2. MODIFICAR EN MEMORIA
        lista.removeIf(h -> h.getID().equals(id));

        // 3. VOLVER A ESCRIBIR TODO
        escribirTodas(lista);

    }

    //Para borrar simplemente escribimos una linea sin nada, ya que escribe desde el principio
    @Override
    public void deleteAll() {
        // Le pide al gestor que escriba una lista vacía
        GestorCSV.escribirTodasLasLineas(FILE_NAME, new ArrayList<>());
    }

    @Override
    public boolean existsById(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        for (Huerto h : findAll()) {
            if (h.getID().equals(id)) {
                return true;
            }
        }

        return false;

    }

    @Override
    public Huerto findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        for (Huerto h : findAll()) {
            if (h.getID().equals(id)) {
                return h;
            }
        }

        return null;
    }

    @Override
    public List<Huerto> findAll() {
        List<Huerto> lista = new ArrayList<>();

        // 1. El GestorCSV lee
        List<String> lineas = GestorCSV.leerLineas(FILE_NAME);

        // 2. El Repositorio traduce
        for (String linea : lineas) {
            lista.add(csvToHueto(linea));
        }

        return lista;
    }


    @Override
    public <S extends Huerto> S save(S huerto) {

        List<Huerto> lista = findAll();

        lista.removeIf(h -> h.getID().equals(huerto.getID()));

        lista.add(huerto);

        //Esperrar a gestorCSV.GestorCSV para la funcion de escribir
        escribirTodas(lista);

        return huerto;
    }
}
