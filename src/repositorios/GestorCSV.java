package repositorios;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Clase con funciones de escritura y lectura GENERICAS
public class GestorCSV {

    /**
     * Lee todas las líneas de un fichero y las devuelve como una Lista de Strings.
     */
    public static List<String> leerLineas(String nombreFichero) {
        List<String> lineas = new ArrayList<>();
        // try-with-resources cierra el 'br' automáticamente
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) { // Evitar líneas vacías
                    lineas.add(linea);
                }
            }
        } catch (FileNotFoundException e) {
            // Si el fichero no existe, devuelve una lista vacía (es normal)
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el fichero: " + e.getMessage());
        }
        return lineas;
    }

    /**
     * Escribe una lista de Strings en un fichero, SOBRESCRIBIENDO el contenido.
     */
    public static void escribirTodasLasLineas(String nombreFichero, List<String> lineas) {
        // try-with-resources cierra el 'bw' automáticamente
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreFichero, false))) { // false = sobrescribir
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el fichero: " + e.getMessage());
        }
    }
}
