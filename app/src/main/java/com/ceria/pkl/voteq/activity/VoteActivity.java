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
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.adapter.ListAdapterOption;
import com.ceria.pkl.voteq.adapter.ListAdapterResult;
import com.ceria.pkl.voteq.itemAdapter.OptionItem;
import com.ceria.pkl.voteq.itemAdapter.ResultItem;
import com.ceria.pkl.voteq.itemAdapter.VoteItem;
import com.ceria.pkl.voteq.models.NetworkService;
import com.ceria.pkl.voteq.presenter.view.CancelVoteView;
import com.ceria.pkl.voteq.presenter.view.DetailVoteView;
import com.ceria.pkl.voteq.presenter.view.UpdateStatusView;
import com.ceria.pkl.voteq.presenter.view.VotingView;
import com.ceria.pkl.voteq.presenter.viewinterface.VotingInterface;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightGridView;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.List;

public class VoteActivity
        extends AppCompatActivity implements VotingInterface, View.OnClickListener
{

    ExpandableHeightGridView gridView;
    List<OptionItem> optionItemList;
    VoteItem voteItem = new VoteItem();
    NetworkService networkService;
    ProgressDialog progressDialog;
    int position, option_id;
    TextView voteUsername, voteDate, voteTitle, voteStatus, voteCategory, voteDescription, voteResponder;
    ImageView voteImage, userImage;
    Button btnVote;
    String labelText, token, id, creator_id, titleText, countText, fragment;
    Snackbar snackbar;
    ExpandableHeightListView listViewOption;
    SharedPreferences sharedPreferences;
    Boolean voted;
    ListAdapterOption listAdapterOption;
    private DetailVoteView presenter;
    private VotingView presenterVoting;
    private CancelVoteView presenterCancelVote;
    private UpdateStatusView presenterUpdateStatus;

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

        voteUsername = (TextView)findViewById(R.id.user);
        voteDate = (TextView)findViewById(R.id.date);
        voteTitle = (TextView)findViewById(R.id.voteTitle);
        voteStatus = (TextView)findViewById(R.id.labelStatus);
        voteCategory = (TextView)findViewById(R.id.voteCategory);
        voteDescription = (TextView)findViewById(R.id.voteDescription);
        voteResponder = (TextView)findViewById(R.id.voteResponder);
        btnVote = (Button)findViewById(R.id.btn_submit_vote);
        voteImage = (ImageView)findViewById(R.id.voteImage);
        userImage = (ImageView)findViewById(R.id.image_circle);

        listViewOption = (ExpandableHeightListView) findViewById(R.id.listOption);
        listViewOption.setExpanded(true);
        listViewOption.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listViewOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listAdapterOption.setSelectedIndex(position);
                listAdapterOption.notifyDataSetChanged();
                option_id = position + 1;
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        titleText = intent.getStringExtra("title");
        countText = intent.getStringExtra("count");
        labelText = intent.getStringExtra("status");
        creator_id = intent.getStringExtra("creator_id");
        position = intent.getIntExtra("position", 0);
        fragment = intent.getStringExtra("fragment");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "");

        progressDialog = new ProgressDialog(this);
        presenter = new DetailVoteView(this);
        presenterVoting = new VotingView(this, false);
        presenterCancelVote = new CancelVoteView(this);
        presenterUpdateStatus = new UpdateStatusView(this);

        presenter.callDetailVote(id);


    }


