package com.andre.dojo.Controller;

import com.andre.dojo.dataModel.City;
import com.andre.dojo.dataModel.Country;
import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.http.Handler;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.andre.dojo.dataModel.City.*;
import static com.andre.dojo.dataModel.Country.*;

public class CityController {
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Handler getAllCity = ctx -> {
        ctx.status(200).json(getAllCities());
    };

    public static Handler getListPageSearch = ctx -> {
        int page = 0;
        Map<String, String> pathParams = ctx.pathParamMap();
        if (pathParams.containsKey("page")) {
            String idStr = ctx.pathParam("page");
            try{
                page = Integer.parseInt(idStr);
            }catch (NumberFormatException e1){
                try {
                    page = (int) Double.parseDouble(idStr);
                }catch (NumberFormatException e2){
                    page = 1;
                }
            }
        }else{
            page = 1;
        }

        Map<String, String> mapSearch = new HashMap<>();
        mapSearch.put("city", ctx.queryParam("city"));
        mapSearch.put("country", ctx.queryParam("country"));
        System.out.println("Page : " +page);

        Metadata<List<City>> res = getAllPageSearch(mapSearch, page);
        for(City city : res.getData()){
            Country c = getOneCountry(city.getCountry_id()).getData();
            city.setCountry(c);
        }
        ctx.status(200).json(gson.toJson(res));

    };

    public static Handler getListCityJoinCountry = ctx -> {
        int id = 0;
        Map<String, String> pathParams = ctx.pathParamMap();
        if (pathParams.containsKey("id")) {
            String idStr = ctx.pathParam("id");
            try{
                id = Integer.parseInt(idStr);
            }catch (NumberFormatException e1){
                try {
                    id = (int) Double.parseDouble(idStr);
                }catch (NumberFormatException e2){
                    id = 0;
                }
            }
        }else{
            id = 0;
        }

        if (id == 0){
            Metadata<List<City>> listCity = getAllCities();
            for(City city : listCity.getData()){
                Country c = getOneCountry(city.getCountry_id()).getData();
                city.setCountry(c);
            }
            ctx.status(200).json(gson.toJson(listCity));
        }else{
            Metadata<City> aCity = getOneCity(id);

            Country c = getOneCountry(aCity.getData().getCountry_id()).getData();
            aCity.getData().setCountry(c);

            ctx.status(200).json(gson.toJson(aCity));
        }
    };

    public static Handler addCityJoinCountry = ctx -> {
        String body = ctx.body();
        JsonObject json = JsonParser.parseString(ctx.body()).getAsJsonObject();
//        JsonObject json = gson.fromJson(body, JsonObject.class);


        if (json.has("city")){
            String city = json.get("city").getAsString();
            if (city == ""){
                System.out.println("city tidak nambah");
                ctx.status(400).json("data yang dibutuhkan tidak ada");

            }else{
                if (json.has("country")) {
                    String country = json.get("country").getAsString();
                    if (country == ""){
                        System.out.println("country tidak nambah");
                        ctx.status(400).json("data yang dibutuhkan tidak ada");
                    }
                    System.out.println("country tidak nambah");
                    try{
                        int country_id = json.get("country_id").getAsInt();
                        Country ctr = Country.getOneCountry(country_id).getData();
                        if (ctr == null){
                            ctx.status(400).json("country_id tidak sesuai");
                        }else{
                            City cityObj = new City();
                            cityObj.setCountry_id(ctr.getCountry_id());
                            cityObj.setCity(city);
                            cityObj.setLast_update(new Timestamp(System.currentTimeMillis()));
                            Metadata<String> res2 = addCity(
                                    cityObj
                            );
                            ctx.status(200).json(gson.toJson(res2));
                        }
                    }catch (NumberFormatException e1){
                        ctx.status(400).json("country_id atau country tidak sesuai");
                    }

                }
            }

        }else {
            System.out.println("city tidak nambah");
            ctx.status(400).json("data yang dibutuhkan tidak ada");
        }

        // ketika country ditambahkan
        Country cObj = new Country();
        cObj.setLast_update(new Timestamp(System.currentTimeMillis()));
//        cObj.setCountry(country);
        // memasukan country terlebih dahulu
        Metadata<String> res = addCountry(
                cObj
        );

        if (res.getStatus() == 200){
            // lanjut mengambil sequence atau id max dengan lastval()
            Country baru = new DBUtils<Country>().getOne("SELECT * FROM country ORDER By country_id DESC LIMIT 1", Country.class).getData();

            System.out.println(gson.toJson(baru));

            // mulai menginput city
            City cityObj = new City();
            cityObj.setCountry_id(baru.getCountry_id());
//            cityObj.setCity(city);
            cityObj.setLast_update(new Timestamp(System.currentTimeMillis()));
            Metadata<String> res2 = addCity(
                    cityObj
            );
            ctx.status(200).json(gson.toJson(res2));
        }else{
            ctx.status(400).json(gson.toJson(res));

        }
    };

    public static Handler updateCityJoinCountry = ctx -> {
        String body = ctx.body();
        JsonObject json = gson.fromJson(body, JsonObject.class);
        String idStr = ctx.pathParam("id");

        int id = 0;
        try{
            id = Integer.parseInt(idStr);
        }catch (NumberFormatException e1){
            try {
                id = (int) Double.parseDouble(idStr);
            }catch (NumberFormatException e2){
                id = 0;
            }
        }

        String city = json.get("city").getAsString();
        String country = json.get("country").getAsString();
        int country_id;
        try {
            country_id = json.get("country_id").getAsInt();
        }catch (NumberFormatException e){
            country_id = 0;
        }

        if (country == null || city == null || id == 0 || country_id == 0) {
            ctx.status(400).json("data yang dibutuhkan tidak sesuai");
        }

        // memasukan country terlebih dahulu
        Country counryObj = new Country();
        counryObj.setCountry(country);
        counryObj.setLast_update(new Timestamp(System.currentTimeMillis()));
        Metadata<String> res = updateCountry(
                counryObj,
                country_id
        );

        if (res.getStatus() == 200){
            // mulai mengupdate city
            City cityObj = new City();
            cityObj.setCity(city);
            cityObj.setCountry_id(country_id);
            cityObj.setLast_update(new Timestamp(System.currentTimeMillis()));
            Metadata<String> res2 = updateCity(
                    cityObj,
                    id
            );
            ctx.status(200).json(gson.toJson(res2));
        }else{
            ctx.status(400).json(gson.toJson(res));

        }
    };
}
