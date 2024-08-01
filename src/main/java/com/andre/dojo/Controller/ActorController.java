package com.andre.dojo.Controller;

import com.andre.dojo.dataModel.actorModel;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.andre.dojo.dataModel.actorModel.*;

public class ActorController {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Handler getAll = ctx -> {
        ctx.status(200).json(getAllActors());
    };

    public static Handler getOne = ctx -> {
        String idStr = ctx.pathParam("id");
        try {
            int id = Integer.parseInt(idStr);
            Metadata<actorModel> hasil = getOneActor(id);
            ctx.json(gson.toJson(hasil));
        } catch (NumberFormatException e) {
            ctx.status(400).json("ID harus berupa angka");
        }
    };

    public static Handler insertOne = ctx -> {
        String body = ctx.body();
        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);

        String firstName = jsonObject.get("first_name").getAsString();
        String lastName = jsonObject.get("last_name").getAsString();

        if (firstName == null || lastName == null) {
            ctx.status(400).json("Both first_name and last_name are required");
            return;
        }
        ctx.status(200).json(addActor(firstName, lastName));
    };

    public static Handler delete = ctx -> {
        String idStr = ctx.pathParam("id");
        try {
            int id = Integer.parseInt(idStr);

            Metadata<String> hasil = deleteActor(id);
            ctx.status(200).json(hasil);
        } catch (NumberFormatException e) {
            ctx.status(400).json("ID harus berupa angka");
        }
    };

    public static Handler update = ctx -> {
        String body = ctx.body();
        JsonObject jsonObject = gson.fromJson(body, JsonObject.class);

        if (jsonObject.isEmpty() || jsonObject.get("id") == null || jsonObject.get("first_name") == null || jsonObject.get("last_name") == null){
            ctx.status(400).json("Data yang diperlukan untuk update, belum dimasukan");
            return;
        }
        String idStr = jsonObject.get("id").getAsString();
        String firstName = jsonObject.get("first_name").getAsString();
        String lastName = jsonObject.get("last_name").getAsString();

        try {
            int id = Integer.parseInt(idStr);
            Metadata<String> hasil = UpdateActor(id, firstName, lastName);
            ctx.status(200).json(hasil);
        }catch (NumberFormatException e){
            ctx.status(400).json("Error, id tidak valid");
        }
    };

    public static Handler getListActorSearch = ctx -> {
        Map<String, String> listParam = new HashMap<>();

        listParam.put("firstName", ctx.queryParam("firstName"));
        listParam.put("lastName", ctx.queryParam("lastName"));
        String pageStr = ctx.pathParam("page");
        int page = 0;
        try{
            page = Integer.parseInt(pageStr);
        }catch (NumberFormatException e1){
            try{
                page = (int) Float.parseFloat(pageStr);
            }catch (NumberFormatException e2){
                page = 1;
            }
        }

        Metadata<List<actorModel>> res = getAllWithSearch(listParam, page);

        ctx.json(gson.toJson(res));


    };
}
