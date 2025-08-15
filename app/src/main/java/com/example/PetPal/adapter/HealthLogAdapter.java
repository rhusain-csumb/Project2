/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
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

    private final Context context;
    private List<HealthLog> healthLogs = new ArrayList<>();

    public HealthLogAdapter(Context context) {
        this.context = context;
    }

    public void setHealthLogs(List<HealthLog> healthLogs) {
        this.healthLogs = healthLogs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HealthLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_health_log, parent, false);
        return new HealthLogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthLogViewHolder holder, int position) {
        HealthLog currentLog = healthLogs.get(position);

        String type = safe(currentLog.getType());
        String date = safe(currentLog.getDate());  // may contain "yyyy-MM-dd HH:mm"
        String desc = safe(currentLog.getDescription());
        String treat = safe(currentLog.getTreatment());

        holder.logTypeTextView.setText(currentLog.getType());
        holder.logDateTextView.setText(currentLog.getDate());
        holder.logDescriptionTextView.setText(currentLog.getDescription());
        holder.logTreatmentTextView.setText(currentLog.getTreatment());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = HealthLogDetailActivity.newIntent(context, currentLog.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return healthLogs.size();
    }

    private static String saf(String s) {
        return s == null ? "" : s;
    }

    static class HealthLogViewHolder extends RecyclerView.ViewHolder {
        final TextView logTypeTextView;
        final TextView logDateTextView;
        final TextView logDescriptionTextView;
        final TextView logTreatmentTextView;

        HealthLogViewHolder(View itemView) {
            super(itemView);
            logTypeTextView = itemView.findViewById(R.id.log_type_textview);
            logDateTextView = itemView.findViewById(R.id.log_date_textview);
            logDescriptionTextView = itemView.findViewById(R.id.log_description_textview);
            logTreatmentTextView = itemView.findViewById(R.id.log_treatment_textview);
        }
    }
}
