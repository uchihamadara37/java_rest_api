package com.andre.dojo.dataModel;

import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;

import java.sql.Timestamp;
import java.util.List;

public class Inventory {
    private int inventory_id;
    private int film_id;
    private int store_id;
    private Timestamp last_update;

    public static Metadata<List<Inventory>> getAllInventory(){
        String sql = "SELECT * FROM inventory;";
        return new DBUtils<Inventory>().getList(sql, Inventory.class);
    }
    public static Metadata<Inventory> getOneInventory(int id){
        String sql = "SELECT * FROM inventory WHERE inventory_id="+id+";";
        return new DBUtils<Inventory>().getOne(sql, Inventory.class);
    }

    public void setLast_update(Timestamp last_update) {
        this.last_update = last_update;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getFilm_id() {
        return film_id;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public Timestamp getLast_update() {
        return last_update;
    }
}
