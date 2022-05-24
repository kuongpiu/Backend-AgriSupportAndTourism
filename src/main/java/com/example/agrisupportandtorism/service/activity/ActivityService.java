package com.example.agrisupportandtorism.service.activity;

import com.example.agrisupportandtorism.entity.activity.Activity;
import com.example.agrisupportandtorism.exception.ResourceNotFoundException;
import com.example.agrisupportandtorism.repository.activity.ActivityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepo activityRepo;

    public List<Activity> findAll() {
        return activityRepo.findAll();
    }

    public Activity findById(Integer id) {
        Optional<Activity> activityOpt = activityRepo.findById(id);
        if (activityOpt.isPresent()) {
            return activityOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Hoạt động này không tồn tại, mã=[%s]", id));
        }
    }

    @Transactional
    public Activity insert(Activity activity) {
        return activityRepo.save(activity);
    }

    @Transactional
    public Activity update(Activity newActivity) {
        Activity activityInRepo = findById(newActivity.getId());

        activityInRepo.setDescription(newActivity.getDescription());

        return activityRepo.save(activityInRepo);
    }

    @Transactional
    public void delete(Integer id) {
        activityRepo.deleteById(id);
    }
}
