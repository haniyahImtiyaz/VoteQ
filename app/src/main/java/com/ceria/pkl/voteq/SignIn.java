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


public class SignIn extends AppCompatActivity implements ClientCallbackSignIn{

    EditText edtEmail;
    EditText edtPassword;
    Button signIn;
    TextView account;
    TextView forgotPassword;
    Intent i;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String token;
    NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        networkService = new NetworkService(this);
        edtEmail = (EditText) findViewById(R.id.txt_email);
        edtPassword = (EditText) findViewById(R.id.txt_password);
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
                networkService.login(edtEmail.getText().toString(), edtPassword.getText().toString(), SignIn.this);
                progressDialog.show();
            }
        });}


    public void onSucceded() {
        Toast.makeText(SignIn.this, "Sign in success", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        i = new Intent(SignIn.this, HomeActivity.class);
        token = networkService.getAuth_token();
        i.putExtra("token",token);
        startActivity(i);
    }

    public void onFailed() {
        Toast.makeText(SignIn.this, "Sign in failure", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
