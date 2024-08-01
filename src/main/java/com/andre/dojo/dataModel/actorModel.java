package com.andre.dojo.dataModel;

import com.andre.dojo.helper.DBUtils;
import com.andre.dojo.helper.Metadata;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String getAllActors(){
        return gson.toJson(new DBUtils<actorModel>().getList("SELECT * FROM actor", actorModel.class));
    }

    public static Metadata<String> addActor(String first_name, String last_name){
        Timestamp curr_timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO public.actor (first_name, last_name, last_update) VALUES ('"+first_name+"', '"+last_name+"', '"+curr_timestamp+"');";
        return DBUtils.postInsert(sql);
    }

    public static Metadata<String> deleteActor(int id){
        String sql = "DELETE FROM public.actor WHERE actor_id="+id+";";
        Metadata<String> hasil = DBUtils.delete(sql);
        resetSequence();
        return hasil;
    }

    public static Metadata<String> UpdateActor(int id, String first_name, String last_name){
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        String query = "UPDATE public.actor SET first_name = "+first_name+", last_name="+last_name+", last_update = "+timeStamp+" WHERE actor_id = "+id+";";
        return DBUtils.update(query);
    }

    public static Metadata<actorModel> getOneActor(int id){
        String query = "SELECT * FROM public.actor WHERE actor_id = "+id+";";
        return new DBUtils<actorModel>().getOne(query, actorModel.class);
    }

    public static void resetSequence(){
        try (Connection con2 = sql2o.beginTransaction()){
            Metadata<List<actorModel>> res =  new DBUtils<actorModel>().getList("SELECT * FROM actor", actorModel.class);
            List<actorModel> listActors = res.getData();

            List<actorModel> actorMax =  con2.createQuery("SELECT * FROM public.actor ORDER BY actor_id DESC LIMIT 1")
                    .executeAndFetch(actorModel.class);

            int id_sequence = 0;
            boolean ketemu = false;
            for(int i = 0; i < listActors.size(); i++){

//                if (listActors.get(i).actor_id != actorMax.getFirst().actor_id){
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
//                }
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

    public static Metadata<List<actorModel>> getAllWithSearch(Map<String, String> listParams, int page){
        String sql = "SELECT * FROM actor WHERE TRUE ";
        String where = "";

        if (listParams.get("firstName") != null || listParams.get("firstName") != ""){
            where += " AND first_name ILIKE '%"+listParams.get("firstName")+"%'";
        }else if (listParams.get("lastName") != null || listParams.get("lastName") != ""){
            where += " AND last_name ILIKE '%"+listParams.get("lastname")+"%'";
        }


        return new DBUtils<actorModel>().getList(
                        sql+where+" ORDER BY actor_id LIMIT 20 OFFSET ("+page+"-1) * 20",
                        actorModel.class
                );

    }


}
