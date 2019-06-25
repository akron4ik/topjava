package ru.javawebinar.topjava;
import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;


import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal MEAL1_USER = new Meal(100000, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500);
    public static final Meal MEAL2_USER = new Meal(100001, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Lunch", 510);
    public static final Meal MEAL3_USER =  new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Dinner", 400);
    public static final Meal MEAL4_ADMIN =  new Meal(100003, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 600);
    public static final Meal MEAL5_ADMIN = new Meal(100004, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Lunch", 520);
    public static final Meal MEAL6_ADMIN =  new Meal(100005, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Dinner", 1000);



    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }


    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }


    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
