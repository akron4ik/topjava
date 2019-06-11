package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDAO mealDAO;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealDAO = new MealDAOImpl();
        MealsUtil.MEAL_LIST.forEach(meal -> mealDAO.save(meal));
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        log.debug("save or update meal");
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("format", format);

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String date = request.getParameter("date");
        LocalDateTime parsedDate = LocalDateTime.parse(date, format);
        Meal meal = new Meal(parsedDate, description, calories);
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            mealDAO.save(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealDAO.save(meal);
        }
        request.setAttribute("meals", MealsUtil.getAllMeals(mealDAO.getAll(),MealsUtil.CALORIES_PER_DAY));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("format", format);
        String action = request.getParameter("action");

        switch (action == null ? "default" : action) {
            case "delete":{
                int id = Integer.parseInt(request.getParameter("id"));
                mealDAO.delete(id);
                response.sendRedirect("meals");
                return;
            }
            case "edit":
            case "add":{

                request.setAttribute("meal", action.equals("add")
                        ? new Meal(LocalDateTime.now(),"",0)
                        : mealDAO.getById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;
            }
            default:
                request.setAttribute("meals", MealsUtil.getAllMeals(mealDAO.getAll(),MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }

    }




}
