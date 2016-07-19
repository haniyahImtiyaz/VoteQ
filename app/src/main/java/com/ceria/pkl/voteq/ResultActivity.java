package com.ceria.pkl.voteq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private TextView textPercentCircle, textResult, textTitle, textValueVote;
    private ExpandableHeightListView expandableListView;
    private ListAdapterResult listAdapterResult;
    List<ResultItem> resultItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Result");

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();

        textPercentCircle = (TextView) findViewById(R.id.textPercentCircle);
        textResult = (TextView) findViewById(R.id.textResult);
        textTitle = (TextView) findViewById(R.id.textTitleResult);
        textValueVote = (TextView) findViewById(R.id.textValueVote);
        expandableListView = (ExpandableHeightListView) findViewById(R.id.list_result);

        resultItemList = new ArrayList<ResultItem>();


        resultItemList.add(get("1","Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("2","Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("3","Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("4","Merah Muda", "1200 Votes", "55%"));

        listAdapterResult = new ListAdapterResult(resultItemList, this);
        expandableListView.setAdapter(listAdapterResult);

        expandableListView.setExpanded(true);

    }

    private ResultItem get(String id, String title, String value, String percent) {
        return new ResultItem(id,title, value, percent);
    }
}
