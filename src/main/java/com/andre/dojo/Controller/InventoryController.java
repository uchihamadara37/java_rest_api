package com.andre.dojo.Controller;

import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.Inventory;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.javalin.http.Handler;

import java.util.List;

import static com.andre.dojo.dataModel.Inventory.getAllInventory;
import static com.andre.dojo.dataModel.Film.getOneFilm;


public class InventoryController {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Handler getInventoryJoinFilm = ctx -> {

        Metadata<List<Inventory>> res = getAllInventory();
        int in = 1;
        for (Inventory i : res.getData()){
            Film film = getOneFilm(i.getFilm_id()).getData();
            i.setFilm(film);

        }
        ctx.status(200).json(gson.toJson(res));
    };
}
