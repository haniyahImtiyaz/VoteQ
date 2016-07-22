package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements ClientCallbackSignIn {
    private TextView textPercentCircle, textResult, textTitle, textValueVote;
    private ExpandableHeightListView expandableListView;
    private ListAdapterResult listAdapterResult;
    List<ResultItem> resultItemList;
    NetworkService networkService;
    ProgressDialog progressDialog;
    String token, id;


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
        id = intent.getStringExtra("id");
        String title = intent.getStringExtra("title");
        String count = intent.getStringExtra("count");

        textPercentCircle = (TextView) findViewById(R.id.textPercentCircle);
        textResult = (TextView) findViewById(R.id.textResult);
        textTitle = (TextView) findViewById(R.id.textTitleResult);
        textValueVote = (TextView) findViewById(R.id.textValueVote);
        expandableListView = (ExpandableHeightListView) findViewById(R.id.list_result);

        resultItemList = new ArrayList<ResultItem>();

        textTitle.setText(title);
        textValueVote.setText(count + " votes");

        expandableListView.setExpanded(true);

        SharedPreferences sharedPrefernces = getSharedPreferences(SignIn.token, Context.MODE_PRIVATE);
        token = sharedPrefernces.getString("token", "");

        networkService = new NetworkService(ResultActivity.this);
        networkService.specificVote(token, id, ResultActivity.this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

    }

    private ResultItem get(String id, String title, String value, String percent) {
        return new ResultItem(id, title, value, percent);
    }

    @Override
    public void onSucceded() {
        progressDialog.dismiss();
        resultItemList = networkService.getResultItemList();
        listAdapterResult = new ListAdapterResult(resultItemList, this);
        expandableListView.setAdapter(listAdapterResult);
        for (int i = 0; i < resultItemList.size(); i++) {

            Collections.sort(resultItemList, new Comparator<ResultItem>() {
                @Override
                public int compare(ResultItem lhs, ResultItem rhs) {
                    return rhs.getTextPercent().compareTo(lhs.getTextPercent());
                }
            });
        }
        String max = resultItemList.get(0).getTextPercent();
        textPercentCircle.setText(resultItemList.get(0).getTextPercent() + "%");
        textResult.setText(resultItemList.get(0).getTextTitle());
        int i = 1;
        int sizeText = 25;
        while (resultItemList.get(i).getTextPercent().equals(max) ){
            if (i == resultItemList.size()-1){
                break;
            }else {
                textResult.setText(textResult.getText() + "/" + resultItemList.get(i).getTextTitle());
                i++;
                sizeText = sizeText - 2;
                textResult.setTextSize(TypedValue.COMPLEX_UNIT_DIP,sizeText);
            }
        }

    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        Toast.makeText(ResultActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
        networkService.specificVote(token, id, ResultActivity.this);
        progressDialog.show();
    }
}
