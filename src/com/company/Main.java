package com.company;

import com.company.Messages.Historia;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            Historia illaDeserta = new Historia("/src/files/missatges.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new KiboBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
