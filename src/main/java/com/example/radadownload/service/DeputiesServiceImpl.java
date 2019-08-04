package com.example.radadownload.service;

import com.example.radadownload.model.DeputyPresence;
import com.example.radadownload.repository.DeputiesRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DeputiesServiceImpl implements DeputiesService {

    private DeputiesRepositoryImpl deputiesRepository;

    private RetrieveDeputiesService retrieveDeputiesService;

    DeputiesServiceImpl(DeputiesRepositoryImpl deputiesRepository,
                        RetrieveDeputiesService retrieveDeputiesService) {
        this.deputiesRepository = deputiesRepository;
        this.retrieveDeputiesService = retrieveDeputiesService;
    }

    @Override
    public List<DeputyPresence> getDeputiesPresence() {

        deputiesRepository.batchInsertDeputiesPresences(retrieveDeputiesService.retrieveDeputiesPresent());


        String f = "Таблица!";
        log.error("getDeputiesPresence. Request: {}", f);
        return deputiesRepository.getAllDeputies();
    }
}
