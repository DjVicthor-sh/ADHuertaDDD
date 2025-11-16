package repositorios;

import dominio.Huerto;
import dominio.Persona;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoHuerto implements IRepositorioExtend<Huerto, Long> {

    BufferedWriter bw = null;
    BufferedReader br = null;

    private static final String SEPARADOR = ",";

    private String huertoToCSV(Huerto huerto) {
        return huerto.getID() + SEPARADOR +
                huerto.getIdPersona() + SEPARADOR +
                huerto.getCultivo() + SEPARADOR +
                huerto.getLocalizacion() + SEPARADOR +
                huerto.getTamanio();
    }

    private Huerto csvToHueto(String csvLine) {

        String[] parte = csvLine.split(SEPARADOR);

        return new Huerto(
                Long.parseLong(parte[0]),
                Long.parseLong(parte[1]),
                parte[2],
                parte[3],
                Float.parseFloat(parte[4])
        );
    }


    @Override
    public Optional<Huerto> findByIdOptional(Long id) {
        return Optional.empty();
    }

    //Suma 1 por cada linea, cada lina es una entidad distinta
    @Override
    public long count() {

        int numLineas = 0;

        try {

            br = new BufferedReader(new FileReader("Huerto.csv"));

            while ((br.readLine()) != null) {
                numLineas += 1;
            }

            br.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return numLineas;
    }

    @Override
    public void deleteById(Long id) {

        /**
         *  Completar el save()
         */

    }

    //Para borrar simplemente escribimos una linea sin nada, ya que escribe desde el principio
    @Override
    public void deleteAll() {

        try {

            bw = new BufferedWriter(new FileWriter("Huerto.csv"));

            bw.write("");
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        String linea;

        List<Huerto> lista = new ArrayList<Huerto>();

        try {

            br = new BufferedReader(new FileReader("Huerto.csv"));

            while (((linea = br.readLine()) != null)) {

                lista.add(csvToHueto(linea));

            }

            br.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }


    @Override
    public <S extends Huerto> S save(S huerto) {

        List<Huerto> lista = findAll();

        lista.removeIf(h -> h.getID().equals(huerto.getID()));

        lista.add(huerto);

        //Esperrar a GestorCSV para la funcion de escribir


        return huerto;
    }
}
