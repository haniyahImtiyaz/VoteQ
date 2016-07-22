package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;


public class SignIn extends AppCompatActivity implements ClientCallbackSignIn {

    EditText edtEmail;
    EditText edtPassword;
    Button signIn;
    TextView account;
    TextView forgotPassword;
    Intent i;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    public static String token;
    NetworkService networkService;
    SharedPreferences sharedPreferences;

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
                if (edtEmail.getText().toString().isEmpty()){
                    new AlertDialog.Builder(SignIn.this).setMessage("Please fill email to continue!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else if (edtPassword.getText().toString().isEmpty()){
                    new AlertDialog.Builder(SignIn.this).setMessage("Please fill password to continue!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else {
                    networkService.login(edtEmail.getText().toString(), edtPassword.getText().toString(), SignIn.this);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            }
        });

        sharedPreferences = getSharedPreferences(token, Context.MODE_PRIVATE);
        String auth_token = sharedPreferences.getString("token", "");
        if (!auth_token.isEmpty()) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }


    public void onSucceded() {
        progressDialog.dismiss();
        token = networkService.getAuth_token();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
        VoteList.listItem.clear();
        MyVoteList.listItem.clear();
        i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    public void onFailed() {
        Toast.makeText(SignIn.this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
}
