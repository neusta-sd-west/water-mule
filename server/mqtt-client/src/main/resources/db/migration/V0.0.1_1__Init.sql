--
-- Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
-- Licensed under the MIT license. See LICENSE file in the project root for details.
--
CREATE SCHEMA metadata;
CREATE SCHEMA timeseries;

CREATE TABLE metadata.thing_nodes
(
    id          VARCHAR(127) PRIMARY KEY,
    parent_id   VARCHAR(127) REFERENCES metadata.thing_nodes (id),
    name        VARCHAR(127) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE metadata.things
(
    id          VARCHAR(127) PRIMARY KEY,
    thing_node_id VARCHAR(127) NOT NULL REFERENCES metadata.thing_nodes (id),
    name        VARCHAR(127) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE metadata.channels
(
    id          VARCHAR(127) PRIMARY KEY,
    thing_id    VARCHAR(127) REFERENCES metadata.things (id),
    name        VARCHAR(127) NOT NULL,
    description VARCHAR(255),
    unit        VARCHAR(10)
);

CREATE TABLE timeseries.measurements
(
    channel_id  VARCHAR(127)   NOT NULL references metadata.Channels (id),
    timestamp   TIMESTAMP WITH TIME ZONE,
    measurement NUMERIC(12, 6) NOT NULL,
    PRIMARY KEY (timestamp, channel_id)
);

-- Tune timescaledb for timeseries
SELECT create_hypertable('timeseries.measurements', 'timestamp');
ALTER TABLE timeseries.measurements
    SET (timescaledb.compress, timescaledb.compress_segmentby = 'channel_id');
-- TODO Check comression policy
-- SELECT add_compression_policy('timeseries.measurements', INTERVAL '7 days');

