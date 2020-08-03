package com.database.DietRestriction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller    // This means that this class is a Controller
@RequestMapping(path = "/diet") // This means URL's start with /diet(after Application path)
public class DietRestrictionController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private DietRestrictionRepository dietRestrictionRepository;

    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody
    String addDietRestriction(@RequestParam String diet, @RequestParam String exIngred, @RequestParam String intolerance) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        DietRestriction dietRestriction = new DietRestriction();
        dietRestriction.setDiet(diet);
        dietRestriction.setExIngred(exIngred);
        dietRestriction.setIntolerance(intolerance);
        return "Saved New Dietary Restriction";
    }

    @PostMapping(path = "/update") // Map ONLY POST Requests
    public @ResponseBody
    String updateDietRestriction(@RequestParam String email, @RequestParam String diet,
                                 @RequestParam String exingred, @RequestParam String intorlerance) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Optional<DietRestriction> resultFromDB = dietRestrictionRepository.findById(email);
        if (!resultFromDB.isPresent()) {
            return "No user by " + email + " is found";
        }
        DietRestriction newDiet = resultFromDB.get();

        newDiet.setDiet(diet);
        newDiet.setExIngred(exingred);
        newDiet.setIntolerance(intorlerance);
        dietRestrictionRepository.save(newDiet);
        return "Update the dietary restriction for user " + email;
    }

    @GetMapping(path = "/search")
    public @ResponseBody
    Iterable<DietRestriction> getDiet(@RequestParam String email) {
        // This returns a JSON or XML with the users

        Optional<DietRestriction> o = dietRestrictionRepository.findById(email);
        return o.map(Collections::singleton)
                .orElseGet(Collections::emptySet);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<DietRestriction> getAllDietRestrictions() {
        // This returns a JSON or XML with the users
        return dietRestrictionRepository.findAll();
    }
}