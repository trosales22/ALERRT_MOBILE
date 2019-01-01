package com.capstone.alerrt_app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.Validation;
import com.capstone.alerrt_app.classes.adapters.NewsfeedAdapter;
import com.capstone.alerrt_app.classes.beans.CommentBean;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddCommentActivity extends AppCompatActivity {
    @InjectView(R.id.txtComment) MaterialEditText txtComment;
    @InjectView(R.id.btnCancelComment) AppCompatButton btnCancelComment;
    @InjectView(R.id.btnSendComment) AppCompatButton btnSendComment;

    CommentBean comment = new CommentBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        ButterKnife.inject(this);

        final MaterialEditText[] editTexts = new MaterialEditText[]{
                txtComment
        };

        btnCancelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Validation.validateEditText(editTexts)){
                    final ProgressDialog progressDialog = new ProgressDialog(AddCommentActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Posting comment... Please wait!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    sendComment();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }
            }
        });
    }

    private void init(){
        comment.setComment(txtComment.getText().toString());

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy | hh:mm a");
        Calendar dateAndTimeCommented_calendar = Calendar.getInstance();

        comment.setDateAndTimeCommented(sdf.format(dateAndTimeCommented_calendar.getTime()));
    }

    private void sendComment(){
        init();

        Map<String, String> parameters = new HashMap<>();

        parameters.put("postID", NewsfeedAdapter.topicPostID);
        parameters.put("commentBy",MainActivity.userID);
        parameters.put("comment",comment.getComment());
        parameters.put("dateAndTimeCommented",comment.getDateAndTimeCommented());

        MyStringRequest request = new MyStringRequest(parameters, Request.Method.POST, EndPoints.ADD_COMMENT_TO_POST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddCommentActivity.this, response, Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), CommentsActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AddCommentActivity", error.toString());
                    }
                }
        );

        MySingleton.getInstance(AddCommentActivity.this).addToRequestQueue(request);
    }
}
