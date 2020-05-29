package com.company.Messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class Intro {
    public List<SendMessage> showIntro(long chat_id){
        List<SendMessage> messages = new ArrayList<>();

        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText("Hola?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);
        message = new SendMessage()
                .setChatId(chat_id)
                .setText("Estàs bé?");
        messages.add(message);

        return messages;
    }
}
