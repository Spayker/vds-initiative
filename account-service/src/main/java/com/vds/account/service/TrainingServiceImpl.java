package com.vds.account.service;

import com.vds.account.domain.Account;
import com.vds.account.domain.Training;
import com.vds.account.exception.TrainingException;
import com.vds.account.repository.TrainingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private TrainingRepository trainingRepository;

    @Override
    public Training findTrainingById(Long id) {
        Optional<Training> training = trainingRepository.findById(id);
        return training.orElse(null);
    }

    @Override
    public List<Training> findTrainingsByDeviceName(String name) {
        return trainingRepository.findByDeviceName(name);
    }

    @Override
    public List<Training> findTrainingsByDeviceFirmware(String firmware) {
        return trainingRepository.findByDeviceFirmware(firmware);
    }

    @Override
    public Training findTrainingByCreatedDate(Date createDate) {
        return trainingRepository.findByCreatedDate(createDate);
    }

    @Override
    public List<Training> findTrainingsByTime(int time) {
        return trainingRepository.findByTime(time);
    }

    @Override
    public List<Training> findTrainingsByType(String type) {
        return trainingRepository.findByType(type);
    }

    @Override
    public List<Training> findTrainingsByCalories(int calories) {
        return trainingRepository.findByCalories(calories);
    }

    @Override
    public List<Training> findTrainingsByAccount(Account account) {
        return trainingRepository.findByAccount(account);
    }

    @Override
    public Training create(Training training) {
        training.setCreatedDate(new Date());
        Training savedTraining = trainingRepository.saveAndFlush(training);
        log.info("new training has been created: " + savedTraining.getId());
        return savedTraining;
    }

    @Override
    public Training saveChanges(Training update) {
        if(trainingRepository.existsById(update.getId())){
            log.debug("account {} changes have been saved", update.getId());
            return trainingRepository.saveAndFlush(update);
        } else {
            throw new TrainingException("can't find training with id " + update.getId());
        }
    }

}
