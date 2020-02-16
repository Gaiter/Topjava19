package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {
    private int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    //    private final Supplier<Boolean> excess;
//    private final AtomicBoolean excess;
    private final boolean excess;

    public MealTo(int id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Boolean getExcess() {
        return excess;
    }

    public int getId() {
        return id;
    }

    public int getCalories() {
        return calories;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
