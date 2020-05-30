package com.company.Messages;

import com.company.Photo;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MessagesManager {


    public List<SendMessage> showMessage(long chat_id, List<String> missatges){
        List<SendMessage> messages = new ArrayList<>();

        for (String text:missatges) {
            String answer = EmojiParser.parseToUnicode(text);
            SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(answer);
            messages.add(message);
        }
        return messages;
    }

    public List<SendMessage> showFinalMessage(long chat_id){
        List<SendMessage> messages = new ArrayList<>();
        String answer = EmojiParser.parseToUnicode("I fins aquí la teva aventura! Si vols tornar a començar només cal que m'enviïs el missatge /start. Cada decisió et portarà per nous camins. Ens veiem aviat!:wave:");
        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText(answer);
        messages.add(message);
        return messages;
    }

    public List<SendPhoto> sendPhoto(long chat_id, Photo photo){
        List<SendPhoto> photos = new ArrayList<>();
        SendPhoto msg = new SendPhoto()
                .setChatId(chat_id)
                .setPhoto(photo.getPath())
                .setCaption(photo.getCaption());
        photos.add(msg);
        return photos;
    }

    public void changeKeyboard (List<String> buttons){ //NO FA REEEEEEEEEEEEEEEEEEEEEEEEES
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();

        for (String button: buttons) {
            row.add(new KeyboardButton(button));
            // Add the first row to the keyboard
            keyboard.add(row);
            // Create another keyboard row
            row = new KeyboardRow();
        }
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
    }
}
