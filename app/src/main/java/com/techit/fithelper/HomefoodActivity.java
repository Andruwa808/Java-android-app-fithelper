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

public class HomefoodActivity extends AppCompatActivity {

    public static String TAG = "ListView";
    Button btnBack, btnEnter;
    private ListView listView;
    int kType;
    int sum;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefood);

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


        final FormList B = new FormList("Chleb żytni razowy", 213, false);
        final FormList B1 = new FormList("Chleb raz. na miodzie", 225, false);
        final FormList B2 = new FormList("Kasza gryczana", 336, false);
        final FormList B3 = new FormList("Kasza jaglana", 346, false);
        final FormList C = new FormList("Kasza perłowa", 327, false);
        final FormList C1 = new FormList("Kasza pęczak", 334, false);
        final FormList C2 = new FormList("Makaron pełnoziarn.", 345, false);
        final FormList C3 = new FormList("Musli", 325, false);
        final FormList D = new FormList("Otręby pszenne", 185, false);
        final FormList K = new FormList("Płatki jęczmienne ", 355, false);
        final FormList K1 = new FormList("Płatki kukurydziane ", 365, false);
        final FormList K2 = new FormList("Płatki owsiane", 366, false);
        final FormList K3 = new FormList("Płatki żytnie", 343, false);
        final FormList K4 = new FormList("Pumpernikiel", 240, false);
        final FormList K5 = new FormList("Ryż brązowy", 322, false);


        final FormList[] slots = new FormList[]{B, B1, B2, B3, C, C1, C2, C3, D, K, K1, K2, K3, K4, K5};

        ArrayAdapter<FormList> arrayAdapter
                = new ArrayAdapter<FormList>(this, android.R.layout.simple_list_item_checked, slots);


        listView.setAdapter(arrayAdapter);

        for (int i = 0; i < slots.length; i++) {
            listView.setItemChecked(i, slots[i].isActive());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomefoodActivity.this, CalculatorActivity.class));
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


