package com.example.radadownload.service;

import com.example.radadownload.model.DeputyCard;
import com.example.radadownload.model.DeputyPresence;
import com.example.radadownload.repository.DeputiesRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RetrieveDeputiesServiceImpl implements RetrieveDeputiesService {

    private DeputiesRepositoryImpl deputiesRepositoryIml;


    RetrieveDeputiesServiceImpl(DeputiesRepositoryImpl deputiesRepository) {
        this.deputiesRepositoryIml = deputiesRepository;
    }

    @Override
    public List<DeputyPresence> retrieveDeputiesPresent() {

        List<DeputyPresence> listDeputiesPresence = new ArrayList<>();

        Document document;
        int i;
        HashMap<Integer, String> hashNameMap = new HashMap<>();
        HashMap<Integer, String> hashPartyMap = new HashMap<>();


        try {

            document = Jsoup.connect("http://w1.c1.rada.gov.ua/pls/radan_gs09/ns_reg_write?g_id=42")
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com").get();

            log.error("setDeputiesPresence. Request: {}", document.title());

            Elements elementListForPartyFree =
                    document.select("li#0idf0 > ul");
            Elements elementListForPartySluga =
                    document.select("li#0idf1 > ul");
            Elements elementListForPartyOpo =
                    document.select("li#0idf2 > ul");
            Elements elementListForPartyEs =
                    document.select("li#0idf4 > ul");
            Elements elementListForPartyMotherland =
                    document.select("li#0idf3 > ul");
            Elements elementListForPartyFuture =
                    document.select("li#0idf6 > ul");
            Elements elementListForPartyGolos =
                    document.select("li#0idf5 > ul");

            Elements elementPartyFree =
                    document.select("li#0idf0 > div > center > b");
            Elements elementPartySluga =
                    document.select("li#0idf1 > div > center > b");
            Elements elementPartyOpo =
                    document.select("li#0idf2 > div > center > b");
            Elements elementPartyEs =
                    document.select("li#0idf4 > div > center > b");
            Elements elementPartyMotherland =
                    document.select("li#0idf3 > div > center > b");
            Elements elementPartyFuture =
                    document.select("li#0idf6 > div > center > b");
            Elements elementPartyGolos =
                    document.select("li#0idf5 > div > center > b");
            for (i = 1; i <= 490; i++) {
                Elements elementName = document.select("li#0idd" + i + " > div.dep");
                if (!elementName.text().equals("")) {
                    hashNameMap.put(i, elementName.text());
                }

                if (elementListForPartyFree.text().contains(elementName.text()))
                    hashPartyMap.put(i, elementPartyFree.text());
                else if (elementListForPartySluga.text().contains(elementName.text()))
                    hashPartyMap.put(i, elementPartySluga.text());
                else if (elementListForPartyOpo.text().contains(elementName.text()))
                    hashPartyMap.put(i, elementPartyOpo.text());
                else if (elementListForPartyMotherland.text().contains(elementName.text()))
                    hashPartyMap.put(i, elementPartyMotherland.text());
                else if (elementListForPartyEs.text().contains(elementName.text()))
                    hashPartyMap.put(i, elementPartyEs.text());
                else if (elementListForPartyGolos.text().contains(elementName.text()))
                    hashPartyMap.put(i, elementPartyGolos.text());
                else if (elementListForPartyFuture.text().contains(elementName.text()))
                    hashPartyMap.put(i, elementPartyFuture.text());

            }

        } catch (IOException connectException) {
            connectException.printStackTrace();
        }

        for (i = 1; i <= 490; i++) {
            if (hashNameMap.containsKey(i))
                listDeputiesPresence.add(
                        DeputyPresence.builder()
                                .deputyId(i)
                                .name(hashNameMap.get(i))
                                .party(hashPartyMap.get(i))
                                .build());
        }
        return listDeputiesPresence;
    }


    @Override
    public List<DeputyCard> retrieveDeputyCard() {

        String urlDeputiesRadaLink1 = "https://itd.rada.gov.ua/mps/info/page?id=";
        String urlDeputiesRadaLink2 = "&is_r_id=1";

        List<DeputyCard> deputyCardList = new ArrayList<>();
        String imageStr = "";


        List<DeputyPresence> model = deputiesRepositoryIml.getAllDeputies();
        List<Integer> depID = model.stream().map(DeputyPresence::getDeputyId).collect(Collectors.toList());
        List<String> depName = model.stream().map(DeputyPresence::getName).collect(Collectors.toList());
        List<String> depParty = model.stream().map(DeputyPresence::getParty).collect(Collectors.toList());
        log.error("setDeputiesPresence. Request: {}", depParty);

        for (int i = 0; i < depID.size(); i++) {
            int integer = depID.get(i);
            try {

                Document document = Jsoup.connect(urlDeputiesRadaLink1 + integer + urlDeputiesRadaLink2)
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com").get();

                String urlPresentElec = "https://w1.c1.rada.gov.ua/pls/radan_gs09/ns_dep_reg_list_print?startDate=29.08.2019&endDate=17.08.2020&kod=";
                Document documentPresentElec = Jsoup.connect(urlPresentElec + integer)
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com").get();
                String urlPresentWrote = "https://w1.c1.rada.gov.ua/pls/radan_gs09/ns_dep_reg_w_list_print?startDate=29.08.2019&endDate=17.08.2020&kod=";
                Document documentPresentWrote = Jsoup.connect(urlPresentWrote + integer)
                        .userAgent("Chrome/4.0.249.0 Safari/532.5")
                        .referrer("http://www.google.com").execute().parse();

                Elements elementImage = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(1) > img");
                for (Element image : elementImage) {

                    log.error("setDeputiesPresence. Image: {}", elementImage.attr("src"));
                    log.error("setDeputiesPresence. Image: {}", image);

                    imageStr = image.attr("src");

                }
                Elements elementName = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > h2");
                Elements elementElectedIn1 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dt:nth-child(1)");
                Elements elementElectedIn3 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dt:nth-child(3)");
                Elements elementElectedIn5 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dt:nth-child(5)");
                Elements elementElectedIn7 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dt:nth-child(7)");

                Elements elementElectedIn2 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dd:nth-child(2)");
                Elements elementElectedIn4 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dd:nth-child(4)");
                Elements elementElectedIn6 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dd:nth-child(6)");
                Elements elementElectedIn8 = document.select("#mp_content > div > table:nth-child(1) > tbody > tr:nth-child(1) > td:nth-child(2) > div.mp-general-info > dl > dd:nth-child(8)");

                Elements elementBirthday = document.select("#mp_content > div > table:nth-child(5) > tbody > tr:nth-child(1) > td:nth-child(2)");

                Elements elementAbout = document.select("#mp_content > div > table:nth-child(5) > tbody > tr:nth-child(2) > td:nth-child(2)");

                Elements elementPost = document.select("#mp_content > div > ul > li:nth-child(1)");

                log.error("setDeputiesPresence. Name: {}", elementName.text());
                String elementElectedIn = elementElectedIn1.text() +
                        "\n" + elementElectedIn3.text() +
                        "\n" + elementElectedIn5.text() +
                        "\n" + elementElectedIn7.text();
                String elementElectedInOut = elementElectedIn2.text() +
                        "\n" + elementElectedIn4.text() +
                        "\n" + elementElectedIn6.text() +
                        "\n" + elementElectedIn8.text();

                log.error("setDeputiesPresence. Elect: {}", elementAbout.text());
                log.error("setDeputiesPresence. Post: {}", elementPost.text());
                log.error("setDeputiesPresence. Elect: {}", elementBirthday.text());


                log.error("setDeputiesPresence. Elect: {}", elementElectedIn);
                log.error("setDeputiesPresence. Elect: {}", elementElectedInOut);

                Elements elementPresentElec = documentPresentElec.select("body > table:nth-child(1) > tbody > tr:nth-child(2) > td > b:nth-child(5)");
                Elements elementAbsentElec = documentPresentElec.select("body > table:nth-child(1) > tbody > tr:nth-child(2) > td > b:nth-child(6)");
                log.error("setDeputiesPresence. Elect: {}", elementPresentElec.text());
                log.error("setDeputiesPresence. Elect: {}", elementAbsentElec.text());

                Elements elementPresentWrote = documentPresentWrote.select("body > table:nth-child(1) > tbody > tr:nth-child(2) > td > b:nth-child(5)");
                Elements elementWasIllWrote = documentPresentWrote.select("body > table:nth-child(1) > tbody > tr:nth-child(2) > td > b:nth-child(7)");
                Elements elementVacationWrote = documentPresentWrote.select("body > table:nth-child(1) > tbody > tr:nth-child(2) > td > b:nth-child(9)");
                Elements elementTripWrote = documentPresentWrote.select("body > table:nth-child(1) > tbody > tr:nth-child(2) > td > b:nth-child(11)");
                Elements elementAbsentWrote = documentPresentWrote.select("body > table:nth-child(1) > tbody > tr:nth-child(2) > td > b:nth-child(13)");

                log.error("setDeputiesPresence. Elect: {}", elementPresentWrote.text());
                log.error("setDeputiesPresence. Elect: {}", elementWasIllWrote.text());
                log.error("setDeputiesPresence. Elect: {}", elementVacationWrote.text());
                log.error("setDeputiesPresence. Elect: {}", elementTripWrote.text());
                log.error("setDeputiesPresence. Elect: {}", elementAbsentWrote.text());

                log.error("setDeputiesPresence. Name: {}", integer);
                log.error("setDeputiesPresence. Link: {}", imageStr);

                URL image = new URL(imageStr);

                log.error("setDeputiesPresence. NAME SHORT: {}", depName.get(i));
                deputyCardList.add(
                        DeputyCard.builder()
                                .id(integer)
                                .fullName(elementName.text())
                                .electedIn(elementElectedIn)
                                .electedInOut(elementElectedInOut)
                                .party(depParty.get(i))
                                .post(elementPost.text())
                                .birthday(elementBirthday.text())
                                .about(elementAbout.text())
                                .presentElec(elementPresentElec.text())
                                .absentElec(elementAbsentElec.text())
                                .present(elementPresentWrote.text())
                                .wasIll(elementWasIllWrote.text())
                                .vacation(elementVacationWrote.text())
                                .trip(elementTripWrote.text())
                                .wasAbsent(elementAbsentWrote.text())
                                .image(image.toString())
                                .shortName(depName.get(i))
                                .build());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deputyCardList;
    }
}

