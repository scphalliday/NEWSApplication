package halliday.steven.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Boolean infoSwitchSwitched = false;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.deleteDatabase("Patients.db");
        Switch infoSwitch = (Switch)findViewById(R.id.infoSwitch);
        TextView openingInformation = (TextView)findViewById(R.id.subtitleText);
        TextView openingInformation2 = (TextView)findViewById(R.id.subtitleText2);
        TextView openingInformation3 = (TextView)findViewById(R.id.subtitleText3);
        TextView openingInformation4 = (TextView)findViewById(R.id.subtitleText4);
        TextView openingInformation5 = (TextView)findViewById(R.id.subtitleText5);
        TextView openingInformation6 = (TextView)findViewById(R.id.subtitleText6);
        TextView openingInformation7 = (TextView)findViewById(R.id.subtitleText7);
        TextView openingInformation8 = (TextView)findViewById(R.id.subtitleText8);
        openingInformation.setText(getString(R.string.blank));
        openingInformation2.setText(getString(R.string.blank));
        openingInformation3.setText(getString(R.string.blank));
        openingInformation4.setText(getString(R.string.blank));
        openingInformation5.setText(getString(R.string.blank));
        openingInformation6.setText(getString(R.string.blank));
        openingInformation7.setText(getString(R.string.blank));
        openingInformation8.setText(getString(R.string.blank));
        infoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    infoSwitchSwitched = true;
                    Log.i("test", "the switch is on");
                    openingInformation.setText(getString(R.string.subtitleText));
                    openingInformation2.setText(getString(R.string.subtitleText2));
                    openingInformation3.setText(getString(R.string.subtitleText3));
                    openingInformation4.setText(getString(R.string.subtitleText4));
                    openingInformation5.setText(getString(R.string.subtitleText5));
                    openingInformation6.setText(getString(R.string.subtitleText6));
                    openingInformation7.setText(getString(R.string.subtitleText7));
                    openingInformation8.setText(getString(R.string.subtitleText8));

                }
                else {
                    infoSwitchSwitched = false;
                    Log.i("test", "the switch is off");
                    openingInformation.setText(getString(R.string.blank));
                    openingInformation2.setText(getString(R.string.blank));
                    openingInformation3.setText(getString(R.string.blank));
                    openingInformation4.setText(getString(R.string.blank));
                    openingInformation5.setText(getString(R.string.blank));
                    openingInformation6.setText(getString(R.string.blank));
                    openingInformation7.setText(getString(R.string.blank));
                    openingInformation8.setText(getString(R.string.blank));

                }
            }
        });
    }
    public void openDataInputPage(View view){
        Intent dataInput = new Intent(getApplicationContext(), DataInputActivity.class);
        dataInput.putExtra("InfoSwitch", infoSwitchSwitched);
        startActivity(dataInput);
    }
    public void openImageViewPage(View view){
        Intent openImage = new Intent(getApplicationContext(), ImageViewActivity.class);
        switch (view.getId()){
            case R.id.ViewButton1:
                openImage.putExtra("ID", "1");
                break;
            case R.id.viewButton2:
                openImage.putExtra("ID", "2");

        }

        startActivity(openImage);
    }
    public void openPastScorePage(View view){
        Intent openScore = new Intent(getApplicationContext(), ViewPastScoresActivity.class);
        startActivity(openScore);
    }
    public void createBeginPopUp(View view){
        dialogBuilder = new AlertDialog.Builder(this);
        final View beginPopUp = getLayoutInflater().inflate(R.layout.begin_popup,null);
        dialogBuilder.setView(beginPopUp);
        dialog = dialogBuilder.create();
        dialog.show();
    }
}