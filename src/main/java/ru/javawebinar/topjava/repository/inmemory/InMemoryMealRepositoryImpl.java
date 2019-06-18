package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer,Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
        Map<Integer, Meal> repMeal = repository.get(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        else if (repMeal.get(meal.getId()) == null){
            return null;
        }
        repository.computeIfAbsent(userId, HashMap::new).put(meal.getId(), meal);

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Map<Integer, Meal> repMeal = repository.get(userId);
        return repMeal.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} ", id);
        Map<Integer, Meal> repMeal = repository.get(userId);
        Meal meal = repMeal != null ? repMeal.get(id) : null;
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        List<Meal> meals = repository.get(userId).values().stream().sorted(Comparator.comparing(Meal::getDate)).collect(Collectors.toList());
        Collections.reverse(meals);
        return meals;
    }

    @Override
    public Collection<Meal> getAllFilter(int userId, LocalDate startDate, LocalDate endDate){

       return repository.get(userId).values().stream()
               .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
               .collect(Collectors.toList());
    }




}

