package com.ceria.pkl.voteq.itemAdapter;

/**
 * Created by win 8 on 7/2/2016.
 */
public class OptionItem {
    private String option, optionValue;

    public OptionItem(String option, String optionValue) {
        this.option = option;
        this.optionValue = optionValue;
    }

    public String getOption() {
        return option;
    }

    public String getOptionValue() {
        return optionValue;
    }

    @Override
    public String toString() {
        return "OptionItem{" +
                "option=" + option +
                ", optionValue=" + optionValue +
                '}';
    }
}
