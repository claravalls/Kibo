package com.company;

import com.company.Messages.Historia;
import com.company.Messages.MessagesManager;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Connections extends Thread {
    private int userId;
    private long chatId;
    private String message;
    private boolean new_mess;
    private boolean isRunning = true;
    private static final String ERROR_MESSAGE = "Ui em sembla que no t'he entès:confused: Torna-ho a provar.";
    private Historia historia;
    private MessagesManager messagesManager;
    private User user;
    private Integer last_mess = -1; //index de l'ultim missatge que ha enviat
    private KiboBot kiboBot;
    private BlockingQueue<String> queue;

    public Connections(int userId, long chatId, Historia historia, KiboBot kiboBot){
        this.userId = userId;
        this.chatId = chatId;
        this.historia = historia;
        this.new_mess = false;
        this.kiboBot = kiboBot;
        this.user = new User();
    }

    public void setQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    public BlockingQueue<String> getQueue() {
        return queue;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNew_mess(boolean new_mess) {
        this.new_mess = new_mess;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                message = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace( );
                break;
            }
            if (message.equals("/start")) {
                messagesManager = new MessagesManager( );
                List<SendMessage> messages = messagesManager.showMessage(chatId, historia.getMissatges( ).get(0).getText( ));
                try {
                    for (SendMessage message : messages) {
                        kiboBot.sendMessage(message); // Sending our message object to user
                        Thread.sleep(1500);
                    }
                    last_mess = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace( );
                }
            } else {
                boolean ok = showNext(chatId, message);
                if (!ok) {
                    String answer = EmojiParser.parseToUnicode(ERROR_MESSAGE);
                    SendMessage message = new SendMessage( )
                            .setChatId(chatId)
                            .setText(answer);
                    kiboBot.sendMessage(message);
                }
            }
            new_mess = false;
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
            isRunning = false;
        }
        try {
            for (SendMessage message : messages) {
                kiboBot.sendMessage(message); // Sending our message object to user
                Thread.sleep(1800);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (SendPhoto photo : photos) {
            kiboBot.sendPhoto(photo); // Sending our message object to user
        }

        return true;
    }
}
