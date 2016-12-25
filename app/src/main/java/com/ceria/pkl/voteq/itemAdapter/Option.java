package com.ceria.pkl.voteq.itemAdapter;

/**
 * Created by Haniyah on 12/24/2016.
 */

public class Option {
        private String option, optionValue;

        public Option(String option, String optionValue) {
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
