package com.capstone.alerrt_app.classes.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.activity.CommentsActivity;
import com.capstone.alerrt_app.classes.data_objects.NotificationCommentsDO;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationCommentsAdapter extends RecyclerView.Adapter<NotificationCommentsAdapter.ViewHolder>{
    private List<NotificationCommentsDO> notificationCommentsList;
    private Context context;
    private View view;
    public static String topicID;

    public NotificationCommentsAdapter(List<NotificationCommentsDO> notificationCommentsList, Context context) {
        this.notificationCommentsList = notificationCommentsList;
        this.context = context;
    }

    @Override
    public NotificationCommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_comments, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationCommentsAdapter.ViewHolder holder, int position) {
        final NotificationCommentsDO notificationCommentsDO = notificationCommentsList.get(position);

        StringBuilder sb = new StringBuilder();

        sb.append(notificationCommentsDO.getCommentBy_fullname());
        sb.append(" commented on your report (");
        sb.append(notificationCommentsDO.getTopicTitle());
        sb.append(") with a status (");
        sb.append(notificationCommentsDO.getTopicStatus().toUpperCase());
        sb.append(").");
        holder.lblNotificationComments_message.setText(sb.toString());

        if(notificationCommentsDO.getCommmentBy_profilePic().isEmpty()){
            holder.imgNotificationComments_commenterProfilePic.setImageResource(R.drawable.logo);
        }else {
            Picasso.with(context)
                    .load(notificationCommentsDO.getCommmentBy_profilePic())
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(holder.imgNotificationComments_commenterProfilePic);
        }

        holder.lblNotificationComments_dateAndTimeCommented.setText(notificationCommentsDO.getDateAndTimeCommented());

        holder.cardView_notificationComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topicID = notificationCommentsDO.getPostID();
                view.getContext().startActivity(new Intent(view.getContext(), CommentsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationCommentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cardView_notificationComments) CardView cardView_notificationComments;
        @InjectView(R.id.imgNotificationComments_commenterProfilePic) CircleImageView imgNotificationComments_commenterProfilePic;
        @InjectView(R.id.lblNotificationComments_message) TextView lblNotificationComments_message;
        @InjectView(R.id.lblNotificationComments_dateAndTimeCommented) TextView lblNotificationComments_dateAndTimeCommented;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,view);
        }
    }
}
