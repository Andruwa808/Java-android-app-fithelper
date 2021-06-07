package com.techit.fithelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


public class MapActivity extends Activity implements SensorEventListener {

    public SensorManager sensorManager;
    public TextView count;
    boolean activityRunning;
    Button btnCalc, btnProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);
        boolean firstStart = prefs2.getBoolean("firstStart", true);

       if (firstStart) {
            showStartDialog();
        }

        count = (TextView) findViewById(R.id.count);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        btnCalc = findViewById(R.id.btnCalc);
        btnProfile = findViewById(R.id.btnProfile);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, CalculatorActivity.class));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapActivity.this, ProfileActivity.class));
            }
        });
        startService();
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Cześć kolego, to krokomierz!")
                .setMessage("Krokomierz, zawsze policzy twoje kroki i pokaże twoją aktywność. " +
                        "Aby czuć się dobrze, zalecamy wykonywanie 10 000 kroków dziennie.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs2 = getSharedPreferences("prefs2", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs2.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, MyService.class);
        serviceIntent.putExtra("inputExtra", count.getText().toString());
        Intent intentSave = new Intent(this, CalculatorActivity.class);
        intentSave.putExtra("myStep", count.getText().toString());
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Czujnik zliczania niedostępny!",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            count.setText(String.valueOf(event.values[0]));
            startService();
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}