package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private String login;
    private String password;
    private UserType userType;
    private List<Ticket> tickets;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.userType = UserType.REGULAR;
        tickets = new ArrayList<>();
    }

    public User(String login, String password, UserType userType) {
        this.login = login;
        this.password = password;
        this.userType = userType;
        if(this.userType == UserType.REGULAR) {
            tickets = new ArrayList<>();
        }
        else{
            tickets = null;
        }
    }

    public Ticket addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        return ticket;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.getLogin()) && password.equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, userType, tickets);
    }

    @Override
    public String toString() {
        if(this.userType == UserType.REGULAR){
            return "User: \nLogin: " + this.login + "\nUser type: " + this.userType
                    + "\nTickets: " + this.tickets.toString();
        }
        else{
            return "User: \nLogin: " + this.login + "\nUser type: " + this.userType;
        }
    }
}
