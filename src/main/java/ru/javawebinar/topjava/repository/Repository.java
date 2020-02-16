package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface Repository {

    void add(Meal meal);

    void delete(int mealId);

    void update(Meal meal);

    List<Meal> getAll();

    Meal getById(int mealId);
}
