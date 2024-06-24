package com.andre.dojo;

import org.sql2o.Sql2o;

public class pgConnection {
    private static Sql2o sql2o;

    static{
        sql2o = new Sql2o("jdbc:postgresql://localhost:5432/dvdrental", "andre", "andre");
    }

    public static Sql2o getSql2o() {
        return sql2o;
    }
}
