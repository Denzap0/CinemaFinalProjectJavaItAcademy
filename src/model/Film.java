package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Film implements Serializable {

    String name;
    LocalDateTime dateTime;
    List<Ticket> tickets;

    public Film(String name, LocalDateTime dateTime, int NumOfTickets) {
        this.name = name;
        this.dateTime = dateTime;
        this.tickets = new ArrayList<>();
        for(int i = 0; i < NumOfTickets; i++){
            tickets.add(new Ticket(i + 1, this));
        }
    }

    public void addTickets(int numToAppend){
        for(int i = 0; i < numToAppend;i++){
            tickets.add(new Ticket(tickets.size() + 1 + i, this));
        }
    }

    @Override
    public String toString() {
        return this.name + ", Date " + dateTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
