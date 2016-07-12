package com.ceria.pkl.voteq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;
    Button btnSignUp;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = (EditText) findViewById(R.id.txt_email);
        edtPassword = (EditText) findViewById(R.id.txt_password);
        edtConfirmPassword = (EditText)findViewById(R.id.txt_confirm_passoword);
        btnSignUp = (Button) findViewById(R.id.butSignUp);
    }
}
