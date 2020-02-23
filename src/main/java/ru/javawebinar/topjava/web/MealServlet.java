package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@WebServlet
public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Autowired
    private MealRestController controller;
    private ConfigurableApplicationContext appCtx;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String userId = request.getParameter("userid");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), userId.length() == 0 ? null : Integer.parseInt(userId),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.save(meal);
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                String dateStartSt = request.getParameter("localdatestart");
                LocalDate dateStart = dateStartSt == null || dateStartSt.length() == 0 ? LocalDate.MIN :
                        LocalDate.parse(dateStartSt, DateTimeFormatter.ISO_DATE);
                String dateEndSt = request.getParameter("localdateend");
                LocalDate dateEnd = dateEndSt == null || dateEndSt.length() == 0 ? LocalDate.MAX :
                        LocalDate.parse(dateEndSt, DateTimeFormatter.ISO_DATE);
                String timeStartSt = request.getParameter("localtimestart");
                LocalTime timeStart = timeStartSt == null || timeStartSt.length() == 0 ? LocalTime.MIN :
                        LocalTime.parse(timeStartSt, DateTimeFormatter.ISO_TIME);
                String timeEndSt = request.getParameter("localtimeend");
                LocalTime timeEnd = timeEndSt == null || timeEndSt.length() == 0 ? LocalTime.MAX :
                        LocalTime.parse(timeEndSt, DateTimeFormatter.ISO_TIME);
                request.setAttribute("meals", controller.getAllByDate(dateStart, dateEnd, timeStart, timeEnd));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();
    }
}
