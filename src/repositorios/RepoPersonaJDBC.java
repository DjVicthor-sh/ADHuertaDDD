package repositorios;

import baseDatos.ConexionDB;
import dominio.Persona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepoPersonaJDBC implements IRepositorioExtend<Persona, Long> {

    @Override
    public List<Persona> findAll() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM Persona";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Persona(
                        rs.getLong("ID"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al leer personas: " + e.getMessage());
        }

        return lista.stream().collect(Collectors.toList());
    }

    @Override
    public List<Persona> findAllToList() {
        return findAll();
    }

    @Override
    public <S extends Persona> S save(S entity) {
        if (entity == null || entity.getID() == null) {
            throw new IllegalArgumentException("La entidad o su ID no pueden ser nulos");
        }

        String sql = "INSERT OR REPLACE INTO Persona (ID, nombre, apellido) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, entity.getID());
            pstmt.setString(2, entity.getNombre());
            pstmt.setString(3, entity.getApellido());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar persona: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public Persona findById(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        return findAll().stream()
                .filter(p -> p.getID().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new IllegalArgumentException("El ID no puede ser nulo");

        String sql = "DELETE FROM Persona WHERE ID = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM Persona";
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public Optional<Persona> findByIdOptional(Long id) {
        return Optional.ofNullable(findById(id));
    }

    //mettodo propio semantico
    public List<Persona> findByApellido(String apellido) {
        return findAll().stream()
                .filter(p -> p.getApellido().equalsIgnoreCase(apellido))
                .collect(Collectors.toList());
    }
}