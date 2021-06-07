package com.techit.fithelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.techit.fithelper.Models.sData;

import java.util.Calendar;


public class CalculatorActivity extends AppCompatActivity {

    Button homeFood, fruits, vegetables, fastFood, drinks, other;
    Button crossFit, btnProfile;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    private String text;

    private FirebaseAuth auth;
    private DatabaseReference users;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase db;
    private String userID;

    int sum;
    int allSum, defaultValue;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart) {
            showStartDialog();
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            text = sharedPreferences.getString(TEXT, "");
            allSum=Integer.parseInt(text);
        }

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        textView = (TextView)findViewById(R.id.textview);

        homeFood = findViewById(R.id.homeFood);
        fruits = findViewById(R.id.fruits);
        vegetables = findViewById(R.id.vegetables);
        fastFood = findViewById(R.id.fastFood);
        drinks = findViewById(R.id.drinks);
        other = findViewById(R.id.other);

        crossFit = findViewById(R.id.crossFit);
        btnProfile = findViewById(R.id.btnProfile);

        Intent intent = getIntent();
        sum = intent.getIntExtra("myIntVariableName", defaultValue);
        allSum+=sum;

        updateViews();

        homeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, HomefoodActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, FruitfoodActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, VegetableActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        fastFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, FastActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        drinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, DrinkActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, OtherActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        crossFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, MapActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, ProfileActivity.class));
                textView.setText(Integer.toString(allSum));
                saveData();
            }
        });

        textView.setText(Integer.toString(allSum));
        startService();
        onTimeSet();
    }

    public void showStartDialog() {
        allSum=0;
        new AlertDialog.Builder(this)
                .setTitle("Kalkulator kalorii")
                .setMessage("Ten kalkulator kalorii pomoże Ci śledzić dietę i " +
                        "liczyć kalorie w ciągu dnia. Każda porcja - 100 gramów. Życzę sukcesów! ")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    public void startService() {
        Intent serviceIntent = new Intent(CalculatorActivity.this, MyService.class);
        serviceIntent.putExtra("inputExtra", allSum);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEXT, textView.getText().toString());
        editor.apply();
    }

    public void updateViews() {
        textView.setText(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textView.setText(Integer.toString(allSum));
        saveData();
    }

    public void onTimeSet() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 50);

        startAlarm(c);
    }

    public void startAlarm(Calendar c) {
        Intent intentSave = getIntent();
        String input = intentSave.getStringExtra("myStep");
        Calendar now=Calendar.getInstance();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            if (c.get(Calendar.DATE)==now.get(Calendar.DATE)){
                sData data = new sData();
                data.setStep(input);
                data.setCa(Integer.toString(allSum));
                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Statistic").push().setValue(data);
                allSum=0;
                textView.setText(Integer.toString(allSum));
                saveData();
            }
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }
}
