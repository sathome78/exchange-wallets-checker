package com.example.demo.schedulers;



import com.example.demo.domain.Coin;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public interface NotificatorService {

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.000000000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    String ABOVE_MAX_LIMIT =
            "\uD83C\uDF4E Кошелёк: %s  Превышен максимальный лимит!  Время: %s\n\n " +
            "Баланс: %s\n\n" +
            "Мин.лимит: %s   Max.Лимит: %s\n";

    String LOW_THAN_MIN_AMOUNT  =
            "\uD83C\uDF4B Кошелёк: %s  Ниже нижнего лимита!    Время: %s\n\n " +
            "Баланс: %s\n\n" +
            "Мин.лимит: %s   Max.Лимит: %s\n";

    String PERMISSIBLE_RANGE =
            "\uD83C\uDF4F Кошелёк: %s  Количество в допустимом диапазоне!    Время: %s\n\n " +
            "Баланс: %s\n\n" +
            "Мин.лимит: %s   Max.Лимит: %s\n";


    void notificate(String template, Coin coin);

    default String getCurrentDate() {
        return dateFormat.format(new Date());
    }
}
