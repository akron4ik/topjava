package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;


@Controller
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String meals(HttpServletRequest request, Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = mealService.getBetweenDates(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> meals = MealsUtil.getFilteredWithExcess(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", meals);
        return "meals";
    }
    @PostMapping("/meals")
    public String save(HttpServletRequest request)throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            mealService.create(meal, SecurityUtil.authUserId());
        } else {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            mealService.update(meal, SecurityUtil.authUserId());
        }
        return "redirect:meals";
    }

    @GetMapping(value = "delete")
    public String delete(HttpServletRequest request){
            int id = Integer.parseInt(request.getParameter("id"));
            mealService.delete(id, SecurityUtil.authUserId());
            return "redirect:meals";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = mealService.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }
    @GetMapping("/save")
    public String create(Model model){
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }





}
