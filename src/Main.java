import dominio.Huerto;
import dominio.Persona;
import repositorios.RepoHuerto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        RepoHuerto rh = new RepoHuerto();


        Huerto h1 = new Huerto(1L, 1L, "Patata", "Madrid", 20);
        Huerto h2 = new Huerto(2L, 1L, "Patata", "Madrid", 20);


        System.out.println("El objeto: " + rh.save(h1) +"\n" +rh.save(h2) + " se guardo correctamente");

        System.out.println(rh.count());

        //rh.deleteAll();
        //System.out.println("csv Borrado");
    }
}