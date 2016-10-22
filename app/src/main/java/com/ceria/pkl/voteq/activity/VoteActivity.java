package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceria.pkl.voteq.ClientCallBackLabel;
import com.ceria.pkl.voteq.ClientCallbackCancel;
import com.ceria.pkl.voteq.ClientCallbackSignIn;
import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.ResultActivity;
import com.ceria.pkl.voteq.adapter.ListAdapterResult;
import com.ceria.pkl.voteq.itemAdapter.ResultItem;
import com.ceria.pkl.voteq.models.NetworkService;
import com.ceria.pkl.voteq.presenter.view.SpecificVoteView;
import com.ceria.pkl.voteq.presenter.view.VotingView;
import com.ceria.pkl.voteq.presenter.viewinterface.VotingInterface;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity extends AppCompatActivity implements VotingInterface, View.OnClickListener,
        ClientCallBackLabel, ClientCallbackCancel, ClientCallbackSignIn {

    ExpandableHeightGridView gridView;
    List<ResultItem> resultItemList;
    NetworkService networkService;
    ProgressDialog progressDialog;
    RadioGroup radioGroupVote;
    int countRadioVote, position, option_id;
    TextView textDate;
    Button btnVote, btnResult;
    ScrollView scrollExpand;
    TextView seekStatusText;
    String labelText, token, id, creator_id, titleText, countText, fragment;
    Snackbar snackbar;
    LinearLayout linearLayout;
    SwitchCompat switchCompat;
    Button btnCancelVote;
    Button btnReload;
    SharedPreferences sharedPreferences;
    Boolean voted;
    private ListAdapterResult listAdapterResult;
    private SpecificVoteView presenter;
    private VotingView presenterVoting;

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
        final TextView labelView = (TextView) findViewById(R.id.txt_stat);
        linearLayout = (LinearLayout) findViewById(R.id.layout_label);
        textDate = (TextView) findViewById(R.id.txt_date_vote);
        radioGroupVote = (RadioGroup) findViewById(R.id.radio_group_vote);
        btnVote = (Button) findViewById(R.id.btn_submit_vote);
        btnVote.setOnClickListener(this);
        btnResult = (Button) findViewById(R.id.btn_result);
        seekStatusText = (TextView) findViewById(R.id.seek_status_text);
        scrollExpand = (ScrollView) findViewById(R.id.scrollExpand);
        switchCompat = (SwitchCompat) findViewById(R.id.compatSwitch);
        btnCancelVote = (Button) findViewById(R.id.btn_cancel_vote);
        btnReload = (Button) findViewById(R.id.btn_reload);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        titleText = intent.getStringExtra("title");
        countText = intent.getStringExtra("count");
        labelText = intent.getStringExtra("status");
        creator_id = intent.getStringExtra("creator_id");
        position = intent.getIntExtra("position", 0);
        fragment = intent.getStringExtra("fragment");

        btnCancelVote.setVisibility(View.GONE);
        btnReload.setVisibility(View.GONE);

        countRadioVote = Integer.parseInt(countText);
        visibleButton(labelText);

        gridView = (ExpandableHeightGridView) findViewById(R.id.grid_sementara_count);
        gridView.setExpanded(true);
        titleView.setText(titleText);
        countView.setText(countText + " Peoples Voted");
        labelView.setText(labelText);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "");

        progressDialog = new ProgressDialog(this);
        presenter = new SpecificVoteView(this);

        option_id = 0;
        load();

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                radioGroupVote.removeAllViews();
                resultItemList.clear();
                if (isChecked) {
                    networkService.updateLabel(token, id, titleText, true, VoteActivity.this);
                    labelText = "Open";
                    if (fragment.equals("voteList")) {
                        VoteLists.listItem.get(position).setLabel("Open");
                        HomeActivity.homeAdapter.notifyDataSetChanged();
                    } else {
                        MyVoteLists.listItem.get(position).setLabel("Open");
                        HomeActivity.homeAdapter2.notifyDataSetChanged();
                    }
                } else {
                    networkService.updateLabel(token, id, titleText, false, VoteActivity.this);
                    labelText = "Closed";
                    if (fragment.equals("voteList")) {
                        VoteLists.listItem.get(position).setLabel("Closed");
                        HomeActivity.homeAdapter.notifyDataSetChanged();
                    } else {
                        MyVoteLists.listItem.get(position).setLabel("Closed");
                        HomeActivity.homeAdapter2.notifyDataSetChanged();
                    }
                }
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoteActivity.this, ResultActivity.class);
                intent.putExtra("title", titleText);
                intent.putExtra("count", countText);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        btnCancelVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkService networkService = new NetworkService(VoteActivity.this);
                networkService.cancelVoted(token, id, VoteActivity.this);
                progressDialog.show();
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });

    }

    private void load() {
        presenter.callSpecificVote(token, id);
        if (labelText.equals("Closed")) {
            seekStatusText.setBackgroundColor(Color.parseColor("#F44336"));
            switchCompat.setChecked(false);
        } else {
            seekStatusText.setBackgroundColor(Color.parseColor("#4CAF50"));
            switchCompat.setChecked(true);
        }

        if (creator_id.equals(token)) {
            switchCompat.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
        } else {
            switchCompat.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

        seekStatusText.setText(labelText);
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
        presenter.callSpecificVote(token, id);
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

    @Override
    public void onSuccessCancelVote() {
        Toast.makeText(VoteActivity.this, "Success Cancel Your Votes", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        VoteLists.listItem = new ArrayList<>();
        MyVoteLists.listItem = new ArrayList<>();
        Intent i = new Intent(VoteActivity.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    @Override
    public void onFailedCancelVotes() {
        Toast.makeText(VoteActivity.this, "Failur to cancel your Vote", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void showProgress() {
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void setCredentialError() {
        Toast.makeText(VoteActivity.this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(VoteActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        VoteLists.listItem = new ArrayList<>();
        MyVoteLists.listItem = new ArrayList<>();
        Intent i = new Intent(VoteActivity.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onSuccedeedGetVote() {
        resultItemList = presenter.resultItemList;
        listAdapterResult = new ListAdapterResult(resultItemList, VoteActivity.this);
        gridView.setAdapter(listAdapterResult);

        //getDateFormat from network Service
        textDate.setText("Since " + presenter.date);

        //Create Radio Button to populate vote options
        for (int i = 0; i < resultItemList.size(); i++) {
            RadioButton radioButtonVote = new RadioButton(this);
            radioButtonVote.setId(Integer.parseInt(resultItemList.get(i).getTextId()));
            radioButtonVote.setText(resultItemList.get(i).getTextTitle());
            radioGroupVote.addView(radioButtonVote);
        }

        if (presenter.voted_option_id != null && labelText.equals("Open")) {
            voted = true;
            option_id = Integer.parseInt(presenter.voted_option_id);
            radioGroupVote.clearCheck();
            radioGroupVote.check(option_id);
            btnCancelVote.setVisibility(View.VISIBLE);
        } else {
            voted = false;
            btnCancelVote.setVisibility(View.INVISIBLE);
        }
        presenterVoting = new VotingView(this, voted);
        btnReload.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (radioGroupVote.getCheckedRadioButtonId() == -1) {
            new AlertDialog.Builder(VoteActivity.this)
                    .setMessage("Please check one option to continue!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else {
            presenterVoting.callVoting(token, id, String.valueOf(radioGroupVote.getCheckedRadioButtonId()));
            snackbar = Snackbar.make(v, "Network Failure", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                    presenterVoting.callVoting(token, id, String.valueOf(radioGroupVote.getCheckedRadioButtonId()));
                }
            });
        }

    }

    @Override
    public void onSucceded() {

    }

    @Override
    public void onFailed() {

    }

    @Override
    public void onTimeout() {

    }
}
