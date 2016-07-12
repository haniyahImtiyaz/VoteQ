package com.ceria.pkl.voteq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity {

    GridView gridView;
    private ListAdapterResult listAdapterResult;
    List<ResultItem> resultItemList;

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
        countView.setText(countText);
        labelView.setText(labelText);

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


}
