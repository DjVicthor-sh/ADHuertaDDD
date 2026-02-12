package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    // Ruta relativa para base de datos SQLite
    private static final String URL = "jdbc:sqlite:huerto_db.db";
    private static Connection connection = null;

    // Obtencion de la conexion (Singleton)
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            crearTablas();
        }
        return connection;
    }

    // Inicializacion del esquema de base de datos
    private static void crearTablas() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            // Entidad Persona
            stmt.execute("CREATE TABLE IF NOT EXISTS Persona (" +
                    "ID INTEGER PRIMARY KEY," +
                    "nombre TEXT," +
                    "apellido TEXT)");

            // Entidad Huerto con campos de objeto valor Tamanio
            stmt.execute("CREATE TABLE IF NOT EXISTS Huerto (" +
                    "ID INTEGER PRIMARY KEY," +
                    "idPersona INTEGER," +
                    "cultivo TEXT," +
                    "localizacion TEXT," +
                    "tamanioValor REAL," +
                    "tamanioUnidad TEXT)");

            // Entidad Labor vinculada a Huerto
            stmt.execute("CREATE TABLE IF NOT EXISTS Labor (" +
                    "ID INTEGER PRIMARY KEY," +
                    "idHuerto INTEGER," +
                    "descripcion TEXT," +
                    "fechaLimite TEXT)");

        } catch (SQLException e) {
            throw new RuntimeException("Error en init DB: " + e.getMessage());
        }
    }
}