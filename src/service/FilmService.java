package service;

import model.Film;
import view.Main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilmService {


    public static void editFilmName(int filmNum, String newFilmName) {

        Main.cinema.getFilms().get(filmNum).setName(newFilmName);


    }

    public static void editFilmDate(int filmNum, LocalDateTime dateTime) {
        Main.cinema.getFilms().get(filmNum).setDateTime(dateTime);

    }

    public static void addTickets(int filmNum, int numOfAppend) {

        Main.cinema.getFilms().get(filmNum).addTickets(numOfAppend);


    }

    public static int findFilmNumByName(String name) {
        for (int i = 0; i < Main.cinema.getFilms().size(); i++) {
            if (Main.cinema.getFilms().get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
