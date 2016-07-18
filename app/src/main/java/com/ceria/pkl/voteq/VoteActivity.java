package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity implements ClientCallbackSignIn {

    GridView gridView;
    private ListAdapterResult listAdapterResult;
    List<ResultItem> resultItemList;
    NetworkService networkService;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String titleText= intent.getStringExtra("title");
        String countText= intent.getStringExtra("count");
        String labelText= intent.getStringExtra("status");

        TextView titleView = (TextView)findViewById(R.id.txt_title);
        TextView countView = (TextView)findViewById(R.id.txt_vote_count);
        TextView labelView = (TextView)findViewById(R.id.txt_stat);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_label);

        titleView.setText(titleText);
        countView.setText(countText +" Peoples Voted");
        labelView.setText(labelText);

        SharedPreferences sharedPrefernces = getSharedPreferences(SignIn.token, Context.MODE_PRIVATE);
        String token = sharedPrefernces.getString("token", "");

        networkService = new NetworkService(VoteActivity.this);
        networkService.specificVote(token, "2", VoteActivity.this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        if(labelText.equals("Closed")){
            linearLayout.setBackgroundColor(Color.parseColor("#F44336"));
        }else{

            linearLayout.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

        gridView= (GridView)findViewById(R.id.grid_sementara_count);

        resultItemList = new ArrayList<ResultItem>();

        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));
        listAdapterResult = new ListAdapterResult(resultItemList, getApplicationContext());

        gridView.setAdapter(listAdapterResult);

    }

    private ResultItem get(String title, String value, String percent) {
        return new ResultItem(title, value, percent);
    }

    @Override
    public void onSucceded() {
        Toast.makeText(VoteActivity.this, "yuyu", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onFailed() {
        Toast.makeText(VoteActivity.this, "yiyi", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
