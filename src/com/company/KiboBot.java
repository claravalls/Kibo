package com.company;

import com.company.Messages.Historia;
import com.company.Messages.Intro;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.util.List;

public class KiboBot extends TelegramLongPollingBot {
    private static final String ERROR_MESSAGE = "Ui em sembla que no t'he entès :confused: Torna-ho a provar.";
    private Historia historia;

    public KiboBot(){
        try {
            this.historia = new Historia("/src/files/missatges.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            //Per fer el emote
            //String answer = EmojiParser.parseToUnicode("Bienvenido a Kibo bot :smile:");
            //SendMessage message = new SendMessage() // Create a message object object
            //            .setChatId(chat_id)
            //            .setText(answer);
            //PER FER FOTO
            // SendPhoto msg = new SendPhoto()
            //            .setChatId(chat_id)
            //            .setPhoto("AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC")
            //            .setCaption("Photo");
            if (update.getMessage().getText().equals("/start")) {
                Intro intro = new Intro(historia.getMissatges().get(0).getText());
                List<SendMessage> messages = intro.showIntro(chat_id);
                try {
                    for (SendMessage message : messages) {
                        execute(message); // Sending our message object to user
                        Thread.sleep(1500);
                    }
                } catch (TelegramApiException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                String answer = EmojiParser.parseToUnicode(ERROR_MESSAGE);
                SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(answer);
                try {
                    execute(message); // Sending our message object to user

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
