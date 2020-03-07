package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    private MealService service;

    @Test(expected = NotFoundException.class)
    public void notFoundGet() {
        service.get(USERMEAL_ID_100004.getId(), ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(USERMEAL_ID_100004.getId(), USER_ID);
        MealTestData.assertMatch(USERMEAL_ID_100004, meal);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(USERMEAL_ID_100004.getId(), ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(USERMEAL_ID_100002.getId(), MealTestData.USER_ID);
        MealTestData.assertMatch(service.getAll(USER_ID), USERMEAL_ID_100007, USERMEAL_ID_100006, USERMEAL_ID_100005,
                USERMEAL_ID_100004, USERMEAL_ID_100003);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> meals = service.getBetweenHalfOpen(LocalDate.of(2015, Month.MAY, 29),
                LocalDate.of(2015, Month.MAY, 30), USER_ID);
        MealTestData.assertMatch(meals, USERMEAL_ID_100004, USERMEAL_ID_100003, USERMEAL_ID_100002);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(MealTestData.USER_ID);
        MealTestData.assertMatch(all, USERMEAL_ID_100007, USERMEAL_ID_100006, USERMEAL_ID_100005, USERMEAL_ID_100004,
                USERMEAL_ID_100003, USERMEAL_ID_100002);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        service.update(USERMEAL_ID_100002, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal testMeal = new Meal(100004, LocalDateTime.of(2015, Month.MAY, 15, 20, 0),
                "Ночной дожор", 2000);
        service.update(testMeal, USER_ID);
        MealTestData.assertMatch(service.get(100004,USER_ID), testMeal);
    }

    @Test
    public void create() {
        Meal testMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 15, 20, 0),
                "Ночной дожор", 2000);
        Meal created = service.create(testMeal, USER_ID);
        int id = created.getId();
        testMeal.setId(id);
        MealTestData.assertMatch(service.get(id, USER_ID), testMeal);
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicateDateCreate() {
        Meal testMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 20, 0),
                "Ночной дожор", 2000);
        service.create(testMeal, USER_ID);
    }
}