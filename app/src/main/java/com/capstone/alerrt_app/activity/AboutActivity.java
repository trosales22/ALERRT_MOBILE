package com.capstone.alerrt_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.capstone.alerrt_app.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutActivity extends AppCompatActivity {
    @InjectView(R.id.lblAppDesc) TextView lblAppDesc;
    @InjectView(R.id.btnBack) AppCompatButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lblAppDesc.setText("This mobile application APP-manila LOCAL EMERGENCY REPORTING AND RESPONSE TOOL (ALERRT) is a way that seeks " +
                "to encourage the people to become proactive members of the community by increasing their awareness thereby improving resilience " +
                "and decreasing vulnerabilities. This will provide the citizens to have an easy means of reporting any incidents (emergencies, " +
                "accidents or concerns) requiring response from any local or national units, allow citizen to have detailed documentation " +
                "of the event (image, video capture), allow concerned government sector to act based on reported scenario and citizen can " +
                "track down government actions. ");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
