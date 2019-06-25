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
        Meal meal = mealService.get(MEAL1_USER.getId(), USER_ID);
        MealTestData.assertMatch(meal, MEAL1_USER);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL1_USER.getId(), USER_ID);
        MealTestData.assertMatch(mealService.getAll(USER_ID), MEAL3_USER, MEAL2_USER);
    }

    @Test
    public void getBetweenDates() {
        LocalDate start = LocalDate.of(2015, Month.MAY, 30);
        LocalDate end = LocalDate.of(2015, Month.MAY, 30);
        List<Meal> result = mealService.getBetweenDates(start, end, USER_ID);
        assertMatch(result, MEAL3_USER, MEAL2_USER, MEAL1_USER);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime start = LocalDateTime.of(2015, Month.MAY, 30, 13, 0);
        LocalDateTime end = LocalDateTime.of(2015, Month.MAY, 30, 20, 0);
        List<Meal> result = mealService.getBetweenDateTimes(start, end, USER_ID);
        assertMatch(result, MEAL3_USER, MEAL2_USER);
    }

    @Test
    public void getAll() {
        List<Meal> mealListOne = mealService.getAll(USER_ID);
        MealTestData.assertMatch(mealListOne, MEAL3_USER, MEAL2_USER, MEAL1_USER);

    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1_USER);
        updated.setDescription("Fastfood");
        updated.setCalories(666);
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(MEAL1_USER.getId(), USER_ID), updated);
    }

    @Test
    public void create() {
        Meal meal = new Meal(null, LocalDateTime.of(2015, Month.JULY, 15, 20, 0), "Dinner", 888);
        Meal createdMeal = mealService.create(meal, USER_ID);
        meal.setId(createdMeal.getId());
        assertMatch(mealService.getAll(USER_ID), meal, MEAL3_USER, MEAL2_USER, MEAL1_USER);

    }

    @Test(expected = NotFoundException.class)
    public void getAlien(){
        mealService.get(MEAL1_USER.getId(), ADMIN_ID);

    }

    @Test(expected = NotFoundException.class)
    public void deleteAlien(){
        mealService.delete(MEAL1_USER.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlien(){
        Meal updated = MEAL4_ADMIN;
        updated.setDescription("updated meal");
        mealService.update(updated, USER_ID);
    }
}