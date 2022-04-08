package halliday.steven.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DataSelection extends AppCompatActivity {

    boolean debug = false;

    Bundle extras = new Bundle();
    String setSelection = "";
    int responseResult = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        if(extras != null){
            setSelection = extras.getString("Set");
            if(debug) {
                Toast.makeText(this, extras.getString("Set"), Toast.LENGTH_SHORT).show();
            }
            switch(setSelection) {
                case "RespiratoryRate":
                    setContentView(R.layout.respiratory_data_selection);
                    break;
                case "Temperature":
                    setContentView(R.layout.temperature_data_selection);
                    break;
                case "HeartRate":
                    setContentView(R.layout.heartrate_data_selection);
                    break;
                case "SystolicBP":
                    setContentView(R.layout.systolicbp_data_selection);
                    break;
                case "Consciousness":
                    setContentView(R.layout.consciousness_data_selection);
                    break;
                case "Oxygen":
                    setContentView(R.layout.oxygen_data_selection);
                default:
                    break;
            }
        }

    }
    public void DataButtonPress(View view){
        switch(view.getId()) {
            /*
            START OF RESPIRATORY BUTTON HANDLING
             */
            case (R.id.respButton0):
                responseResult = 0;
                finish();
                break;
            case (R.id.respButton1):
                responseResult = 1;
                finish();
                break;
            case (R.id.respButton2):
                responseResult = 2;
                finish();
                break;
            case (R.id.respButton3):
                responseResult = 3;
                finish();
                break;
            case (R.id.respButton4):
                responseResult = 4;
                finish();
                break;/*
            END OF RESPIRATORY BUTTON HANDLING

            START OF TEMPERATURE BUTTON HANDLING
             */
            case (R.id.tempButton0):
                responseResult = 0;
                finish();
                break;
            case (R.id.tempButton1):
                responseResult = 1;
                finish();
                break;
            case (R.id.tempButton2):
                responseResult = 2;
                finish();
                break;
            case (R.id.tempButton3):
                responseResult = 3;
                finish();
                break;
            case (R.id.tempButton4):
                responseResult = 4;
                finish();
                break;
            /*
            END OF TEMPERATURE BUTTON HANDLING

            START OF HEART RATE BUTTON HANDLING
             */
            case (R.id.hrButton0):
                responseResult = 0;
                finish();
                break;
            case (R.id.hrButton1):
                responseResult = 1;
                finish();
                break;
            case (R.id.hrButton2):
                responseResult = 2;
                finish();
                break;
            case (R.id.hrButton3):
                responseResult = 3;
                finish();
                break;
            case (R.id.hrButton4):
                responseResult = 4;
                finish();
                break;
            case (R.id.hrButton5):
                responseResult = 5;
                finish();
                break;
            /*
            END OF HEART RATE BUTTON HANDLING

            START OF BP BUTTON HANDLING
             */
            case (R.id.bpButton0):
                responseResult = 0;
                finish();
                break;
            case (R.id.bpButton1):
                responseResult = 1;
                finish();
                break;
            case (R.id.bpButton2):
                responseResult = 2;
                finish();
                break;
            case (R.id.bpButton3):
                responseResult = 3;
                finish();
                break;
            case (R.id.bpButton4):
                responseResult = 4;
                finish();
                break;
            /*
            END OF BP BUTTON HANDLING

            START OF CONSCIOUSNESS BUTTON HANDLING
             */
            case (R.id.conButton0):
                responseResult = 0;
                finish();
                break;
            case (R.id.conButton1):
                responseResult = 1;
                finish();
                break;
            case (R.id.conButton2):
                responseResult = 2;
                finish();
                break;
            case (R.id.conButton3):
                responseResult = 3;
                finish();
                break;
            case (R.id.conButton4):
                responseResult = 4;
                finish();
                break;
             /*
             END OF CONSCIOUSNESS BUTTON HANDLING

             START OF OXYGEN BUTTON HANDLING
              */
            case (R.id.oxyButton0):
                responseResult = 0;
                finish();
                break;
            case (R.id.oxyButton1):
                responseResult = 1;
                finish();
                break;
            case (R.id.oxyButton2):
                responseResult = 2;
                finish();
                break;
            case (R.id.oxyButton3):
                responseResult = 3;
                finish();
                break;
            case (R.id.oxyButton4):
                responseResult = 4;
                finish();
                break;
            case (R.id.oxyButton5):
                responseResult = 5;
                finish();
                break;
            case (R.id.oxyButton6):
                responseResult = 6;
                finish();
                break;
            case (R.id.oxyButton7):
                responseResult = 7;
                finish();
                break;
            case (R.id.oxyButton8):
                responseResult = 8;
                finish();
                break;
            case (R.id.oxyButton9):
                responseResult = 9;
                finish();
                break;
            case (R.id.oxyButton10):
                responseResult = 10;
                finish();
                break;
            case (R.id.oxyButton11):
                responseResult = 11;
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        /*
        This for the mean time returns 0, the default value of responseResult.
        this will select by default the "first" option on the preceding screen
        will guarantee a bad news...
        DOES NOT WORK CURRENTLY WITH O2 SATURATION, NEEDS OWN SCREEN FOR 2 SCALES
         */
        Intent response = new Intent();
        response.putExtra("Set", responseResult);
        setResult(RESULT_OK, response);

        super.finish();
    }
}