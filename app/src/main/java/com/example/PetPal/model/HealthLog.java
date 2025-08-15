/**
 * This is an android moble application called PetPal. This practical app will track pet visits,
 * vaccinations, feeding schedules, and medications. Key features will include an emergency contact list
 * and the potential to scan food/medication for streamlined data entry.
 * @authors: Rasna Husain and Chanroop Randhawa
 */

package com.example.PetPal.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "health_logs",
        foreignKeys = @ForeignKey(entity = Pet.class,
                parentColumns = "pet_id",
                childColumns = "pet_id",
                onDelete = CASCADE))
public class HealthLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int pet_id;
    private String type;
    private String date;
    private String description;
    private String treatment;

    public HealthLog(int pet_id, String type, String date, String description, String treatment) {
        this.pet_id = pet_id;
        this.type = type;
        this.date = date;
        this.description = description;
        this.treatment = treatment;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getPet_id() {
        return pet_id;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTreatment() {
        return treatment;
    }

    // Setters (required by Room)
    public void setId(int id) {
        this.id = id;
    }

    public void setPet_id(int pet_id) {
        this.pet_id = pet_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}
