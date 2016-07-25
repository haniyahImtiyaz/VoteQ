package com.ceria.pkl.voteq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity implements ClientCallbackSignIn, ClientCallbackReset {

    EditText email;
    Button submit;
    Intent i;
    NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.txt_email_reset);
        submit = (Button) findViewById(R.id.btn_reset);
        final String emailText = email.getText().toString();

        networkService = new NetworkService(ForgotPassword.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkService.resetRequest(emailText, ForgotPassword.this);
            }

        });
    }

    @Override
    public void onSucceded() {
        Toast.makeText(ForgotPassword.this, "Silakan cek email anda", Toast.LENGTH_SHORT).show();
        i = new Intent(ForgotPassword.this, ResetActivity.class);
        startActivity(i);
    }

    @Override
    public void onFailed() {
        Toast.makeText(ForgotPassword.this, "Tidak dapat melakukan Request", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailNotFound() {
        Toast.makeText(ForgotPassword.this, "Email tidak terdaftar", Toast.LENGTH_SHORT).show();
    }
}
