package com.ceria.pkl.voteq.itemAdapter;

/**
 * Created by win 8 on 7/2/2016.
 */
public class ResultItem {
    private String textId, textTitle, textValue, textPercent;

    public ResultItem(String textId, String textTitle, String textValue, String textPercent) {
        this.textId = textId;
        this.textTitle = textTitle;
        this.textValue = textValue;
        this.textPercent = textPercent;
    }

    public String getTextId() {
        return textId;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public String getTextValue() {
        return textValue;
    }

    public String getTextPercent() {
        return textPercent;
    }
}
