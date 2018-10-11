package com.example.demo.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class NumberFormatter {
    public static final DecimalFormat DECIMAL_FORMAT;

    static{
        DECIMAL_FORMAT = new DecimalFormat("###,###,##0.000000000");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setGroupingSeparator(' ');

        DECIMAL_FORMAT.setDecimalFormatSymbols(decimalFormatSymbols);
    }

}
