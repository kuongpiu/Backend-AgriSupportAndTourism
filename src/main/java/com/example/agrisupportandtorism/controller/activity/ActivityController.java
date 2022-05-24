package com.example.agrisupportandtorism.controller.activity;

import com.example.agrisupportandtorism.entity.activity.Activity;
import com.example.agrisupportandtorism.repository.activity.ActivityRepo;
import com.example.agrisupportandtorism.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:9528/")
@RequestMapping(value = "/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping(value = "/all")
    public List<Activity> getAll(){
        return activityService.findAll();
    }

    @PostMapping
    public Activity insertActivity(@Valid @RequestBody Activity activity){
        return activityService.insert(activity);
    }

    @PutMapping
    public Activity updateActivity(@Valid @RequestBody Activity activity){
        return activityService.update(activity);
    }
}
