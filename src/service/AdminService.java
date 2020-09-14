package service;

import model.User;
import model.UserType;
import view.Main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminService {

    public static void addAdmin(String login, String password) {

        Main.cinema.addUser(new User(login, password, UserType.ADMIN));
    }

    public static boolean deleteUser(String nameToDelete){
        return CinemaService.removeUser(nameToDelete);

    }


}
