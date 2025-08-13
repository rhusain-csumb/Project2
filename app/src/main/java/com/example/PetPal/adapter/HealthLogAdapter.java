/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

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

/**
 * RecyclerView Adapter for displaying a list of health logs
 */
public class HealthLogAdapter extends RecyclerView.Adapter<HealthLogAdapter.HealthLogViewHolder> {
    private List<HealthLog> healthLogList;

    public HealthLogAdapter(List<HealthLog> healthLogList) {
        this.healthLogList = healthLogList;
    }

    @NonNull
    @Override
    public HealthLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_health_log, parent, false);
        return new HealthLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthLogViewHolder holder, int position) {
        HealthLog healthLog = healthLogList.get(position);
        holder.logTitle.setText(healthLog.type + " • " + healthLog.date);
        holder.logDescription.setText(healthLog.description);
    }

    @Override
    public int getItemCount() {
        return healthLogList.size();
    }

    /**
     * Updates the list of health logs in the adapter.
     * @param newLogs The new list of logs.
     */
    public void updateLogs(List<HealthLog> newLogs) {
        this.healthLogList = newLogs;
        notifyDataSetChanged();
    }

    static class HealthLogViewHolder extends RecyclerView.ViewHolder {
        TextView logTitle, logDescription;

        public HealthLogViewHolder(@NonNull View itemView) {
            super(itemView);
            logTitle = itemView.findViewById(R.id.log_title);
            logDescription = itemView.findViewById(R.id.log_desc);
        }
    }
}