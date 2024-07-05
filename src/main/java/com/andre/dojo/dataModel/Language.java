package com.andre.dojo.dataModel;

import java.sql.Timestamp;

public class Language {
    private int language_id;
    private String name;
    private Timestamp last_update;
    private Film film;

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public Film getFilm() {
        return film;
    }

    public int getLanguage_id() {
        return language_id;
    }
}
