package com.andre.dojo.dataModel;

import com.andre.dojo.Enum.MPAARating;
import com.andre.dojo.Interface.ResultSetHandler2;
import com.andre.dojo.helper.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.sql2o.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Film {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private int film_id;
    private String title;
    private String description;
    private int release_year;
    private int language_id;
    private int rental_duration;
    private float rental_rate;
    private int length;
    private float replacement_cost;
    private MPAARating rating;
    private Timestamp last_update;
    private String[] special_features;
    private TSVector fulltext;
    private List<Inventory> inventory = new ArrayList<>();
    private FilmCategory film_category;
    private FilmActor film_actor;
    private Language language;

    public static String getFilmJoinInventory(){
        ParamJoin2Table param = new ParamJoin2Table("film f", "inventory i", "f.film_id", "i.film_id", 20);




        List<Film> res = DBUtils.executeQueryJoin(
//                "SELECT * FROM :p1 JOIN :p2 ON :p3 = :p4 LIMIT :p5;",
                """
                    SELECT 
                    f.film_id, 
                    f.title, 
                    f.description, 
                    f.release_year,
                    f.rental_duration,
                    f.rental_rate
                    
                    FROM film f LEFT JOIN inventory i ON f.film_id = i.film_id
                    WHERE f.film_id = :p1
                    LIMIT 10;
                """,
                Film.class,
//                "film", "inventory", "film.film_id", "inventory.film_id", 10
                100
        );
        return gson.toJson(res);
    }

    public static Metadata<List<Film>> getListFilm(){
        String sql = """
                SELECT 
                f.film_id, 
                f.title, 
                f.description, 
                f.release_year,
                f.rental_duration,
                f.rental_rate
                FROM film f LIMIT 100;
                """;
        return new DBUtils<Film>().getList(sql, Film.class);
    }

    public static Metadata<Film> getOneFilm(int id){
        String sql = """
                SELECT 
                f.film_id, 
                f.title, 
                f.description, 
                f.release_year,
                f.rental_duration,
                f.rental_rate
                FROM film f WHERE film_id = """+id+"""
                ; """;
        return new DBUtils<Film>().getOne(sql, Film.class);
    }






    public void setFilm_actor(FilmActor film_actor) {
        this.film_actor = film_actor;
    }

    public void setFilm_category(FilmCategory film_category) {
        this.film_category = film_category;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public FilmActor getFilm_actor() {
        return film_actor;
    }

    public FilmCategory getFilm_category() {
        return film_category;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    public Language getLanguage() {
        return language;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setRating(MPAARating rating) {
        this.rating = rating;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    public void setRental_duration(int rental_duration) {
        this.rental_duration = rental_duration;
    }

    public void setRental_rate(float rental_rate) {
        this.rental_rate = rental_rate;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setReplacement_cost(float replacement_cost) {
        this.replacement_cost = replacement_cost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFulltext(TSVector fulltext) {
        this.fulltext = fulltext;
    }

    public void setSpecial_features(String[] special_features) {
        this.special_features = special_features;
    }

    public float getRental_rate() {
        return rental_rate;
    }
    public int getFilm_id(){
        return film_id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public int getRelease_year(){
        return release_year;
    }
    public int getLanguage_id(){
        return language_id;
    }
    public int getRental_duration(){
        return rental_duration;
    }
    public int getLength(){
        return length;
    }
    public float getReplacement_cost(){
        return replacement_cost;
    }
    public MPAARating getRating(){
        return rating;
    }
    public Timestamp getLast_update(){
        return last_update;
    }
    public String[] getSpecial_features(){
        return special_features;
    }
    public TSVector getFulltext(){
        return fulltext;
    }

}
