package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetActivity extends AppCompatActivity implements ClientCallbackSignIn {

    EditText edtCode,edtPassword,edtPasswordConfirm;
    Button btnReset;
    NetworkService networkService;
    ProgressDialog progressDialog;
    String emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        edtCode = (EditText)findViewById(R.id.txt_code);
        edtPassword = (EditText)findViewById(R.id.txt_pwd);
        edtPasswordConfirm = (EditText)findViewById(R.id.txt_pwd_confir);

        Intent intent = getIntent();
        emailText=intent.getStringExtra("email");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");

        btnReset = (Button)findViewById(R.id.btn_reset_pwd);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkService = new NetworkService(ResetActivity.this);
                String codeText = edtCode.getText().toString();
                String pwdtext = edtPassword.getText().toString();
                String pwdConfirText = edtPasswordConfirm.getText().toString();
                if(pwdtext.length()<6 || pwdConfirText.length()<6){
                    Toast.makeText(ResetActivity.this, "Password must contain 6 string or more", Toast.LENGTH_SHORT).show();
                }else{
                    if (pwdtext.equals(pwdConfirText)){

                        networkService.resetPassword(codeText,pwdtext,pwdConfirText, emailText, ResetActivity.this);
                        progressDialog.show();
                        progressDialog.setCanceledOnTouchOutside(false);
                    }else{
                        Toast.makeText(ResetActivity.this, "Password didn't match", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

            }
        });
    }
    @Override
    public void onSucceded() {
        progressDialog.dismiss();
        Toast.makeText(ResetActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ResetActivity.this, SignIn.class);
        startActivity(i);
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        Toast.makeText(ResetActivity.this, "Password gagal diubah", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeout() {
        Toast.makeText(ResetActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
    }
}
