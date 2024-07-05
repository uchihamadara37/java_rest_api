package com.andre.dojo.helper;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class ParamJoin2Table {
    public static String query;
    private String table1;
    private String table2;
    private String comparison1;
    private String comparison2;
    private Integer limit;

    public ParamJoin2Table(String table1, String table2, String comparison1, String comparison2, int limit) {
        this.table1 = table1;
        this.table2 = table2;
        this.comparison1 = comparison1;
        this.comparison2 = comparison2;
        this.limit = limit;
    }
    public Map<String, String> generateMapParams(){
        Map<String, String> param = new HashMap<>();
        param.put("table1", table1);
        param.put("table2", table2);
        param.put("comparison1", comparison1);
        param.put("comparison2", comparison2);
        param.put("limit", limit.toString());
        return param;
    }

    public static String generateQuery() {
        query = "SELECT * FROM :p1 JOIN :p2 ON :p3 = :p4 LIMIT :p5;";
        return query;
    }

}
