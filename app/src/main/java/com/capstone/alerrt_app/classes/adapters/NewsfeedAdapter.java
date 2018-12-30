package com.capstone.alerrt_app.classes.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.activity.HomeActivity;
import com.capstone.alerrt_app.classes.data_objects.NewsfeedDO;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewsfeedAdapter extends RecyclerView.Adapter<NewsfeedAdapter.ViewHolder>{
    private List<NewsfeedDO> newsfeedList;
    private Context context;
    private View view;

    public static String topicPostID;

    public NewsfeedAdapter(List<NewsfeedDO> newsfeedList, Context context) {
        this.newsfeedList = newsfeedList;
        this.context = context;
    }

    @Override
    public NewsfeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.newsfeed, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsfeedAdapter.ViewHolder holder, int position) {
        final NewsfeedDO newsfeedDO = newsfeedList.get(position);

        holder.txtPostedBy.setText(newsfeedDO.getTopic_postedBy());
        holder.txtTopicDateAndTimePosted.setText(newsfeedDO.getTopic_dateAndTimePosted());
        holder.txtTopicTitle.setText(newsfeedDO.getTopicTitle());
        holder.txtTopicDetails.setText(newsfeedDO.getTopicDetails());

        if(newsfeedDO.getTopicImage().isEmpty()){
            holder.imgTopic.setImageResource(R.drawable.no_image_available);
        }else {
            Picasso.with(context)
                    .load(newsfeedDO.getTopicImage())
                    .error(R.drawable.no_image_available)
                    .placeholder(R.drawable.no_image_available)
                    .into(holder.imgTopic);
        }

        holder.btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicPostID = newsfeedDO.getTopicPostID();

                view.getContext().startActivity(new Intent(view.getContext(), HomeActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsfeedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.txtPostedBy) TextView txtPostedBy;
        @InjectView(R.id.txtTopicDateAndTimePosted) TextView txtTopicDateAndTimePosted;
        @InjectView(R.id.txtTopicDetails) TextView txtTopicDetails;
        @InjectView(R.id.txtTopicTitle) TextView txtTopicTitle;

        @InjectView(R.id.imgTopic) ImageView imgTopic;
        @InjectView(R.id.btnComments) AppCompatButton btnComments;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }
    }
}
