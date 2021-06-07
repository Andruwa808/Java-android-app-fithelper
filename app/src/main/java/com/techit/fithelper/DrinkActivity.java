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

public class DrinkActivity extends AppCompatActivity {

    Button btnBack;
    public static String TAG = "ListView";
    private ListView listView;
    int sum;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        listView = findViewById(R.id.listView);
        btnBack = findViewById(R.id.btnBack);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: " + position);
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                FormList slots = (FormList) listView.getItemAtPosition(position);
                slots.setActive(!currentCheck);
            }
        });


        final FormList B = new FormList("Bób", 88, false);
        final FormList F = new FormList("Fasola biała", 288, false);
        final FormList F1 = new FormList("Fasola szparagowa", 27, false);
        final FormList G = new FormList("Groszek zielony", 75, false);
        final FormList M = new FormList("Migdały", 572, false);
        final FormList O = new FormList("Olej rzepakowy", 884, false);
        final FormList O1 = new FormList("Oliwa z oliwek", 882, false);
        final FormList O2 = new FormList("Orzechy laskowe", 640, false);
        final FormList O3 = new FormList("Orzechy pistacjowe", 589, false);
        final FormList O4 = new FormList("Orzechy włoskie", 645, false);
        final FormList O5 = new FormList("Orzechy ziemne", 560, false);
        final FormList P1 = new FormList("Pestki Dyni", 556, false);
        final FormList P2 = new FormList("Pestki słonecznika", 560, false);

        final FormList[] slots = new FormList[]{B, F, F1, G, M, O, O1, O2, O3, O4, O5, P1,P2};

        ArrayAdapter<FormList> arrayAdapter
                = new ArrayAdapter<FormList>(this, android.R.layout.simple_list_item_checked, slots);


        listView.setAdapter(arrayAdapter);

        for (int i = 0; i < slots.length; i++) {
            listView.setItemChecked(i, slots[i].isActive());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DrinkActivity.this, CalculatorActivity.class));
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

    }
}

