package com.company.Messages;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class Historia  {
    private List<Missatge> missatges;

    public Historia(String fitxer) throws FileNotFoundException {
        String f = new File("").getAbsolutePath();
        Gson gson = new Gson();

        JsonReader reader = new JsonReader(new FileReader(f.concat(fitxer)));
        Historia h = gson.fromJson(reader, Historia.class);
        Collections.sort(h.getMissatges());
        this.missatges = h.missatges;
    }
    
    public List<Missatge> getMissatges() {
        return missatges;
    }

    public void setMissatges(List<Missatge> missatges) {
        this.missatges = missatges;
    }
}
