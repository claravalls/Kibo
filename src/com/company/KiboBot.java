package com.company;

import com.company.Messages.Historia;
import com.company.Messages.MessagesManager;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class KiboBot extends TelegramLongPollingBot {
    private static final String ERROR_MESSAGE = "Ui em sembla que no t'he entès:confused: Torna-ho a provar.";
    private Historia historia;
    private MessagesManager messagesManager;
    private User user;
    private Integer last_mess = -1; //index de l'ultim missatge que ha enviat

    public KiboBot(){
        try {
            this.historia = new Historia("/src/files/missatges.json");
            this.user = new User();
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
                messagesManager = new MessagesManager();
                List<SendMessage> messages = messagesManager.showMessage(chat_id, historia.getMissatges().get(0).getText());
                try {
                    for (SendMessage message : messages) {
                        execute(message); // Sending our message object to user
                        Thread.sleep(1500);
                    }
                    last_mess = 0;
                } catch (TelegramApiException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                boolean ok = showNext(chat_id, message_text);
                if(!ok) {
                    String answer = EmojiParser.parseToUnicode(ERROR_MESSAGE);
                    SendMessage message = new SendMessage( )
                            .setChatId(chat_id)
                            .setText(answer);
                    try {
                        execute(message);

                    } catch (TelegramApiException e) {
                        e.printStackTrace( );
                    }
                }
            }
        }
    }

    private boolean showNext (long chat_id, String missatge){
        List<SendMessage> messages;
        List<SendPhoto> photos = new ArrayList<>();
        if (last_mess == 0){
            user.setName(missatge); //recullo el nom
            List<String> text = new ArrayList<>();
            text.add("Tens un nom que queda bé amb la teva cara, " + user.getName());
            text.addAll(historia.getMissatges().get(1).getText());
            messages = messagesManager.showMessage(chat_id, text);
            last_mess = 1;
        }
        else if (last_mess < 0){
            return false;
        }
        else {
            List<String> key_words = historia.getMissatges().get(last_mess).getKey_words();
            missatge = missatge.toLowerCase();

            if (missatge.contains(key_words.get(0)) || missatge.contains(key_words.get(1))) {
                last_mess = historia.getMissatges().get(last_mess).getSeguent().get(0);
                messages = messagesManager.showMessage(chat_id, historia.getMissatges().get(last_mess).getText());
            }
            else if(missatge.contains(key_words.get(2)) || missatge.contains(key_words.get(3))){
                last_mess = historia.getMissatges().get(last_mess).getSeguent().get(1);
                messages = messagesManager.showMessage(chat_id, historia.getMissatges().get(last_mess).getText());
            }
            else
                return false;
        }
        if(historia.getMissatges().get(last_mess).getPhotos() != null){
            for (Photo photo: historia.getMissatges().get(last_mess).getPhotos()) {
                photos.addAll(messagesManager.sendPhoto(chat_id, photo));
            }
        }

        if(historia.getMissatges().get(last_mess).getButtons() != null){
            messagesManager.changeKeyboard(historia.getMissatges().get(last_mess).getButtons());
        }

        while(historia.getMissatges().get(last_mess).getSeguent().size() == 1){ //salta de branca (només té una opció)
            last_mess = historia.getMissatges().get(last_mess).getSeguent().get(0);
            messages.addAll(messagesManager.showMessage(chat_id, historia.getMissatges().get(last_mess).getText()));
        }
        if(historia.getMissatges().get(last_mess).isEnd()){ //ha arribat a un final
            last_mess= -1;
            messages.addAll(messagesManager.showFinalMessage(chat_id));
        }
        try {
            for (SendMessage message : messages) {
                execute(message); // Sending our message object to user
                Thread.sleep(1800);
            }
        } catch (TelegramApiException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            for (SendPhoto photo : photos) {
                execute(photo); // Sending our message object to user
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return true;
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
