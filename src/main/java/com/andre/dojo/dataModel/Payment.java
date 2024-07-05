package com.andre.dojo.dataModel;

import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;

import static com.andre.dojo.pgConnection.getSql2o;

public class Payment {
    private int payment_id;
    private int customer_id;
    private int staff_id;
    private int rental_id;
    private float amount;
    private Timestamp payment_date;


    public static Sql2o sql2o = getSql2o();
    static Gson gson = new Gson();

    public static String getAllPayment(){
        String sql = "SELECT * FROM payment";

        try (Connection con = sql2o.open()) {
            List<Payment> hasil = con.createQuery(sql).executeAndFetch(Payment.class);
            Metadata<List<Payment>> hasil2 = new Metadata<>("Berhasil", 200, hasil);
            return gson.toJson(hasil2);
        }
        catch (Sql2oException e){
            Metadata<List<Payment>> res = new Metadata<>(e.toString(), 404, null);
            return gson.toJson(res);
        }
    }
}
