package com.example.radadownload.repository;

import com.example.radadownload.model.DeputyCard;
import com.example.radadownload.model.DeputyPresence;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class DeputiesRepositoryImpl {

    private JdbcTemplate jdbcTemplate;

    public DeputiesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<DeputyPresence> getAllDeputies() {
        List<DeputyPresence> deputyPresenceList = new ArrayList<>();

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM DEPUTY_PRESENCE");
        for (Map row : maps) {
            deputyPresenceList.add(
                    DeputyPresence.builder()
                            .id((Long) row.get("ID"))
                            .name((String) row.get("NAME"))
                            .deputyId((Integer) row.get("DEPUTY_ID"))
                            .party((String) row.get("PARTY"))
                            .build());
        }
        log.error("setDeputiesPresence. Request: {}", "Работает!!!");

        return deputyPresenceList;
    }

    public List<DeputyCard> getDeputiesCardRepository() {
        List<DeputyCard> deputyCardList = new ArrayList<>();
//        List<DeputyPresence> model = getAllDeputies();
//        List<String> depName = model.stream().map(DeputyPresence::getName).collect(Collectors.toList());

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("SELECT * FROM deputy_full_info");

//        for (DeputyPresence arrayList : model){
//
//        }
        for (Map row : maps)
        {
            log.error("setDeputiesPresence. Request: {}", row.get("DEP_IMAGE"));

            deputyCardList.add(
                    DeputyCard.builder()
                            .id((Integer) row.get("DEPUTY_ID"))
                            .fullName((String) row.get("FULL_NAME"))
                            .electedIn((String) row.get("ELECTED_IN"))
                            .electedInOut((String) row.get("ELECTED_IN_OUT"))
                            .party((String) row.get("PARTY"))
                            .post((String) row.get("POST"))
                            .birthday((String) row.get("BIRTHDAY"))
                            .about((String) row.get("ABOUT"))
                            .present((String) row.get("PRESENT"))
                            .presentElec((String) row.get("PRESENT_ELEC"))
                            .absentElec((String) row.get("ABSENT_ELEC"))
                            .wasIll((String) row.get("WAS_ILL"))
                            .vacation((String) row.get("VACATION"))
                            .trip((String) row.get("TRIP"))
                            .wasAbsent((String) row.get("WAS_ABSENT"))
                            .image((String) row.get("DEP_IMAGE"))
                            .shortName((String) row.get("DEP_SHORT_NAME"))
                            .build());
        }
        return deputyCardList;
    }

    public int batchInsertDeputiesPresences(List<DeputyPresence> deputyPresenceList) {

        int[] inserted = jdbcTemplate.batchUpdate("INSERT INTO DEPUTY_PRESENCE (DEPUTY_ID, NAME, PARTY) " +
                "VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DeputyPresence deputy = deputyPresenceList.get(i);
                ps.setInt(1, deputy.getDeputyId());
                ps.setString(2, deputy.getName());
                ps.setString(3, deputy.getParty());

            }

            @Override
            public int getBatchSize() {
                return deputyPresenceList.size();
            }
        });
        return inserted.length;
    }


    public int batchInsertDeputiesCardInfo(List<DeputyCard> deputyCardsList) {

        int[] inserted = jdbcTemplate.batchUpdate("INSERT INTO DEPUTY_FULL_INFO (DEPUTY_ID, FULL_NAME, ELECTED_IN, " +
                "ELECTED_IN_OUT, PARTY, POST, BIRTHDAY, ABOUT, PRESENT_ELEC, ABSENT_ELEC, " +
                "PRESENT, WAS_ILL, VACATION, TRIP, WAS_ABSENT, DEP_IMAGE, DEP_SHORT_NAME) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                DeputyCard deputy = deputyCardsList.get(i);
                ps.setInt(1, deputy.getId());
                ps.setString(2, deputy.getFullName());
                ps.setString(3, deputy.getElectedIn());
                ps.setString(4, deputy.getElectedInOut());
                ps.setString(5, deputy.getParty());
                ps.setString(6, deputy.getPost());
                ps.setString(7, deputy.getBirthday());
                ps.setString(8, deputy.getAbout());
                ps.setString(9, deputy.getPresentElec());
                ps.setString(10, deputy.getAbsentElec());
                ps.setString(11, deputy.getPresent());
                ps.setString(12, deputy.getWasIll());
                ps.setString(13, deputy.getVacation());
                ps.setString(14, deputy.getTrip());
                ps.setString(15, deputy.getWasAbsent());
                ps.setString(16, deputy.getImage());
                ps.setString(17, deputy.getShortName());

            }

            @Override
            public int getBatchSize() {
                return deputyCardsList.size();
            }
        });
        return inserted.length;
    }

}
