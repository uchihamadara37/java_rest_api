package com.andre.dojo.Controller;

import com.google.gson.Gson;
import io.javalin.http.Handler;

import static com.andre.dojo.dataModel.Film.getFilmJoinInventory;

public class FilmController {
    private static Gson gson = new Gson();

    public static Handler getAllwithInventory = ctx -> {
        ctx.status(200).json(getFilmJoinInventory());
    };
}
