import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EscrituraCSV {

    static BufferedWriter bw = null;

    public static void crearPersonasCSV() {
        try {
            bw = new BufferedWriter(new FileWriter("Personas.csv"));

            bw.write("Prueba");
            bw.newLine();
            bw.write("Prueba");
            bw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
