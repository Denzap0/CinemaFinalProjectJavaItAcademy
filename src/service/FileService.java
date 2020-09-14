package service;

import model.Cinema;
import view.Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileService {



    public static void updateCinema(Cinema cinema) {
        try (ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream(Main.file))){
            oOut.writeObject(cinema);
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
