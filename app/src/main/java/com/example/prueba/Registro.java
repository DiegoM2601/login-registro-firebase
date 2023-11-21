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

public class Registro extends AppCompatActivity {

    TextInputEditText txtEmail, txtContrasenia;
    Button btnRegistro;

    FirebaseAuth mAuth;

    ProgressBar barraProgreso;

    TextView goLogin;

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
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtUsuario);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        btnRegistro = findViewById(R.id.btnRegistro);
        barraProgreso = findViewById(R.id.barraProgreso);
        goLogin = findViewById(R.id.goLogin);


        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barraProgreso.setVisibility(View.VISIBLE);

                String email, contrasenia;
                email = String.valueOf(txtEmail.getText());
                contrasenia = String.valueOf(txtContrasenia.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registro.this, "Escribe tu Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(contrasenia)) {
                    Toast.makeText(Registro.this, "Escribe tu Contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, contrasenia)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                barraProgreso.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registro.this, "Cuenta creada exitosamente.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(Registro.this, "Error al registrar el nuevo usuario.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
}