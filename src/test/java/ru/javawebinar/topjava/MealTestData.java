package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal USERMEAL_ID_100002 = new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30,
            10, 0), "Завтрак", 500);
    public static final Meal USERMEAL_ID_100003 = new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30,
            13, 0), "Обед", 1000);
    public static final Meal USERMEAL_ID_100004 = new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30,
            20, 0), "Ужин", 500);
    public static final Meal USERMEAL_ID_100005 = new Meal(100005, LocalDateTime.of(2015, Month.MAY, 31,
            10, 0), "Завтрак", 1000);
    public static final Meal USERMEAL_ID_100006 = new Meal(100006, LocalDateTime.of(2015, Month.MAY, 31,
            13, 0), "Обед", 500);
    public static final Meal USERMEAL_ID_100007 = new Meal(100007, LocalDateTime.of(2015, Month.MAY, 31,
            20, 0), "Ужин", 510);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
}

