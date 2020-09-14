package service;

import java.io.*;
import java.time.LocalDateTime;

public class LogService {

    public static File file = new File("src/Files/Logs.txt");

    public static void addLoggedAction(String value) {

        try (FileWriter logWriter = new FileWriter(file,true)) {
            LocalDateTime dateTime = LocalDateTime.now();
            if(file.length() != 0){
                logWriter.write("\n");
            }
            logWriter.write(value + " (" + dateTime + ")");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
