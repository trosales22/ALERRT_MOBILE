package com.capstone.alerrt_app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.adapters.AgencyAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewSpecificAgencyActivity extends AppCompatActivity {

    @InjectView(R.id.lblAgencyCaption) TextView lblAgencyCaption;
    @InjectView(R.id.lblAgencyFullname) TextView lblAgencyFullname;
    @InjectView(R.id.lblAgencyPosition) TextView lblAgencyPosition;
    @InjectView(R.id.lblAgencyContactNumber) TextView lblAgencyContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_specific_agency);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        setTitle(AgencyAdapter.agencyCaption.concat(" | ALERRT"));

        lblAgencyCaption.setText(AgencyAdapter.agencyCaption);
        lblAgencyFullname.setText(AgencyAdapter.agencyFullname);
        lblAgencyPosition.setText(AgencyAdapter.agencyPosition);
        lblAgencyContactNumber.setText(AgencyAdapter.agencyContactNumber.replaceAll(",","\n"));
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
