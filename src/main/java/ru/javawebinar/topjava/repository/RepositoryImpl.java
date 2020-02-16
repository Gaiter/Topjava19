package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RepositoryImpl implements Repository {

    private static final AtomicInteger number = new AtomicInteger(6);

    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public RepositoryImpl() {
        for (Meal meal : MealsUtil.getMeals()) {
            meals.put(meal.getId(), meal);
        }
    }

    public void add(Meal meal) {
        meal.setId(number.getAndIncrement());
        meals.put(meal.getId(), meal);
    }

    public void delete(int mealId) {
        meals.remove(mealId);
    }

    public void update(Meal meal) {
        meals.put(meal.getId(), meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    public Meal getById(int mealId) {
        return meals.get(mealId);
    }
}
