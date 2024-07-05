package com.andre.dojo.helper;

import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.Inventory;
import com.google.gson.Gson;

public class Mapper2Table<T, U>{
    private  T Parent;
    private  U Anak;
    public Mapper2Table(T Parent, U Anak) {
        this.Parent = Parent;
        this.Anak = Anak;
    }

    public T getData() {
        if (Parent instanceof Film && Anak instanceof Inventory){
            ((Film) Parent).getInventory().add((Inventory) Anak);
            return Parent;
        }else{

            // bablas
            return Parent;
        }
    }

    @Override
    public String toString(){
        Gson gson = new Gson();
        if (Parent instanceof Film && Anak instanceof Inventory){
            ((Film) Parent).getInventory().add((Inventory) Anak);
            return gson.toJson(Parent);
        }else{

            // bablas
            return gson.toJson(Parent);
        }
    }


}
