package com.ceria.pkl.voteq;

/**
 * Created by pandhu on 11/07/16.
 */
public class HomeItem {
    private String id, textTitle, textCount, label, idCreator;

    public HomeItem(String id, String textTitle, String textCount, String label, String idCreator) {
        this.id = id;
        this.textTitle = textTitle;
        this.textCount = textCount;
        this.label = label;
        this.idCreator = idCreator;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
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


    public String getIdCreator() {
        return idCreator;
    }

    @Override
    public String toString() {
        return "HomeItem{" +
                "id='" + id + '\'' +
                "textTitle='" + textTitle + '\'' +
                ", textCount='" + textCount + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
