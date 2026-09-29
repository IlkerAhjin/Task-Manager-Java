package com.example.demo;

import java.time.LocalDate;

public class DeadlineTask extends Task {
    private final LocalDate date;

    public DeadlineTask(String title, String description, boolean done, LocalDate date) {
        super(title, description, done);
        this.date = date;
    }

    public boolean isOverdue(){
        return date.isBefore(LocalDate.now())&&!super.isDone();
}

    public LocalDate getDate() {
        return date;
    }

}

