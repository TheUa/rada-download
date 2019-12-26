package com.example.radadownload.service;

import com.example.radadownload.model.DeputyCard;
import com.example.radadownload.model.DeputyPresence;
import com.example.radadownload.repository.DeputiesRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeputiesServiceImpl implements DeputiesService {

    private DeputiesRepositoryImpl deputiesRepositoryIml;

    private RetrieveDeputiesService retrieveDeputiesService;

    DeputiesServiceImpl(DeputiesRepositoryImpl deputiesRepository,
                        RetrieveDeputiesService retrieveDeputiesService) {
        this.deputiesRepositoryIml = deputiesRepository;
        this.retrieveDeputiesService = retrieveDeputiesService;
    }

    @Override
    public List<DeputyPresence> getDeputiesPresence() {

        deputiesRepositoryIml.batchInsertDeputiesPresences(retrieveDeputiesService.retrieveDeputiesPresent());

        String f = "Таблица!";
        log.error("getDeputiesPresence. Request: {}", f);
        return deputiesRepositoryIml.getAllDeputies();
    }

    @Override
    public List<DeputyPresence> getDeputiesPresenceForWeb() {
        return deputiesRepositoryIml.getAllDeputies();
    }

    @Override
    public List<DeputyCard> getDeputiesCard() {

        List<DeputyCard> deputyCards;
        List<Integer> depID;

        List<DeputyPresence> model = deputiesRepositoryIml.getAllDeputies();

        depID = model.stream().map(DeputyPresence::getDeputyId).collect(Collectors.toList());

        log.error("setDeputiesPresence. Request: {}", depID);

        deputiesRepositoryIml.batchInsertDeputiesCardInfo(retrieveDeputiesService.retrieveDeputyCard());

        deputyCards = retrieveDeputiesService.retrieveDeputyCard();


        return deputyCards;
    }

    @Override
    public List<DeputyCard> getDeputiesAllCard() {
        List<DeputyCard> deputyCards;
        log.error("setDeputiesPresence. Request: {}", " Скачивают весь список");

        deputyCards = deputiesRepositoryIml.getDeputiesCardRepository();

        return deputyCards;
    }

}
