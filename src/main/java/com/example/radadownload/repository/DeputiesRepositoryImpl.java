package com.example.radadownload.repository;

import com.example.radadownload.model.DeputyPresence;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
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
                            .presenceCount((Integer) row.get("PRESENCE_COUNT"))
                            .build());
        }
        return deputyPresenceList;
    }

    public int batchInsertDeputiesPresences(List<DeputyPresence> deputyPresenceList) {

        int[] inserted = jdbcTemplate.batchUpdate("INSERT INTO DEPUTY_PRESENCE (DEPUTY_ID, NAME, PRESENCE_COUNT) " +
                "VALUES (?, ?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DeputyPresence deputy = deputyPresenceList.get(i);
                ps.setInt(1, deputy.getDeputyId());
                ps.setString(2, deputy.getName());
                ps.setInt(3, deputy.getPresenceCount());
            }

            @Override
            public int getBatchSize() {
                return deputyPresenceList.size();
            }
        });
        return inserted.length;
    }
}
