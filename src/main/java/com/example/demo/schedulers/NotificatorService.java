package com.example.demo.schedulers;



import com.example.demo.domain.Coin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface NotificatorService {

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    String ABOVE_MAX_LIMIT =
            "\uD83C\uDF4E Кошелёк: %s\nПревышен максимальный лимит!\nВремя: %s\n\n" +
            "Баланс: %s  \nБаланс в $: %s\n\n" +
            "Мин.лимит: %s \nMax.лимит: %s\n\n"+
            "Мин.лимит в $: %s \nMax.лимит в $: %s\n";

    String LOW_THAN_MIN_AMOUNT  =
            "\uD83C\uDF4B Кошелёк: %s\nНиже нижнего лимита!\nВремя: %s\n\n" +
            "Баланс: %s  \nБаланс в $: %s\n\n" +
            "Мин.лимит: %s \nMax.лимит: %s\n\n"+
            "Мин.лимит в $: %s \nMax.лимит в $: %s\n";

    String PERMISSIBLE_RANGE =
            "\uD83C\uDF4F Кошелёк: %s\nКоличество в допустимом диапазоне!\nВремя: %s\n\n" +
            "Баланс: %s  \nБаланс в $: %s\n\n" +
            "Мин.лимит: %s \nMax.лимит: %s\n\n"+
            "Мин.лимит в $: %s \nMax.лимит в $: %s\n";


    void notificate(String renderedTemplate);

    static String getCurrentDate() {
        return dateFormat.format(new Date());
    }
}
