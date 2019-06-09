package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDAO {
    List<MealTo> allMeals();
    void add(MealTo mealTo);
    void delete(MealTo mealTo);
    void edit(MealTo mealTo);
    MealTo getById(int id);
}
