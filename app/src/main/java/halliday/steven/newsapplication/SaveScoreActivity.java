package halliday.steven.newsapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.w3c.dom.Text;

public class SaveScoreActivity extends AppCompatActivity {

    int score;
    Bundle extras;
    Patient patient;

    EditText idEntry;
    EditText nameEntry;

    TextView autoScoreInput;
    boolean exists = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);
        idEntry = (EditText)findViewById(R.id.textBoxId);
        nameEntry = (EditText)findViewById(R.id.TextBoxPatientName);
        extras = getIntent().getExtras();
        score = extras.getInt("Score");
        autoScoreInput = (TextView)findViewById(R.id.NewsScore);
        autoScoreInput.setText(String.valueOf(score));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveButtonPress(View view) throws JSONException {
        try {
            int tryInt = Integer.parseInt(idEntry.getText().toString().trim());

            SharedPreferences checkExists = getApplication().getSharedPreferences(String.valueOf(idEntry.getText()).trim(), Context.MODE_PRIVATE);
            if (checkExists.getAll().size() != 0) {
                Toast.makeText(this, "An entry with that ID already exists", Toast.LENGTH_LONG).show();
                exists = true;
                saveData();
            } else {
                saveData();
            }
        }
        catch(Exception e){
            Toast.makeText(this, "ID must be a number", Toast.LENGTH_LONG).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveData() throws JSONException {
        String patId = idEntry.getText().toString().trim();
        String name = nameEntry.getText().toString().trim();
        patient = new Patient(getApplication(), patId, name, score, exists);
        exists = false;
        patient.saveData();

    }
    public void CancelButtonPress(View view){
        finish();
    }
}
/*
    private void storeData(types to store){
        //saving basic information to the device via shared preference
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("file-name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key-name", variable);
        editor.putBoolean("key-name", variable);
        editor.commit();
    }
    private void loadData(){
        //loading basic information from device via shared preference
        SharedPreferences sharedPreferences = this.getApplication().getSharedPreferences("file-name", Context.MODE_PRIVATE);
        var = sharedPreferences.getInt("key-name", default value);
        var = sharedPreferences.getBoolean("key-name", default value);
    }
 */