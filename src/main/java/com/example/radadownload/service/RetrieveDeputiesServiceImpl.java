package com.example.radadownload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.radadownload.model.DeputyPresence;
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
        int i;
        HashMap<Integer, String> elementNameMap = new HashMap<>();
        HashMap<Integer, Integer> elementPresentMap = new HashMap<>();

        for (
                int indexURL = min;
                indexURL <= max; indexURL += 20
        ) {
            try {

                document = Jsoup.connect("http://w1.c1.rada.gov.ua/pls/radan_gs09/ns_reg_write?g_id=" + max).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();

                log.error("setDeputiesPresence. Request: {}", document.title());

                for (i = 1; i <= 490; i++) {
                    Elements elementName = document.select("li#0idd" + i + " > div.dep");
                    Elements elementsPresent = document.select("li#0idd" + i + " > div.golos");
                    if (!elementName.text().equals("")) {
                        elementNameMap.put(i, elementName.text());
                        Integer value = elementPresentMap.get(i);
                        if (value != null) {
                            if ((elementsPresent.text().equals("Зареєстрований")||elementsPresent.text().equals("Зареєстрована")))
                                elementPresentMap.put(i, value + 1);
                        } else elementPresentMap.put(i, 0);
                    }
                }


            } catch (IOException connectException) {
                connectException.printStackTrace();
            }
        }
        for (i = 1; i <= 490; i++) {
            if (elementNameMap.containsKey(i) && elementPresentMap.containsKey(i))
                listDeputiesPresence.add(
                        DeputyPresence.builder()
                                .deputyId(i)
                                .name(elementNameMap.get(i))
                                .presenceCount(elementPresentMap.get(i))
                                .build());
        }
        return listDeputiesPresence;
    }
}

