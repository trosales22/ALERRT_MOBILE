package com.capstone.alerrt_app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.SharedPrefManager;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AccountSettingsActivity extends AppCompatActivity {

    @InjectView(R.id.frmUpdateAccount_txtFullname) EditText frmUpdateAccount_txtFullname;
    @InjectView(R.id.frmUpdateAccount_txtEmail) EditText frmUpdateAccount_txtEmail;
    @InjectView(R.id.frmUpdateAccount_txtMobileNumber) EditText frmUpdateAccount_txtMobileNumber;
    @InjectView(R.id.frmUpdateAccount_btnUpdateAccount) Button frmUpdateAccount_btnUpdateAccount;
    @InjectView(R.id.frmUpdateAccount_btnUpdatePassword) Button frmUpdateAccount_btnUpdatePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        frmUpdateAccount_txtFullname.setText(MainActivity.userFullname);
        frmUpdateAccount_txtEmail.setText(MainActivity.userEmailAddress);
        frmUpdateAccount_txtMobileNumber.setText(MainActivity.userMobileNumber);

        frmUpdateAccount_btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountSettingsActivity.this, R.style.MyDialogTheme);
                alertDialogBuilder.setTitle("Account Settings");

                alertDialogBuilder
                        .setMessage("Are you sure you want to update your information? This will automatically logged you out for security purposes.")
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                updateAccount();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        frmUpdateAccount_btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void updateAccount(){
        Map<String, String> parameters = new HashMap<>();

        parameters.put("fullname", frmUpdateAccount_txtFullname.getText().toString());
        parameters.put("emailAddress", frmUpdateAccount_txtEmail.getText().toString());
        parameters.put("mobileNumber", frmUpdateAccount_txtMobileNumber.getText().toString());

        MyStringRequest request = new MyStringRequest(parameters, Request.Method.POST, EndPoints.UPDATE_ACCOUNT.concat(MainActivity.userID),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AccountSettingsActivity.this, response, Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AccountSettingsActivity", error.toString());
                    }
                }
        );

        MySingleton.getInstance(AccountSettingsActivity.this).addToRequestQueue(request);
    }
}
