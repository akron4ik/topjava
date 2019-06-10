package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;



public class MealDAOImpl implements MealDAO {
    private static final List<Meal> mealList = new ArrayList<>();
    private static AtomicInteger id = new AtomicInteger();
    static {
        mealList.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealList.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealList.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealList.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealList.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealList.add(new Meal(id.incrementAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }

    @Override
    public List<MealTo> allMeals() {
        return MealsUtil.getAllMeals(mealList, 2000);
    }

    @Override
    public void add(Meal meal) {
        if (meal.getId() == null)
            meal.setId(id.incrementAndGet());
        mealList.add(meal);
    }

    @Override
    public void delete(int id) {
        mealList.stream()
                .filter(meal -> meal.getId() == id)
                .findFirst()
                .ifPresent(mealList::remove);
    }

    @Override
    public void update(Meal meal) {
        delete(meal.getId());
        add(meal);
    }

    @Override
    public Meal getById(int id) {
        return mealList.stream()
                .filter(meal -> meal.getId() == id)
                .findFirst()
                .get();
    }
}
