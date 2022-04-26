package com.example.parsagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Receive daily data from intent
        Bundle extras = getIntent().getExtras();
        String[] daily = extras.getStringArray("daily");

        tvTest = findViewById(R.id.tvTest);
        tvTest.setText(daily[0]);
    }


}