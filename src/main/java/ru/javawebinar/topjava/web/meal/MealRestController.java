package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeConverter;
import java.net.URI;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping(value = MealRestController.REST_MEALS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    public static final String REST_MEALS_URL = "/rest/meals";

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime localStartDateTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime localEndDateTime;

    @GetMapping
    public List<MealTo> getAll(){
      return super.getAll();
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id){
        return super.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        super.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id){
        super.update(meal, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal){
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_MEALS_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
    //home work base
    @PostMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam String startDate, @RequestParam String endDate){
        localStartDateTime = LocalDateTime.parse(startDate);
        localEndDateTime = LocalDateTime.parse(endDate);
        LocalDate startdate = localStartDateTime.toLocalDate();
        LocalDate enddate = localEndDateTime.toLocalDate();
        LocalTime starttime = localStartDateTime.toLocalTime();
        LocalTime endtime = localEndDateTime.toLocalTime();
        return super.getBetween(startdate, starttime, enddate, endtime);
    }
    //home work optional
    @PostMapping(value = "/filtered", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String startTime, @RequestParam String endTime) throws ParseException {
        DateTimeConverter dateTimeConverter = new DateTimeConverter();

        LocalDate startdate = dateTimeConverter.parse(startDate, Locale.getDefault()).toLocalDate();
        LocalDate enddate = dateTimeConverter.parse(endDate, Locale.getDefault()).toLocalDate();
        LocalTime starttime = dateTimeConverter.parse(startTime, Locale.getDefault()).toLocalTime();
        LocalTime endtime = dateTimeConverter.parse(endTime, Locale.getDefault()).toLocalTime();

        return super.getBetween(startdate, starttime, enddate, endtime);
    }



}