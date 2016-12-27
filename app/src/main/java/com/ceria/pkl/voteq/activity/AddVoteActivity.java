package com.ceria.pkl.voteq.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.presenter.view.CreateVoteView;
import com.ceria.pkl.voteq.presenter.view.UploadImageVoteView;
import com.ceria.pkl.voteq.presenter.viewinterface.CreateVoteInterface;
import com.ceria.pkl.voteq.presenter.viewinterface.UploadImageInterface;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddVoteActivity extends AppCompatActivity implements CreateVoteInterface, UploadImageInterface, View.OnClickListener {

    private static int RESULT_LOAD_IMG = 1;
    private List<String> optionItemList;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private CreateVoteView presenter;
    private UploadImageVoteView presenterUploadImage;
    private String token;
    private  Uri fileUri;
    private ArrayAdapter listAdapterOption;
    private EditText editTextTitle, editTextStarted, editTextEnded, editTextDescription, editTextOption, editTextCategory;
    private TextInputLayout layoutTitle, layoutDescription, layoutStarted, layoutEnded;
    private Button buttonAddOption;
    private ExpandableHeightListView expandableListView;
    private TextView btnSelectImage;
    private ImageView imageVote;
    private boolean complete = true;

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

        editTextTitle = (EditText) findViewById(R.id.textTitle);
        editTextStarted = (EditText) findViewById(R.id.textStarted);
        editTextEnded = (EditText) findViewById(R.id.textEnded);
        editTextDescription = (EditText) findViewById(R.id.textDescription);
        editTextOption = (EditText) findViewById(R.id.textOption);
        editTextCategory = (EditText)findViewById(R.id.textCategory);
        layoutTitle = (TextInputLayout)findViewById(R.id.titleWrapper);
        layoutDescription = (TextInputLayout)findViewById(R.id.descriptionWrapper);
        layoutStarted = (TextInputLayout)findViewById(R.id.startedWrapper);
        layoutEnded = (TextInputLayout)findViewById(R.id.endedWrapper);
        buttonAddOption = (Button) findViewById(R.id.buttonAddOp);
        btnSelectImage = (TextView) findViewById(R.id.btnSelectImage);
        imageVote = (ImageView) findViewById(R.id.imageVote);
        findViewById(R.id.buttonDone).setOnClickListener(this);
        expandableListView = (ExpandableHeightListView) findViewById(R.id.expandable_listview);

        optionItemList = new ArrayList<>();
        listAdapterOption = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, optionItemList);
        expandableListView.setAdapter(listAdapterOption);
        expandableListView.setExpanded(true);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        token = sharedPreferences.getString("token", "");
        presenter = new CreateVoteView(this);
        presenterUploadImage = new UploadImageVoteView(this);

        editTextTitle.addTextChangedListener(setListenerEditText(layoutTitle, editTextTitle, "Please, Enter Title Vote"));
        editTextDescription.addTextChangedListener(setListenerEditText(layoutDescription, editTextDescription, "Please, Enter Description Vote"));

        editTextStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, editTextStarted, layoutStarted);
            }
        });

        editTextEnded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, editTextEnded, layoutEnded);
            }
        });

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

        String[] list = new String[]{"Politik", "Pendidikan", "Agroindustri", "Komputer",
                "Fashion", "Musik", "Otomotif", "Makanan", "Gaya Hidup", "Others"};
        ListPopupWindow lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setModal(true);

        editTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpw.setAnchorView(editTextCategory);
                lpw.show();
            }
        });

        lpw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTextCategory.setText(list[position]);
                lpw.dismiss();
            }
        });


        progressDialog = new ProgressDialog(this);
    }

    void btnAddOptionClick(View v) {
        if (editTextOption.getText().toString().trim().length() == 0) {
            new AlertDialog.Builder(AddVoteActivity.this)
                    .setMessage("please, fill this option value!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        } else {
            String option = editTextOption.getText().toString();
            optionItemList.add(option);
            listAdapterOption.notifyDataSetChanged();
            editTextOption.setText("");
            editTextOption.setHint("Insert Option " + (listAdapterOption.getCount() + 1));
        }
    }

    void btnSelectImageClick(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                fileUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(fileUri,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                imageVote.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
            Log.d("PickImage", e.toString());
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void showDatePickerDialog(View v, EditText edt, TextInputLayout til) {
        DialogFragment newFragment = new DatePickerFragment(edt, til);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        EditText edt;
        TextInputLayout til;

        public DatePickerFragment(EditText edt, TextInputLayout til) {
            this.edt = edt;
            this.til = til;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            c.add(Calendar.DATE, 1);
            Date min = new Date(String.valueOf(c.getTime()));
            // Create activity_card new instance of DatePickerDialog and return it
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMinDate(min.getTime());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month = month + 1;
            edt.setText(year + "-" + month + "-" + day);
            til.setErrorEnabled(false);
        }
    }

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
        Toast.makeText(AddVoteActivity.this, "Error, Please Try Again Later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(AddVoteActivity.this, "Network Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onSuccedeed() {
        if(fileUri != null){
            presenterUploadImage.uploadFile(getRealPathFromURI(fileUri), token, presenter.getId());
        }else{
            navigateToHome();
        }
    }

    void setErrorEditText(TextInputLayout til, EditText et, String msg) {
        if(et.getText().length() == 0){
            til.setError(msg);
            complete = false;
        }
    }

//    @Override
//    public void setOptionsEmpty() {
//        editTextOption.setError("Option cannot less than 2, Please fill option again");
//    }

    void setOptionNotEmpty() {
        String option = editTextOption.getText().toString();
        if(editTextOption.getText().length() > 0) {
            complete = false;
            new AlertDialog.Builder(AddVoteActivity.this).setMessage("Option '" + option + "' haven't added in option list, Do you want add '" + option + "' to option list ?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            listAdapterOption.notifyDataSetChanged();
                            editTextOption.setText("");
                           }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            editTextOption.setText("");
                       }
                    })
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        setErrorEditText(layoutTitle, editTextTitle, "Title cannot be empty");
        setErrorEditText(layoutStarted, editTextStarted, "Please, Select date for started vote");
        setErrorEditText(layoutEnded, editTextEnded, "Please, Select date for ended vote");
        setErrorEditText(layoutDescription, editTextDescription, "Description cannot be empty");
        setOptionNotEmpty();
        if(complete){
            presenter.callCreateVote(token, editTextTitle.getText().toString(), editTextDescription.getText().toString(),
                    editTextStarted.getText().toString(), editTextEnded.getText().toString(), optionItemList);

        }
    }

    TextWatcher setListenerEditText(TextInputLayout til, EditText et, String message) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {  }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String email = et.getText().toString();
                if (email.isEmpty()) {
                    til.setError(message);
                    et.requestFocus();
                } else {
                    til.setErrorEnabled(false);
                    complete = true;
                }
            }

        };
    }
}
