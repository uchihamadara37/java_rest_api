package com.andre.dojo.dataModel;

import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class City {
    private int city_id;
    private String city;
    private int country_id;
    private Timestamp last_update;

    private Country country;

//    public City(String city, int country_id) {
//        this.city = city;
//        this.country_id = country_id;
//        this.last_update = new Timestamp(System.currentTimeMillis());
//    }

    public static Metadata<List<City>> getAllCities() {
        return new DBUtils<City>().getList(
                "SELECT * FROM city ORDER BY city_id DESC LIMIT 30",
                City.class
        );
    }
    public static Metadata<List<City>> getAllPageSearch(Map<String, String> keySearch, int page) {
        String sql = """
            SELECT 
            a.city_id, 
            a.city, 
            a.country_id, 
            a.last_update
            
            FROM city a 
            JOIN country b ON a.country_id = b.country_id
            WHERE TRUE
        """;
        String where = "";
        if (keySearch.get("city") != null){
            where += " AND a.city ILIKE '%"+keySearch.get("city")+"%' ";
        }
        if (keySearch.get("country") != null ){
            where += " AND b.country ILIKE '%"+keySearch.get("country")+"%' ";
        }
        System.out.println("okok");
        return new DBUtils<City>().getList(
                sql + where+" ORDER BY city_id ASC LIMIT 20 OFFSET ("+page+" - 1) * 20",
                City.class
        );
    }
    public static Metadata<City> getOneCity(int id) {
        return new DBUtils<City>().getOne(
                "SELECT * FROM city WHERE city_id="+id,
                City.class
        );
    }
    public static Metadata<String> addCity(City object) {
        String sql = "INSERT INTO city (city, country_id, last_update) VALUES (:city, :country_id, :last_update)";
        return DBUtils.postInsert(
            sql,
            object
        );
    }

    public static Metadata<String> updateCity(City object, int city_id) {
        String sql = "UPDATE city SET city=:city, country_id=:country_id, last_update=:last_update WHERE city_id="+city_id;
        return DBUtils.postInsert(
                sql,
                object
        );
    }


    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public Timestamp getLast_update() {
        return last_update;
    }

    public int getCountry_id() {
        return country_id;
    }

    public Country getCountry() {
        return country;
    }

    public int getCity_id() {
        return city_id;
    }
}
