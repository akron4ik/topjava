package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.Collection;


public interface MealDAO {
    Collection<Meal> getAll();
    Meal save(Meal meal);
    void delete(int id);
    Meal getById(int id);
}
