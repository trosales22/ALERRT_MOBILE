package com.capstone.alerrt_app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.adapters.AgencyAdapter;
import com.capstone.alerrt_app.classes.beans.TopicBean;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostActivity extends AppCompatActivity {
    @InjectView(R.id.imgTopicInCardView) CircleImageView imgTopicInCardView;
    @InjectView(R.id.txtTopicLocationName) TextView txtTopicLocationName;
    @InjectView(R.id.txtTopicLocationID) TextView txtTopicLocationID;
    @InjectView(R.id.txtTopicLocationAddress) TextView txtTopicLocationAddress;
    @InjectView(R.id.txtTopicLocationLatAndLong) TextView txtTopicLocationLatAndLong;
    @InjectView(R.id.txtTopicTitle) MaterialEditText txtTopicTitle;
    @InjectView(R.id.imgTopic) ImageView imgTopic;
    @InjectView(R.id.btnCancelPost) AppCompatButton btnCancelPost;
    @InjectView(R.id.btnAddPhoto) AppCompatButton btnAddPhoto;
    @InjectView(R.id.btnAddLocation) AppCompatButton btnAddLocation;
    @InjectView(R.id.btnPost) AppCompatButton btnPost;

    private int PICK_IMAGE_REQUEST = 1;
    private int PLACE_PICKER_REQUEST = 1;

    TopicBean topic = new TopicBean();
    Bitmap bitmap;
    String placeID,placeName,placeAddress,placeLatLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.inject(this);

        setTitle("Report To ".concat(AgencyAdapter.agencyCaption).concat(" | ALERRT"));

        final MaterialEditText[] editTexts = new MaterialEditText[]{
                txtTopicTitle
        };

        btnCancelPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 2. pick image only
                intent.setType("image/*");
                // 3. start activity
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btnAddLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;

                try{
                    intent = builder.build(AddPostActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = new ProgressDialog(AddPostActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Posting your topic... Please wait!");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                addPost();
                                progressDialog.dismiss();
                            }
                        }, 3000);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri uri = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                    // rotating bitmap
                    String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
                    Cursor cur = getContentResolver().query(uri, orientationColumn, null, null, null);
                    int orientation = -1;
                    if (cur != null && cur.moveToFirst()) {
                        orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
                    }
                    Matrix matrix = new Matrix();
                    matrix.postRotate(orientation);

                    bitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    topic.setTopicImage(encodedImage);

                    imgTopic.setImageBitmap(bitmap);
                    imgTopicInCardView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK){
                try {
                    Place place = PlacePicker.getPlace(data, AddPostActivity.this);

                    placeID = place.getId();
                    placeName = (String) place.getName();
                    placeAddress = (String) place.getAddress();
                    placeLatLang = String.valueOf(place.getLatLng());

                    txtTopicLocationName.setText(placeName);
                    txtTopicLocationID.setText("Place ID: \n" + placeID + "\n");
                    txtTopicLocationAddress.setText("Address: \n" + placeAddress + "\n");
                    txtTopicLocationLatAndLong.setText(placeLatLang);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }catch(Exception ex){
            Log.e("Error Message", ex.toString());
        }
    }

    private void init(){
        topic.setTopicTitle(txtTopicTitle.getText().toString());

        topic.setTopicLocationID(placeID);
        topic.setTopicLocationName(placeName);
        topic.setTopicLocationAddress(txtTopicLocationAddress.getText().toString());
        topic.setTopicAgencyID(AgencyAdapter.agencyID);
        topic.setTopicPostedBy(MainActivity.userID);

        if(topic.getTopicImage() == null){
            topic.setTopicImage("");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy | hh:mm a");
        Calendar dateAndTimePosted_calendar = Calendar.getInstance();

        topic.setTopicDateAndTimePosted(sdf.format(dateAndTimePosted_calendar.getTime()));
    }

    public void addPost(){
        init();

        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "image/jpeg; charset=utf-8");
        params.put("topicTitle", topic.getTopicTitle());
        params.put("topicImage", topic.getTopicImage());

        params.put("topicLocationID",topic.getTopicLocationID());
        params.put("topicLocationName",topic.getTopicLocationName());
        params.put("topicLocationAddress",topic.getTopicLocationAddress());
        params.put("topicAgencyID", topic.getTopicAgencyID());

        params.put("topicPostedBy",topic.getTopicPostedBy());
        params.put("topicDateAndTimePosted",topic.getTopicDateAndTimePosted());

        MyStringRequest request = new MyStringRequest(params,Request.Method.POST, EndPoints.ADD_POST,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AddPostActivity.this, response, Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        MySingleton.getInstance(AddPostActivity.this).addToRequestQueue(request);
    }
}