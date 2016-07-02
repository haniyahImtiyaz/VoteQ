package com.ceria.pkl.voteq;

/**
 * Created by win 8 on 7/2/2016.
 */
public class ResultItem {
    private String textTitle, textValue, textPercent;

    public ResultItem(String textTitle, String textValue, String textPercent) {
        this.textTitle = textTitle;
        this.textValue = textValue;
        this.textPercent = textPercent;
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
