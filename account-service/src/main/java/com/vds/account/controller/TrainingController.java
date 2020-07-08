package com.vds.account.controller;

import com.vds.account.domain.Training;
import com.vds.account.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Training> getTrainingById(@PathVariable Long id) {
        return new ResponseEntity<>(trainingService.findTrainingById(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<Training> createTraining(@Valid @RequestBody Training training) {
        return new ResponseEntity<>(trainingService.create(training), HttpStatus.CREATED);
    }

}
