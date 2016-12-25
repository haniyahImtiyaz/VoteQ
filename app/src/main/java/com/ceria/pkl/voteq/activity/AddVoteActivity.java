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
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.adapter.ListAdapterOption;
import com.ceria.pkl.voteq.itemAdapter.OptionItem;
import com.ceria.pkl.voteq.models.network.UploadImageVote;
import com.ceria.pkl.voteq.presenter.view.CreateVoteView;
import com.ceria.pkl.voteq.presenter.view.UploadImageVoteView;
import com.ceria.pkl.voteq.presenter.viewinterface.CreateVoteInterface;
import com.ceria.pkl.voteq.presenter.viewinterface.UploadImageInterface;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddVoteActivity extends AppCompatActivity implements CreateVoteInterface, UploadImageInterface,  View.OnClickListener {

    List<String> optionItemList;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String token, id;
    Uri fileUri;
    private EditText editTextTitle, editTextStarted, editTextEnded, editTextDescription, editTextOption ;
    private Button buttonAddOption;
    private ArrayAdapter listAdapterOption;
    private ExpandableHeightListView expandableListView;
    private CreateVoteView presenter;
    private UploadImageVoteView presenterUploadImage;
    private TextView btnSelectImage;
    private ImageView imageVote;
    private static int RESULT_LOAD_IMG = 1;

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
        buttonAddOption = (Button) findViewById(R.id.buttonAddOp);
        btnSelectImage = (TextView)findViewById(R.id.btnSelectImage);
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

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        editTextStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, editTextStarted);
            }
        });

        editTextEnded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v, editTextEnded);
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
                    String option = editTextOption.getText().toString();
                    optionItemList.add(option);
                    listAdapterOption.notifyDataSetChanged();
                    editTextOption.setText("");
                    editTextOption.setHint("Option " + (listAdapterOption.getCount() + 1));
                }
            }
        });

        progressDialog = new ProgressDialog(this);


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
            Log.d("haio", e.toString());
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        EditText edt;
        public DatePickerFragment(EditText edt) {
            this.edt = edt;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create activity_card new instance of DatePickerDialog and return it
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
           // dialog.getDatePicker().setMinDate((System.currentTimeMillis() - 1000));
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            month = month + 1;
            edt.setText(year+"-"+month+"-"+day);
        }
    }
    public void showDatePickerDialog(View v, EditText edt) {
        DialogFragment newFragment = new DatePickerFragment(edt);
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
        progressDialog.dismiss();
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
        finish();
    }

    @Override
    public void onSuccedeed() {
        presenterUploadImage.uploadFile(getRealPathFromURI(fileUri), token, presenter.getId());
    }

    @Override
    public void setTitleEmpty() {
        editTextTitle.setError("Title Cannot be Empty");
    }

    @Override
    public void setOptionsEmpty() {
        editTextOption.setError("Option cannot less than 2, Please fill option again");
    }

    @Override
    public void setOptionNotEmpty() {
        final String option = editTextOption.getText().toString();
        new AlertDialog.Builder(AddVoteActivity.this).setMessage("Option '" + option + "' haven't added in option list, Do you want add '" + option + "' to option list ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listAdapterOption.notifyDataSetChanged();
                        editTextOption.setText("");
                       // editTextOption.setHint("Insert Option " + (listAdapterOption.getCount() + 1));
                        presenter.callCreateVote(token, editTextTitle.getText().toString(), editTextDescription.getText().toString(),
                                editTextStarted.getText().toString(), editTextEnded.getText().toString(), optionItemList);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editTextOption.setText("");
                        presenter.callCreateVote(token, editTextTitle.getText().toString(), editTextDescription.getText().toString(),
                                editTextStarted.getText().toString(), editTextEnded.getText().toString(), optionItemList);
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
     presenter.callCreateVote(token, editTextTitle.getText().toString(), editTextDescription.getText().toString(),
              editTextStarted.getText().toString(), editTextEnded.getText().toString(), optionItemList);
    }
}
