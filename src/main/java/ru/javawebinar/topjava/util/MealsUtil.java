package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MealsUtil {
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,1),
            new Meal(LocalDateTime.of(2016, Month.APRIL, 30, 10, 0), "Завтрак", 600,1),
            new Meal(LocalDateTime.of(2016, Month.APRIL, 30, 13, 0), "Обед", 1000,1),
            new Meal(LocalDateTime.of(2016, Month.APRIL, 30, 20, 0), "Ужин", 200,1),
            new Meal(LocalDateTime.of(2016, Month.APRIL, 6, 10, 0), "Завтрак", 1000,1),//
            new Meal(LocalDateTime.of(2016, Month.APRIL, 6, 13, 0), "Обед", 500,1),//
            new Meal(LocalDateTime.of(2016, Month.APRIL, 6, 20, 0), "Ужин", 510,1),//
            new Meal(LocalDateTime.of(2016, Month.JANUARY, 30, 10, 0), "Завтрак", 600,2),
            new Meal(LocalDateTime.of(2016, Month.JANUARY, 30, 13, 0), "Обед", 1000,2),
            new Meal(LocalDateTime.of(2016, Month.JANUARY, 30, 20, 0), "Ужин", 200,2),
            new Meal(LocalDateTime.of(2016, Month.JANUARY, 31, 10, 0), "Завтрак", 1000,2),
            new Meal(LocalDateTime.of(2016, Month.JANUARY, 31, 13, 0), "Обед", 500,2),
            new Meal(LocalDateTime.of(2016, Month.JANUARY, 31, 20, 0), "Ужин", 510,2),
            new Meal(LocalDateTime.of(2017, Month.DECEMBER, 30, 10, 0), "Завтрак", 600,2),
            new Meal(LocalDateTime.of(2017, Month.DECEMBER, 30, 13, 0), "Обед", 1000,2),
            new Meal(LocalDateTime.of(2017, Month.DECEMBER, 30, 20, 0), "Ужин", 200,2),
            new Meal(LocalDateTime.of(2017, Month.DECEMBER, 31, 10, 0), "Завтрак", 1000,2),
            new Meal(LocalDateTime.of(2017, Month.DECEMBER, 31, 13, 0), "Обед", 500,2),
            new Meal(LocalDateTime.of(2017, Month.DECEMBER, 31, 20, 0), "Ужин", 510,2)

            );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static List<MealTo> getWithExcess(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFilteredWithExcess(meals, caloriesPerDay, meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> getFilteredWithExcess(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createWithExcess(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    private static MealTo createWithExcess(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}