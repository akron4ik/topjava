package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();//итоговый список в который добавляем все подходящие записи
        Map<LocalDate, Integer> dayAndCalories = new HashMap<>();//мапа для подсчета всех калорий в день

        for (UserMeal u: mealList) {
           dayAndCalories.merge(u.getDateTime()//считаем все калории в день,
                    .toLocalDate(), u.getCalories(), (a,b)-> b + u.getCalories());//ключ - день, значение - кол-во калорий
        }

        for (UserMeal u: mealList) {
            if (TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime)
                    && dayAndCalories.getOrDefault(u.getDateTime().toLocalDate(),0)>caloriesPerDay )  {
                result.add(new UserMealWithExceed(u.getDateTime(),u.getDescription(),u.getCalories(), false));
            }
            else if (TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime)
                    && dayAndCalories.getOrDefault(u.getDateTime().toLocalDate(),0)<caloriesPerDay ){
                result.add(new UserMealWithExceed(u.getDateTime(),u.getDescription(),u.getCalories(), true));
            }
        }
        return result;
    }
}
