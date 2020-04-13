package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    MealService mealService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());
        MEAL_MATCHER.assertMatch(mealService.getAll(USER_ID), MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEALTO_MATCHER.contentJson(
                        MealsUtil.createTo(MEAL7, true),
                        MealsUtil.createTo(MEAL6, true),
                        MealsUtil.createTo(MEAL5, true),
                        MealsUtil.createTo(MEAL4, true),
                        MealsUtil.createTo(MEAL3, false),
                        MealsUtil.createTo(MEAL2, false),
                        MealsUtil.createTo(MEAL1, false)
                        ));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(null, of(2018, Month.MAY, 31, 10, 0), "Новый Завтрак", 500);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        MEAL_MATCHER.assertMatch(returned, expected);
        MEAL_MATCHER.assertMatch(mealService.getAll(USER_ID), MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1, expected);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1.getId(), MEAL1.getDateTime(), "Обновленный Завтрак", 500);
        mockMvc.perform(put(REST_URL + MEAL1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MEAL_MATCHER.assertMatch(mealService.get(MEAL1.getId(), USER_ID), updated);
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter")
                .param("startDate", "2020-01-30")
                .param("startTime", "07:00")
                .param("endDate", "2020-01-30")
                .param("endTime", "20:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEALTO_MATCHER.contentJson(
                        MealsUtil.createTo(MEAL2, false),
                        MealsUtil.createTo(MEAL1, false)));
    }
}