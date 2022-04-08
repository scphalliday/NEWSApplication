package halliday.steven.newsapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class DataInputActivity extends AppCompatActivity {

    boolean debug = false;

    boolean extraInfoSwitched;
    Bundle extras;

    /*
    Respiratory rate
    0 = >=25
    1 = 21-24
    2 = 20-12
    3 = 9-11
    4 = <=8
    Oxygen (scale 1)
    0 = >=96
    1 = 94-95
    2 = 92-93
    3 = <=91
    Oxygen (scale 2)
    0 = >=97 (on o2)
    1 = 95-96 (on o2)
    2 = 93-94 (on o2)
    3 = >=93 (on o2) / 88-92 (air)
    4 = 86-86
    5 = 84-85
    6 = <=83
    SystolicBP
    0 = >= 220
    1 = 219-111
    2 = 101-110
    3 = 91-100
    4 = 90-<=50
    Pulse
    0 = >=131
    1 = 130-111
    2 = 110-91
    3 = 90-51
    4 = 41-50
    5 = <=40
    Consciousness
    0 = alert
    1 = Confused
    2 = V
    3 = P
    4 = U
    Temperature
    0 = >= 39.1
    1 = 38.1-39
    2 = 38-36.1
    3 = 35.1-36
    4 = <=35
    */



    int finalNewsResult = -1;

    //result numbers will be numbered between 0 and 6. that being the number of possible thresholds

    int respiratoryRateResult = -1;
    int oxygenSaturationResult = -1;
    boolean oxygen = false;
    int temperatureResult = -1;
    int heartRateResult = -1;
    int systolicBPResult = -1;
    int conciousnessResult = -1;
    boolean redScore = false;

    public void startTEST(){
        //2,0,2,3,1,0  VALUES TO SCORE 0 TOTAL
        System.out.println("Respiratory options test...\n\n");
        System.out.println("Expected results in order: 3 2 0 1 3");
        for(int i = 0 ; i < 5; i++){
            TESTMETHOD(new int[]{i, 0, 2, 3, 1, 0}); //RESPIRATORY TEST
        }
        System.out.println("--------------------------------------");
        System.out.println("Oxygen sat options test...\n\n");
        System.out.println("Expected results in order: 0 1 2 3 3 2 1 0 0 1 2 3");
        for(int i = 0 ; i < 12; i++){
            TESTMETHOD(new int[]{2, i, 2, 3, 1, 0}); //OXYGEN TEST
        }
        System.out.println("--------------------------------------");
        System.out.println("Temperature options test...\n\n");
        System.out.println("Expected results in order: 2 1 0 1 3");
        for(int i = 0 ; i < 5; i++){
            TESTMETHOD(new int[]{2, 0, i, 3, 1, 0}); //TEMPERATURE TEST
        }
        System.out.println("--------------------------------------");
        System.out.println("heart rate options test...\n\n");
        System.out.println("Expected results in order: 3 2 1 0 1 3");
        for(int i = 0 ; i < 6; i++){
            TESTMETHOD(new int[]{2, 0, 2, i, 1, 0}); //HEART RATE TEST
        }
        System.out.println("--------------------------------------");
        System.out.println("blood pressure options test...\n\n");
        System.out.println("Expected results in order: 3 0 1 2 3");
        for(int i = 0 ; i < 5; i++){
            TESTMETHOD(new int[]{2, 0, 2, 3, i, 0}); //BP TEST
        }
        System.out.println("--------------------------------------");
        System.out.println("consciousness options test...\n\n");
        System.out.println("Expected results in order: 0 3 3 3");
        for(int i = 0 ; i < 5; i++){
            TESTMETHOD(new int[]{2, 0, 2, 3, 1, i}); //CONSCIOUSNESS TEST
        }
        System.out.println("--------------------------------------");
    }
    public void TESTMETHOD(int[] nums){
        //resp, oxy, temp, heart, bp, consciouness
        String[] sets = {"RespiratoryRate","Oxygen","Temperature","HeartRate","SystolicBP","Consciousness"};
        StringBuilder text = new StringBuilder();
        for(int i = 0; i < nums.length; i++){
            set = sets[i];
            //setButtonText(numOptions[i]);
            setScore(nums[i]);
            //text.append(set).append(" option: ").append(numOptions[i]).append("\n");
        }
        calculateNews(null);
    }

    boolean debugTesting = false;

    String set = "";
    int response = 0;
    int responseResult = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_input);
        extras = getIntent().getExtras();
        extraInfoSwitched = extras.getBoolean("InfoSwitch");
        if(debugTesting){
            startTEST();
        }
    }

    public void openDataSelectionPage(View view){
        switch(view.getId()){
            case R.id.RespiratoryRateButton:
                set = "RespiratoryRate";
                break;
            case R.id.OxygenSaturationButton:
                set = "Oxygen";
                break;
            case R.id.TemperatureButton:
                set = "Temperature";
                break;
            case R.id.HeartRateButton:
                set = "HeartRate";
                break;
            case R.id.SystolicBPButton:
                set = "SystolicBP";
                break;
            case R.id.ConciousnessLevelButton:
                set = "Consciousness";
                break;
        }
        Intent dataInput = new Intent(getApplicationContext(), DataSelection.class);
        dataInput.putExtra("Set", set);
        startActivityForResult(dataInput, responseResult);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*
        will set every score to "0" until DataSelection activity is coded up.
         */
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == responseResult){
            if(data.hasExtra("Set")){
                response = data.getExtras().getInt("Set");
                setButtonText(response);
                setScore(response);
                if(debug) {
                    Toast.makeText(getApplicationContext(), responseToScore(response), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void setButtonText(int response){
        Button button;
        switch(set){
            case "RespiratoryRate":
                button = (Button)findViewById(R.id.RespiratoryRateButton);
                button.setText(responseToScore(response));
                break;
            case "Oxygen":
                button = (Button)findViewById(R.id.OxygenSaturationButton);
                button.setText(responseToScore(response));
                break;
            case "Temperature":
                button = (Button)findViewById(R.id.TemperatureButton);
                button.setText(responseToScore(response));
                break;
            case "HeartRate":
                button = (Button)findViewById(R.id.HeartRateButton);
                button.setText(responseToScore(response));
                break;
            case "SystolicBP":
                button = (Button)findViewById(R.id.SystolicBPButton);
                button.setText(responseToScore(response));
                break;
            case "Consciousness":
                button = (Button)findViewById(R.id.ConciousnessLevelButton);
                button.setText(responseToScore(response));
                break;
            default:
                break;
        }
    }
    public String responseToScore(int response){
        //take number from 0 - ~6, turns into the score string equivalent depending on set
        switch(set){
            case "RespiratoryRate":
                switch(response){
                    case 0:
                        return ">=25";
                    case 1:
                        return "21-24";
                    case 2:
                        return "20-12";
                    case 3:
                        return "9-11";
                    case 4:
                        return "<=8";
                    default:
                        break;
                }
                break;
            case "Oxygen":
                //oxygen to get own activity to decide spo2 scale 1 or 2
                /*
                this will return the "0" response from scale 1 of oxygen saturation until
                the preceding activities are created and running. this is for debug and
                testing purposes.
                 */
                switch(response){
                    //SCALE 1 CASES
                    case 0:
                        return ">=96";
                    case 1:
                        return "95-94";
                    case 2:
                        return "93-92";
                    case 3:
                        return ">=91";
                    //SCALE 2 CASES
                    case 4:
                        return ">=97 on O2";
                    case 5:
                        return "96-95 on O2";
                    case 6:
                        return "94-93 on O2";
                    case 7:
                        return ">=93 on air";
                    case 8:
                        return "92-88";
                    case 9:
                        return "87-86";
                    case 10:
                        return "85-84";
                    case 11:
                        return "<=83";
                    default:
                        break;
                }
                break;
            case "Temperature":
                switch(response){
                    case 0:
                        return ">=39.1";
                    case 1:
                        return "38.1-39";
                    case 2:
                        return "38-36.1";
                    case 3:
                        return "35.1-36";
                    case 4:
                        return "<=35";
                    default:
                        break;
                }
                break;
            case "HeartRate":
                switch(response){
                    case 0:
                        return ">=131";
                    case 1:
                        return "130-111";
                    case 2:
                        return "110-91";
                    case 3:
                        return "90-51";
                    case 4:
                        return "41-50";
                    case 5:
                        return "<=40";
                    default:
                        break;
                }
                break;
            case "SystolicBP":
                switch(response){
                    case 0:
                        return ">=220";
                    case 1:
                        return "219-111";
                    case 2:
                        return "101-110";
                    case 3:
                        return "91-100";
                    case 4:
                        return "<91";
                    default:
                        break;
                }
                break;
            case "Consciousness":
                switch(response){
                    case 0:
                        return "Alert";
                    case 1:
                        return "Confused";
                    case 2:
                        return "Voice";
                    case 3:
                        return "Pain";
                    case 4:
                        return "Unresponsive";
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return "error";
    }
    public void setScore(int response){
        //set value of score data per set for news calculation
        switch(set){
            case "RespiratoryRate":
                respiratoryRateResult = response;
                break;
            case "Oxygen":
                oxygenSaturationResult = response;
                break;
            case "Temperature":
                temperatureResult = response;
                break;
            case "HeartRate":
                heartRateResult = response;
                break;
            case "SystolicBP":
                systolicBPResult = response;
                break;
            case "Consciousness":
                conciousnessResult = response;
                break;
            default:
                break;
        }
    }
    public void calculateNews(View view){

        if(respiratoryRateResult == -1 || oxygenSaturationResult == -1 || temperatureResult == -1 || heartRateResult == -1 || systolicBPResult == -1 || conciousnessResult == -1){
            Toast.makeText(this, "Please complete the data entry first", Toast.LENGTH_SHORT).show();
        } else {
            redScore = false;
            int totalScore = 0;
            StringBuilder redScoreParams = new StringBuilder();
            switch (respiratoryRateResult) {
                case 0:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Respiratory Rate\n");
                    break;
                case 1:
                    totalScore += 2;
                    break;
                case 2:
                    break; //no points
                case 3:
                    totalScore += 1;
                    break;
                case 4:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Respiratory Rate\n");
                    break;
                default:
                    break;
            }
            switch (oxygenSaturationResult) {
                case 0:
                    //no score given
                    break;
                case 1:
                    totalScore += 1;
                    break;
                case 2:
                    totalScore += 2;
                    break;
                case 3:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Oxygen Saturation\n");
                    break;
                case 4:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Oxygen Saturation\n");
                    break;
                case 5:
                    totalScore += 2;
                    break;
                case 6:
                    totalScore += 1;
                    break;
                case 7:
                    // no score
                    break;
                case 8:
                    // no score
                    break;
                case 9:
                    totalScore += 1;
                    break;
                case 10:
                    totalScore += 2;
                    break;
                case 11:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Oxygen Saturation\n");
                    break;
                default:
                    break;
            }
            switch (temperatureResult) {
                case 0:
                    totalScore += 2;
                    break;
                case 1:
                    totalScore += 1;
                    break;
                case 2:
                    break; //no points
                case 3:
                    totalScore += 1;
                    break;
                case 4:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Temperature\n");
                    break;
                default:
                    break;
            }
            switch (heartRateResult) {
                case 0:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Heart Rate\n");
                    break;
                case 1:
                    totalScore += 2;
                    break;
                case 2:
                    totalScore += 1;
                    break;
                case 3:
                    break; // no points given
                case 4:
                    totalScore += 1;
                    break;
                case 5:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Heart Rate\n");
                    break;
                default:
                    break;
            }
            switch (systolicBPResult) {
                case 0:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Systolic Blood Pressure\n");
                    break;
                case 1:
                    break; // no given points
                case 2:
                    totalScore += 1;
                    break;
                case 3:
                    totalScore += 2;
                    break;
                case 4:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Systolic Blood Pressure\n");
                    break;
                default:
                    break;
            }
            switch (conciousnessResult) {
                case 0:
                    break; // no points given
                case 1:
                case 2:
                case 3:
                case 4:
                    totalScore += 3;
                    redScore = true;
                    redScoreParams.append(" - Consciousness\n");
                    break;
                default:
                    break;
            }
            if(oxygen){
                totalScore += 2;
            }
            System.out.println("Output Score: " + totalScore);
            if(!debugTesting) {
                Intent scoreDisplay = new Intent(getApplicationContext(), ScoreDisplayActivity.class);
                scoreDisplay.putExtra("Score", totalScore);
                scoreDisplay.putExtra("RedScore", redScore);
                scoreDisplay.putExtra("RedValues", redScoreParams.toString());
                //rest of data for purpose of saving and recall
                startActivity(scoreDisplay);
            }
            else{
                //Toast.makeText(this,"Total score with testing method: " + String.valueOf(totalScore), Toast.LENGTH_LONG).show();
            }
        }
        /*
        resp - 0 = 3 points - 1 = 2 points - 2 = 0 points - 3 = 1 point - 4 = 3 points.
        o2 scale1 - 0 = 0 points - 1 = 1 point - 2 = 2 points - 3 = 3 points.
        o2 scale2 - 0 = 3 points. 1 = 2 points. - 2 = 1 point - 3 = 0 points - 4 = 1 point - 5 = 2 points - 6 = 3 points
        o2Bool - false = o points. - true - 2 points.
        BP - 0 = 3 points - 1 = 0 points - 2 = 1 point - 3 = 2 points - 4 = 3 points
        pulse - 0 = 3 points - 1 = 2 points - 2 = 1 point - 3 = 0 points - 4 = 1 point - 5 = 3 points
        consciousness - 0 = 0 points - 1 = 3 points.
        temperature - 0 = 2 points - 1 = 1 point - 2 = 0 points - 3 = 1 point - 4 = 3 points
    int respiratoryRateResult = -1;
    int oxygenSaturationResult = -1;
    boolean oxygen = false;
    int temperatureResult = -1;
    int heartRateResult = -1;
    int systolicBPResult = -1;
    int conciousnessResult = -1;
    boolean redScore = false;
         */
    }
    public void setOxyToggle(View view){
        oxygen = !oxygen;
        if(debug) {
            Toast.makeText(getApplicationContext(), String.valueOf(oxygen), Toast.LENGTH_SHORT).show();
        }
    }
}