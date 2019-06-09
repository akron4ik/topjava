package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDateTime;
import java.time.Month;

import java.util.Arrays;
import java.util.List;

public class MealDAOImpl implements MealDAO {
    private static List<MealTo> meals;

    static {
        meals = Arrays.asList(
                new MealTo(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500,false),
                new MealTo(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000,false),
                new MealTo(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500,false),
                new MealTo(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000,true),
                new MealTo(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500,true),
                new MealTo(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,true)
        );


    }


    @Override
    public List<MealTo> allMeals() {
        return meals;
    }

    @Override
    public void add(MealTo mealTo) {

    }

    @Override
    public void delete(MealTo mealTo) {

    }

    @Override
    public void edit(MealTo mealTo) {

    }

    @Override
    public MealTo getById(int id) {
        return null;
    }
}
