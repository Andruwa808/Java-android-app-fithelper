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

public class FastActivity extends AppCompatActivity {

    Button btnBack;
    public static String TAG = "ListView";
    private ListView listView;
    int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast);

        btnBack = findViewById(R.id.btnBack);
        listView = findViewById(R.id.listView);
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

        final FormList F = new FormList("Flądra ", 53, false);
        final FormList I = new FormList("Indyk - pierś", 84, false);
        final FormList I1 = new FormList("Indyk - tuszka", 129, false);
        final FormList J = new FormList("Jajko białko", 49, false);
        final FormList J1 = new FormList("Jajko żółtko", 314, false);
        final FormList J2 = new FormList("Jajko całe", 140, false);
        final FormList K = new FormList("Kaczka - tuszka", 308, false);
        final FormList K1 = new FormList("Kraby ", 84, false);
        final FormList K2 = new FormList("Krewetki ", 106, false);
        final FormList K3 = new FormList("Kurczak - pierś", 99, false);
        final FormList K4 = new FormList("Kurczak - tuszka ", 158, false);
        final FormList L = new FormList("Łosoś ", 201, false);
        final FormList M = new FormList("Makrela ", 181, false);
        final FormList M1 = new FormList("Mintaj", 73, false);
        final FormList P = new FormList("Pstrąg", 97, false);
        final FormList T = new FormList("Tuńczyk", 137, false);


        final FormList[] slots = new FormList[]{F, I, I1, J, J1, J2, K, K1, K2, K3, K4, L, M, M1, P, T};

        ArrayAdapter<FormList> arrayAdapter
                = new ArrayAdapter<FormList>(this, android.R.layout.simple_list_item_checked, slots);


        listView.setAdapter(arrayAdapter);

        for (int i = 0; i < slots.length; i++) {
            listView.setItemChecked(i, slots[i].isActive());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FastActivity.this, CalculatorActivity.class));
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

