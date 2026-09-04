
package com.example.demo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "task_type")
@JsonSubTypes({@JsonSubTypes.Type(value = Task.class,name = "NORMAL"),@JsonSubTypes.Type( value = DeadlineTask.class, name = "DEADLINE")})
public class Task {
    private String title;
    private String description;
    private boolean done;
    private int id;
    public enum Priority {LOW,MEDIUM,HIGH};
    private Priority prio =  Priority.LOW;
    public enum Kategorie {WORK,PERSONAL,SCHOOL}
    private Kategorie kat = Kategorie.WORK;
    public Task(String title,String description,boolean done){
        this.title = title;
        this.description = description;
        this.done = done;
    }

    public void setPrio(Priority Choice){
        this.prio = Choice;
    }

    public Priority getPrio(){
        return prio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void showTask(){
        System.out.println("Your task: "+title);
        System.out.println("Description:  "+description);
        System.out.println("Status: "+ (done ? "done" : "undone"));
        System.out.println("Id: "+id+".");
        System.out.println("Prio: "+prio);
        System.out.println("Kat: "+kat);
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public boolean isDone() {
        return done;
    }
    public void setKat(Kategorie kat) {this.kat = kat;}
    public Kategorie getKat() {
        return kat;
    }
}
