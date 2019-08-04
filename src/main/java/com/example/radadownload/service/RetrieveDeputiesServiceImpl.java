package com.example.radadownload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.radadownload.model.DeputyPresence;
import com.example.radadownload.repository.DeputiesRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RetrieveDeputiesServiceImpl implements RetrieveDeputiesService {

    @Override
    public List<DeputyPresence> retrieveDeputiesPresent() {

        List<DeputyPresence> listDeputiesPresence = new ArrayList<>();

        int min = 12008;
        int max = 12948;
        Document document;
        int i = 3;

        for (
                int indexURL = max;
                indexURL <= max; indexURL += 20
        ) {
            try {

                document = Jsoup.connect("http://w1.c1.rada.gov.ua/pls/radan_gs09/ns_reg_write?g_id=" + max).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();

                log.error("setDeputiesPresence. Request: {}", document.title());

//                for (i = 1; i <= 490; i++) {
                Elements elementName = document.select("li#0idd" + i + " > div.dep");
                Elements elementsPresent = document.select("li#0idd" + i + " > div.golos");
                log.error("setDeputiesPresence. Request: {}", elementName.text());
//                    if (!elementsPresent.text().equals("") && elementsPresent.text().equals("Зареєстрований")) {
//                        listDPpresent.set(i - 1, value + 1);
//                    }
                listDeputiesPresence.add(
                        DeputyPresence.builder()
                                .deputyId(i)
                                .name(elementName.text())
                                .presenceCount(i + 100)
                                .build());
                log.error("setDeputiesPresence. Request: {}", listDeputiesPresence);


//                }
            } catch (IOException connectException) {
                connectException.printStackTrace();
            }
        }
        return listDeputiesPresence;
    }
}

