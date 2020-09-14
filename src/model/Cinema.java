package model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cinema implements Serializable {

    private ArrayList<User> users ;
    private ArrayList<Film> films ;



    public Cinema() {
        users = new ArrayList<>();
        films = new ArrayList<>();
    }

    public Cinema(ArrayList<User> users, ArrayList<Film> films) {
        this.users = users;
        this.films = films;
    }

    public Film addFilm(Film film){

        films.add(film);
        return film;
    }

    public void addUser(User user){
        users.add(user);
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }
}
