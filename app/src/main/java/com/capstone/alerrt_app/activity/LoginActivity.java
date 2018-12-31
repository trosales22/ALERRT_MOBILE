package com.capstone.alerrt_app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.SharedPrefManager;
import com.capstone.alerrt_app.classes.beans.UserBean;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.frmLoginAccount_txtEmail) EditText frmLoginAccount_txtEmail;
    @InjectView(R.id.frmLoginAccount_txtPassword) EditText frmLoginAccount_txtPassword;
    @InjectView(R.id.frmLoginAccount_btnLoginAccount) Button frmLoginAccount_btnLoginAccount;
    @InjectView(R.id.btnGoToSignUpPage) TextView btnGoToSignUpPage;

    ProgressDialog progressDialog;
    UserBean userBean = new UserBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        frmLoginAccount_btnLoginAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                userBean.setEmailAddress(frmLoginAccount_txtEmail.getText().toString());
                userBean.setPassword(frmLoginAccount_txtPassword.getText().toString());

                try{
                    validate();
                }catch(Exception ex){
                    Toast.makeText(LoginActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnGoToSignUpPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    public void validate(){
        if(TextUtils.isEmpty(userBean.getEmailAddress())){
            frmLoginAccount_txtEmail.setError("Please enter your email!");
            frmLoginAccount_txtEmail.requestFocus();
        }else if(TextUtils.isEmpty(userBean.getPassword())){
            frmLoginAccount_txtPassword.setError("Please enter your password!");
            frmLoginAccount_txtPassword.requestFocus();
        }else {

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            loginUser();
                        }
                    }, 3000);
        }
    }

    public void loginUser(){
        final Map<String, String> parameters = new HashMap<>();

        parameters.put("email",userBean.getEmailAddress());
        parameters.put("password",userBean.getPassword());

        MyStringRequest request = new MyStringRequest(parameters, Request.Method.POST, EndPoints.LOGIN_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        if(response.equalsIgnoreCase("invalid")){
                            Toast.makeText(LoginActivity.this, "Invalid email and password! Please try again!", Toast.LENGTH_LONG).show();
                        }else{
                            SharedPrefManager.getInstance(getApplicationContext())
                                    .userLogin(userBean.getEmailAddress());

                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );

        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(request);
    }
}
