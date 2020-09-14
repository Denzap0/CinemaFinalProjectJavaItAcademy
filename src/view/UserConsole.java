package view;

import model.Ticket;
import model.User;
import service.CinemaService;
import service.LogService;
import service.ManagerService;
import service.UserService;

import javax.rmi.CORBA.Util;
import java.util.Scanner;

public class UserConsole {

    public static void userMenu() {
        System.out.println("Hello user - " + Main.curUser.getLogin() + "\nUser menu:");
        boolean check = true;
        UserService.removeRubbishTickets();
        while (check) {
            System.out.println("1.All films");
            System.out.println("2.Get ticket by film number");
            System.out.println("3.Return ticket by number");
            System.out.println("4.Your tickets");
            System.out.println("5.Quit");
            Scanner sc = new Scanner(System.in);
            switch (sc.nextShort()){
                case 1:
                    CinemaService.allFilms();

                    break;
                case 2:
                    Ticket ticket = UserService.addTicket(UserService.curUserNum);
                    if(ticket != null){
                        System.out.println("You bought a ticket");
                        LogService.addLoggedAction("User " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin()
                                + "bought a ticket for a film" + ticket.getFilm().getName());
                    }
                    else{
                        System.out.println("There are no tickets for this film");
                    }


                    break;
                case 3:
                    System.out.println("Write number of your ticket to return");
                    int numOfTicket = sc.nextInt();
                    ticket = UserService.returnTicket(numOfTicket,UserService.curUserNum);
                    LogService.addLoggedAction("User " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin() +
                            " canceled ticket for film " + ticket.getFilm().getName());

                    break;
                case 4:
                    UserService.yourTickets(UserService.curUserNum);

                    break;
                case 5:
                    check = false;
                    System.out.println("Good bye");
                    LogService.addLoggedAction("User " + Main.cinema.getUsers().get(UserService.curUserNum).getLogin() + " logged out");
                    break;
                default:
                    System.out.println("Wrong choose");
                    check = true;
            }
        }
    }
}
