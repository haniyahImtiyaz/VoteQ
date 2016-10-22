package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.presenter.view.SignupView;
import com.ceria.pkl.voteq.presenter.viewinterface.SignupInterface;

/**
 * Created by win 8 on 9/30/2016.
 */
public class SignUp extends AppCompatActivity implements SignupInterface, View.OnClickListener {
    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;
    Button btnSignUp;
    ProgressDialog progressDialog;
    private SignupView presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtEmail = (EditText) findViewById(R.id.txt_email);
        edtPassword = (EditText) findViewById(R.id.txt_password);
        edtConfirmPassword = (EditText) findViewById(R.id.txt_confirm_passoword);
        btnSignUp = (Button) findViewById(R.id.butSignUp);

        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(this);
        presenter = new SignupView(this, this.getApplicationContext());
    }

    @Override
    public void showProgress() {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onEmailSame() {
        Toast.makeText(SignUp.this, "Email has already been taken", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordLess() {
        Toast.makeText(SignUp.this, "Password is too short (minimum is 6 characters)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfirmPassNotMatch() {
        Toast.makeText(SignUp.this, "doesn't match Password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(SignUp.this, "Network Failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        presenter.signUpAuth(edtEmail.getText().toString(), edtPassword.getText().toString(), edtConfirmPassword.getText().toString());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        progressDialog.dismiss();
        super.onPause();
    }
}
