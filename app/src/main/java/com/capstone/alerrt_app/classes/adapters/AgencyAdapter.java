package com.capstone.alerrt_app.classes.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.activity.ViewSpecificAgencyActivity;
import com.capstone.alerrt_app.classes.data_objects.AgencyDO;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AgencyAdapter extends RecyclerView.Adapter<AgencyAdapter.ViewHolder>{
    private List<AgencyDO> agencyList;
    private Context context;
    private View view;

    public static String agencyID,agencyCaption,agencyFullname,agencyPosition,agencyContactNumber;

    public AgencyAdapter(List<AgencyDO> agencyList, Context context) {
        this.agencyList = agencyList;
        this.context = context;
    }

    @Override
    public AgencyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agency, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgencyAdapter.ViewHolder holder, int position) {
        final AgencyDO agencyDO = agencyList.get(position);

        holder.lblAgencyCaption.setText(agencyDO.getAgencyCaption());
        holder.btnViewAgency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agencyID = agencyDO.getAgencyID();
                agencyCaption = agencyDO.getAgencyCaption();
                agencyFullname = agencyDO.getAgencyFirstname().concat(" ").concat(agencyDO.getAgencyLastname());
                agencyPosition = agencyDO.getAgencyPosition();
                agencyContactNumber = agencyDO.getAgencyContactNumber();

                view.getContext().startActivity(new Intent(view.getContext(), ViewSpecificAgencyActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return agencyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.cardView_agency) CardView cardView_agency;
        @InjectView(R.id.lblAgencyCaption) TextView lblAgencyCaption;
        @InjectView(R.id.btnViewAgency) AppCompatButton btnViewAgency;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,view);
        }
    }
}
