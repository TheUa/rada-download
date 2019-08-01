package com.example.radadownload.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.radadownload.model.DeputyPresence;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class RetrieveDeputiesService implements DeputiesService {


    public static List<DeputyPresence> retrievePresenceFromRadaWebsite() {
        List<DeputiesService> retrievePresenceFromRadaWebsite = new ArrayList<>();
        int min = 12008;
        int max = 12948;
        Document document = null;
        int i;
        for (
                int indexURL = min;
                indexURL <= max; indexURL += 20
        ) {
            try {
                document = Jsoup.connect("http://w1.c1.rada.gov.ua/pls/radan_gs09/ns_reg_write?g_id=" + max).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
                for (i = 1; i <= 490; i++) {
                    Elements elementsPresent = document.select("li#0idd" + i + " > div.golos");
                    if (!elementsPresent.text().equals("") && elementsPresent.text().equals("Зареєстрований")) {
                        listDPpresent.set(i - 1, value + 1);
                    }
                    Elements elementName = document.select("li#0idd" + i + " > div.dep");

                }
            } catch (IOException var12) {
                var12.printStackTrace();
            }
        }
        return retrievePresenceFromRadaWebsite();
    }

    @Override
    public List<DeputyPresence> getDeputiesPresence() {
        return null;
    }

    @Override
    public Integer retrieveDeputiesPresenceAndSave() {
        int min = 12008;
        int max = 12948;
        Document retrievePresenceFromRadaWebsite = null;
        ArrayList<Integer> listDPnumber = new ArrayList();
        ArrayList<Integer> listDPpresent = new ArrayList();
        ArrayList<String> listDPname = new ArrayList();

        int i;
        for (i = 0; i <= 490; i = i + 1) {
            listDPpresent.add(0);
        }

        for (
                int indexURL = min;
                indexURL <= max; indexURL += 20) {
            try {
                retrievePresenceFromRadaWebsite = Jsoup.connect("http://w1.c1.rada.gov.ua/pls/radan_gs09/ns_reg_write?g_id=" + max).userAgent("Chrome/4.0.249.0 Safari/532.5").referrer("http://www.google.com").get();
                for (i = 1; i <= 490; i = i + 1) {
                    Integer size = listDPpresent.size();
                    Integer value = (Integer) listDPpresent.get(i - 1);
                    Elements elementsPresent = retrievePresenceFromRadaWebsite.select("li#0idd" + i + " > div.golos");
                    if (!elementsPresent.text().equals("") && elementsPresent.text().equals("Зареєстрований")) {
                        listDPpresent.set(i - 1, value + 1);
                    }
                }
                System.out.println(retrievePresenceFromRadaWebsite.title());
            } catch (IOException var12) {
                var12.printStackTrace();
            }
        }

        for (i = 0; i < 490; i = i + 1) {
            i = listDPnumber.size();
            listDPnumber.add(i + 1);
            assert retrievePresenceFromRadaWebsite != null;
            Elements elementName = retrievePresenceFromRadaWebsite.select("li#0idd" + i + " > div.dep");
            if (!elementName.text().equals("")) {
                listDPname.add(elementName.text());
            }
            System.out.print(i + ". ");
            System.out.print(elementName.text() + "        присутствовал - ");
            System.out.println(listDPpresent.get(i) + " раз");
        }
        return null;
    }
}

