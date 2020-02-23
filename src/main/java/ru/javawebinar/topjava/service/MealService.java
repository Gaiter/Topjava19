package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal save(Meal meal, int userId) {
        Meal mealSave = checkNotFoundWithId(repository.save(meal,userId),meal.getId());
        if (mealSave == null) {
            throw new NotFoundException("Meal not found");
        }
        return mealSave;
    }

    public void delete(int id, int userId) {
        if (!repository.delete(id, userId)) {
            throw new NotFoundException("Meal not found id=" + id);
        }
    }

    public Meal get(int id, int userId) {
        Meal meal = checkNotFoundWithId(repository.get(id, userId),id);
        if (meal == null) {
            throw new NotFoundException("Meal not found id=" + id);
        }
        return meal;
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public Collection<Meal> getAllByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllByDate(userId, startDate, endDate);
    }
}