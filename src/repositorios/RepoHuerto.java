package repositorios;

import dominio.Huerto;

import java.util.List;
import java.util.Optional;

public class RepoHuerto implements IRepositorioExtend<Huerto,Long> {
    
    @Override
    public Optional<Huerto> findByIdOptional(Long id) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteAll() {

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

    @Override
    public <S extends Huerto> S save(S huerto) {


        return null;
    }
}
