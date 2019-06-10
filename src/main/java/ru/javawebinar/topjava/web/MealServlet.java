package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDAO;
import ru.javawebinar.topjava.dao.MealDAOImpl;
import ru.javawebinar.topjava.model.Meal;
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
    private MealDAO mealDAO = new MealDAOImpl();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("format", format);

        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String date = request.getParameter("date");
        LocalDateTime parsedDate = LocalDateTime.parse(date, format);
        Meal meal = new Meal(parsedDate, description, calories);
        String id = request.getParameter("mealId");

        if (id == null || id.isEmpty()) {
            mealDAO.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            mealDAO.update(meal);
        }
        request.setAttribute("meals", mealDAO.allMeals());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("format", format);
        String action = request.getParameter("action");

        switch (action == null ? "default" : action) {
            case "delete":{
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                mealDAO.delete(mealId);
                response.sendRedirect("meals");
                return;
            }
            case "edit":{
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealDAO.getById(mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;

            }
            case "add":{
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;
            }
            default:
                request.setAttribute("meals", mealDAO.allMeals());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }

    }




}
