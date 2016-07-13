package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements ClientCallback {
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;
    Button btnSignUp;
    Intent i;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = (EditText) findViewById(R.id.txt_email);
        edtPassword = (EditText) findViewById(R.id.txt_password);
        edtConfirmPassword = (EditText)findViewById(R.id.txt_confirm_passoword);
        btnSignUp = (Button) findViewById(R.id.butSignUp);

        final NetworkService networkService = new NetworkService(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkService.signUp(edtEmail.getText().toString(), edtPassword.getText().toString(),edtConfirmPassword.getText().toString(), SignUp.this);
                progressDialog.show();
            }
        });
    }

    @Override
    public void onSucceeded() {
        Toast.makeText(this, "Sign Up Success", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
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
        Toast.makeText(this, "Email has already been taken", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}


