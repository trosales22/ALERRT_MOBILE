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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.adapters.EmptyRecyclerViewAdapter;
import com.capstone.alerrt_app.classes.adapters.NotificationCommentsAdapter;
import com.capstone.alerrt_app.classes.beans.NotificationCommentsBean;
import com.capstone.alerrt_app.classes.data_objects.NotificationCommentsDO;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NotificationCommentsActivity extends AppCompatActivity {

    @InjectView(R.id.swipeToRefresh_notificationComments) SwipeRefreshLayout swipeToRefresh_notificationComments;
    @InjectView(R.id.recyclerView_notificationComments) RecyclerView recyclerView_notificationComments;

    public static NotificationCommentsBean notificationCommentsBean = new NotificationCommentsBean();

    private List<NotificationCommentsDO> notificationCommentsDOList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter_emptyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_comments);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        notificationCommentsDOList = new ArrayList<>();

        recyclerView_notificationComments = findViewById(R.id.recyclerView_notificationComments);
        recyclerView_notificationComments.setHasFixedSize(true);
        recyclerView_notificationComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        swipeToRefresh_notificationComments.setColorSchemeResources(R.color.colorPrimary);

        showAllNotificationComments();

        swipeToRefresh_notificationComments.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(notificationCommentsDOList != null){
                    notificationCommentsDOList.clear();
                }

                showAllNotificationComments();
                swipeToRefresh_notificationComments.setRefreshing(false);
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

    private void showAllNotificationComments(){
        try{
            final ProgressDialog progressDialog = new ProgressDialog(NotificationCommentsActivity.this);
            progressDialog.setMessage("Loading notification for comments...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            MyStringRequest request = new MyStringRequest(null, Request.Method.GET, EndPoints.SHOW_ALL_NOTIF_COMMENTS + MainActivity.userID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            if(response.equalsIgnoreCase("no_data")){
                                adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
                                recyclerView_notificationComments.setAdapter(adapter_emptyRecyclerView);
                            }else{
                                try {
                                    JSONObject response_object = new JSONObject(response);
                                    JSONArray comments = response_object.getJSONArray("notif_comments");

                                    for(int a = 0; a < comments.length(); a++) {
                                        JSONObject notifCommentsObject = comments.getJSONObject(a);

                                        JSONArray commenterInfo = notifCommentsObject.getJSONArray("commenterInfo");
                                        for (int b = 0; b < commenterInfo.length(); b++) {
                                            JSONObject commenterInfoObject = commenterInfo.getJSONObject(b);

                                            notificationCommentsBean.setCommentID(notifCommentsObject.getString("CommentID"));
                                            notificationCommentsBean.setCommentBy_userID(commenterInfoObject.getString("UserID"));
                                            notificationCommentsBean.setPostID(notifCommentsObject.getString("PostID"));
                                            notificationCommentsBean.setTopicTitle(notifCommentsObject.getString("TopicTitle"));
                                            notificationCommentsBean.setTopicStatus(notifCommentsObject.getString("TopicStatus"));
                                            notificationCommentsBean.setDateAndTimeCommented(notifCommentsObject.getString("DateAndTimeCommented"));
                                            notificationCommentsBean.setComment(notifCommentsObject.getString("Message"));
                                            notificationCommentsBean.setCommentBy_fullname(commenterInfoObject.getString("Fullname"));
                                            notificationCommentsBean.setCommmentBy_profilePic(commenterInfoObject.getString("ProfilePicture"));

                                            NotificationCommentsDO notificationCommentsDO = new NotificationCommentsDO(
                                                    notificationCommentsBean.getCommentID(),
                                                    notificationCommentsBean.getPostID(),
                                                    notificationCommentsBean.getTopicTitle(),
                                                    notificationCommentsBean.getTopicStatus(),
                                                    notificationCommentsBean.getCommentBy_fullname(),
                                                    notificationCommentsBean.getCommmentBy_profilePic(),
                                                    notificationCommentsBean.getCommentBy_userID(),
                                                    notificationCommentsBean.getComment(),
                                                    notificationCommentsBean.getDateAndTimeCommented()
                                            );


                                            notificationCommentsDOList.add(notificationCommentsDO);
                                        }
                                    }

                                    adapter = new NotificationCommentsAdapter(notificationCommentsDOList,getApplicationContext());
                                    recyclerView_notificationComments.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Log.e("NotifCommentsActivity", e.toString());
                                }

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e("NotifCommentsActivity", error.toString());
                        }
                    }
            );

            MySingleton.getInstance(NotificationCommentsActivity.this).addToRequestQueue(request);
        }catch(Exception ex){
            Log.e("NotifCommentsActivity", ex.toString());
        }
    }
}
