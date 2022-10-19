package com.example.plantdiseasesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button selectBtn,predictBtn,captureBtn;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.green));


//       Start Wheat Button
        Button wheatbutton = findViewById(R.id.wheatbtn);
        wheatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,WheatActivity.class);
                startActivity(intent);
            }
        });
//       End Wheat Button
//       Start Potato Button
        Button potatobutton = findViewById(R.id.potatoBtn);
        potatobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PotatoActivity.class);
                startActivity(intent);
            }
        });
//       End Potato Button
//       Start Tomato Button
        Button tomatobutton = findViewById(R.id.tomatoBtn);
        tomatobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TomatoActivity.class);
                startActivity(intent);
            }
        });
//       Start Tomato Button
//       Start Paper Button
        Button paperbutton = findViewById(R.id.paperBtn);
        paperbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PepperActivity.class);
                startActivity(intent);
            }
        });
//       End Paper Button
    }
}