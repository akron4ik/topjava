package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class MealRestControllerTest extends AbstractControllerTest {

    public static final String REST_MEALS_URL = MealRestController.REST_MEALS_URL + '/';

    @Test
    void testGetAll() throws Exception {
         mockMvc.perform(get(REST_MEALS_URL))
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(contentJson(MEALS1));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_MEALS_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_MEALS_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Meal> expected = List.of(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        assertMatch(mealService.getAll(USER_ID), expected);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_MEALS_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void testCreateWithLocation() throws Exception {
        Meal expected = getCreated();
        ResultActions action = mockMvc.perform(post(REST_MEALS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());
        Meal returned = readFromJson(action, Meal.class);
        expected.setId(returned.getId());
        assertMatch(returned, expected);
        assertMatch(mealService.getAll(USER_ID), expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    void testGetBetween() throws Exception {
        mockMvc.perform(post(REST_MEALS_URL + "filter")
                .param("startDate", "2015-05-31T10:00:00")
                .param("endDate", "2015-05-31T13:00:00")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(contentJson(MealsUtil.getFilteredWithExcess(mealService.getBetweenDates(LocalDate.of(2015, 05, 31), LocalDate.of(2015, 05, 31), USER_ID)
                        , 2000, LocalTime.of( 10,00), LocalTime.of(13,00))));
    }

    @Test
    void testGetBetweenOptional() throws Exception {
        mockMvc.perform(get(REST_MEALS_URL + "filtered")
                .param("startDate", "2015-05-31")
                .param("endDate", "2015-05-31")
                .param("startTime", "10:00:00")
                .param("endTime", "13:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(contentJson(MealsUtil.getFilteredWithExcess(mealService.getBetweenDates(LocalDate.of(2015, 05, 31), LocalDate.of(2015, 05, 31), USER_ID)
                        , 2000, LocalTime.of( 10,00), LocalTime.of(13,00))));
    }
}