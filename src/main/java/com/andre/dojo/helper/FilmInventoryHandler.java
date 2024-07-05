package com.andre.dojo.helper;

import com.andre.dojo.Enum.MPAARating;
import com.andre.dojo.Interface.ResultSetHandler2;
import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.Inventory;
import org.sql2o.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilmInventoryHandler implements ResultSetHandler<Film> {

    @Override
    public Film handle(ResultSet rs) throws SQLException {
        if (rs.next()){
//            f.film_id,
//                    f.title,
//                    f.description,
//                    f.release_year,
//                    f.rental_duration,
//                    f.rental_rate,
//
//                    i.inventory_id,
//                    i.store_id
            Film film = new Film();
            film.setFilm_id(rs.getInt("film_id"));
            film.setTitle(rs.getString("title"));
            film.setDescription(rs.getString("description"));
            film.setRelease_year(rs.getInt("release_year"));
//            film.setLanguage_id(rs.getInt("language_id"));
            film.setRental_duration(rs.getInt("rental_duration"));
            film.setRental_rate(rs.getFloat("rental_rate"));
//            film.setLength(rs.getInt("length"));
//            film.setReplacement_cost(rs.getFloat("replacement_cost"));

            // Untuk MPAARating, asumsikan ini adalah enum
//            String ratingString = rs.getString("rating");
//            film.setRating(ratingString != null ? MPAARating.valueOf(ratingString) : null);

//            film.setLast_update(rs.getTimestamp("last_update"));

            // Untuk special_features, asumsikan ini adalah array string yang disimpan sebagai text
//            String specialFeaturesString = rs.getString("special_features");
//            if (specialFeaturesString != null) {
//                film.setSpecial_features(specialFeaturesString.split(","));
//            } else {
//                film.setSpecial_features(new String[0]);
//            }

            // Untuk TSVector, ini mungkin memerlukan penanganan khusus tergantung pada implementasi Anda
            // Ini adalah contoh sederhana, Anda mungkin perlu menyesuaikannya
//            Object tsvectorObj = rs.getObject("fulltext");
//            if (tsvectorObj != null) {
//                film.setFulltext(new TSVector(tsvectorObj.toString()));
//            }

//            Inventory inventory = new Inventory();
//            inventory.setInventory_id(rs.getInt("inventory_id"));
//            inventory.setFilm_id(rs.getInt("film_id"));
//            inventory.setStore_id(rs.getInt("store_id"));
//            inventory.setLast_update(rs.getTimestamp("last_update"));
//
//            film.getInventory().add(inventory);
            return film;
        }
        return null;
    }
}
