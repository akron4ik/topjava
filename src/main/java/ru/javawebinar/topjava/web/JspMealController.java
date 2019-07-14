package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class JspMealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    public String meals(Model model){
        List<MealTo> meals = MealsUtil.getWithExcess(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping(value = "delete")
    public String delete(HttpServletRequest request){
            int id = Integer.parseInt(request.getParameter("id"));
            mealService.delete(id, SecurityUtil.authUserId());
            return "redirect:meals";
    }


}
