package com.andre.dojo.dataModel;

import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.sql2o.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FilmActor {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private int film_id;
    private int actor_id;
    private Timestamp last_update;
    private List<actorModel> actors = new ArrayList<>();
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
        Metadata<List<FilmActor>> res = DBUtils.executeQueryJoin(
                query,
                FilmActor.class,
                10

        );
        return gson.toJson(res);
    }
    //             _______     _____
    //            /       |   /
    //           /        |  /
    //          /         | /
    //         /          |/
    //        /     /|    |

    public static Metadata<List<FilmActor>> getAllFilmActor(){
        String sql = "SELECT * FROM film_actor LIMIT 30;";
        return new DBUtils<FilmActor>().getList(sql, FilmActor.class);
    }
    public static Metadata<FilmActor> getOneFilmActor(int id){
        String sql = "SELECT * FROM film_actor WHERE inventory_id="+id+";";
        return new DBUtils<FilmActor>().getOne(sql, FilmActor.class);
    }
    public static Metadata<List<FilmActor>> getListJoin(){
        String sql = """
        SELECT 
        fa.film_id,
        fa.actor_id,
        fa.last_update
        
        FROM film_actor fa
        JOIN actor a ON fa.actor_id = a.actor_id
        LIMIT :p1;
        """;
        return new DBUtils<FilmActor>().getList(sql, FilmActor.class, 20);
    }


    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public void setActor(actorModel actor) {
        this.actor = actor;
    }

    public actorModel getActor() {
        return actor;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setActors(List<actorModel> actor) {
        this.actors = actor;
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

    public List<actorModel> getActors() {
        return actors;
    }
}
