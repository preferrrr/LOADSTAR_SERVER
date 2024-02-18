package com.lodestar.lodestar_server.hashtag.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HashtagRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;

    public void saveHashtags(Long boardId, List<String> hashtagNames) {

        String saveHashtagsQuery = "insert into board_hashtag(board_id, hashtag_name) VALUES (?,?)";

        jdbcTemplate.batchUpdate(saveHashtagsQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, boardId );
                ps.setString(2, hashtagNames.get(i));
            }

            @Override
            public int getBatchSize() {
                return hashtagNames.size();
            }
        });
    }
}
