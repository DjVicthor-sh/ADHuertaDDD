import modelo.Huerto;
import repositorios.RepoHuerto;

public class Main {
    public static void main(String[] args) {

        //Crear repositorio
        RepoHuerto rh = new RepoHuerto();

        //Construir la Entidad
        Huerto h1 = new Huerto(1L, 1L,"Patata","Madrid", 20.4F);

        //Mostrar
        System.out.println(h1.toString());

    }
}