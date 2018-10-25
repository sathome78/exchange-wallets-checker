package com.example.demo.schedulers;



import com.example.demo.domain.Coin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface NotificatorService {

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    String ABOVE_MAX_LIMIT =
            "\uD83C\uDF4E Кошелёк: %s  Превышен максимальный лимит!  Время: %s\n\n " +
            "Баланс: %s   Баланс в $:\n\n" +
            "Мин.лимит: %s   Max.Лимит: %s\n"+
            "Мин.лимит в $: %s   Max.Лимит в $: %s\n";

    String LOW_THAN_MIN_AMOUNT  =
            "\uD83C\uDF4B Кошелёк: %s  Ниже нижнего лимита!    Время: %s\n\n " +
            "Баланс: %s   Баланс в $:\n\n" +
            "Мин.лимит: %s   Max.Лимит: %s\n"+
            "Мин.лимит в $: %s   Max.Лимит в $: %s\n";

    String PERMISSIBLE_RANGE =
            "\uD83C\uDF4F Кошелёк: %s  Количество в допустимом диапазоне!    Время: %s\n\n " +
            "Баланс: %s   Баланс в $:\n\n" +
            "Мин.лимит: %s   Max.Лимит: %s\n"+
            "Мин.лимит в $: %s   Max.Лимит в $: %s\n";


    void notificate(String template, Coin coin);

    default String getCurrentDate() {
        return dateFormat.format(new Date());
    }
}
