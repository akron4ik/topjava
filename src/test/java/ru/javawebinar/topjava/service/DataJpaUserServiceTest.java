package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import java.util.Collections;
import java.util.List;
import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;



@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {
    @Autowired
    private UserService service;


   @Test
    public void getUserMealById() throws Exception{
        User user = service.getMealByUser(USER_ID);
        List<Meal> meals = user.getMeals();
        Collections.reverse(meals);
        MealTestData.assertMatch(meals, MEALS);
    }
}