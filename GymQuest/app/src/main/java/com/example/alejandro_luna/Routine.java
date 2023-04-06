package com.example.alejandro_luna;

public class Routine {
    public String title,duration,exercises;


    public Routine(){

    }
    public Routine(String title, String duration, String exercises){
        this.title=title;
        this.duration=duration;
        this.exercises=exercises;

    }
    public String getTitle() {
        return title;
    }
}
