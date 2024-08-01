package com.andre.dojo.Controller;

import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.actorModel;
import com.andre.dojo.dataModel.FilmActor;
import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.http.Handler;

import java.util.List;

import static com.andre.dojo.dataModel.Film.getOneFilm;
import static com.andre.dojo.dataModel.FilmActor.*;
import static com.andre.dojo.dataModel.actorModel.getOneActor;

public class FilmActorController {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Handler getAll_Actor_and_Film = ctx -> {
        Metadata<List<FilmActor>> res = getListJoin();
        System.out.println(gson.toJson(res));

        for(FilmActor fa : res.getData()){
            Film film = getOneFilm(fa.getFilm_id()).getData();
            fa.setFilm(film);
            List<FilmActor> fa2s = new DBUtils<FilmActor>().getList(
                    "SELECT * FROM film_actor WHERE film_id=:p1",
                    FilmActor.class,
                    film.getFilm_id()
            ).getData();
            for (FilmActor fa3 : fa2s){
                actorModel act = actorModel.getOneActor(fa3.getActor_id()).getData();
                fa.getActors().add(act);
            }
//            actorModel am = getOneActor(fa.getActor_id()).getData();
//            fa.setActor(am);
        }


        ctx.status(200).json(gson.toJson(res));
    };
}
