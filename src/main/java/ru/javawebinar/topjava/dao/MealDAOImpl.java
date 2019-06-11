package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;



public class MealDAOImpl implements MealDAO {
    private Map<Integer, Meal> mealList = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger();

    @Override
    public Collection<Meal> getAll() {
        return mealList.values();
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null)
            meal.setId(id.incrementAndGet());
        mealList.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        mealList.remove(id);
    }


    @Override
    public Meal getById(int id) {
        return mealList.get(id);
    }
}
