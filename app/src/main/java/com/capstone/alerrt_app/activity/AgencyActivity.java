package com.capstone.alerrt_app.activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.adapters.AgencyAdapter;
import com.capstone.alerrt_app.classes.adapters.EmptyRecyclerViewAdapter;
import com.capstone.alerrt_app.classes.beans.AgencyBean;
import com.capstone.alerrt_app.classes.data_objects.AgencyDO;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AgencyActivity extends AppCompatActivity {

    @InjectView(R.id.recyclerView_agency) RecyclerView recyclerView_agency;
    @InjectView(R.id.swipeToRefresh_agency) SwipeRefreshLayout swipeToRefresh_agency;

    View view;
    AgencyBean agencyBean = new AgencyBean();

    private List<AgencyDO> agencyDOList;
    private AgencyAdapter adapter;
    private RecyclerView.Adapter adapter_emptyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        agencyDOList = new ArrayList<>();

        recyclerView_agency = findViewById(R.id.recyclerView_agency);
        recyclerView_agency.setHasFixedSize(true);
        recyclerView_agency.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        swipeToRefresh_agency.setColorSchemeResources(R.color.colorPrimary);

        showAllAgency();

        swipeToRefresh_agency.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(agencyDOList != null){
                    agencyDOList.clear();
                }

                showAllAgency();
                swipeToRefresh_agency.setRefreshing(false);
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

    private void showAllAgency(){
        try{
            final ProgressDialog progressDialog = new ProgressDialog(AgencyActivity.this);
            progressDialog.setMessage("Loading agency...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            MyStringRequest request = new MyStringRequest(null, Request.Method.GET, EndPoints.SHOW_ALL_AGENCY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            if(response.equalsIgnoreCase("no_data")){
                                adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
                                recyclerView_agency.setAdapter(adapter_emptyRecyclerView);
                            }else{
                                try {
                                    JSONObject responseObject = new JSONObject(response);
                                    JSONArray agencyArray = responseObject.getJSONArray("agency");

                                    for(int a = 0; a < agencyArray.length(); a++) {
                                        JSONObject agencyObject = agencyArray.getJSONObject(a);

                                        agencyBean.setAgencyID(agencyObject.getString("AgencyID"));
                                        agencyBean.setAgencyCaption(agencyObject.getString("AgencyCaption"));
                                        agencyBean.setAgencyDescription(agencyObject.getString("AgencyDescription"));
                                        agencyBean.setAgencyContactNumber(agencyObject.getString("AgencyContactNumber"));
                                        agencyBean.setAgencyLocation(agencyObject.getString("AgencyLocation"));
                                        agencyBean.setAgencyStatus(agencyObject.getString("AgencyStatus"));
                                        agencyBean.setAgencyImage(agencyObject.getString("AgencyImage"));
                                        agencyBean.setAgencyAvailability(agencyObject.getString("AgencyAvailability"));

                                        AgencyDO agencyDO = new AgencyDO(
                                                agencyBean.getAgencyID(),
                                                agencyBean.getAgencyCaption(),
                                                agencyBean.getAgencyDescription(),
                                                agencyBean.getAgencyContactNumber(),
                                                agencyBean.getAgencyLocation(),
                                                agencyBean.getAgencyStatus(),
                                                agencyBean.getAgencyImage(),
                                                agencyBean.getAgencyAvailability()
                                        );

                                        agencyDOList.add(agencyDO);
                                    }

                                    adapter = new AgencyAdapter(agencyDOList,getApplicationContext());
                                    recyclerView_agency.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Toast.makeText(AgencyActivity.this,  e.toString(), Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(AgencyActivity.this,  error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
            );

            MySingleton.getInstance(AgencyActivity.this).addToRequestQueue(request);
        }catch(Exception ex){
            Toast.makeText(AgencyActivity.this,  ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
