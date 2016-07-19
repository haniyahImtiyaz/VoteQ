package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioGroup radioGroupVote;
    int countRadioVote;
    TextView textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String titleText= intent.getStringExtra("title");
        String countText= intent.getStringExtra("count");
        String labelText= intent.getStringExtra("status");

        TextView titleView = (TextView)findViewById(R.id.txt_title);
        TextView countView = (TextView)findViewById(R.id.txt_vote_count);
        TextView labelView = (TextView)findViewById(R.id.txt_stat);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout_label);
        radioGroupVote = (RadioGroup)findViewById(R.id.radio_group_vote);
        countRadioVote = Integer.parseInt(countText);

        SharedPreferences sharedPrefernces = getSharedPreferences(SignIn.token, Context.MODE_PRIVATE);
        String token = sharedPrefernces.getString("token", "");

        networkService = new NetworkService(VoteActivity.this);
        networkService.specificVote(token, id, VoteActivity.this);

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

        resultItemList.add(get("1","Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("2","Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("3","Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("4","Merah Muda", "1200 Votes", "55%"));

        titleView.setText(titleText);
        countView.setText(countText +" Peoples Voted");
        labelView.setText(labelText);
    }

    private ResultItem get(String id, String title, String value, String percent) {
        return new ResultItem(id, title, value, percent);
    }

    @Override
    public void onSucceded() {
        resultItemList = networkService.getResultItemList();
        listAdapterResult = new ListAdapterResult(resultItemList, VoteActivity.this);
        gridView.setAdapter(listAdapterResult);
        textDate = (TextView)findViewById(R.id.txt_vote_date);

        //getDateFormat from network Service
        textDate.setText(networkService.getDate());

        //Create Radio Button to populate vote options
        for (int i=0;i<resultItemList.size();i++){
            RadioButton radioButtonVote = new RadioButton(this);
            radioButtonVote.setId(Integer.parseInt(resultItemList.get(i).getTextId()));
            radioButtonVote.setText(resultItemList.get(i).getTextTitle());
            radioGroupVote.addView(radioButtonVote);
        }
        progressDialog.dismiss();
    }

    @Override
    public void onFailed() {
        Toast.makeText(VoteActivity.this, "Failure", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}
