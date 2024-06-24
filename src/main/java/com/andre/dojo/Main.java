package com.andre.dojo;

import com.andre.dojo.Controller.ActorController;
import com.andre.dojo.Controller.PaymentController;
import com.andre.dojo.dataModel.Metadata;
import com.andre.dojo.dataModel.actorModel;
import com.google.gson.Gson;
import io.javalin.Javalin;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.andre.dojo.dataModel.Payment.getAllPayment;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) {

        System.out.println(new Timestamp(System.currentTimeMillis()));
        var app = Javalin.create(/*config*/)
                .get("/getActors", ActorController.getAll)
                .post("/addActor", ActorController.insertOne)
                .delete("/deleteActor", ActorController.delete)
                .put("/updateActor", ActorController.update)

                .get("/payment", PaymentController.getAllPayments)
                .start(7070);
    }
}