package com.andre.dojo;

import com.andre.dojo.Controller.ActorController;
import com.andre.dojo.Controller.FilmActorController;
import com.andre.dojo.Controller.FilmController;
import com.andre.dojo.Controller.PaymentController;
import io.javalin.Javalin;

import java.sql.Timestamp;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {

        System.out.println(new Timestamp(System.currentTimeMillis()));
        var app = Javalin.create(/*config*/)
                .get("/getActors", ActorController.getAll)
                .get("/getOne/{id}", ActorController.getOne)
                .post("/addActor", ActorController.insertOne)
                .delete("/deleteActor/{id}", ActorController.delete)
                .put("/updateActor", ActorController.update)

                .get("/payment", PaymentController.getAllPayments)

                .get("/film", FilmController.getAllFilms)
                .get("/film/{id}", FilmController.getAllFilms)

                .get("/FilmAndInventory", FilmController.getAllwithInventory)
                .get("/filmActor", FilmController.GetFilmWithActor)
                .start(7070);
    }
}