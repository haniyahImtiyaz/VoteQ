package com.ceria.pkl.voteq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

        textPercentCircle = (TextView) findViewById(R.id.textPercentCircle);
        textResult = (TextView) findViewById(R.id.textResult);
        textTitle = (TextView) findViewById(R.id.textTitleResult);
        textValueVote = (TextView) findViewById(R.id.textValueVote);
        expandableListView = (ExpandableHeightListView) findViewById(R.id.list_result);

        resultItemList = new ArrayList<ResultItem>();

        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));
        resultItemList.add(get("Merah Muda", "1200 Votes", "55%"));

        listAdapterResult = new ListAdapterResult(resultItemList, this);
        expandableListView.setAdapter(listAdapterResult);

        expandableListView.setExpanded(true);

    }

    private ResultItem get(String title, String value, String percent) {
        return new ResultItem(title, value, percent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
