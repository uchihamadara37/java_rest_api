package com.andre.dojo.dataModel;

import com.andre.dojo.helper.DBUtils;
import org.sql2o.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class FilmActor {
    private int film_id;
    private int actor_id;
    private Timestamp last_update;
    private actorModel actor;
    private Film film;

    public static String GetFilm_Actor_and_FilmActor(){
        String query = """
                SELECT
                f.film_id,
                f.title,
                f.description,
                f.release_year,
                f.rating,
                
                fa.last_update,
                
                a.actor_id,
                a.first_name,
                a.last_name,
                a.last_update_actor
                FROM film_actor fa 
                JOIN actor a ON fa.actor_id = a.actor_id
                JOIN film f ON f.film_id = fa.film_id
                LIMIT :p1
        """;
        List<FilmActor> res = DBUtils.executeQueryJoin(
                query,

        )
    }

    static class FA_Film_Actor_Handler implements ResultSetHandler<FilmActor>{
        @Override
        public FilmActor handle(ResultSet rs) throws SQLException {
            if (rs.next()){
                FilmActor filmActor = new FilmActor();

            };
        }
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setActor(actorModel actor) {
        this.actor = actor;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public int getFilm_id() {
        return film_id;
    }

    public Film getFilm() {
        return film;
    }

    public int getActor_id() {
        return actor_id;
    }

    public actorModel getActor() {
        return actor;
    }
}
