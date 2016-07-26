package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity implements ClientCallbackReset {

    EditText email;
    Button submit;
    Intent i;
    NetworkService networkService;
    ProgressDialog progressDialog;
    private String emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.txt_email_reset);
        submit = (Button) findViewById(R.id.btn_reset);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailText = email.getText().toString();
                networkService = new NetworkService(ForgotPassword.this);
                networkService.resetRequest(emailText, ForgotPassword.this);
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
            }

        });
    }

    @Override
    public void onSucceded() {
        progressDialog.dismiss();
        Toast.makeText(ForgotPassword.this, "Silakan cek email anda", Toast.LENGTH_SHORT).show();
        i = new Intent(ForgotPassword.this, ResetActivity.class);
        i.putExtra("email",emailText);
        startActivity(i);
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        Toast.makeText(ForgotPassword.this, "Tidak dapat melakukan Request", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeout() {
        Toast.makeText(ForgotPassword.this, "Network Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailNotFound() {
        progressDialog.dismiss();
        Toast.makeText(ForgotPassword.this, "Email tidak terdaftar", Toast.LENGTH_SHORT).show();
    }
}
