package com.company.Messages;

import com.company.Photo;

import java.util.List;

public class Missatge implements Comparable<Missatge>{
    private List<String> text;
    private Integer id;
    private List<Integer> seguent;
    private List<String> key_words;
    private List<Photo> photos;
    private boolean end;

    public List<String> getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }

    public List<Integer> getSeguent() {
        return seguent;
    }

    public boolean isEnd() {
        return end;
    }

    public List<String> getKey_words() {
        return key_words;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    @Override
    public int compareTo(Missatge o) {
        if (o == null) {
            return -1;
        }
        return this.getId() < o.getId() ? -1 : 1;
    }
}
