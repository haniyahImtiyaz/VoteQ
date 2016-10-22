package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ceria.pkl.voteq.ForgotPassword;
import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.presenter.view.LoginView;
import com.ceria.pkl.voteq.presenter.viewinterface.LoginInterface;


public class SignIn extends AppCompatActivity implements LoginInterface, View.OnClickListener {

    public static String token;
    EditText edtEmail;
    EditText edtPassword;
    TextView account;
    TextView forgotPassword;
    Intent i;
    ProgressDialog progressDialog;
    private LoginView presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        progressDialog = new ProgressDialog(this);
        edtEmail = (EditText) findViewById(R.id.txt_email);
        edtPassword = (EditText) findViewById(R.id.txt_password);
        account = (TextView) findViewById(R.id.newAccount);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        findViewById(R.id.butSignIn).setOnClickListener(this);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });

        presenter = new LoginView(this, this.getApplicationContext());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "");
        Log.d("logToken", "t : " + token);
        if (!token.isEmpty()) {
            navigateToHome();
        }

    }

    @Override
    protected void onPause() {
        progressDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        hideProgress();
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressDialog.setMessage("Please Wait ....");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void setCredentialError() {
        Toast.makeText(SignIn.this, "Invalid Email or Password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }


    @Override
    public void onNetworkFailure() {
        Toast.makeText(SignIn.this, "Network Failure Login", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        presenter.auth(edtEmail.getText().toString(), edtPassword.getText().toString());
    }
}
