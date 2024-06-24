package com.andre.dojo.dataModel;

import com.google.gson.Gson;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;

import static com.andre.dojo.pgConnection.getSql2o;

public class actorModel {
    private int actor_id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;

    public int getActor_id() {
        return actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    @Override
    public String toString(){
        return "id : "+actor_id+" first_name : "+first_name+" last_name : "+last_name+" timestamp : "+last_update+"\n";
    }

    public String getLast_name() {
        return last_name;
    }

    public Timestamp getTimestamp() {
        return last_update;
    }

    public static Sql2o sql2o = getSql2o();
    public static Gson gson = new Gson();

    public static Metadata<List<actorModel>> getAllActors(){
        String sql = "SELECT actor_id, first_name, last_name, last_update FROM actor";

        try (Connection con = sql2o.open()) {
            List<actorModel> hasil = con.createQuery(sql).executeAndFetch(actorModel.class);
            Metadata<List<actorModel>> hasil2 = new Metadata<>("Berhasil", 200, hasil);
            return hasil2;
        }
        catch (Sql2oException e){
            Metadata<List<actorModel>> res = new Metadata<>(e.toString(), 404, null);
            return res;
        }
    }

    public static boolean addActor(String first_name, String last_name){
        Timestamp curr_timestamp = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO public.actor (first_name, last_name, last_update) VALUES (:firstname, :lastname, :curr_timestamp);";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("firstname", first_name)
                    .addParameter("lastname", last_name)
                    .addParameter("curr_timestamp", curr_timestamp)
                    .executeUpdate();
            return true;
        }catch (Sql2oException e){
            System.out.println("gagal : "+e);
            return false;
        }
    }

    public static boolean deleteActor(int id){
        String sql = "DELETE FROM public.actor WHERE actor_id=:id;";

        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();


            return true;


        }catch (Sql2oException e){
            System.out.println("gagal"+e);
            return false;
        }

    }

    public static void resetSequence(){
        try (Connection con2 = sql2o.beginTransaction()){
            Metadata<List<actorModel>> res =  getAllActors();
            List<actorModel> listActors = res.getData();

            List<actorModel> actorMax =  con2.createQuery("SELECT * FROM public.actor ORDER BY actor_id DESC LIMIT 1")
                    .executeAndFetch(actorModel.class);


            int id_sequence = 0;
            boolean ketemu = false;
            for(int i = 0; i < listActors.size(); i++){

                if (listActors.get(i).actor_id != actorMax.getFirst().actor_id){
                    ketemu = false;
                    for (int j = 0; j < listActors.size(); j++) {

                        if (listActors.get(i).actor_id + 1 == listActors.get(j).actor_id){
//                        System.out.println("id0 : "+listActors.get(i).actor_id+" id1 : "+listActors.get(j).actor_id);
                            ketemu = true;
                            id_sequence = listActors.get(j).actor_id;
                            break;
                        }
                    }
//                System.out.println(listActors.get(i).actor_id);
                    if (!ketemu){
                        System.out.println("id aneh ke"+i+" : "+listActors.get(i).actor_id);
                        id_sequence = listActors.get(i).actor_id;
                        break;
                    }
                    if (listActors.get(i).actor_id == 202){
                        System.out.println("Oalah .... apa benar? : "+ketemu);
                    }
                }
            }
            System.out.println("sequence : "+id_sequence);
            System.out.println("data size : "+listActors.size());

            con2.createQuery("SELECT setval('actor_actor_id_seq', :id_max, true);")
                    .addParameter("id_max", id_sequence)
                    .executeScalar();
            con2.commit();
            return;

        }catch (Sql2oException e){
            System.out.println("gagal sql2 : "+e);
            return;
        }
    }

    public static boolean UpdateActor(int id, String first_name, String last_name){
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

        try (Connection con = sql2o.open()){
            con.createQuery("UPDATE public.actor SET first_name = :firstName, last_name=:lastName, last_update = :ts WHERE actor_id = :id;")
                    .addParameter("firstName", first_name)
                    .addParameter("lastName", last_name)
                    .addParameter("id", id)
                    .addParameter("ts", timeStamp)
                    .executeUpdate();
            return true;
        }catch (Sql2oException e){
            System.out.println("gagal update : "+e);
            return false;
        }
    }
}
