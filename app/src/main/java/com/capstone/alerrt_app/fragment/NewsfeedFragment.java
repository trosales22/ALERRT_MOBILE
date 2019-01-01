package com.capstone.alerrt_app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.capstone.alerrt_app.classes.beans.TopicBean;
import com.capstone.alerrt_app.classes.data_objects.NewsfeedDO;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsfeedFragment extends Fragment {
    @InjectView(R.id.recyclerView_newsfeed) RecyclerView recyclerView_newsfeed;
    @InjectView(R.id.swipeToRefresh) SwipeRefreshLayout mSwipeRefreshLayout;

    View view;
    TopicBean topicBean = new TopicBean();

    private List<NewsfeedDO> newsfeedList;
    private NewsfeedAdapter adapter;
    private RecyclerView.Adapter adapter_emptyRecyclerView;
    public static String topicPostID,topicPosterUserID;
    private Map<String, String> parameters;

    public NewsfeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_newsfeed, container, false);
        ButterKnife.inject(this, view);

        recyclerView_newsfeed = view.findViewById(R.id.recyclerView_newsfeed);
        recyclerView_newsfeed.setHasFixedSize(true);
        recyclerView_newsfeed.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsfeedList = new ArrayList<>();
        parameters = new HashMap<>();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(newsfeedList != null){
                    newsfeedList.clear();
                }

                showAllPost(EndPoints.SHOW_ALL_POST, parameters);

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        adapter = new NewsfeedAdapter(newsfeedList, getActivity().getApplicationContext());
        recyclerView_newsfeed.setAdapter(adapter);
    }

    public void showAllPost(final String url, Map params){
        try{
            adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
            recyclerView_newsfeed.setAdapter(adapter_emptyRecyclerView);

            MyStringRequest request = new MyStringRequest(params, Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("no_data")){
                                adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
                                recyclerView_newsfeed.setAdapter(adapter_emptyRecyclerView);
                            }else{
                                try {
                                    JSONObject response_object = new JSONObject(response);

                                    JSONArray posts = response_object.getJSONArray("posts");

                                    for(int a = 0; a < posts.length(); a++){
                                        JSONObject post = posts.getJSONObject(a);

                                        JSONArray posterInfo_array = post.getJSONArray("posterInfo");
                                        for(int b = 0; b < posterInfo_array.length(); b++){
                                            JSONObject posterInfo_object = posterInfo_array.getJSONObject(b);

                                            topicPostID = post.getString("ID");
                                            topicPosterUserID = post.getString("TopicPostedBy");
                                            topicBean.setTopicPostedBy(posterInfo_object.getString("Fullname"));
                                            topicBean.setTopicDateAndTimePosted(post.getString("TopicDateAndTimePosted"));
                                            topicBean.setTopicTitle(post.getString("TopicTitle"));
                                            topicBean.setTopicImage(post.getString("TopicImage"));

                                            NewsfeedDO newsfeedDO = new NewsfeedDO(
                                                    topicPostID,
                                                    topicPosterUserID,
                                                    topicBean.getTopicPostedBy(),
                                                    topicBean.getTopicDateAndTimePosted(),
                                                    topicBean.getTopicLocation(),
                                                    topicBean.getTopicTitle(),
                                                    topicBean.getTopicImage(),
                                                    "Attention: " + topicBean.getTopicAttention() + "\nStatus: " + topicBean.getTopicStatus()
                                            );

                                            newsfeedList.add(newsfeedDO);
                                        }
                                    }

                                    adapter = new NewsfeedAdapter(newsfeedList, getActivity().getApplicationContext());
                                    recyclerView_newsfeed.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Log.e("NewsfeedFragment", e.toString());
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            adapter_emptyRecyclerView = new EmptyRecyclerViewAdapter();
                            recyclerView_newsfeed.setAdapter(adapter_emptyRecyclerView);

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

                            Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    }
            );

            request.setRetryPolicy(new DefaultRetryPolicy(0, -1, 0));
            MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);

            adapter.notifyDataSetChanged();
        }catch(Exception ex){
            Log.e("NewsfeedFragment", ex.toString());
        }
    }
}
