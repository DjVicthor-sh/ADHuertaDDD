package repositorios;

import baseDatos.ConexionDB;
import dominio.Huerto;
import dominio.Tamanio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RepoHuertoJDBC implements IRepositorioExtend<Huerto, Long> {

    @Override
    public List<Huerto> findAll() {
        List<Huerto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Huerto";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Reconstruccion del objeto valor desde columnas
                Tamanio t = new Tamanio(rs.getFloat("tamanioValor"), rs.getString("tamanioUnidad"));

                lista.add(new Huerto(
                        rs.getLong("ID"),
                        rs.getLong("idPersona"),
                        rs.getString("cultivo"),
                        rs.getString("localizacion"),
                        t
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error en findAll: " + e.getMessage());
        }
        return lista.stream().collect(Collectors.toList());
    }

    @Override
    public <S extends Huerto> S save(S entity) {
        if (entity == null || entity.getID() == null) throw new IllegalArgumentException("Datos nulos");

        String sql = "INSERT OR REPLACE INTO Huerto (ID, idPersona, cultivo, localizacion, tamanioValor, tamanioUnidad) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, entity.getID());
            pstmt.setLong(2, entity.getIdPersona());
            pstmt.setString(3, entity.getCultivo());
            pstmt.setString(4, entity.getLocalizacion());
            // Desglose de objeto valor
            pstmt.setFloat(5, entity.getTamanio().getTamanio());
            pstmt.setString(6, entity.getTamanio().getUnidad());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error en save: " + e.getMessage());
        }
        return entity;
    }

    @Override
    public Huerto findById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID nulo");
        return findAll().stream()
                .filter(h -> h.getID().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) throw new IllegalArgumentException("ID nulo");
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Huerto WHERE ID = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Huerto");
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
    public Optional<Huerto> findByIdOptional(Long id) {
        return Optional.ofNullable(findById(id));
    }

    // Busqueda por tipo de cultivo
    public List<Huerto> findByCultivo(String cultivo) {
        return findAll().stream()
                .filter(h -> h.getCultivo().equalsIgnoreCase(cultivo))
                .collect(Collectors.toList());
    }

    @Override
    public List<Huerto> findAllToList() {
        return findAll();
    }
}