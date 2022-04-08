package halliday.steven.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ImageViewActivity extends AppCompatActivity {

    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extras = getIntent().getExtras();
        String id = extras.getString("ID");
        if(id.equals("1")) {
            setContentView(R.layout.activity_image_view_observation_chart);
        }
        else {
            setContentView(R.layout.activity_image_view_threshold_chart);

        }
    }
}