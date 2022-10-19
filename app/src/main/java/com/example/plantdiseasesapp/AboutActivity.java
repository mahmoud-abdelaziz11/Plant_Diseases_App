package com.example.plantdiseasesapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class AboutActivity extends Activity {
    TextView diseaseName,treatment,information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        diseaseName=findViewById(R.id.diseaseName);
        treatment=findViewById(R.id.treatment);
        information=findViewById(R.id.information);
        String disease = getIntent().getStringExtra("diseaseName");
        String info = getIntent().getStringExtra("information");
        String treat = getIntent().getStringExtra("treatment");

        diseaseName.setText(disease);
        information.setText(info);
        information.setMovementMethod(new ScrollingMovementMethod());
        treatment.setText(treat);
        treatment.setMovementMethod(new ScrollingMovementMethod());
    }
}
