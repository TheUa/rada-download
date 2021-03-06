package com.example.radadownload.rest;

import com.example.radadownload.model.DeputyCard;
import com.example.radadownload.model.DeputyPresence;
import com.example.radadownload.service.DeputiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("deputies")
public class DeputiesController {

    private DeputiesService deputiesService;

    @Autowired
    DeputiesController(DeputiesService deputiesService) {
        this.deputiesService = deputiesService;
    }

    @GetMapping("/")
    public List<DeputyPresence> getDeputiesPresence() {
        return deputiesService.getDeputiesPresence();
    }

    @GetMapping("/android")
    public List<DeputyPresence> getDeputiesPresenceForWeb() {
        return deputiesService.getDeputiesPresenceForWeb();
    }

    @GetMapping("/downloadFullCard")
    public List<DeputyCard>  getDeputiesCard() {
        return deputiesService.getDeputiesCard();
    }

    @GetMapping("/card")
    public List<DeputyCard> getDeputiesAllCard () {
        return deputiesService.getDeputiesAllCard();
    }
}
