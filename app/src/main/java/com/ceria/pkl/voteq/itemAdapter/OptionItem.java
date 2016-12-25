package com.ceria.pkl.voteq.itemAdapter;

import android.widget.RadioButton;

/**
 * Created by win 8 on 7/2/2016.
 */
public class OptionItem {
    private String id, title, percentage, image;
    private int total_voter;
    private RadioButton radioButton;

    public OptionItem(String title) {
        this.title = title;
    }

    public OptionItem(String id, String title, String percentage, int total_voter, String image) {
        this.id = id;
        this.title = title;
        this.percentage = percentage;
        this.total_voter = total_voter;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotal_voter() {
        return total_voter;
    }

    public void setTotal_voter(int total_voter) {
        this.total_voter = total_voter;
    }

    public void setRadioButton(){
        radioButton.setChecked(true);
    }
}
