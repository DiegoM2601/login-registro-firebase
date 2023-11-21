package com.example.prueba;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText txtEmail, txtContrasenia;
    Button btnLogin;

    FirebaseAuth mAuth;

    ProgressBar barraProgreso;

    TextView goRegistro;

    @Override
    public void onStart() {
        super.onStart();
        // Comprobar si el usuario ya se encuentra logeado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtUsuario);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        btnLogin = findViewById(R.id.btnLogin);
        barraProgreso = findViewById(R.id.barraProgreso);
        goRegistro = findViewById(R.id.goRegistro);
        mAuth = FirebaseAuth.getInstance();

        goRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
                finish();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barraProgreso.setVisibility(View.VISIBLE);

                String email, contrasenia;
                email = String.valueOf(txtEmail.getText());
                contrasenia = String.valueOf(txtContrasenia.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Escribe tu Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contrasenia)) {
                    Toast.makeText(Login.this, "Escribe tu Contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, contrasenia)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                barraProgreso.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Has iniciado sesión exitosamente.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Login.this, "El usuario y la contraseña no coinciden.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}