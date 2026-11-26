package pl.merito.project.model;

import java.io.Serializable;

// Główna encja projektu opisująca pojedynczą obserwację nocnego nieba.
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String title;
    private final String place;
    private final int year;

    public Observation(String title, String place, int year) {
        this.title = title;
        this.place = place;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getPlace() {
        return place;
    }

    public int getYear() {
        return year;
    }
}
