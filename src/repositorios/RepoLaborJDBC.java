package repositorios;

import baseDatos.ConexionDB;
import dominio.Labor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepoLaborJDBC implements IRepositorioExtend<Labor, Long> {

    @Override
    public List<Labor> findAll() {
        List<Labor> lista = new ArrayList<>();
        String sql = "SELECT * FROM Labor";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Labor(
                        rs.getLong("ID"),
                        rs.getLong("idHuerto"),
                        rs.getString("descripcion"),
                        rs.getString("fechaLimite")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer las labores: " + e.getMessage());
        }

        return lista.stream().collect(Collectors.toList());
    }

    @Override
    public List<Labor> findAllToList() {
        return findAll();
    }

    @Override
    public <S extends Labor> S save(S entity) {
        if (entity == null || entity.getID() == null) {
            throw new IllegalArgumentException("La entidad o su ID no pueden ser nulos");
        }

        // para insertar o actualizar si el ID ya existe
        String sql = "INSERT OR REPLACE INTO Labor (ID, idHuerto, descripcion, fechaLimite) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, entity.getID());
            pstmt.setLong(2, entity.getIdHuerto());
            pstmt.setString(3, entity.getDescripcion());
            pstmt.setString(4, entity.getFechaLimite());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la labor: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public Labor findById(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        return findAll().stream()
                .filter(l -> l.getID().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        String sql = "DELETE FROM Labor WHERE ID = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar la labor: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM Labor";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar todas las labores: " + e.getMessage());
        }
    }

    @Override
    public long count() {
        return findAll().stream().count();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id) != null;
    }

    @Override
    public Optional<Labor> findByIdOptional(Long id) {
        return Optional.ofNullable(findById(id));
    }

    //el mettdo propio semantico
    public List<Labor> findByHuerto(Long idHuerto) {
        return findAll().stream()
                .filter(l -> l.getIdHuerto().equals(idHuerto))
                .collect(Collectors.toList());
    }
}