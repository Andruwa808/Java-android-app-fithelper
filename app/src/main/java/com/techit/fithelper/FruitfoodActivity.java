//project developer
package com.techit.fithelper;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;


public class FruitfoodActivity extends AppCompatActivity {

    public static String TAG = "ListView";
    public Button btnBack;
    private ListView listView;
    int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruitfood);

        listView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " +position);
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                FormList slots = (FormList) listView.getItemAtPosition(position);
                slots.setActive(!currentCheck);
            }
        });

        FormList A = new FormList("Agrest ",41, false);
        FormList A1 = new FormList("Ananas ",54, false);
        FormList A2 = new FormList("Arbuz ",36, false);
        FormList A3 = new FormList("Awokado ", 160, false);
        FormList B = new FormList("Banan ",95, false);
        FormList B1 = new FormList("Brzoskwinie   ",46, false);
        FormList C = new FormList("Cytryna ",36, false);
        FormList C1 = new FormList("Czarne jagody", 45, false);
        FormList C2 = new FormList("Czereśnie ",61, false);
        FormList C3 = new FormList("Grapefruit  ",36, false);
        FormList C4 = new FormList("Gruszki  ",54, false);
        FormList J = new FormList("Jabłka  ",46, false);
        FormList K = new FormList("Kiwi",56, false);
        FormList M = new FormList("Maliny ",29, false);
        FormList M1 = new FormList("Mandarynki ",42, false);
        FormList M2 = new FormList("Mango ",67, false);
        FormList M3 = new FormList("Melon ", 35, false);
        FormList M4 = new FormList("Morele ", 47, false);
        FormList N = new FormList("Nektarynka  ",48, false);
        FormList P = new FormList("Pomarańcze",44, false);
        FormList P1 = new FormList("Porzeczki białe",33, false);
        FormList P2 = new FormList("Porzeczki czarne",35, false);
        FormList P3 = new FormList("Porzeczki czerwone",31, false);
        FormList P4 = new FormList("Poziomki",33, false);
        FormList S = new FormList("Śliwki",45, false);
        FormList T = new FormList("Truskawki",28, false);
        FormList W = new FormList("Winogrona",69, false);
        FormList W1 = new FormList("Wiśnie",47, false);


        FormList[] slots = new FormList[]{A,A1,A2,A3,B,B1,C,C1,C2,C3,C4,J,K,M,M1,M2,M3,M4,N,P,
        P1,P2,P3,P4,S,T,W,W1};

        ArrayAdapter<FormList> arrayAdapter
                = new ArrayAdapter<FormList>(this, android.R.layout.simple_list_item_checked , slots);


        listView.setAdapter(arrayAdapter);

        for(int i=0;i< slots.length; i++ )  {
            listView.setItemChecked(i,slots[i].isActive());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FruitfoodActivity.this, CalculatorActivity.class));
            }
        });

    }
    public void btnEnter(View view)  {

        SparseBooleanArray sp = listView.getCheckedItemPositions();

        StringBuilder sb= new StringBuilder();

        for(int i=0;i<sp.size();i++){
            if(sp.valueAt(i)==true){
                FormList slot= (FormList) listView.getItemAtPosition(i);
                String s= slot.getsName();
                sum+=slot.getkType();
                sb = sb.append(" \n"+s);
            }
        }
        Toast.makeText(this, "Selected items are: "+ sb.toString() + "\n\nCalories: " + sum, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, CalculatorActivity.class);
        intent.putExtra("myIntVariableName", sum);
        startActivity(intent);
}}
