package com.ceria.pkl.voteq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResetActivity extends AppCompatActivity {

    EditText edtCode,edtPassword,edtPasswordConfirm;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        edtCode = (EditText)findViewById(R.id.txt_code);
        edtPassword = (EditText)findViewById(R.id.txt_pwd);
        edtPasswordConfirm = (EditText)findViewById(R.id.txt_confirm_passoword);

        Intent i = getIntent();

        btnReset = (Button)findViewById(R.id.btn_reset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
