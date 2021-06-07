package com.techit.fithelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.rengwuxian.materialedittext.MaterialEditText;

public class OtherActivity extends AppCompatActivity {

    Button btnBack;
    public static String TAG = "ListView";
    private ListView listView;
    int kType;
    int sum;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        btnBack = findViewById(R.id.btnBack);

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

        final FormList J = new FormList("Jogurt naturalny 2%", 60, false);
        final FormList J1 = new FormList("Jogurt z owocami", 62, false);
        final FormList K = new FormList("Kefir 2% tł.", 51, false);
        final FormList M = new FormList("Maślanka 0,5% tł.", 37, false);
        final FormList M1 = new FormList("Mleko 0,5% tł.", 39, false);
        final FormList M2 = new FormList("Mleko 2% tł.", 51, false);
        final FormList M3 = new FormList("Mleko 3,2% tł.", 61, false);
        final FormList M4 = new FormList("Mleko kozie", 68, false);
        final FormList M5 = new FormList("Mleko owcze", 107, false);
        final FormList S = new FormList("Ser feta", 215, false);
        final FormList S1 = new FormList("Ser mozarella", 251, false);
        final FormList S2 = new FormList("Ser twaróg chudy", 99, false);
        final FormList S3 = new FormList("Ser twaróg półtłusty", 133, false);
        final FormList S4 = new FormList("Ser twaróg tłusty", 175, false);
        final FormList S5 = new FormList("Ser żółty - ok.", 350, false);
        final FormList S6 = new FormList("Serek homogen", 159, false);
        final FormList S7 = new FormList("Serwatka płynna", 25, false);
        final FormList S8 = new FormList("Śmietana 18% tł.", 184, false);

        final FormList[] slots = new FormList[]{J, J1, K, M, M1, M2, M3, M4, M5, S, S1, S2, S3, S4, S5, S6, S7, S8};

        ArrayAdapter<FormList> arrayAdapter
                = new ArrayAdapter<FormList>(this, android.R.layout.simple_list_item_checked, slots);


        listView.setAdapter(arrayAdapter);

        for (int i = 0; i < slots.length; i++) {
            listView.setItemChecked(i, slots[i].isActive());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtherActivity.this, CalculatorActivity.class));
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
