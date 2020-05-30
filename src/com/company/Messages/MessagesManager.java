package com.company.Messages;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
}
