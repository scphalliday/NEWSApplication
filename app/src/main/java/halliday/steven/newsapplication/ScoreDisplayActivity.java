package halliday.steven.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ScoreDisplayActivity extends AppCompatActivity {

    Bundle extras;
    int score = -1;
    boolean redScore;
    TextView scoreResponse;
    TextView clinicalRisk;
    TextView addInfo;
    TextView freq;
    TextView clinicalResponse;
    String redValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);
        scoreResponse = (TextView)findViewById(R.id.scoreTitleText);
        clinicalRisk = (TextView)findViewById(R.id.clinicalRiskDisplay);
        addInfo = (TextView)findViewById(R.id.responseExtraInfo);
        freq = (TextView)findViewById(R.id.monitorFreq);
        clinicalResponse = (TextView)findViewById(R.id.clinResponse);
        extras = getIntent().getExtras();
        score = extras.getInt("Score", -1);
        redScore = extras.getBoolean("RedScore", false);
        redValues = extras.getString("RedValues", "");
        TextView scoreDisplay = (TextView)findViewById(R.id.Score);
        scoreDisplay.setText(String.valueOf(score));
        if(redScore && score < 5){
            scoreResponse.setText("Urgent ward-based response");
            clinicalRisk.setText("Low - medium");
            freq.setText("\tMinimum 1 hourly");
            scoreResponse.setBackgroundColor(0xFFFFF3B8);
            clinicalResponse.setText("Registered nurse to inform medical team caring for the patient, who will review and decide whether escalation of care is necessary.");
            addInfo.setText("Red score.\nA score of 3 was entered in the following parameter(s)\n" + redValues + "\nResponse " +
                    "by a clinician or team with competence in the assessment of acutely ill patients and in recognising when the " +
                    "escalation of care to a critical care team is appropriate");
        }
        else {
            if (score >= 0 && score <= 4) {
                scoreResponse.setText("Ward-based response");
                clinicalRisk.setText("Low");
                if(score == 0){
                    freq.setText("\tMinimum 12 hourly");
                    clinicalResponse.setText("Continue routine NEWS monitoring");
                }
                else {
                    freq.setText("\tMinimum 4-6 hourly");
                    clinicalResponse.setText("A registered nurse must decide whether increased frequency of monitoring and/or escalation of care is required.");
                }
                scoreResponse.setBackgroundColor(0xFFAEFA9D);
            } else if (score == 5 || score == 6) {
                scoreResponse.setText("Key threshold for urgent response");
                clinicalRisk.setText("Medium");
                freq.setText("\tMinimum 1 hourly");
                scoreResponse.setBackgroundColor(0xFFFFF3B8);
                clinicalResponse.setText("Registered nurse to immediately inform the medical team caring for the patient.\nRegistered nurse to request urgent assessment in the care of cutely ill patients.\nProvide clinical care in an environment with monitoring facilities.");
                if(redScore){
                    addInfo.setText("Red score.\nA score of 3 was entered in the following parameter(s)\n" + redValues + "\nResponse by a clinician or team with competence in the assessment of acutely ill patients and in recognising when the escalation of care to a critical care team is appropriate");
                }
                else {
                    addInfo.setText("Response by a clinician or team with competence in the assessment of acutely ill patients and in recognising when the escalation of care to a critical care team is appropriate");
                }
            } else if (score >= 7) {
                scoreResponse.setText("Urgent or emergency response");
                clinicalRisk.setText("High");
                freq.setText("\tContinuous monitoring");
                scoreResponse.setBackgroundColor(0xFFFFA9A9);
                clinicalResponse.setText("Registered nurse to immediately inform the medical team caring for the patient. - this should be at least at specialist registrar level.\nEmergency assessment by a team with critical care competencies, including practitioner(s) with advanced airway management skills.\nConsider transfer of care to a level 2 or 3 clinical care facility, ei. higher-dependency unit or ICU.\nClinical care in an environment with monitoring facilities.");
                if(redScore){
                    addInfo.setText("Red score.\nA score of 3 was entered in the following parameter(s)\n" + redValues + "\nResponse by a clinician or team with competence in the assessment of acutely ill patients and in recognising when the escalation of care to a critical care team is appropriate\n\nThe response team must also include staff with critical care skills, including airway management");
                }
                else {
                    addInfo.setText("Response by a clinician or team with competence in the assessment of acutely ill patients and in recognising when the escalation of care to a critical care team is appropriate\n\nThe response team must also include staff with critical care skills, including airway management");
                }
            }
        }
        Toast.makeText(this, String.valueOf(score), Toast.LENGTH_SHORT).show();
    }
    public void startOver(View view){
        Intent startOver = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(startOver);
    }
    public void saveButtonPress(View view){
        Intent save = new Intent(getApplicationContext(), SaveScoreActivity.class);
        save.putExtra("Score", score);
        startActivity(save);
    }
}