package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.presenter.view.GetImageView;
import com.ceria.pkl.voteq.presenter.view.LoginView;
import com.ceria.pkl.voteq.presenter.viewinterface.LoginInterface;

import java.io.File;


public class SignIn extends AppCompatActivity implements LoginInterface, View.OnClickListener {

    public static String token;
    EditText edtEmail;
    EditText edtPassword;
    TextView account;
    TextView forgotPassword;
    TextInputLayout layoutEmail, layoutPassword;
    Intent i;
    ProgressDialog progressDialog;
    private LoginView presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        progressDialog = new ProgressDialog(this);
        edtEmail = (EditText) findViewById(R.id.textEmail);
        edtPassword = (EditText) findViewById(R.id.textPassword);
        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        account = (TextView) findViewById(R.id.newAccount);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        layoutEmail = (TextInputLayout)findViewById(R.id.emailWrapper);
        layoutPassword = (TextInputLayout)findViewById(R.id.passwordWrapper);
        findViewById(R.id.butSignIn).setOnClickListener(this);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(SignIn.this, ForgotPassword.class);
             //   startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(SignIn.this, SignUp.class);
                startActivity(i);
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = edtEmail.getText().toString();
                if (email.isEmpty()) {
                    layoutEmail.setError("Enter Your Email");
                    edtEmail.requestFocus();
                } else {
                    layoutEmail.setErrorEnabled(false);
                }
            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String pwd = edtPassword.getText().toString();
                if (pwd.isEmpty()) {
                    layoutPassword.setError("Enter Your Password");
                    edtPassword.requestFocus();
                } else {
                    layoutPassword.setErrorEnabled(false);
                }
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
    public void setEmailError() {
        layoutEmail.setError("Email cannot be empty");
    }

    @Override
    public void setPasswordError() {
        layoutPassword.setError("Password cannot be empty");
    }

    @Override
    public void onClick(View v) {
            presenter.auth(edtEmail.getText().toString(), edtPassword.getText().toString());
    }

}
