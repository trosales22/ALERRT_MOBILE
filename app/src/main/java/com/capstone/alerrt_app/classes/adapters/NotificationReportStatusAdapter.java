package com.capstone.alerrt_app.classes.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.data_objects.NotificationReportStatusDO;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationReportStatusAdapter extends RecyclerView.Adapter<NotificationReportStatusAdapter.ViewHolder>{
    private List<NotificationReportStatusDO> notificationReportStatusList;
    private Context context;
    private View view;
    private static String statusID;

    public NotificationReportStatusAdapter(List<NotificationReportStatusDO> notificationReportStatusList, Context context) {
        this.notificationReportStatusList = notificationReportStatusList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_report_status, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NotificationReportStatusDO notificationReportStatusDO = notificationReportStatusList.get(position);

        StringBuilder sb = new StringBuilder();

        sb.append(notificationReportStatusDO.getUpdatedBy_fullname());
        sb.append(" updated your report status (");
        sb.append(notificationReportStatusDO.getStatusTopicTitle());
        sb.append(") into ");
        sb.append(notificationReportStatusDO.getStatusType().toUpperCase());
        sb.append(".");

        holder.lblNotificationReportStatus_message.setText(sb.toString());

        if(notificationReportStatusDO.getUpdatedBy_profilePic().isEmpty()){
            holder.imgNotificationReportStatus_updaterProfilePic.setImageResource(R.drawable.logo);
        }else {
            Picasso.with(context)
                    .load(notificationReportStatusDO.getUpdatedBy_profilePic())
                    .error(R.mipmap.ic_launcher_round)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(holder.imgNotificationReportStatus_updaterProfilePic);
        }

        holder.lblNotificationReportStatus_dateAndTimeUpdated.setText(notificationReportStatusDO.getStatusDateAndTimeUpdated());
    }

    @Override
    public int getItemCount() {
        return notificationReportStatusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.imgNotificationReportStatus_updaterProfilePic) CircleImageView imgNotificationReportStatus_updaterProfilePic;
        @InjectView(R.id.lblNotificationReportStatus_message) TextView lblNotificationReportStatus_message;
        @InjectView(R.id.lblNotificationReportStatus_dateAndTimeUpdated) TextView lblNotificationReportStatus_dateAndTimeUpdated;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,view);
        }
    }
}
