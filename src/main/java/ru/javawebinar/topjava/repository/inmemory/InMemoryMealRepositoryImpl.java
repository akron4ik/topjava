package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
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
        Meal mealNew = null;

        if (meal.isNew() && repository.get(userId)!=null) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(),meal);
            mealNew = meal;
        }
        else if(meal.isNew() && repository.get(userId)==null){
            Map<Integer, Meal> repMeal = new HashMap<>();
            meal.setId(counter.incrementAndGet());
            repMeal.put(meal.getId(),meal);
            repository.put(userId, repMeal);
        }
        else if(!meal.isNew()) {
            mealNew = repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return mealNew;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        boolean flag = false;
        if(repository.get(userId) != null){
            repository.get(userId).remove(id);
            flag = true;
        }
        return flag;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} ", id);
        Meal meal = null;
        if(repository.get(userId) != null) {
            meal = repository.get(userId).get(id);
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        return repository.get(userId).values();
    }

    @Override
    public Collection<MealTo> getAllFilter(int userId, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate){
      List<Meal> meals = new ArrayList<>(getAll(userId));
      return MealsUtil.getFilteredWithExcess(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY,
                meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime)
                        && DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate));

    }




}

