package service;

import model.User;
import model.UserType;
import view.Main;

public class ManagerService {

    public static int curManagerNum;

    public static void addManager(String login, String password){
        Main.cinema.getUsers().add(new User(login,password, UserType.MANAGER));

    }


}
