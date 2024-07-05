package com.andre.dojo.dataModel;

import java.sql.Timestamp;

public class FilmCategory {
    private int film_id;
    private int category_id;
    private Timestamp last_update;
    private Film film;

    public void setFilm(Film film) {
        this.film = film;
    }

    public Film getFilm() {
        return film;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public int getFilm_id() {
        return film_id;
    }

    public int getCategory_id() {
        return category_id;
    }
}
