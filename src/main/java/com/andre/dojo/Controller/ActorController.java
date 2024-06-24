package com.andre.dojo.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.javalin.http.Handler;

import static com.andre.dojo.dataModel.actorModel.*;

public class ActorController {
    private static Gson gson = new Gson();
    public static Handler getAll = ctx -> {
        ctx.status(200).json(gson.toJson(getAllActors()));
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
        boolean hasil = addActor(firstName, lastName);
        if (hasil){
            ctx.status(200).json("Berhasil menambahkan");
        }else{
            ctx.status(400).json("Gagal menambahkan");
        }
    };

    public static Handler delete = ctx -> {
        String idStr = ctx.queryParam("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);

                boolean hasil = deleteActor(id);
                if (hasil)  ctx.json("User ID: " + id + " berhasil dihapus" );
                else ctx.json("User ID: " + id + " gagal dihapus" );

            } catch (NumberFormatException e) {
                ctx.status(400).json("ID harus berupa angka");
            }
        } else {
            ctx.status(400).json("ID tidak diberikan");
        }
        resetSequence();
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
            boolean hasil = UpdateActor(id, firstName, lastName);
            if (hasil){
                ctx.status(200).json("Update data berhasil");
            }else{
                ctx.status(400).json("Update data gagal");
            }
        }catch (NumberFormatException e){
            ctx.status(400).json("Error, id tidak valid");
            return;
        }
    };
}
