package service;

import model.Film;
import model.Ticket;
import model.User;
import view.Main;

import java.time.LocalDateTime;

public class CinemaService /*extends IService*/ {

    /*@Override
     *//*public void getAllUsers(Cinema cinema) {
    updateUsers(cinema);
    return cinema.getUsers();
  }

  public static void updateUsers(Cinema cinema) {
    List<User> users = FileService.getAllUsers();
    cinema.setUsers(users);
  }*/

    public static boolean addUser(User user0) {
        boolean check = true;

        for (User user : Main.cinema.getUsers()) {
            if (user.getLogin().equals(user0.getLogin())) {
                check = false;
            }
        }
        if (check) {
            Main.cinema.addUser(user0);
            FileService.updateCinema(Main.cinema);
            return true;
        }
        return false;
    }

    public static void allFilms() {
        int num = 1;
        for (Film film : Main.cinema.getFilms()) {
            System.out.println(num + ": " + film.getName() + " Date: " + film.getDateTime());
            num++;
        }
    }

    public static Film addFilm(String name, LocalDateTime dateTime, int numOfTickets) {
        Film film = new Film(name, dateTime, numOfTickets);
        return Main.cinema.addFilm(film);
    }

    public static boolean checkFilms(String name) {
        for (Film film : Main.cinema.getFilms()) {
            if (film.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public static String deleteFilm(int numToDelete) {
        String deletedFilmName = Main.cinema.getFilms().get(numToDelete - 1).getName();

        for (int i = 0; i < Main.cinema.getFilms().get(numToDelete - 1).getTickets().size(); i++) {
            if(!Main.cinema.getFilms().get(numToDelete - 1).getTickets().get(i).isAvailable()){
                Main.cinema.getFilms().get(numToDelete - 1).getTickets().get(i).setAvailable(true);
            }
        }
        Main.cinema.getFilms().remove(numToDelete - 1);
        FileService.updateCinema(Main.cinema);
        return deletedFilmName;
    }

    public static boolean removeUser(String nameToDelete){
        for(int i = 0; i < Main.cinema.getUsers().size(); i++){
            if(Main.cinema.getUsers().get(i).getLogin().equals(nameToDelete)){
                for(int j = 0; j < Main.cinema.getUsers().get(i).getTickets().size(); j++){
                    Main.cinema.getUsers().get(i).getTickets().get(j).setAvailable(true);
                }
                Main.cinema.getUsers().remove(i);

                return true;
            }
        }
        return false;
    }

    public static boolean availableTickets(int filmNum){
        int i = 1;
        boolean check = false;
        for(Ticket ticket: Main.cinema.getFilms().get(filmNum).getTickets()){
            if(ticket.isAvailable()){
                System.out.print(i + " ");
                check = true;
            }
            i++;
        }
        if(!check){
            System.out.println("There are no available tickets for this film");
            return false;
        }
        else{
            System.out.println();
        }
        return true;
    }
}
