package com.example.radadownload.service;

import com.example.radadownload.model.DeputyCard;
import com.example.radadownload.model.DeputyPresence;

import java.util.List;

public interface DeputiesService {

    List<DeputyPresence> getDeputiesPresence();

    List<DeputyPresence> getDeputiesPresenceForWeb();

    List<DeputyCard> getDeputiesCard();

    List<DeputyCard> getDeputiesAllCard();
}
