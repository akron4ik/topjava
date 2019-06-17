package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    // false if not found
    boolean delete(int id, int userId);

    // null if not found
    Meal get(int id, int userId);

    Collection<Meal> getAll(int userId);

    Collection<MealTo> getAllFilter(int userId, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate);
}
