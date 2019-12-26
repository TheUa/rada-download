package com.example.radadownload.service;


import com.example.radadownload.model.DeputyCard;
import com.example.radadownload.model.DeputyPresence;

import java.util.List;

public interface RetrieveDeputiesService {

    List<DeputyPresence> retrieveDeputiesPresent();

    List<DeputyCard> retrieveDeputyCard();
}
