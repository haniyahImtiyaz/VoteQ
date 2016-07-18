package com.ceria.pkl.voteq;

/**
 * Created by pandhu on 11/07/16.
 */
public class HomeItem {
    private String id, textTitle, textCount,label;
    private int image;



    public HomeItem(String id, String textTitle, String textCount, String label, int image) {
        this.id = id;
        this.textTitle = textTitle;
        this.textCount = textCount;
        this.label = label;
        this.image = image;
    }

    public String getId(){
        return id;
    }
    public String getTextTitle() {
        return textTitle;
    }

    public String getTextCount() {
        return textCount;
    }

    public String getLabel() {
        return label;
    }

    public int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "HomeItem{" +
                "id='" + id + '\'' +
                "textTitle='" + textTitle + '\'' +
                ", textCount='" + textCount + '\'' +
                ", label='" + label + '\'' +
                ", image=" + image +
                '}';
    }
}
