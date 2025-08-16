/**
 * HealthLogAdapter.java
 *
 * Adapter class for displaying a list of HealthLog objects inside a RecyclerView.
 *
 * This adapter is used to show pet health logs such as vaccinations, treatments, and visits.
 * Each item in the RecyclerView represents a single health log entry and shows details like:
 *   - Log type (e.g., Vaccination, Checkup)
 *   - Date
 *   - Description
 *   - Treatment
 *
 * Clicking on a log opens HealthLogDetailActivity to view more details.
 *
 * @authors:
 *   Rasna Husain
 *   Chanroop Randhawa
 *
 *   Last Updated: August 15, 2025
 */

package com.example.PetPal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.PetPal.HealthLogDetailActivity;
import com.example.PetPal.R;
import com.example.PetPal.model.HealthLog;

import java.util.ArrayList;
import java.util.List;

public class HealthLogAdapter extends RecyclerView.Adapter<HealthLogAdapter.HealthLogViewHolder> {

    // Holds the Activity/Fragment context (needed for inflating layouts & starting activities)
    private final Context context;

    // List of health log entries to display
    private List<HealthLog> healthLogs = new ArrayList<>();

    /**
     * Constructor for HealthLogAdapter
     * @param context - Context of the Activity or Fragment using this adapter
     */
    public HealthLogAdapter(Context context) {
        this.context = context;
    }

    /**
     * Updates the list of health logs in the adapter.
     * @param healthLogs - New list of HealthLog objects
     */
    public void setHealthLogs(List<HealthLog> healthLogs) {
        this.healthLogs = healthLogs;
        notifyDataSetChanged(); // Refresh RecyclerView display
    }

    @NonNull
    @Override
    public HealthLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single health log item
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_health_log, parent, false);
        return new HealthLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthLogViewHolder holder, int position) {
        // Get the current health log for this row
        HealthLog currentLog = healthLogs.get(position);

        // Display health log data in a user-friendly format
        holder.logTypeTextView.setText("Type: " + currentLog.getType());
        holder.logDateTextView.setText("When: " + currentLog.getDate());
        holder.logDescriptionTextView.setText("Description: " + currentLog.getDescription());
        //holder.logTreatmentTextView.setText("Treatment: " + currentLog.getTreatment());

        // Set click listener for the item to open HealthLogDetailActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = HealthLogDetailActivity.newIntent(context, currentLog.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        // Number of health logs in the list
        return healthLogs.size();
    }

    /**
     * ViewHolder class for holding references to UI components in each item view.
     */
    static class HealthLogViewHolder extends RecyclerView.ViewHolder {
        final TextView logTypeTextView;
        final TextView logDateTextView;
        final TextView logDescriptionTextView;
        //final TextView logTreatmentTextView;

        /**
         * Constructor for ViewHolder
         * @param itemView - The view for a single item in the list
         */
        HealthLogViewHolder(View itemView) {
            super(itemView);
            logTypeTextView = itemView.findViewById(R.id.log_type_textview);
            logDateTextView = itemView.findViewById(R.id.log_date_textview);
            logDescriptionTextView = itemView.findViewById(R.id.log_description_textview);
            //logTreatmentTextView = itemView.findViewById(R.id.log_treatment_textview);
        }
    }
}
