package com.example.gymquest;

public class UserRoutine {
    public String title,duration,exercises,email;


    public UserRoutine(){

    }
    public UserRoutine(String title, String duration, String exercises, String email){
        this.title=title;
        this.duration=duration;
        this.exercises=exercises;
        this.email=email;

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
    public String getEmail() {
        return email;
    }
}
