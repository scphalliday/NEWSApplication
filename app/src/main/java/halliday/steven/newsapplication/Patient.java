package halliday.steven.newsapplication;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Patient {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    boolean exists;
    Application app;
    String id;
    String name;
    int newScore;
    ArrayList<String> scores = new ArrayList<>();

    /*
    int respiratoryRateResult = -1;
    int oxygenSaturationResult = -1;
    boolean oxygen = false;
    int temperatureResult = -1;
    int heartRateResult = -1;
    int systolicBPResult = -1;
    int conciousnessResult = -1;
    boolean redScore = false;
     */
    public Patient(Application app, String id, String name, int score, boolean exists) throws JSONException {
        this.app = app;
        this.id = id;
        this.name = name;
        newScore = score;
        this.exists = exists;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveData() throws JSONException {
        /*
        a check prior to this will ensure data is not overwritten unknowingly
         */
        SharedPreferences sharedPreferences = app.getSharedPreferences(String.valueOf(id), Context.MODE_PRIVATE);
        /*
        load scores from file ID, add them to scores array before continuing.
         */
        Map<String, ?> data = (Map<String, ?>) sharedPreferences.getAll();
        for(Map.Entry<String, ?> entry : data.entrySet()){
            if (entry.getKey().equals("Scores")){
                JSONArray jsonArray = new JSONArray(entry.getValue().toString());
                for(int i = 0 ; i < jsonArray.length(); i++){
                    scores.add(jsonArray.getString(i));
                }
            }
        }
        /*
        once scores array is current, add new score, then convert to JSONArray for saving.
         */
        //app.getApplicationContext().deleteDatabase("Patients.db");
        LocalDateTime time = LocalDateTime.now();
        scores.add("Score: " + newScore + ". Recorded on: " + time.format(formatter));
        ArrayList<String> scoreBreak = new ArrayList<>();
        for(int i = 0 ; i < scores.size(); i++){
            scoreBreak.add("\"" + scores.get(i) + "\"");
        }
        String toSave =  "[" + String.join(",", scoreBreak) + "]";
        Toast.makeText(app, this.name + " " + toSave + " saved to the file: " + id, Toast.LENGTH_LONG).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", this.name);
        editor.putString("Scores", toSave);
        editor.apply();
        DB.dbhelp dbhelper = new DB.dbhelp(app.getApplicationContext());
        LocalDateTime now = LocalDateTime.now();
        SQLiteDatabase dataBase = dbhelper.getWritableDatabase();
        if(!exists) {
            ContentValues values = new ContentValues();
            values.put(DB.db.id, id);
            values.put(DB.db.columnName, name);
            values.put(DB.db.columnScore, newScore);
            values.put(DB.db.columnTime, now.format(formatter));
            long newRow = dataBase.insert(DB.db.tableName, null, values);
        }
        else{
            ContentValues values = new ContentValues();
            values.put(DB.db.id, id);
            values.put(DB.db.columnName, name);
            values.put(DB.db.columnScore, newScore);
            values.put(DB.db.columnTime, now.format(formatter));
            long newRow = dataBase.replaceOrThrow(DB.db.tableName, DB.db.id, values);
        }
    }
    private void loadData() throws JSONException {
        /*
        loading data is for the "view past scores" section from the main menu
        probably will have an array return type
        TODO
         */
        SharedPreferences sharedPreferences = app.getSharedPreferences(String.valueOf(id), Context.MODE_PRIVATE);
        Map<String, ?> data = (Map<String, ?>) sharedPreferences.getAll();
        for(Map.Entry<String, ?> entry : data.entrySet()){
            if(entry.getKey().equals("Name")){
                this.name = String.valueOf(entry.getValue());
            }
            else if (entry.getKey().equals("Scores")){
                System.out.println(entry.getValue());
                JSONArray jsonArray = new JSONArray(entry.getValue().toString());
                for(int i = 0 ; i < jsonArray.length(); i++){
                    scores.add(jsonArray.getString(i));
                }
            }
        }
    }

    /*
    public void saveOrderedCollection(Collection collection, String key){
    JSONArray jsonArray = new JSONArray(collection);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(key, jsonArray.toString());
    editor.commit();
    }

    public Collection (String loadOrderedCollectionkey){
    ArrayList arrayList = new ArrayList;
    SharedPreferences.Editor editor = sharedPreferences.edit();
    JSONArray jsonArray = new JSONArray(editor.getString(key, "[]"));
    for (int i = 0; i < jsonArray.length(); i++) {
        arrayList.put(jsonArray.get(i));
    }
    return arrayList;
    }
     */
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
    public void addScore(int score){
        scores.add(String.valueOf(score));
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
