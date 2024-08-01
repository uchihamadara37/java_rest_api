package com.andre.dojo.dataModel;

import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;

import java.sql.Timestamp;
import java.util.List;

public class Country {
    private int country_id;
    private String country;
    private Timestamp last_update;

//    public Country(String country) {
//        this.country = country;
//        this.last_update = new Timestamp(System.currentTimeMillis());
//    }

    public static Metadata<List<City>> getAllCountry() {
        return new DBUtils<City>().getList(
                "SELECT * FROM country LIMIT 50",
                City.class
        );
    }
    public static Metadata<Country> getOneCountry(int id) {
        return new DBUtils<Country>().getOne(
                "SELECT * FROM country WHERE country_id="+id,
                Country.class
        );
    }
    public static Metadata<String> addCountry(Country object) {
        String sql = "INSERT INTO country(country, last_update) VALUES (:country, :last_update)";
        return DBUtils.postInsert(
                sql,
                object
        );
    }

    public static Metadata<String> updateCountry(Country object, int country_id) {
        String sql = "UPDATE country SET country=:country, last_update=:last_update WHERE country_id="+country_id;
        return DBUtils.postInsert(
                sql,
                object
        );
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public int getCountry_id() {
        return country_id;
    }

    public String getCountry() {
        return country;
    }
}
