package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {
    @Autowired
    MealService service;

    @Test
    public void getMealAndUser(){
        Meal meal = service.getMealWithUser(MEAL1_ID, USER_ID);
        User user = meal.getUser();
        UserTestData.assertMatch(user, USER);
        assertMatch(meal, MEAL1);
    }

    @Test
    public void getMealAndUserNotFound(){
        thrown.expect(NotFoundException.class);
        service.getMealWithUser(1,1);
    }

}