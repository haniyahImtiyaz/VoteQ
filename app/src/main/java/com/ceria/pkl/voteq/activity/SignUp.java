package com.ceria.pkl.voteq.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.presenter.view.SignupView;
import com.ceria.pkl.voteq.presenter.viewinterface.SignupInterface;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by win 8 on 9/30/2016.
 */
public class SignUp extends AppCompatActivity implements SignupInterface, View.OnClickListener, LocationListener {
    EditText edtEmail, edtPassword, edtConfirmPassword, edtBirth, edtGender, edtJob, edtName ;
    Button btnSignUp, btnCurrentLocation;
    ProgressDialog progressDialog;
    private SignupView presenter;
    private ListPopupWindow lpw;
    private String[] list;
    LocationManager locationManager;
    String mprovider, city;
    double lat, lang;
    TextView cityName, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cityName = (TextView)findViewById(R.id.location);
        edtEmail = (EditText) findViewById(R.id.textEmail);
        edtPassword = (EditText) findViewById(R.id.textPassword);
        edtPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtConfirmPassword = (EditText) findViewById(R.id.textPasswordConfirm);
        edtConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
        edtBirth = (EditText) findViewById(R.id.textBirth);
        edtJob = (EditText) findViewById(R.id.textJob);
        edtName = (EditText) findViewById(R.id.textUsername);
        edtGender = (EditText) findViewById(R.id.textGender);
        btnSignUp = (Button) findViewById(R.id.butSignUp);
        btnCurrentLocation = (Button) findViewById(R.id.btnCurrentLocation);
        login = (TextView)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, SignIn.class);
                startActivity(i);
            }
        });

        progressDialog = new ProgressDialog(this);
        btnSignUp.setOnClickListener(this);
        presenter = new SignupView(this, this.getApplicationContext());

        edtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        list = new String[]{"Female", "Male"};
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setModal(true);

        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lpw.setAnchorView(edtGender);
                lpw.show();
            }
        });

        lpw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edtGender.setText(list[position]);
                lpw.dismiss();
            }
        });

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                mprovider = locationManager.getBestProvider(criteria, false);

                if (mprovider != null && !mprovider.equals("")) {
                    if (ActivityCompat.checkSelfPermission(SignUp.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SignUp.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                            !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        {

                        }
                    }
                    Location location = locationManager.getLastKnownLocation(mprovider);
                    locationManager.requestLocationUpdates(mprovider, 15000, 1, SignUp.this);

                    if (location != null){
                        onLocationChanged(location);}
                    else {
                        Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SignUp.this);
                        alertDialogBuilder
                                .setMessage("GPS is disabled in your device. Enable it?")
                                .setCancelable(false)
                                .setPositiveButton("Enable GPS",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                Intent callGPSSettingIntent = new Intent(
                                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                                startActivity(callGPSSettingIntent);
                                            }
                                        });
                        alertDialogBuilder.setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();
                    }
                }
            }
        });
    }

    @Override
    public void showProgress() {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onEmailSame() {
        Toast.makeText(SignUp.this, "Email has already been taken", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordLess() {
        Toast.makeText(SignUp.this, "Password is too short (minimum is 6 characters)", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConfirmPassNotMatch() {
        Toast.makeText(SignUp.this, "doesn't match Password", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(SignUp.this, "Network Failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        presenter.signUpAuth(edtName.getText().toString(),
                edtEmail.getText().toString(),
                edtJob.getText().toString(),
                edtPassword.getText().toString(),
                edtConfirmPassword.getText().toString(),
                edtBirth.getText().toString(),
                edtGender.getText().toString(),
                lat, lang);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        progressDialog.dismiss();
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {

        lang = location.getLongitude();
        lat = location.getLatitude();
        cityName.setText(String.valueOf(lang));
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(
                    location.getLatitude(),
                    location.getLongitude(),
                    // In this sample, get just activity_card single address.
                    1);
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            // String errorMessage = getString(R.string.service_not_available);
            // Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            //  errorMessage = getString(R.string.invalid_lat_long_used);
            // Log.e(TAG, errorMessage + ". " +
            //         "Latitude = " + location.getLatitude() +
            //        ", Longitude = " +
            //       location.getLongitude(), illegalArgumentException);
        }
        if (addresses.size() > 0) {
            city = addresses.get(0).getCountryName();
            cityName.setText(city);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressLint("ValidFragment")
    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create activity_card new instance of DatePickerDialog and return it
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(new Date().getTime());
            return dialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            edtBirth.setText(year+"-"+month+"-"+day);
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