//    @Override
//    public void succes() {
//        if (seekStatusText.getText().toString().equals("Open")) {
//            seekStatusText.setText("Closed");
//            labelText = "Closed";
//            seekStatusText.setBackgroundColor(Color.parseColor("#F44336"));
//        } else {
//            seekStatusText.setText("Open");
//            labelText = "Open";
//            seekStatusText.setBackgroundColor(Color.parseColor("#4CAF50"));
//        }
//        visibleButton(labelText);
//        seekStatusText.setText(labelText);
//        presenter.callSpecificVote(token, id);
//    }


    @Override
    public void showProgress() {
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
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
        VoteList.listItem = new ArrayList<>();
        MyVoteList.listItem = new ArrayList<>();
        Intent i = new Intent(VoteActivity.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void onSucceededGetVote() {
        optionItemList = presenter.getOptionItemList();
        voteItem = presenter.getVoteItem();
        Log.d("hai", voteItem.toString());
        listAdapterOption = new ListAdapterOption(presenter.getOptionItemList(), this);
        listViewOption.setAdapter(listAdapterOption);
        voteUsername.setText(voteItem.getUsername());
        voteDate.setText(voteItem.getStarted() + " - " + voteItem.getEnded());
        voteTitle.setText(voteItem.getTitle());
        voteCategory.setText(voteItem.getCategory());
        voteStatus.setText(voteItem.getStatus());
        voteDescription.setText(voteItem.getDescription());
        voteResponder.setText(voteItem.getResponder() + "responder");
        LazyHeaders.Builder builder = new LazyHeaders.Builder()
                .addHeader("Authorization", "Token token=" + token);
        GlideUrl glideUrl = new GlideUrl("https://electa-engine.herokuapp.com" + voteItem.getUserImage(), builder.build());

        Glide.with(this)
                .load(glideUrl)
                .into(userImage);

        if (voteItem.getVoteImage() == "") {
            String lowerTitle = voteItem.getTitle().toLowerCase();
            char lowerTitleFirst = lowerTitle.charAt(0);
            if (Character.isDigit(lowerTitleFirst)) {
                String imageHold = "activity_card" + String.valueOf(lowerTitleFirst);
                voteImage.setImageResource(this.getResources().getIdentifier(imageHold, "drawable", this.getPackageName()));
            } else {
                voteImage.setImageResource(this.getResources().getIdentifier(String.valueOf(lowerTitleFirst), "drawable", this.getPackageName()));
            }
        } else {
            Glide.with(this)
                    .load(voteItem.getVoteImage())
                    .into(voteImage);
        }
//        //listAdapterResult = new ListAdapterResult(resultItemList, VoteActivity.this);
        //gridView.setAdapter(listAdapterResult);
//        if(presenter.voted_option_id == null){
//            voted = false;
//        }else{
//            voted = true;
//        }
//        presenterVoting = new VotingView(this, voted);
//        textDate.setText("Since " + presenter.date);

//        //Create Radio Button to populate vote options
//        for (int i = 0; i < optionItemList.size(); i++) {
//            RadioButton radioButtonVote = new RadioButton(this);
//            radioButtonVote.setId(i+1);
//            radioButtonVote.setText(optionItemList.get(i).getTitle());
//            ImageView imageView = new ImageView(this);
//            imageView.setImageResource(R.mipmap.ic_launcher);
//            radioGroupVote.addView(radioButtonVote);
//        }

//        if (presenter.voted_option_id != null && labelText.equals("Open")) {
//            option_id = Integer.parseInt(presenter.voted_option_id);
//            radioGroupVote.clearCheck();
//            radioGroupVote.check(option_id);
//            btnCancelVote.setVisibility(View.VISIBLE);
//        } else {
//            btnCancelVote.setVisibility(View.INVISIBLE);
//        }
//        btnReload.setVisibility(View.GONE);
    }

    @Override
    public void onSucceededCancelVote() {
//        radioGroupVote.removeAllViews();
//        resultItemList.clear();
//        presenter = new DetailVoteView(this);
//        presenter.callSpecificVote(id);
//        int count = Integer.parseInt(countText) - 1;
//        if(fragment == "voteList"){
//            VoteList.listItem.get(position).setTextCount(String.valueOf(count));
//            HomeActivity.homeAdapter.notifyDataSetChanged();
//        }else{
//            MyVoteList.listItem.get(position).setTextCount(String.valueOf(count));
//            HomeActivity.homeAdapter2.notifyDataSetChanged();
//        }
//        Toast.makeText(VoteActivity.this, "Success Cancel Your Votes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSucceededUpdateStatus() {
//        if (presenterUpdateStatus.is_open == false) {
//            seekStatusText.setText("Closed");
//            seekStatusText.setBackgroundColor(Color.parseColor("#F44336"));
//            switchCompat.setChecked(false);
//            radioGroupVote.setVisibility(View.GONE);
//            btnResult.setVisibility(View.VISIBLE);
//            if (fragment.equals("voteList")) {
//                VoteList.listItem.get(position).setLabel("Open");
//                HomeActivity.homeAdapter.notifyDataSetChanged();
//            } else {
//                MyVoteList.listItem.get(position).setLabel("Open");
//                HomeActivity.homeAdapter2.notifyDataSetChanged();
//            }
//        } else {
//            seekStatusText.setText("Open");
//            seekStatusText.setBackgroundColor(Color.parseColor("#4CAF50"));
//            switchCompat.setChecked(true);
//            radioGroupVote.setVisibility(View.VISIBLE);
//            btnResult.setVisibility(View.GONE);
//            if (fragment.equals("voteList")) {
//                VoteList.listItem.get(position).setLabel("Closed");
//                HomeActivity.homeAdapter.notifyDataSetChanged();
//            } else {
//                MyVoteList.listItem.get(position).setLabel("Closed");
//                HomeActivity.homeAdapter2.notifyDataSetChanged();
//            }
//        }
    }

    @Override
    public void onClick(View v) {
            presenterVoting.callVoting("Token token="+token, voteItem.getId(),option_id);
            snackbar = Snackbar.make(v, "Network Failure", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                  //  presenterVoting.callVoting(token, id, String.valueOf(radioGroupVote.getCheckedRadioButtonId()));
                }
            });


    }
}
