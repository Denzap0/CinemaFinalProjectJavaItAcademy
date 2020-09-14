package model;

import java.io.Serializable;
import java.util.UUID;

public class Ticket implements Serializable {

  private int id;
  private boolean isAvailable;
  private Film film;
  private User owner;

  public Ticket(int id, Film film){
    this.id = id;
    this.isAvailable = true;
    this.film = film;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public Film getFilm() {
    return film;
  }

  public void setFilm(Film film) {
    this.film = film;
  }

  @Override
  public String toString() {
    return "Place: " + id + " Film name: " + film.getName() + " Date: " + film.getDateTime();
  }
}
