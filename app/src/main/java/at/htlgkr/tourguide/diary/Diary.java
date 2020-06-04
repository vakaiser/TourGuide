package at.htlgkr.tourguide.diary;

import java.time.LocalDate;

public class Diary {

    String country;
    LocalDate date;
    String description;

    public Diary(String place, LocalDate date, String description) {
        this.country = place;
        this.date = date;
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
