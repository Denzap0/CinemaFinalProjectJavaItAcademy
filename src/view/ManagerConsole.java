package view;

import model.Ticket;
import service.FilmService;
import service.LogService;
import service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ManagerConsole {

    public static void managerMenu() {
        System.out.println("Hello manager!");
        boolean check = true;
        while (check) {
            System.out.println("1.Edit film");
            System.out.println("2.Buy or return ticket of user");
            System.out.println("3.Quit");
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    System.out.println("Enter film name: ");
                    String filmName = sc.next();
                    int filmNum = FilmService.findFilmNumByName(filmName);
                    if (filmNum == -1) {
                        System.out.println("There is no film with that name");
                    } else {
                        System.out.println("1.Edit name of film");
                        System.out.println("2.Edit date of film");
                        System.out.println("3.Add tickets for film");
                        switch (sc.nextInt()) {
                            case 1:
                                System.out.println("Enter new film name");
                                String newFilmName = sc.next();
                                FilmService.editFilmName(filmNum, newFilmName);
                                System.out.println("Name changed successfully");
                                LogService.addLoggedAction("Manager " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin() + " changed film name to " + newFilmName);
                                break;
                            case 2:
                                System.out.println("Enter date and time to change (yyyy MM dd HH mm)");
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");
                                sc.nextLine();
                                String newDate = sc.nextLine();
                                LocalDateTime formattedDateTime = LocalDateTime.parse(newDate, formatter);
                                FilmService.editFilmDate(filmNum, formattedDateTime);
                                System.out.println("Date changed successfully");
                                LogService.addLoggedAction("Manager " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                        + " changed film " + Main.cinema.getFilms().get(filmNum).getName() + " date to " + formattedDateTime);

                                break;
                            case 3:
                                System.out.println("Enter num of tickets to add: ");
                                int numToAppend = sc.nextInt();
                                FilmService.addTickets(filmNum, numToAppend);
                                LogService.addLoggedAction("Manager " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                        + " added " + numToAppend + " tickets for a film " + Main.cinema.getFilms().get(filmNum).getName());

                                break;
                            default:
                                System.out.println("Wrong choose");
                                break;
                        }
                    }
                    break;
                case 2:
                    System.out.println("Write login of user");
                    String loginForEdit = sc.next();
                    int userNum = UserService.findUserByName(loginForEdit);
                    if(userNum == -1){
                        System.out.println("There is no user with this name");
                    }
                    else{
                        System.out.println(Main.cinema.getUsers().get(userNum).toString());
                        System.out.println("1.Buy ticket for user");
                        System.out.println("2.Return ticket of user");
                        System.out.println("3.Quit");
                        switch (sc.nextInt()){
                            case 1:
                                if(UserService.addTicket(userNum) != null){
                                    System.out.println("You successfully bought a ticket for your user");
                                    LogService.addLoggedAction("Manager " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                            + " added ticket for user " + Main.cinema.getUsers().get(userNum).getLogin() );
                                }else{
                                    System.out.println("There are no tickets for this film");
                                }
                                break;
                            case 2:
                                int i = 1;
                                for(Ticket ticket: UserService.getTicketsByUserNum(userNum)){
                                    System.out.println("Num: " + i + ticket.toString());
                                }
                                System.out.println("Write ticket number");
                                int ticketNum = sc.nextInt();
                                UserService.returnTicket(ticketNum,userNum);
                                LogService.addLoggedAction("Manager " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                        + " returned ticket for user " + Main.cinema.getUsers().get(userNum).getLogin() );
                                break;
                            case 3:
                                System.out.println("Good bye manager!");
                                check = false;
                                LogService.addLoggedAction("Manager " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                        + " logged out");
                        }
                    }

                    break;
                case 3:
                    check = false;
                    System.out.println("Good bye manager");
                    LogService.addLoggedAction("Manager " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                    + " logged out");
                    break;
                default:
                    System.out.println("Wrong choose");
            }
        }
    }
}
