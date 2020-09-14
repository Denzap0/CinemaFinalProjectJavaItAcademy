package view;

import model.Film;
import model.User;
import service.*;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AdminConsole {

    public static void adminMenu() {

        System.out.println("Hello admin!");


        Scanner sc = new Scanner(System.in);
        boolean check = true;
        while (check) {
            System.out.println("Select an option:");
            System.out.println("1. Add film");
            System.out.println("2. Delete film");
            System.out.println("3. All films");
            System.out.println("4. All users");
            System.out.println("5. Add user");
            System.out.println("6. Delete user by name");
            System.out.println("7. Edit user");
            System.out.println("8. Quit");
            switch (Integer.parseInt(sc.next())) {
                case 1:
                    System.out.println("Add film:");
                    System.out.println("Write name of your film");
                    String filmName;

                    while (true) {

                        filmName = sc.next();
                        sc.nextLine();
                        if (!CinemaService.checkFilms(filmName)) {
                            System.out.println("Film with that name already registered, try again");
                            continue;
                        }
                        System.out.println("Write date and time of your film(yyyy MM dd HH mm)");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");

                        LocalDateTime formattedDateTime = LocalDateTime.parse(sc.nextLine(), formatter);

                        System.out.println("Write number of tickets for your film");
                        int numOfTickets = sc.nextInt();

                        System.out.println("You added film with name " + CinemaService.addFilm(filmName, formattedDateTime, numOfTickets).getName());
                        FileService.updateCinema(Main.cinema);

                        break;
                    }
                    LogService.addLoggedAction("Admin " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin() + " added film " + filmName);
                    break;

                case 2:
                    System.out.println("Delete film:");
                    System.out.println("Write number of film to delete(from all films)");
                    int numToDelete = sc.nextInt();
                    String deleteFilmName = Main.cinema.getFilms().get(numToDelete - 1).getName();
                    System.out.println("You deleted a film " + CinemaService.deleteFilm(numToDelete));
                    LogService.addLoggedAction("Admin " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin() + " deleted " + deleteFilmName);
                    break;
                case 3:
                    System.out.println("All films");
                    for (Film film : Main.cinema.getFilms()) {
                        System.out.println(film.toString());
                    }
                    break;
                case 4:
                    System.out.println("All users:");
                    for (User user : Main.cinema.getUsers()) {
                        System.out.println(user.toString());
                    }
                    break;
                case 5:
                    System.out.println("Write login of user: ");
                    String login = sc.next();
                    System.out.println("Write password of user: ");
                    String password = sc.next();
                    if (UserService.registerUser(login, password)) {
                        System.out.println("You successfully registered user with login " + login);
                        LogService.addLoggedAction("Admin " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                + " added user " + login);
                    } else {
                        System.out.println("User with this login already registered");
                    }

                    break;
                case 6:
                    System.out.println("Enter user name to delete");
                    String nameToDelete = sc.next();

                    if (AdminService.deleteUser(nameToDelete)) {
                        System.out.println("You successfully deleted user with login " + nameToDelete);
                        LogService.addLoggedAction("Admin " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                + " deleted user " + nameToDelete);
                    } else {
                        System.out.println("There is no user with this login");
                    }

                    break;
                case 7:
                    System.out.println("Write user login:");
                    int userNum = UserService.findUserByName(sc.next());
                    if (userNum != -1) {
                        System.out.println(Main.cinema.getUsers().get(userNum).toString());
                        System.out.println("1. Edit login");
                        System.out.println("2. Edit password");

                        switch (sc.nextInt()) {
                            case 1:
                                System.out.println("Write new login for user");
                                String newName = sc.next();
                                UserService.editUserLogin(userNum, newName);
                                System.out.println("User login edited successfully");
                                LogService.addLoggedAction("Admin " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                        + " edited user name to " + newName);
                                break;
                            case 2:
                                System.out.println("Write new password of user");
                                String newPassword = sc.next();
                                try {
                                    UserService.editUserPassword(userNum, UserService.passwordHash(newPassword));
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                }
                                LogService.addLoggedAction("Admin " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                        + " edited password of user " + Main.cinema.getUsers().get(userNum).getLogin() + " to " + newPassword);
                                break;
                        }
                    } else {
                        System.out.println("There are no user with this login");
                    }
                    break;
                case 8:
                    check = false;
                    System.out.println("Good bye admin!");
                    LogService.addLoggedAction("Admin " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                            + " logged out ");
                    break;

                default:
                    System.out.println("Wrong choose");
            }
        }
    }
}
