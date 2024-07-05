package com.andre.dojo.helper;

import com.andre.dojo.Interface.ResultSetHandler2;
import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.actorModel;
import com.google.gson.Gson;
import org.sql2o.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import static com.andre.dojo.pgConnection.getSql2o;

public class DBUtils {
    static Gson gson = new Gson();

    public static Sql2o sql2o = getSql2o();

    public static String getList(String query, Class<?> object){
        try (Connection con = sql2o.open()) {
            List<?> hasil = con.createQuery(query).executeAndFetch(object);
            Metadata<List<?>> hasil2 = new Metadata<>("Berhasil", 200, hasil);
            return gson.toJson(hasil2);
        }
        catch (Sql2oException e){
            Metadata<List<actorModel>> res = new Metadata<>("Tidak bisa menampilkan data. "+e.toString(), 404, null);
            return gson.toJson(res);
        }
    }

    public static String getOne(String query, Class<?> object){
        try (Connection con = sql2o.open()) {
            List<?> hasil = con.createQuery(query).executeAndFetch(object);
            Metadata<List<?>> hasil2 = new Metadata<>("Berhasil", 200, hasil);
            return gson.toJson(hasil2);
        }
        catch (Sql2oException e){
            Metadata<List<actorModel>> res = new Metadata<>("Tidak bisa menampilkan data. "+e.toString(), 404, null);
            return gson.toJson(res);
        }
    }

    public static String postInsert(String query){
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .executeUpdate();
            return gson.toJson("berhasil input data");
        }catch (Sql2oException e){
            System.out.println("gagal : "+e);
            return gson.toJson("gagal input data");
        }
    }

    public static String delete(String query){
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .executeUpdate();
            return gson.toJson("berhasil menghapus data");
        }catch (Sql2oException e){
            System.out.println("gagal"+e);
            return gson.toJson("gagal menghapus data");
        }

    }

    public static String update(String query){
        try (Connection con = sql2o.open()){
            con.createQuery(query)
                    .executeUpdate();
            return gson.toJson("berhasil update data");
        }catch (Sql2oException e){
            System.out.println("gagal update : "+e);
            return gson.toJson("gagal update data");
        }
    }

    public static <T> List<T> executeQueryJoin(String query, ResultSetHandler<T> handler, Object... params) {
        try (Connection con = sql2o.open()) {
            System.out.println(query);
            List<T> res = con.createQuery(query)
                .withParams(params)
                    .executeAndFetch(handler);
            System.out.println(res);
            return res;
        }
        catch (Sql2oException e){
            e.printStackTrace();
            System.out.println("gagal ambil mase : "+e);
            return null;
        }
    }


}
