package com.example.gymquest.Language;
import java.io.Serializable;

public class Language implements Serializable {
    private String name;
    private int image;
    private String language_code;

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
    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }
    public String getLanguage_code() {
        return language_code;
    }
}
