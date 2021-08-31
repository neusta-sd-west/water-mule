//
// Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//
package de.nsdw.watermule.mqtt.db;

import de.nsdw.watermule.mqtt.dto.ChannelDto;
import de.nsdw.watermule.mqtt.dto.DeviceDto;
import de.nsdw.watermule.mqtt.dto.NodeDto;
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

    public List<NodeDto> getNodeList() {
        return this.jdbcTemplate.query(
                "select id, name, description from metadata.thing_nodes",
                (rs, rowNum) -> new NodeDto(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description")));
    }

    public List<DeviceDto> getDeviceList() {
        return this.jdbcTemplate.query(
                "select id, thing_node_id, name, description from metadata.things",
                (rs, rowNum) -> new DeviceDto(
                        rs.getString("id"),
                        rs.getString("thing_node_id"),
                        rs.getString("name"),
                        rs.getString("description")));
    }

    public List<ChannelDto> getChannelList() {
        return this.jdbcTemplate.query(
                "select id, thing_id, name, description, unit from metadata.channels",
                (rs, rowNum) -> new ChannelDto(
                        rs.getString("id"),
                        rs.getString("thing_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("unit")));
    }

}
