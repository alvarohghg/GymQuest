package com.example.gymquest;
import java.io.Serializable;

public class Language implements Serializable {
    private String name;
    private int image;

    public Language(){
    }
    public String getName(){
        return name;
    }
    public int getImage() {
        return image;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImage(int image) {
        this.image = image;
    }
}
