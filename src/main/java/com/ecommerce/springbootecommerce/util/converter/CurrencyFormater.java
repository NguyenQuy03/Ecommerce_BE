package com.ecommerce.springbootecommerce.util.converter;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormater {
    public static String formatDollars(double value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_EVEN);
        return format.format(value);
    }
}
