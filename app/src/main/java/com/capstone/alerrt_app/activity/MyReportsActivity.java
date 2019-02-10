package com.capstone.alerrt_app.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.adapters.EmptyRecyclerViewAdapter;
import com.capstone.alerrt_app.classes.adapters.NewsfeedAdapter;
import com.capstone.alerrt_app.classes.beans.MyReportsBean;
import com.capstone.alerrt_app.classes.data_objects.NewsfeedDO;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyReportsActivity extends AppCompatActivity {

    @InjectView(R.id.swipeToRefresh_myReports)
    SwipeRefreshLayout swipeToRefresh_myReports;
    @InjectView(R.id.recyclerView_myReports)
    RecyclerView recyclerView_myReports;

    public static MyReportsBean myReportsBean = new MyReportsBean();

    private List<NewsfeedDO> newsfeedDataObjectList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter_emptyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        newsfeedDataObjectList = new ArrayList<>();

        recyclerView_myReports = findViewById(R.id.recyclerView_myReports);
        recyclerView_myReports.setHasFixedSize(true);
        recyclerView_myReports.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        swipeToRefresh_myReports.setColorSchemeResources(R.color.colorPrimary);

        showAllMyReports();

        swipeToRefresh_myReports.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(newsfeedDataObjectList != null){
                    newsfeedDataObjectList.clear();
                }

                showAllMyReports();
                swipeToRefresh_myReports.setRefreshing(false);
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

    public void showAllMyReports(){
        try{
            adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
            recyclerView_myReports.setAdapter(adapter_emptyRecyclerView);

            MyStringRequest request = new MyStringRequest(null, Request.Method.GET, EndPoints.SHOW_MY_REPORTS.concat(MainActivity.userID),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("no_data")){
                                adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
                                recyclerView_myReports.setAdapter(adapter_emptyRecyclerView);
                            }else{
                                try {
                                    JSONObject response_object = new JSONObject(response);

                                    JSONArray posts = response_object.getJSONArray("posts");

                                    for(int a = 0; a < posts.length(); a++){
                                        JSONObject post = posts.getJSONObject(a);

                                        JSONArray posterInfo_array = post.getJSONArray("posterInfo");
                                        for(int b = 0; b < posterInfo_array.length(); b++){
                                            JSONObject posterInfo_object = posterInfo_array.getJSONObject(b);

                                            myReportsBean.setTopicPostID(post.getString("TopicID"));
                                            myReportsBean.setTopicPosterUserID(post.getString("TopicPostedBy"));
                                            myReportsBean.setTopicPostedBy(posterInfo_object.getString("Fullname"));
                                            myReportsBean.setTopicDateAndTimePosted(post.getString("TopicDateAndTimePosted"));
                                            myReportsBean.setTopicSeverity(post.getString("TopicSeverity"));
                                            myReportsBean.setTopicTitle(post.getString("TopicTitle"));
                                            myReportsBean.setTopicImage(post.getString("TopicImage"));
                                            myReportsBean.setTopicAgency(post.getString("TopicAgency"));
                                            myReportsBean.setTopicLocationAddress(post.getString("TopicLocationAddress"));
                                            myReportsBean.setTopicStatus(post.getString("TopicStatus"));

                                            NewsfeedDO newsfeedDO = new NewsfeedDO(
                                                    myReportsBean.getTopicPostID(),
                                                    myReportsBean.getTopicPosterUserID(),
                                                    myReportsBean.getTopicPostedBy(),
                                                    myReportsBean.getTopicDateAndTimePosted(),
                                                    myReportsBean.getTopicLocationAddress(),
                                                    myReportsBean.getTopicSeverity(),
                                                    myReportsBean.getTopicTitle(),
                                                    myReportsBean.getTopicImage(),
                                                    myReportsBean.getTopicAgency(),
                                                    myReportsBean.getTopicStatus()
                                            );

                                            newsfeedDataObjectList.add(newsfeedDO);
                                        }
                                    }

                                    adapter = new NewsfeedAdapter(newsfeedDataObjectList, getApplicationContext());
                                    recyclerView_myReports.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Log.e("MyReportsActivity", e.toString());
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
                            recyclerView_myReports.setAdapter(adapter_emptyRecyclerView);

                            String message = null;
                            if (error instanceof NetworkError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (error instanceof ServerError) {
                                message = "The server could not be found. Please try again after some time!!";
                            } else if (error instanceof AuthFailureError) {
                                message = "Cannot connect to Internet...Please check your connection!";
                            } else if (error instanceof ParseError) {
                                message = "Parsing error! Please try again after some time!!";
                            } else if (error instanceof TimeoutError) {
                                message = "Connection TimeOut! Please check your internet connection.";
                            }

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    }
            );

            request.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);

            adapter.notifyDataSetChanged();
        }catch(Exception ex){
            Log.e("MyReportsActivity", ex.toString());
        }
    }
}
