package com.ceria.pkl.voteq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.List;

public class AddVoteActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextOption;
    private Button buttonAddOption, buttonDone;
    private ListAdapterOption listAdapterOption;
    private ExpandableHeightListView expandableListView;
    List<OptionItem> optionItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                optionItemList.add(get("Option "+(listAdapterOption.getCount()+1), editTextOption.getText().toString()));
                listAdapterOption.notifyDataSetChanged();
                editTextOption.setText("");
                editTextOption.setHint("Option "+(listAdapterOption.getCount()+1));
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
}
