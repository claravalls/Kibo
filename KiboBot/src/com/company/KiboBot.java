package com.company;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class KiboBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            if (update.getMessage().getText().equals("/start")) {

                List<SendMessage> messages = new ArrayList<>();

                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Bienvendio a Kibo bot");
                SendMessage message2 = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("Isla desierta");
                messages.add(message);
                messages.add(message2);
                try {
                    for (int i = 0; i < messages.size(); i++) {
                        execute(messages.get(i)); // Sending our message object to user
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public String getBotUsername() {
        return "Kibobotbot";
    }

    @Override
    public String getBotToken() {
        return "1175244759:AAGBwHCMkVdTc-aX8eyEsBwTx6UkWmnpkho";
    }
}
