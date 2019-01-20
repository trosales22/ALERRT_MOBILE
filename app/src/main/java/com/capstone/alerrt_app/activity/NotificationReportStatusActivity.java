package com.capstone.alerrt_app.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.adapters.EmptyRecyclerViewAdapter;
import com.capstone.alerrt_app.classes.adapters.NotificationReportStatusAdapter;
import com.capstone.alerrt_app.classes.beans.NotificationReportStatusBean;
import com.capstone.alerrt_app.classes.data_objects.NotificationReportStatusDO;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NotificationReportStatusActivity extends AppCompatActivity {

    @InjectView(R.id.swipeToRefresh_notificationReportStatus) SwipeRefreshLayout swipeToRefresh_notificationReportStatus;
    @InjectView(R.id.recyclerView_notificationReportStatus) RecyclerView recyclerView_notificationReportStatus;

    public static NotificationReportStatusBean notificationReportStatusBean = new NotificationReportStatusBean();

    private List<NotificationReportStatusDO> notificationReportStatusDOList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter_emptyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_report_status);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        notificationReportStatusDOList = new ArrayList<>();

        recyclerView_notificationReportStatus = findViewById(R.id.recyclerView_notificationReportStatus);
        recyclerView_notificationReportStatus.setHasFixedSize(true);
        recyclerView_notificationReportStatus.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        swipeToRefresh_notificationReportStatus.setColorSchemeResources(R.color.colorPrimary);

        showAllNotificationReportStatus();

        swipeToRefresh_notificationReportStatus.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(notificationReportStatusDOList != null){
                    notificationReportStatusDOList.clear();
                }

                showAllNotificationReportStatus();
                swipeToRefresh_notificationReportStatus.setRefreshing(false);
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

    @SuppressLint("LongLogTag")
    private void showAllNotificationReportStatus(){
        try{
            final ProgressDialog progressDialog = new ProgressDialog(NotificationReportStatusActivity.this);
            progressDialog.setMessage("Loading notification for report status...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            MyStringRequest request = new MyStringRequest(null, Request.Method.GET, EndPoints.SHOW_ALL_NOTIF_REPORT_STATUS + MainActivity.userID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            if(response.equalsIgnoreCase("no_data")){
                                adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
                                recyclerView_notificationReportStatus.setAdapter(adapter_emptyRecyclerView);
                            }else{
                                try {
                                    JSONObject responseObject = new JSONObject(response);
                                    JSONArray reportStatusArray = responseObject.getJSONArray("notif_report_status");

                                    for(int a = 0; a < reportStatusArray.length(); a++) {
                                        JSONObject notifReportStatusObject = reportStatusArray.getJSONObject(a);

                                        JSONArray updaterInfoArray = notifReportStatusObject.getJSONArray("updaterInfo");
                                        for (int b = 0; b < updaterInfoArray.length(); b++) {
                                            JSONObject updaterInfoObject = updaterInfoArray.getJSONObject(b);

                                            notificationReportStatusBean.setStatusID(notifReportStatusObject.getString("StatusID"));
                                            notificationReportStatusBean.setStatusPostID(notifReportStatusObject.getString("StatusPostID"));
                                            notificationReportStatusBean.setStatusTopicTitle(notifReportStatusObject.getString("StatusTopicTitle"));
                                            notificationReportStatusBean.setStatusType(notifReportStatusObject.getString("StatusType"));
                                            notificationReportStatusBean.setStatusDateAndTimeUpdated(notifReportStatusObject.getString("StatusDateAndTime"));
                                            notificationReportStatusBean.setUpdatedBy_userID(updaterInfoObject.getString("UserID"));
                                            notificationReportStatusBean.setUpdatedBy_fullname(updaterInfoObject.getString("Fullname"));
                                            notificationReportStatusBean.setUpdatedBy_profilePic(updaterInfoObject.getString("ProfilePicture"));

                                            NotificationReportStatusDO notificationReportStatusDO = new NotificationReportStatusDO(
                                                    notificationReportStatusBean.getStatusID(),
                                                    notificationReportStatusBean.getStatusPostID(),
                                                    notificationReportStatusBean.getStatusTopicTitle(),
                                                    notificationReportStatusBean.getStatusType(),
                                                    notificationReportStatusBean.getUpdatedBy_fullname(),
                                                    notificationReportStatusBean.getUpdatedBy_profilePic(),
                                                    notificationReportStatusBean.getUpdatedBy_userID(),
                                                    notificationReportStatusBean.getStatusDateAndTimeUpdated()
                                            );

                                            notificationReportStatusDOList.add(notificationReportStatusDO);
                                        }
                                    }

                                    adapter = new NotificationReportStatusAdapter(notificationReportStatusDOList,getApplicationContext());
                                    recyclerView_notificationReportStatus.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Log.e("NotifReportStatusActivity", e.toString());
                                }

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e("NotifReportStatusActivity", error.toString());
                        }
                    }
            );

            MySingleton.getInstance(NotificationReportStatusActivity.this).addToRequestQueue(request);
        }catch(Exception ex){
            Log.e("NotifReportStatusActivity", ex.toString());
        }
    }
}
