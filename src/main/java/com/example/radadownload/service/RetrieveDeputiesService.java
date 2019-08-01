package com.example.radadownload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.radadownload.model.DeputyPresence;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class RetrieveDeputiesService implements DeputiesService {

    public static List<DeputyPresence> retrievePresenceFromRadaWebsite() {

        List<DeputyPresence> retrievePresenceFromRadaWebsiteList = new ArrayList<>();


        int min = 12008;
        int max = 12948;
        Document document = null;
        int i;
        List<Map<String, Object>> maps = new ArrayList<>();

        for (
                int indexURL = max;
                indexURL <= max; indexURL += 20
        ) {
            try {

                document = Jsoup.connect("http://w1.c1.rada.gov.ua/pls/radan_gs09/ns_reg_write?g_id=" + max).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
                for (i = 1; i <= 490; i++) {
                    Elements elementName = document.select("li#0idd" + i + " > div.dep");
                    Elements elementsPresent = document.select("li#0idd" + i + " > div.golos");
//                    if (!elementsPresent.text().equals("") && elementsPresent.text().equals("Зареєстрований")) {
//                        listDPpresent.set(i - 1, value + 1);
//                    }
                    retrievePresenceFromRadaWebsiteList.add(
                            DeputyPresence.builder()
                                    .name(elementName.text())
                                    .deputyId(i)
                                    .presenceCount(i + 100)
                                    .build());


                }
            } catch (IOException connectException) {
                connectException.printStackTrace();
            }
        }
        return retrievePresenceFromRadaWebsiteList;
    }

    @Override
    public List<DeputyPresence> getDeputiesPresence() {
        return null;
    }

    @Override
    public Integer retrieveDeputiesPresenceAndSave() {

        return null;
    }
}

