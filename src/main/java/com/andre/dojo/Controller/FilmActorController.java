package com.andre.dojo.Controller;

import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.FilmActor;
import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import io.javalin.http.Handler;

import java.util.List;

import static com.andre.dojo.dataModel.FilmActor.GetFilm_Actor_and_FilmActor;
import static com.andre.dojo.dataModel.FilmActor.getAllFilmActor;

public class FilmActorController {
    private static Gson gson = new Gson();

    public static Handler getAll_Actor_and_Film = ctx -> {
        List<FilmActor> res = getAllFilmActor().getData();
        for(FilmActor fa : res){
            List<Film> films = new DBUtils<Film>().getList(
                    """
                SELECT 
                f.film_id, 
                f.title, 
                f.description, 
                f.release_year,
                f.rental_duration,
                f.rental_rate
                FROM film f WHERE film_id = """+fa.getFilm_id()+"""
                ; 
                """,
            Film.class).getData();
//            fa.setFilm();
        }
        ctx.status(200).json(GetFilm_Actor_and_FilmActor());
    };
}
