package service;


import model.Cinema;
import model.Ticket;
import model.User;
import model.UserType;
import view.Main;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserService {

    public static int curUserNum = 0;

    public static User loginUser(String login, String password) throws NoSuchAlgorithmException {
        for (User user : Main.cinema.getUsers()) {
            if (user.getLogin().equals(login) && user.getPassword().equals(UserService.passwordHash(password))) {
                return user;
            }
            curUserNum++;
        }
        return null;
    }

    public static Ticket addTicket(int userNum) {
        System.out.println("Write number of film to get ticket");
        Scanner sc = new Scanner(System.in);
        int filmNum = sc.nextInt();
        if(CinemaService.availableTickets(filmNum - 1)){
            System.out.println("Choose your ticket");
            int ticketNum = sc.nextInt();
            Main.cinema.getFilms().get(filmNum - 1).getTickets().get(ticketNum - 1).setAvailable(false);
            Main.cinema.getFilms().get(filmNum - 1).getTickets().get(ticketNum - 1).setOwner(Main.cinema.getUsers().get(userNum));
            Main.cinema.getUsers().get(userNum).getTickets().add(Main.cinema.getFilms().get(filmNum - 1).getTickets().get(ticketNum - 1));
            return Main.cinema.getFilms().get(filmNum - 1).getTickets().get(ticketNum - 1);
        }
        return null;
    }

    public static Ticket returnTicket(int ticketNum, int userNum) {
        Ticket ticket = Main.cinema.getUsers().get(userNum).getTickets().get(ticketNum - 1);
        ticket.setAvailable(true);
        Main.cinema.getUsers().get(userNum).getTickets().remove(ticketNum - 1);
        System.out.println("You successfully returned a ticket");
        return ticket;
    }

    public static void yourTickets(int userNum) {
        System.out.println("Your tickets: ");
        int num = 1;
        for (Ticket ticket : Main.cinema.getUsers().get(userNum).getTickets()) {
            System.out.println("Number: " + num + " " + ticket.toString());
            num++;
        }

    }

    public static boolean registerUser(String login, String password) {
        String hashedPassword = null;
        try {
            hashedPassword = UserService.passwordHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(login, hashedPassword);

        return CinemaService.addUser(user);
    }

    public static void removeRubbishTickets() {
        for (int i = 0; i < Main.cinema.getUsers().get(curUserNum).getTickets().size(); i++) {
            if (Main.cinema.getUsers().get(curUserNum).getTickets().get(i).isAvailable()) {
                Main.cinema.getUsers().get(curUserNum).getTickets().remove(i);
            }
        }
    }

    public static int findUserByName(String loginForFind) {
        for (int i = 0; i < Main.cinema.getUsers().size(); i++) {
            if (Main.cinema.getUsers().get(i).getLogin().equals(loginForFind)) {
                return i;
            }
        }
        return -1;
    }

    public static List<Ticket> getTicketsByUserNum(int userNum) {
        return Main.cinema.getUsers().get(userNum).getTickets();
    }

    public static void editUserLogin(int userNum, String newLogin) {
        Main.cinema.getUsers().get(userNum).setLogin(newLogin);
    }

    public static void editUserPassword(int userNum, String newPassword) {
        String hashedPassword = null;
        try {
            hashedPassword = passwordHash(newPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Main.cinema.getUsers().get(userNum).setPassword(newPassword);
    }

    public static UserType getUserType(int userNum) {
        return Main.cinema.getUsers().get(userNum).getUserType();
    }

    public static String passwordHash(String password) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : bytes) {
            strBuilder.append(b);
        }
        return strBuilder.toString();


    }
}
