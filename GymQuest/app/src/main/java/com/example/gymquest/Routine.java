package com.example.gymquest;

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
    public String getDuration() {
        return duration;
    }
    public String getExercises() {
        return exercises;
    }
}
