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

    private RetrieveDeputiesServiceImpl retrieveDeputiesServiceImpl;

    DeputiesServiceImpl(DeputiesRepositoryImpl deputiesRepository,
                        RetrieveDeputiesServiceImpl retrieveDeputiesServiceImpl) {
        this.deputiesRepository = deputiesRepository;
        this.retrieveDeputiesServiceImpl = retrieveDeputiesServiceImpl;
    }

    @Override
    public List<DeputyPresence> getDeputiesPresence() {
        String f = "Проблема с получением таблицы";
        log.error("getDeputiesPresence. Request: {}", f);
        return deputiesRepository.getAllDeputies();
    }
}
