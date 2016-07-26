package com.ceria.pkl.voteq;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        edtCode = (EditText)findViewById(R.id.txt_code);
        edtPassword = (EditText)findViewById(R.id.txt_pwd);
        edtPasswordConfirm = (EditText)findViewById(R.id.txt_pwd_confir);

        Intent i = getIntent();

        btnReset = (Button)findViewById(R.id.btn_reset_pwd);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkService = new NetworkService(ResetActivity.this);
                String codeText = edtCode.getText().toString();
                String pwdtext = edtPassword.getText().toString();
                String pwdConfirText = edtPasswordConfirm.getText().toString();
                networkService.resetPassword(codeText,pwdtext,pwdConfirText, ResetActivity.this);
            }
        });
    }
    @Override
    public void onSucceded() {
        Toast.makeText(ResetActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
        Intent i;
        i = new Intent(ResetActivity.this, SignIn.class);
        startActivity(i);
    }

    @Override
    public void onFailed() {
        Toast.makeText(ResetActivity.this, "Password gagal diubah", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeout() {
        Toast.makeText(ResetActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
    }


}
