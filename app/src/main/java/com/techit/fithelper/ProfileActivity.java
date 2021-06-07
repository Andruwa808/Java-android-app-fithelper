package com.techit.fithelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.techit.fithelper.Models.User;
import com.techit.fithelper.Models.sData;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG ="Profile" ;
    Button btnCalc, crossFit;

    private FirebaseAuth auth;
    private DatabaseReference users;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase db;
    private String userID, userID2;

    private ListView ListInFo;
    private ListView ListInFo2;

    FirebaseListAdapter adapter;

    private List<String> ListSt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences prefs1 = getSharedPreferences("prefs1", MODE_PRIVATE);
        boolean firstStart = prefs1.getBoolean("firstStart", true);

        btnCalc = findViewById(R.id.btnCalc);
        crossFit = findViewById(R.id.crossFit);

        if (firstStart) {
            showStartDialog();
        }

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, CalculatorActivity.class));
            }
        });

        crossFit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MapActivity.class));
            }
        });

        ListInFo = (ListView) findViewById(R.id.in_fo);
        ListInFo2 = (ListView) findViewById(R.id.dataSa);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();



        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            final User inFo = new User();
            inFo.setName(ds.child(userID).getValue(User.class).getName()); //set the name
            inFo.setRost(ds.child(userID).getValue(User.class).getRost()); //set the email
            inFo.setVes(ds.child(userID).getValue(User.class).getVes());

            //display all the information
            Log.d(TAG, "showData: name: " + inFo.getName());
            Log.d(TAG, "showData: " + inFo.getRost());
            Log.d(TAG, "showData: " + inFo.getVes());

            ArrayList<String> array  = new ArrayList<>();
            array.add(inFo.getName());
            array.add(inFo.getRost() + " sm");
            array.add(inFo.getVes() + " kg");
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array);
            ListInFo.setAdapter(adapter);

            /*ListInFo.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
                }
            });*/

            /*final sData inFo2 = new sData();
            inFo2.setCa(ds.child(userID).getValue(sData.class).getCa());

            Log.d(TAG, "showData: " + inFo2.getCa());

            ArrayList<String> array2  = new ArrayList<>();
            array2.add(inFo2.getCa());
            ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,array2);
            ListInFo2.setAdapter(adapter2);*/
        }
    }

    private void showStartDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Mój profil")
                .setMessage("To jest profil użytkownika, tutaj możesz zobaczyć informacje o " +
                        "sobie i statystyki w ciągu dnia, liczbę kroków i kalorii, które zawsze będą" +
                        " Cię na bieżąco informować o twoim stanie zdrowia i pokazywać twoje postępy.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

        SharedPreferences prefs1 = getSharedPreferences("prefs1", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs1.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
