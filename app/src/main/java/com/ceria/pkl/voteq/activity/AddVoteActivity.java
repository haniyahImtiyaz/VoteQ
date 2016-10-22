package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.adapter.ListAdapterOption;
import com.ceria.pkl.voteq.itemAdapter.OptionItem;
import com.ceria.pkl.voteq.presenter.view.CreateVoteView;
import com.ceria.pkl.voteq.presenter.viewinterface.CreateVoteInterface;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.List;

public class AddVoteActivity extends AppCompatActivity implements CreateVoteInterface, View.OnClickListener {

    List<OptionItem> optionItemList;
    List<String> listOption = new ArrayList<>();
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String token;
    private EditText editTextTitle, editTextOption;
    private Button buttonAddOption, btnDone;
    private ListAdapterOption listAdapterOption;
    private ExpandableHeightListView expandableListView;
    private CreateVoteView presenter;

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
        findViewById(R.id.buttonDone).setOnClickListener(this);
        expandableListView = (ExpandableHeightListView) findViewById(R.id.expandable_listview);

        optionItemList = new ArrayList<>();
        listAdapterOption = new ListAdapterOption(optionItemList, this);
        expandableListView.setAdapter(listAdapterOption);
        expandableListView.setExpanded(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "");
        presenter = new CreateVoteView(this);

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

        progressDialog = new ProgressDialog(this);


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
        Toast.makeText(AddVoteActivity.this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(AddVoteActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onSuccedeed() {
        finish();
    }

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
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
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
        } else if (optionItemList.size() < 2) {
            new AlertDialog.Builder(AddVoteActivity.this).setMessage("List option can't only one item, Please add option to continue!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        } else {
            Log.d("logOp", listOption.toString());
            presenter.callCreateVote(token, editTextTitle.getText().toString(), listOption, true);
        }
    }
}
