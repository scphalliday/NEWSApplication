package halliday.steven.newsapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewPastScoresActivity extends AppCompatActivity {

    TextView nameView;
    TextView scoreView;
    EditText idEntry;
    TextView Lane1;
    TextView Lane2;
    TextView Lane3;
    TextView Lane4;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_scores);
        scoreView = (TextView) findViewById(R.id.scoreView);
        nameView = (TextView)findViewById(R.id.patNameView);
        idEntry = (EditText) findViewById(R.id.idTextBox);

        Lane1 = (TextView)findViewById(R.id.lane1);
        Lane2 = (TextView)findViewById(R.id.lane2);
        Lane3 = (TextView)findViewById(R.id.lane3);
        Lane4 = (TextView)findViewById(R.id.lane4);

        DB.dbhelp dbhelper = new DB.dbhelp(getApplicationContext());
        SQLiteDatabase dataBase = dbhelper.getReadableDatabase();
        String sortOrder = DB.db.columnTime + " COLLATE NOCASE DESC";
        Cursor cursor = dataBase.query(DB.db.tableName, null,null,null,null,null,sortOrder);
        List items = new ArrayList<>();
        while(cursor.moveToNext()){
            List<String> param = new ArrayList<>();
            param.add(cursor.getString(cursor.getColumnIndexOrThrow(DB.db.id)));
            param.add(cursor.getString(cursor.getColumnIndexOrThrow(DB.db.columnName)));
            param.add(cursor.getString(cursor.getColumnIndexOrThrow(DB.db.columnScore)));
            param.add(cursor.getString(cursor.getColumnIndexOrThrow(DB.db.columnTime)));
            items.add(param);
        }
        cursor.close();
        for(int i = 0 ; i < items.size(); i++){
            List<String> temp = (List<String>) items.get(i);
            Lane1.append(temp.get(0) + "\n");
            Lane2.append(temp.get(1) + "\n");
            Lane3.append(temp.get(2) + "\n");
            Lane4.append(temp.get(3) + "\n");
        }
    }

    public void viewPast(View view) throws JSONException {

        try {
            int tryInt = Integer.parseInt(idEntry.getText().toString().trim());

            SharedPreferences sharedPreferences = getSharedPreferences(idEntry.getText().toString().trim(), Context.MODE_PRIVATE);
            if (sharedPreferences.getAll().size() == 0) {
                Toast.makeText(this, "No patient exists on file with that ID", Toast.LENGTH_LONG).show();
            } else {
                scoreView.setText("");
                Map<String, ?> data = (Map<String, ?>) sharedPreferences.getAll();
                for (Map.Entry<String, ?> entry : data.entrySet()) {
                    if (entry.getKey().equals("Scores")) {
                        JSONArray jsonArray = new JSONArray(entry.getValue().toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            scoreView.append(" - " + jsonArray.getString(i) + "\n");
                        }
                    } else if (entry.getKey().equals("Name")) {
                        nameView.setText("Patient " + entry.getValue().toString() + "'s recent scores");
                    }
                }
            }
        }
        catch(Exception e){
            Toast.makeText(this, "ID must be a number.", Toast.LENGTH_LONG).show();
        }

    }
}