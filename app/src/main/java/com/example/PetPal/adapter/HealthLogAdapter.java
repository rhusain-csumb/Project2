package com.example.PetPal.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.R;
import com.example.PetPal.model.HealthLog;

import java.util.List;

public class HealthLogAdapter extends RecyclerView.Adapter<HealthLogAdapter.HealthLogHolder> {

    private List<HealthLog> healthLogs;

    public HealthLogAdapter(List<HealthLog> healthLogs) {
        this.healthLogs = healthLogs;
    }

    @NonNull
    @Override
    public HealthLogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health_log, parent, false);
        return new HealthLogHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthLogHolder holder, int position) {
        HealthLog healthLog = healthLogs.get(position);
        holder.bind(healthLog);
    }

    @Override
    public int getItemCount() {
        return healthLogs.size();
    }

    public void updateLogs(List<HealthLog> newLogs) {
        this.healthLogs = newLogs;
        notifyDataSetChanged();
    }

    static class HealthLogHolder extends RecyclerView.ViewHolder {
        private final TextView logTitle;
        private final TextView logDesc;

        public HealthLogHolder(@NonNull View itemView) {
            super(itemView);
            logTitle = itemView.findViewById(R.id.log_title);
            logDesc = itemView.findViewById(R.id.log_desc);
        }

        public void bind(HealthLog healthLog) {
            logTitle.setText(healthLog.type + " • " + healthLog.date);
            logDesc.setText(healthLog.description);
        }
    }
}