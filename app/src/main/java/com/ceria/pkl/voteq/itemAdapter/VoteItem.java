package com.ceria.pkl.voteq.itemAdapter;

/**
 * Created by Haniyah on 12/17/2016.
 */

public class VoteItem {
    private String id, username, started, ended, title, category, description, status, userImage, voteImage;
    private int responder;

    public VoteItem(String id, String username, String started, String ended, String title, String category, String description, String status, int responder, String userImage, String voteImage) {
        this.id = id;
        this.username = username;
        this.started = started;
        this.ended = ended;
        this.title = title;
        this.category = category;
        this.description = description;
        this.status = status;
        this.responder = responder;
        this.userImage = userImage;
        this.voteImage = voteImage;
    }

    public VoteItem() {  }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStarted() {
        return started;
    }

    public void setStarted(String started) {
        this.started = started;
    }

    public String getEnded() {
        return ended;
    }

    public void setEnded(String ended) {
        this.ended = ended;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResponder() {
        return responder;
    }

    public void setResponder(int responder) {
        this.responder = responder;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getVoteImage() {
        return voteImage;
    }

    public void setVoteImage(String voteImage) {
        this.voteImage = voteImage;
    }

    @Override
    public String toString() {
        return "VoteItem{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", started='" + started + '\'' +
                ", ended='" + ended + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", responder='" + responder + '\'' +
                ", userImage=" + userImage +
                ", voteImage=" + voteImage +
                '}';
    }
}
