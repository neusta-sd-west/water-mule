//
// Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//
package de.nsdw.watermule.mqtt.db;

import de.nsdw.watermule.mqtt.dto.WmMessage;
import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.ZoneOffset;
import java.util.List;

import static java.sql.Timestamp.from;

@Repository
public class WmRepository {
    private static final String INSERT_MEASUREMENTS_QUERY =
            "INSERT INTO timeseries.measurements(channel_id, timestamp, measurement) VALUES(?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public WmRepository(@NonNull final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveMessage(String topic, WmMessage wmMessage) {
        this.jdbcTemplate.update(
                INSERT_MEASUREMENTS_QUERY,
                preparedStatement -> {
                    preparedStatement.setString(1, topic);
                    preparedStatement.setTimestamp(2, from(wmMessage.getTimestamp().toInstant(ZoneOffset.UTC)));
                    preparedStatement.setBigDecimal(3, wmMessage.getValue());
                });
    }

    public List<String> readTopics() {
        return this.jdbcTemplate.queryForList("select id from metadata.channels", String.class);
    }
}
