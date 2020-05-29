package com.company.Messages;

import java.util.List;

public class Historia {
    private int id;
    private List<Missatge> missatges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Missatge> getMissatges() {
        return missatges;
    }

    public void setMissatges(List<Missatge> missatges) {
        this.missatges = missatges;
    }
}
