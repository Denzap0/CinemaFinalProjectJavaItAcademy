package view;

import model.Cinema;
import model.User;
import model.UserType;
import service.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    public static Cinema cinema;
    public static File file = new File("src/Files/CinemaBase.txt");
    public static User curUser;

    public static void main(String[] args) {

        if (file.length() == 0) {
            try (ObjectOutputStream oOut = new ObjectOutputStream(new FileOutputStream(file))) {
                cinema = new Cinema();
                AdminService.addAdmin("1", UserService.passwordHash("1"));
                ManagerService.addManager("2",UserService.passwordHash("2"));
                oOut.writeObject(cinema);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }else {
            try (ObjectInputStream oIn = new ObjectInputStream(new FileInputStream(file))) {
                cinema = (Cinema) oIn.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



        System.out.println("Hello user!");
        System.out.println("Enter 1 for login, 2 for registration:");

        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextInt()) {
            case 1: {
                loginMenu();
                break;
            }
            case 2: {
                registerMenu();
                break;
            }
            default:
                System.out.println("Typo!");
        }
        FileService.updateCinema(cinema);
    }

    public static void loginMenu() {
        System.out.println("Login menu");
        while (true) {
            System.out.println("Login:");
            Scanner sc = new Scanner(System.in);
            String login = sc.next();
            System.out.println("Password:");
            String password = sc.next();
            try {
                curUser = UserService.loginUser(login, password);
            }catch (NoSuchAlgorithmException e){
                e.printStackTrace();
            }
            if (curUser != null) {
                if (curUser.getUserType() == UserType.REGULAR) {
                    LogService.addLoggedAction("User " + curUser.getLogin() + " logged in");
                    UserConsole.userMenu();

                } else if (curUser.getUserType() == UserType.ADMIN) {
                    LogService.addLoggedAction("Admin " + curUser.getLogin() + " logged in");
                    AdminConsole.adminMenu();

                } else {
                    LogService.addLoggedAction("Manager " + curUser.getLogin() + " logged in");
                    ManagerConsole.managerMenu();

                }

                break;
            } else {
                System.out.println("Login or password is invalid, try again");
            }

        }
        // check is admin type then to AdminConsole static method
    }

    public static void registerMenu() {
        System.out.println("Register menu:");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Login");
            String login = sc.nextLine();
            System.out.println("Password");
            String password = sc.nextLine();
            if (UserService.registerUser(login, password)) {
                LogService.addLoggedAction("User " + login + " registered");
                break;
            } else {
                System.out.println("User with this login already registered, try again");
            }
        }

        loginMenu();
    }

}
