package com.andre.dojo;

import com.andre.dojo.Controller.*;
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
                .get("/actorSearch/{page}", ActorController.getListActorSearch)

                .get("/payment", PaymentController.getAllPayments)

                .get("/film", FilmController.getAllFilms)
                .get("/film/{id}", FilmController.getAllFilms)

                .get("/FilmAndInventory", FilmController.getAllwithInventory)
                .get("/filmActor", FilmController.GetFilmWithActor)
                .get("/InventoryJoinFilm", InventoryController.getInventoryJoinFilm)
                .get("/FilmActorJoinActorJoinFilm", FilmActorController.getAll_Actor_and_Film)

                .get("/getAllCities", CityController.getAllCity)
                .get("/getCityById/{id}", CityController.getListCityJoinCountry)

                .get("/getList", CityController.getListPageSearch)
                .get("/getList/{page}", CityController.getListPageSearch)
                .post("/insertCity", CityController.addCityJoinCountry)
                .put("/updateCity/{id}", CityController.updateCityJoinCountry)

                .start(7070);
    }
}