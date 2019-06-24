package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(100000, USER_ID);
        MealTestData.assertMatch(meal, MEAL1);
    }

    @Test
    public void delete() {
        mealService.delete(100000, USER_ID);
        MealTestData.assertMatch(mealService.getAll(USER_ID), MEAL3, MEAL2);
    }

    @Test
    public void getBetweenDates() {
        LocalDate start = LocalDate.of(2015, Month.MAY, 30);
        LocalDate end = LocalDate.of(2015, Month.MAY, 30);
        List<Meal> result = mealService.getBetweenDates(start, end, USER_ID);
        assertMatch(result, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime start = LocalDateTime.of(2015, Month.MAY, 30, 13, 0);
        LocalDateTime end = LocalDateTime.of(2015, Month.MAY, 30, 20, 0);
        List<Meal> result = mealService.getBetweenDateTimes(start, end, USER_ID);
        assertMatch(result, MEAL3, MEAL2);
    }

    @Test
    public void getAll() {
        List<Meal> mealListOne = mealService.getAll(USER_ID);
        List<Meal> mealListTwo = mealService.getAll(ADMIN_ID);
        MealTestData.assertMatch(mealListOne, MEAL3, MEAL2, MEAL1);
        MealTestData.assertMatch(mealListTwo, MEAL6, MEAL5, MEAL4);

    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Fastfood");
        updated.setCalories(666);
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(100000, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal meal = new Meal(null, LocalDateTime.of(2015, Month.JULY, 15, 20, 0), "Dinner", 888);
        Meal createdMeal = mealService.create(meal, USER_ID);
        meal.setId(createdMeal.getId());
        assertMatch(mealService.getAll(USER_ID), meal, MEAL3, MEAL2, MEAL1);

    }

    @Test(expected = NotFoundException.class)
    public void getAlien(){
        mealService.get(100000, ADMIN_ID);

    }

    @Test(expected = NotFoundException.class)
    public void deleteAlien(){
        mealService.delete(100000, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlien(){
        Meal updated = new Meal(MEAL1);
        Meal up = mealService.get(updated.getId(), ADMIN_ID);
        mealService.update(up, ADMIN_ID);
    }
}