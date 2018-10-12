package com.example.demo.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberFormatter {
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,##0.000");

    public static String format(BigDecimal bigDecimal) {

        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(' ');

        DECIMAL_FORMAT.setDecimalFormatSymbols(decimalFormatSymbols);
        return DECIMAL_FORMAT.format(bigDecimal);
    }

}
