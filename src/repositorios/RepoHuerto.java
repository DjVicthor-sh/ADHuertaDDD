package repositorios;

import dominio.Huerto;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class RepoHuerto implements IRepositorioExtend<Huerto, Long> {

    BufferedWriter bw = null;
    BufferedReader br = null;

    String ln;
    int numLineas = 0;

    @Override
    public Optional<Huerto> findByIdOptional(Long id) {
        return Optional.empty();
    }

    //Suma 1 por cada linea, cada lina es una entidad distinta
    @Override
    public long count() {

        try {

            br = new BufferedReader(new FileReader("Huerto.csv"));

            while ((ln = br.readLine()) != null) {
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
        return false;
    }

    @Override
    public Huerto findById(Long id) {
        return null;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    //Por el momento guarda solo un objeto, se tendria que poder guardar mas de uno usando una lkista o similar.
    @Override
    public <S extends Huerto> S save(S huerto) {

        try {

            bw = new BufferedWriter(new FileWriter("Huerto.csv"));

            //Funcion provisional con toString
            bw.write(huerto.toString());
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return huerto;
    }
}
