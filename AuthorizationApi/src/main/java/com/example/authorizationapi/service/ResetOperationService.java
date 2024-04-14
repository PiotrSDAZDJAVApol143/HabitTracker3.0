package com.example.authorizationapi.service;

import com.example.authorizationapi.model.ResetOperations;
import com.example.authorizationapi.model.User;
import com.example.authorizationapi.repository.ResetOperationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class ResetOperationService {

    private final ResetOperationsRepository resetOperationsRepository;


    @Transactional
    public ResetOperations initResetOperation(User user){
        log.info("--START initResetOperation");
        ResetOperations resetOperations = new ResetOperations();

        resetOperations.setUid(UUID.randomUUID().toString());
        resetOperations.setCreateDate(LocalDateTime.now());
        resetOperations.setUser(user);

        resetOperationsRepository.deleteAllByUser(user);
        log.info("--STOP initResetOperation");
        return resetOperationsRepository.saveAndFlush(resetOperations);
    }

    public void endOperation(String uid){
        resetOperationsRepository.findByUid(uid).ifPresent(resetOperationsRepository::delete);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    protected void deleteExpireOperation(){
        List<ResetOperations> resetOperations = resetOperationsRepository.findExpiredOperations();
        log.info("Find {} expired operations to delete",resetOperations.size());
        if (resetOperations != null && !resetOperations.isEmpty()){
            resetOperationsRepository.deleteAll(resetOperations);
        }
    }

}
