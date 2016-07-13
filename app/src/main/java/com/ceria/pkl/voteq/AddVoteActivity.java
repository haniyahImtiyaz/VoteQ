package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.List;

public class AddVoteActivity extends AppCompatActivity implements ClientCallbackSignIn {

    private EditText editTextTitle, editTextOption;
    private Button buttonAddOption, buttonDone;
    private ListAdapterOption listAdapterOption;
    private ExpandableHeightListView expandableListView;
    List<OptionItem> optionItemList;
    List<String> listOption;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Vote");

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextOption = (EditText) findViewById(R.id.editTextOption);
        buttonAddOption = (Button) findViewById(R.id.buttonAddOp);
        buttonDone = (Button) findViewById(R.id.buttonDone);
        expandableListView = (ExpandableHeightListView) findViewById(R.id.expandable_listview);

        optionItemList = new ArrayList<OptionItem>();
        listAdapterOption = new ListAdapterOption(optionItemList, this);
        expandableListView.setAdapter(listAdapterOption);

        expandableListView.setExpanded(true);

        buttonAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextOption.getText().toString().trim().length() == 0) {
                    new AlertDialog.Builder(AddVoteActivity.this)
                            .setMessage("please, fill this option value!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else {
                    listOption.add(editTextOption.getText().toString());
                    optionItemList.add(get("Option " + (listAdapterOption.getCount() + 1), editTextOption.getText().toString()));
                    listAdapterOption.notifyDataSetChanged();
                    editTextOption.setText("");
                    editTextOption.setHint("Option " + (listAdapterOption.getCount() + 1));
                }
            }
        });

        final NetworkService networkService = new NetworkService(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(SignIn.token, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");
               networkService.createVote(token, editTextTitle.getText().toString(), listOption, "true", AddVoteActivity.this);
                progressDialog.show();
            }
        });

    }

    private OptionItem get(String option, String optionValue) {
        return new OptionItem(option, optionValue);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onSucceded() {
        Toast.makeText(AddVoteActivity.this, "Create Vote Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailed() {
        Toast.makeText(AddVoteActivity.this, "Create Vote Failure", Toast.LENGTH_SHORT).show();
    }
}
