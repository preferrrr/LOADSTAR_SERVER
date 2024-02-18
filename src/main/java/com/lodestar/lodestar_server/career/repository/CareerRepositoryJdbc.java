package com.lodestar.lodestar_server.career.repository;

import com.lodestar.lodestar_server.career.dto.response.CareerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CareerRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;

    public void saveCareers(Long userId, List<CareerDto> dtos) {

        String saveCareerQuery = "insert into career(user_id, x, y1, y2, range_name, created_at, modified_at, status) VALUES (?,?,?,?,?,?,?,?)";

        jdbcTemplate.batchUpdate(saveCareerQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CareerDto dto = dtos.get(i);
                ps.setLong(1, userId);
                ps.setString(2, dto.getX());
                ps.setLong(3, dto.getY().get(0));
                ps.setLong(4, dto.getY().get(1));
                ps.setString(5, dto.getRangeName());
                ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                ps.setString(8,"y");
            }

            @Override
            public int getBatchSize() {
                return dtos.size();
            }
        });
    }
}
