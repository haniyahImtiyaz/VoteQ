package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceria.pkl.voteq.models.NetworkService;

public class SignUp extends AppCompatActivity implements ClientCallback {
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;
    Button btnSignUp;
    ProgressDialog progressDialog;
    NetworkService networkService;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = (EditText) findViewById(R.id.txt_email);
        edtPassword = (EditText) findViewById(R.id.txt_password);
        edtConfirmPassword = (EditText) findViewById(R.id.txt_confirm_passoword);
        btnSignUp = (Button) findViewById(R.id.butSignUp);

        networkService = new NetworkService(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        sharedPreferences = getSharedPreferences("TOKEN_USER", Context.MODE_PRIVATE);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().isEmpty()) {
                    Toast.makeText(SignUp.this, "Please, fill the Email to continue!", Toast.LENGTH_LONG).show();
                } else if (!isEmailValid(edtEmail.getText().toString())) {
                    Toast.makeText(SignUp.this, "Sorry, Email is invalid", Toast.LENGTH_LONG).show();
                } else if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                    Toast.makeText(SignUp.this, "Sorry, password didn't match", Toast.LENGTH_LONG).show();
                } else if (edtPassword.length() < 6) {
                    Toast.makeText(SignUp.this, "Sorry, password must have at least 6 characters", Toast.LENGTH_LONG).show();
                } else {
                    networkService.signUp(edtEmail.getText().toString(), edtPassword.getText().toString(), edtConfirmPassword.getText().toString(), SignUp.this);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            }
        });
    }

    public boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    @Override
    public void onSucceeded() {
        progressDialog.dismiss();
        String token = networkService.getAuth_token();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed() {
        Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onEmailSame() {
        Toast.makeText(this, "Sorry, Email has already been taken or Email invalid", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }
}


