package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.repository.RepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private Repository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new RepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meal");

        String action = request.getParameter("action");
        if (action == null) {
            action = "listMeals";
        }
        switch (action) {
            case "delete": {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                repository.delete(mealId);
                request.getSession().setAttribute("meals", MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                response.sendRedirect("meals");
                break;
            }
            case "edit": {
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = repository.getById(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("mealadd.jsp").forward(request, response);
                break;
            }
            case "listMeals": {
                request.setAttribute("meals", MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
                break;
            }
            case "add": {
                request.getRequestDispatcher("mealadd.jsp").forward(request, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            repository.add(new Meal(0, localDateTime, description, calories));
        } else {
            repository.update((new Meal(Integer.parseInt(id), localDateTime, description, calories)));
        }
        RequestDispatcher view = request.getRequestDispatcher("meals.jsp");
        request.setAttribute("meals", MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }
}
