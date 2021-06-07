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


public class VegetableActivity extends AppCompatActivity {

    Button btnBack;
    public static String TAG = "ListView";
    private ListView listView;
    int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable);

        btnBack = findViewById(R.id.btnBack);

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

        FormList B = new FormList("Bakłażan",21, false);
        FormList B1 = new FormList("Brokuły",27, false);
        FormList B2 = new FormList("Brukselka",37, false);
        FormList B3 = new FormList("Burak", 38, false);
        FormList C = new FormList("Cebula",30, false);
        FormList C1 = new FormList("Cukinia",15, false);
        FormList C2 = new FormList("Cykoria",21, false);
        FormList C3 = new FormList("Czosnek", 146, false);
        FormList D = new FormList("Dynia",28, false);
        FormList K = new FormList("Kalafior ",22, false);
        FormList K1 = new FormList("Kalarepa ",29, false);
        FormList K2 = new FormList("Kapusta biała",29, false);
        FormList K3 = new FormList("Kapusta czerwona",27, false);
        FormList K4 = new FormList("Kapusta pekińska",12, false);
        FormList K5 = new FormList("Koper ",26, false);
        FormList M = new FormList("Marchew ",27, false);
        FormList O = new FormList("Ogórek zielony",13, false);
        FormList P = new FormList("Papryka świeża",28, false);
        FormList P1 = new FormList("Pietruszka ( korzeń )",38, false);
        FormList P2 = new FormList("Pietruszka ( natka )",41, false);
        FormList P3 = new FormList("Pomidor", 15, false);
        FormList P4 = new FormList("Por", 24, false);
        FormList R = new FormList("Rzepa ",26, false);
        FormList R1 = new FormList("Rzeżucha ",16, false);
        FormList R2 = new FormList("Rzodkiewki ",14, false);
        FormList S = new FormList("Sałata ",14, false);
        FormList S1 = new FormList("Seler ( korzeń )",21, false);
        FormList S2 = new FormList("Szczaw ",21, false);
        FormList S3 = new FormList("Szczypiorek ",29, false);
        FormList S4 = new FormList("Szparagi ",18, false);
        FormList S5 = new FormList("Szpinak ",16, false);


        FormList[] slots = new FormList[]{B,B1,B2,B3,C,C1,C2,C3,D,K,K1,K2,K3,K4,K5,M,O,P,P1,P2,P3,
                P4,R,R1,R2,S,S1,S2,S3,S4,S5};

        ArrayAdapter<FormList> arrayAdapter
                = new ArrayAdapter<FormList>(this, android.R.layout.simple_list_item_checked , slots);


        listView.setAdapter(arrayAdapter);

        for(int i=0;i< slots.length; i++ )  {
            listView.setItemChecked(i,slots[i].isActive());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VegetableActivity.this, CalculatorActivity.class));
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
