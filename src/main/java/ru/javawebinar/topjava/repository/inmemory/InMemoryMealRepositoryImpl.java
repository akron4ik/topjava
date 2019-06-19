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
        return repMeal == null ? null : meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Map<Integer, Meal> repMeal = repository.get(userId);
        return repMeal != null && repMeal.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} ", id);
        Map<Integer, Meal> repMeal = repository.get(userId);
        return repMeal != null ? repMeal.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        return getAllSort(userId);
        /*Map<Integer, Meal> repMeal = repository.get(userId);
        return repMeal == null ? Collections.emptyList() : repMeal.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());*/
    }

    @Override
    public List<Meal> getAllFilter(int userId, LocalDate startDate, LocalDate endDate){
        log.info("getAllFilter {}", userId);
        //Map<Integer, Meal> repMeal = repository.get(userId);
        /*return repMeal == null ? Collections.emptyList() : repMeal.values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());*/
        return getAllSort(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate)).collect(Collectors.toList());

    }

    public List<Meal> getAllSort(int userId){
        Map<Integer, Meal> repMeal = repository.get(userId);
        return repMeal == null ? Collections.emptyList() : repMeal.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }




}

