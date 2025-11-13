package repositorios;

import java.util.List;
import java.util.Optional;

public class RepoHuerto<T, ID> implements IRepositorioExtend<T,ID> {
    //hcerlo generico <T, ID>
    @Override
    public Optional findByIdOptional(Object o) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Object o) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(Object o) {
        return false;
    }

    @Override
    public Object findById(Object o) {
        return null;
    }

    @Override
    public List findAll() {
        return List.of();
    }

    @Override
    public Object save(Object entity) {
        return null;
    }
}
