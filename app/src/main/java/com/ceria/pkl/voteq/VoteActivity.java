package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity implements ClientCallbackSignIn, ClientCallBackLabel {

    ExpandableHeightGridView gridView;
    private ListAdapterResult listAdapterResult;
    List<ResultItem> resultItemList;
    NetworkService networkService;
    ProgressDialog progressDialog;
    RadioGroup radioGroupVote;
    int countRadioVote;
    TextView textDate;
    Button btnVote, btnResult;
    SeekBar seekBarStatus;
    ScrollView scrollExpand;
    TextView seekStatusText;
    String labelText, token, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView titleView = (TextView) findViewById(R.id.txt_title);
        TextView countView = (TextView) findViewById(R.id.txt_vote_count);
        TextView labelView = (TextView) findViewById(R.id.txt_stat);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_label);
        seekBarStatus = (SeekBar) findViewById(R.id.seekBarStatus);
        textDate = (TextView) findViewById(R.id.txt_date_vote);
        radioGroupVote = (RadioGroup) findViewById(R.id.radio_group_vote);
        btnVote = (Button) findViewById(R.id.btn_submit_vote);
        btnResult = (Button) findViewById(R.id.btn_result);
        seekStatusText = (TextView) findViewById(R.id.seek_status_text);
        scrollExpand = (ScrollView)findViewById(R.id.scrollExpand);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        final String titleText = intent.getStringExtra("title");
        final String countText = intent.getStringExtra("count");
        labelText = intent.getStringExtra("status");
        String creator_id = intent.getStringExtra("creator_id");

        countRadioVote = Integer.parseInt(countText);
        visibleButton(labelText);

        SharedPreferences sharedPrefernces = getSharedPreferences(SignIn.token, Context.MODE_PRIVATE);
        token = sharedPrefernces.getString("token", "");

        networkService = new NetworkService(VoteActivity.this);
        networkService.specificVote(token, id, VoteActivity.this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        if (labelText.equals("Closed")) {
            seekStatusText.setBackgroundColor(Color.parseColor("#F44336"));
            seekBarStatus.setProgress(1);
        } else {
            seekStatusText.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

        if (creator_id.equals(token)) {
            seekBarStatus.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
        } else {
            seekBarStatus.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

        seekStatusText.setText(labelText);

        gridView = (ExpandableHeightGridView) findViewById(R.id.grid_sementara_count);
        gridView.setExpanded(true);
        titleView.setText(titleText);
        countView.setText(countText + " Peoples Voted");
        labelView.setText(labelText);

        seekBarStatus.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBarStatus.getProgress() == 1) {
                    resultItemList.clear();
                    radioGroupVote.removeAllViews();
                    networkService.updateLabel(token, id, titleText, false, VoteActivity.this);
                } else {
                    resultItemList.clear();
                    radioGroupVote.removeAllViews();
                    networkService.updateLabel(token, id, titleText, true, VoteActivity.this);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkService.givingVote(token, id, radioGroupVote.getCheckedRadioButtonId(), VoteActivity.this);
                VoteList.listItem = new ArrayList<HomeItem>();
                MyVoteList.listItem = new ArrayList<HomeItem>();
                Intent i = new Intent(VoteActivity.this, HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoteActivity.this, ResultActivity.class);
                intent.putExtra("title",titleText);
                intent.putExtra("count",countText );
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
    }




    @Override
    public void onSucceded() {
        resultItemList = networkService.getResultItemList();
        listAdapterResult = new ListAdapterResult(resultItemList, VoteActivity.this);
        gridView.setAdapter(listAdapterResult);

        if (resultItemList.size() > 2) {

        }

        //getDateFormat from network Service
        textDate.setText("Since " + networkService.getDate());

        //Create Radio Button to populate vote options
        for (int i = 0; i < resultItemList.size(); i++) {
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

    @Override
    public void succes() {
        if (seekStatusText.getText().toString().equals("Open")) {
            seekStatusText.setText("Closed");
            labelText = "Closed";
            seekStatusText.setBackgroundColor(Color.parseColor("#F44336"));
        } else {
            seekStatusText.setText("Open");
            labelText = "Open";
            seekStatusText.setBackgroundColor(Color.parseColor("#4CAF50"));
        }
        visibleButton(labelText);
        seekStatusText.setText(labelText);
        networkService.specificVote(token, id, VoteActivity.this);
    }

    @Override
    public void fail() {
        Toast.makeText(VoteActivity.this, "Failure update status", Toast.LENGTH_SHORT).show();
    }

    private void visibleButton(String label) {
        if (label.equals("Open")) {
            btnResult.setVisibility(View.GONE);
            radioGroupVote.setVisibility(View.VISIBLE);
            btnVote.setVisibility(View.VISIBLE);

        } else {
            btnResult.setVisibility(View.VISIBLE);
            radioGroupVote.setVisibility(View.GONE);
            btnVote.setVisibility(View.GONE);
        }
    }
}
