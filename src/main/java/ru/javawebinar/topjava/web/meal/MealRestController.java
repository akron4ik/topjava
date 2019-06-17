package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;


@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal){
        log.info("create {}", meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("getAll {}");
        return MealsUtil.getWithExcess(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFiltered(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        log.info("getAllFiltered ");
        if(startTime == null){
          startTime = LocalTime.MIN;
        }
        if(endTime == null){
          endTime = LocalTime.MAX;
        }
        if(startDate == null){
          startDate= LocalDate.MIN;
        }
        if(endDate == null){
          endDate=LocalDate.MAX;
        }
        return service.getAllFilter(authUserId(),startTime,endTime,startDate,endDate);
    }



}