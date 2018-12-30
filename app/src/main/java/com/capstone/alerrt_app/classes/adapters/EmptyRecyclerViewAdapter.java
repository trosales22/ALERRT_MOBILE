package com.capstone.alerrt_app.classes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstone.alerrt_app.R;

public class EmptyRecyclerViewAdapter extends RecyclerView.Adapter<EmptyRecyclerViewAdapter.ViewHolder>{

    public EmptyRecyclerViewAdapter(){}

    @Override
    public EmptyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_post_available, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmptyRecyclerViewAdapter.ViewHolder holder, int position) {}

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
