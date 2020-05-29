package com.company.Messages;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

public class Intro {
    private List<String> miss_intro;

    public Intro(List<String> miss_intro){
        this.miss_intro = miss_intro;
    }
    public List<SendMessage> showIntro(long chat_id){
        List<SendMessage> messages = new ArrayList<>();
        for (String text:miss_intro) {
            String answer = EmojiParser.parseToUnicode(text);
            SendMessage message = new SendMessage()
                    .setChatId(chat_id)
                    .setText(answer);
            messages.add(message);
        }

        return messages;
    }
}
