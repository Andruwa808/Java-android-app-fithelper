package com.techit.fithelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.techit.fithelper.Models.User;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseDatabase db;
    DatabaseReference users;

    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        root = findViewById(R.id.root_element);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Intent intent = new Intent(MainActivity.this, MapActivity.class );
                    startActivity(intent);
                }
                else {
                }
            }
        };

        FirebaseUser user = auth.getCurrentUser();
        if(user != null) {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInWindow();
            }
        });
    }

    private void showSignInWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logowanie");
        dialog.setMessage("Wpisz swoje informacje");

        LayoutInflater inflater = LayoutInflater.from(this);                     // створили об'єкт, щоб получити шаблон
        View sign_in_window = inflater.inflate(R.layout.sign_in_window, null); // получаємо потрібний шаблон
        dialog.setView(sign_in_window); //встановлюємо потрібний шаблон для вспливаючого вікна

        final MaterialEditText email = sign_in_window.findViewById(R.id.emailField);
        final MaterialEditText pass = sign_in_window.findViewById(R.id.passField);

        dialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        }); // Кнопка сказування

        dialog.setPositiveButton("Logowanie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Wpisz swój adres e-mail", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(pass.getText().toString().length()<4){
                    Snackbar.make(root, "Wprowadź hasło więcej 4 symboli", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(MainActivity.this, MapActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {                   //неуспішна авторизація користувача
                        Snackbar.make(root, "Błąd autoryzacji. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Rejestracja");
        dialog.setMessage("Wprowadź wszystkie dane, aby się zarejestrować");

        LayoutInflater inflater = LayoutInflater.from(this);                     // створили об'єкт, щоб получити шаблон
        View register_window = inflater.inflate(R.layout.register_window, null); // получаємо потрібний шаблон
        dialog.setView(register_window); //встановлюємо потрібний шаблон для вспливаючого вікна

        final MaterialEditText email = register_window.findViewById(R.id.emailField);
        final MaterialEditText pass = register_window.findViewById(R.id.passField);
        final MaterialEditText name = register_window.findViewById(R.id.nameField);
        final MaterialEditText rost = register_window.findViewById(R.id.rostField);
        final MaterialEditText ves = register_window.findViewById(R.id.vesField);

        dialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        }); // Кнопка сказування

        dialog.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root, "Wpisz swój adres e-mail", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(name.getText().toString())){
                    Snackbar.make(root, "Wpisz swoje imię", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(rost.getText().toString())){
                    Snackbar.make(root, "Zapisz swój wzrost, patrz", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(ves.getText().toString())){
                    Snackbar.make(root, "Zapisz swoją wagę, kg", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(pass.getText().toString().length()<4){
                    Snackbar.make(root, "Wprowadź hasło więcej 4 symboli", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                // Реєстація користувача
                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail(email.getText().toString());
                                user.setPass(pass.getText().toString());
                                user.setName(name.getText().toString());
                                user.setRost(rost.getText().toString());
                                user.setVes(ves.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)                    //ключ по якому індентифікується користувач в БД
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(root, "Dodaj użytkownika!", Snackbar.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Błąd rejestracji " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }
}
