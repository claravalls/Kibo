package com.company;

import com.company.Messages.Historia;
import com.company.Messages.MessagesManager;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class KiboBot extends TelegramLongPollingBot {
    private Historia historia;

    private List<Connections> connections = new ArrayList<>();

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

            if (message_text.equals("/start")) {
                Connections c = new Connections(update.getMessage( ).getFrom( ).getId( ), chat_id, historia, this);
                c.setMessage(message_text);
                c.setNew_mess(true);
                c.setQueue(new LinkedBlockingQueue<>());
                c.getQueue().offer(message_text);
                connections.add(c);
                c.start( );
            }
            else{
                Connections c = getThread(update.getMessage( ).getFrom( ).getId( ));
                c.getQueue().offer(message_text);
                c.setNew_mess(true);
            }
        }
    }

    public Connections getThread (int userId){
        for (int i = connections.size(); i > 0; i--) {
            Connections c = connections.get(i - 1);
            if(c.getUserId() == userId){
                return c;
            }
        }
        return null;
    }

    public void sendMessage (SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace( );
        }
    }
    public void sendPhoto (SendPhoto message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace( );
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
