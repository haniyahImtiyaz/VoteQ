package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SignIn extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signIn;
    TextView account;
    TextView forgotPassword;
    Intent i;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");

        requestQueue = Volley.newRequestQueue(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.butSignIn);
        account = (TextView) findViewById(R.id.newAccount);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(SignIn.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void onSucceedeed() {
        Toast.makeText(SignIn.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    public void onFailed() {
        Toast.makeText(SignIn.this, "Password/NIM salah", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
