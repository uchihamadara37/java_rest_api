package com.andre.dojo.helper;

import com.andre.dojo.Interface.ResultSetHandler2;
import com.andre.dojo.dataModel.Film;
import com.andre.dojo.dataModel.actorModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.http.MetaData;
import org.simpleflatmapper.sql2o.SfmResultSetHandlerFactoryBuilder;
import org.sql2o.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import static com.andre.dojo.pgConnection.getSql2o;

public class DBUtils<T>{
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Sql2o sql2o = getSql2o();

    public Metadata<List<T>> getList(String query, Class<T> object){
        try (Connection con = sql2o.open()) {
            List<T> hasil = con.createQuery(query).executeAndFetch(object);
            return new Metadata<>("Berhasil", 200, hasil);
        }
        catch (Sql2oException e){
            return new Metadata<>("Tidak bisa menampilkan data. "+e.toString(), 404, null);
        }
    }

    public Metadata<List<T>> getList(String query, Class<T> object, Object... params){
        try (Connection con = sql2o.open()) {
            List<T> hasil = con.createQuery(query)
                    .withParams(params)
                    .executeAndFetch(object);
            return new Metadata<>("Berhasil", 200, hasil);
        }
        catch (Sql2oException e){
            return new Metadata<>("Tidak bisa menampilkan data. "+e.toString(), 404, null);
        }
    }

    public Metadata<T> getOne(String query, Class<T> object){
        try (Connection con = sql2o.open()) {
            List<T> hasil = con.createQuery(query).executeAndFetch(object);
            return new Metadata<>("Berhasil", 200, hasil.getFirst());
        }
        catch (Sql2oException e){
            return new Metadata<>("Tidak bisa menampilkan data. "+e.toString(), 404, null);
        }
    }







    public static Metadata<String> postInsert(String query){
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .executeUpdate();
            return new Metadata<String>("berhasil input", 200, null);
        }catch (Sql2oException e){
            System.out.println("gagal : "+e);
            return new Metadata<String>("gagal input", 400, e.toString());
        }
    }
    public static Metadata<String> postInsert(String query, Object... params){
        try (Connection con = sql2o.open()) {
            con.createQuery(query).withParams(params)
                    .executeUpdate();
            return new Metadata<String>("berhasil input", 200, null);
        }catch (Sql2oException e){
            System.out.println("gagal : "+e);
            return new Metadata<String>("gagal input", 400, e.toString());
        }
    }
    public static <T> Metadata<String> postInsert(String query, T object){
        try (Connection con = sql2o.open()) {
            con.createQuery(query).bind(object)
                    .executeUpdate();
            return new Metadata<String>("berhasil input", 200, null);
        }catch (Sql2oException e){
            System.out.println("gagal : "+e);
            return new Metadata<String>("gagal input", 400, e.toString());
        }
    }




    public static Metadata<String> update(String query){
        try (Connection con = sql2o.open()){
            con.createQuery(query)
                    .executeUpdate();
            return new Metadata<String>("berhasil update", 200, null);
        }catch (Sql2oException e){
            System.out.println("gagal update : "+e);
            return new Metadata<String>("gagal update", 400, e.toString());
        }
    }

    public static <T> Metadata<String> update(String query, T object){
        try (Connection con = sql2o.open()){
            con.createQuery(query).bind(object)
                    .executeUpdate();
            return new Metadata<String>("berhasil update", 200, null);
        }catch (Sql2oException e){
            System.out.println("gagal update : "+e);
            return new Metadata<String>("gagal update", 400, e.toString());
        }
    }

    public static Metadata<String> delete(String query){
        try (Connection con = sql2o.open()) {
            con.createQuery(query)
                    .executeUpdate();
            return new Metadata<String>("berhasil update", 200, null);
        }catch (Sql2oException e){
            System.out.println("gagal"+e);
            return new Metadata<String>("gagal update", 400, e.toString());
        }
    }

    public static <T> Metadata<List<T>> executeQueryJoin(String query, Class<T> handler, Object... params) {
        try (Connection con = sql2o.open()) {
            System.out.println(query);
            List<T> res = con.createQuery(query)
                .withParams(params)
                    .executeAndFetch(handler);
            System.out.println(res);
            return new Metadata<List<T>>("Berhasil ambil mase", 200, res);
        }
        catch (Sql2oException e){
            e.printStackTrace();
            System.out.println("gagal ambil mase : "+e);
            return new Metadata<List<T>>("Gagal ambil", 404, null);
        }
    }


}
