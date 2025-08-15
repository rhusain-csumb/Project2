/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.adapter;

import android.content.Context;
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
 * RecyclerView Adapter for displaying a list of pet health logs.
 */
public class HealthLogAdapter extends RecyclerView.Adapter<HealthLogAdapter.HealthLogViewHolder> {

    private List<HealthLog> healthLogs;
    private Context context;

    /**
     * Constructor for HealthLogAdapter.
     * @param context The context from the calling activity.
     * @param healthLogs The list of HealthLog objects to display.
     */
    public HealthLogAdapter(Context context, List<HealthLog> healthLogs) {
        this.context = context;
        this.healthLogs = healthLogs;
    }

    @NonNull
    @Override
    public HealthLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflates the layout for a single list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_health_log, parent, false);
        return new HealthLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthLogViewHolder holder, int position) {
        // Binds the data from the list to the views in the ViewHolder
        HealthLog log = healthLogs.get(position);
        holder.logTitle.setText(log.getLogTitle());
        holder.logDate.setText(log.getDate());
        holder.logNotes.setText(log.getNotes());

        // You can add an OnClickListener here if you want to allow editing a log
    }

    @Override
    public int getItemCount() {
        // Returns the total number of items in the list
        return healthLogs.size();
    }

    /**
     * The ViewHolder class that holds references to the views for each list item.
     */
    static class HealthLogViewHolder extends RecyclerView.ViewHolder {
        TextView logTitle, logDate, logNotes;

        public HealthLogViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initializes the views by finding them by their ID in the layout file
            logTitle = itemView.findViewById(R.id.log_title);
            logDate = itemView.findViewById(R.id.log_date);
            logNotes = itemView.findViewById(R.id.log_notes);
        }
    }

    /**
     * Method to update the list of health logs and refresh the RecyclerView.
     * @param newHealthLogs The new list of health logs.
     */
    public void updateLogs(List<HealthLog> newHealthLogs) {
        this.healthLogs = newHealthLogs;
        notifyDataSetChanged();
    }
}
