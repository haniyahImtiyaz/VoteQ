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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    ArrayList<String> listOption = new ArrayList<>();
    ProgressDialog progressDialog;
    NetworkService networkService;

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

        optionItemList = new ArrayList<>();
        listAdapterOption = new ListAdapterOption(optionItemList, this);
        expandableListView.setAdapter(listAdapterOption);

        expandableListView.setExpanded(true);

        editTextOption.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    buttonAddOption.callOnClick();
                    return true;
                }
                return false;
            }
        });

        buttonAddOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTitle.getText().toString().length() == 0) {
                    new AlertDialog.Builder(AddVoteActivity.this).setMessage("Please fill title to continue!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else if (editTextOption.getText().toString().trim().length() == 0) {
                    new AlertDialog.Builder(AddVoteActivity.this)
                            .setMessage("please, fill this option value!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else {
                    String option = editTextOption.getText().toString();
                    optionItemList.add(get("Option " + (listAdapterOption.getCount() + 1), option));
                    listAdapterOption.notifyDataSetChanged();
                    editTextOption.setText("");
                    editTextOption.setHint("Option " + (listAdapterOption.getCount() + 1));
                }
            }
        });

        networkService = new NetworkService(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTitle.getText().toString().length() == 0) {
                    new AlertDialog.Builder(AddVoteActivity.this).setMessage("Please fill title to continue!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else if (editTextOption.getText().toString().length() != 0) {
                    final String option = editTextOption.getText().toString();
                    new AlertDialog.Builder(AddVoteActivity.this).setMessage("Option '" + option + "' haven't added in option list, Do you want add '" + option + "' to option list ?")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    optionItemList.add(get("Option " + (listAdapterOption.getCount() + 1), option));
                                    listAdapterOption.notifyDataSetChanged();
                                    editTextOption.setText("");
                                    editTextOption.setHint("Option " + (listAdapterOption.getCount() + 1));
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    editTextOption.setText("");
                                }
                            })
                            .show();
                } else if (optionItemList.size() == 0) {
                    new AlertDialog.Builder(AddVoteActivity.this).setMessage("Please fill the option list to continue!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("TOKEN_USER", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "");
                    networkService.createVote(token, editTextTitle.getText().toString(), listOption, true, AddVoteActivity.this);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            }
        });

    }

    private OptionItem get(String option, String optionValue) {
        listOption.add(optionValue);
        return new OptionItem(option, optionValue);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onSucceded() {
        progressDialog.dismiss();
        VoteList.listItem = new ArrayList<>();
        MyVoteList.listItem = new ArrayList<>();
        Intent i = new Intent(AddVoteActivity.this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        Toast.makeText(AddVoteActivity.this, "Create Vote Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeout() {
        progressDialog.dismiss();
        Toast.makeText(AddVoteActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
    }
}
