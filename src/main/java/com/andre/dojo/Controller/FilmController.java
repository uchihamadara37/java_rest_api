package com.andre.dojo.Controller;

import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.FilmActor;
import com.andre.dojo.dataModel.Inventory;
import com.andre.dojo.dataModel.actorModel;
import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.http.Handler;

import java.util.List;

import static com.andre.dojo.dataModel.Film.getFilmJoinInventory;
import static com.andre.dojo.dataModel.Film.getListFilm;

public class FilmController {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Handler getAllwithInventory = ctx -> {
        List<Film> films = getListFilm().getData();
        for (Film f : films){
            Metadata<List<Inventory>> inventories= new DBUtils<Inventory>().getList(
                    "SELECT * FROM inventory WHERE film_id = "+f.getFilm_id()+";",
                    Inventory.class
            );
            f.setInventory(inventories.getData());
        }

        ctx.status(200).json(gson.toJson(films));
    };

    public static Handler GetFilmWithActor = ctx -> {
        List<Film> films = getListFilm().getData();
        for (Film f : films){
            List<FilmActor> filmActors = new DBUtils<FilmActor>().getList(
                    "SELECT * FROM film_actor WHERE film_id = "+f.getFilm_id()+";",
                    FilmActor.class
            ).getData();

            FilmActor fa = new FilmActor();
            for (FilmActor fas : filmActors){
                actorModel am = new DBUtils<actorModel>().getOne("SELECT * FROM actor WHERE actor_id="+fas.getActor_id()+";", actorModel.class).getData();
                fa.getActor().add(am);
            }
            f.setFilm_actor(fa);
        }

        ctx.status(200).json(gson.toJson(films));
    } ;

    public static Handler getAllFilms = ctx -> {
        ctx.status(200).json(getListFilm());
    };
}

