package baseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    // La URL usa "jdbc:sqlite:" seguido del nombre del archivo.
    // Al no poner una ruta C:\..., Java lo creará en la carpeta raíz del proyecto (Ruta relativa).
    private static final String URL = "jdbc:sqlite:huerto_db.db";
    private static Connection connection = null;

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            crearTablas(); // Cada vez que conectamos, nos aseguramos de que las tablas existen
        }
        return connection;
    }

    // Este método crea las tablas automáticamente si no existen.
    // Así tu práctica es "ejecutar y listo" para el profesor.
    private static void crearTablas() {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            // Tabla Persona
            stmt.execute("CREATE TABLE IF NOT EXISTS Persona (" +
                    "ID INTEGER PRIMARY KEY," +
                    "nombre TEXT," +
                    "apellido TEXT)");

            // Tabla Huerto (con las columnas para el objeto valor Tamanio)
            stmt.execute("CREATE TABLE IF NOT EXISTS Huerto (" +
                    "ID INTEGER PRIMARY KEY," +
                    "idPersona INTEGER," +
                    "cultivo TEXT," +
                    "localizacion TEXT," +
                    "tamanioValor REAL," +
                    "tamanioUnidad TEXT)");

            // Tabla Labor
            stmt.execute("CREATE TABLE IF NOT EXISTS Labor (" +
                    "ID INTEGER PRIMARY KEY," +
                    "idHuerto INTEGER," +
                    "descripcion TEXT," +
                    "fechaLimite TEXT)");

        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar las tablas: " + e.getMessage());
        }
    }
}
