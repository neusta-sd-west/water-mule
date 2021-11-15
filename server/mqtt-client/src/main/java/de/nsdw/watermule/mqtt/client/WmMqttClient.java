//
// Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//
package de.nsdw.watermule.mqtt.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.nsdw.watermule.mqtt.db.WmRepository;
import de.nsdw.watermule.mqtt.dto.WmMessage;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

import static java.lang.String.format;

@Slf4j
@Component
public class WmMqttClient {
    private final MqttClient mqttClient;
    private final String mqttBrokerUri;
    private final String mqttUser;
    private final String mqttPassword;
    private final WmRepository repository;
    private List<String> mqttTopics;

    public WmMqttClient(@Value("${wm.mqtt.broker.host}") @NonNull final String mqttBrokerHost,
                        @Value("${wm.mqtt.broker.port}") final int mqttBrokerPort,
                        @Value("${wm.mqtt.broker.user}") @NonNull final String mqttBrokerUser,
                        @Value("${wm.mqtt.broker.password}") @NonNull final String mqttBrokerPassword,
                        final @NonNull WmRepository repository
                        ) throws Exception {

        final String clientId = "simple-watermule-consumer";
        this.mqttBrokerUri = format("ssl://%s:%d", mqttBrokerHost, mqttBrokerPort);
        this.mqttUser = mqttBrokerUser;
        this.mqttPassword = mqttBrokerPassword;
        final MemoryPersistence persistence = new MemoryPersistence();
        this.mqttClient = new MqttClient(this.mqttBrokerUri, clientId, persistence);
        this.repository = repository;
        this.mqttTopics = null;
    }

    @Bean
    private ObjectMapper objectMapper() {
        final ObjectMapper jackson = new ObjectMapper();
        jackson.registerModule(new JavaTimeModule());
        return jackson;
    }

    @Scheduled(initialDelay = 60000, fixedRate = 20000)
    private void updateSubscriptions() throws MqttException {
        // TODO: Remove deleted channels
        List<String> actualTopics = this.repository.readTopics();
        actualTopics.removeAll(this.mqttTopics);
        if(actualTopics.size()>0){
            for(String topic: actualTopics){
                log.info("Registering new mqtt-Topic: {}", topic);
            }
            this.mqttClient.subscribe(actualTopics.toArray(new String[0]));
            this.mqttTopics.addAll(actualTopics);
        }
    }

    private void connect() throws Exception {
        if (this.mqttClient.isConnected()) {
            log.info("Client was already connected...");
            return;
        }
        log.info("Connecting to mqtt client at {}...", this.mqttBrokerUri);
        final MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(this.mqttUser);
        connOpts.setPassword(this.mqttPassword.toCharArray());
        connOpts.setAutomaticReconnect(true);
        this.mqttClient.connect(connOpts);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startListening() throws Exception {
        this.connect();
        this.mqttClient.setCallback(new WmMqttCallback());
        log.info("Registering topics and start listening...");
        this.mqttTopics = this.repository.readTopics();

        for(String topic: this.mqttTopics){
            log.info("Registering mqtt-Topic: {}", topic);
        }

        if(this.mqttTopics.size() > 0) {
            this.mqttClient.subscribe(this.mqttTopics.toArray(new String[0]));
        }
    }

    private class WmMqttCallback implements MqttCallback {
        @Override
        public void connectionLost(final Throwable cause) {
            log.error("MQTT connection lost. Trying to reconnect...", cause);
        }

        @Override
        public void messageArrived(final String topic, final MqttMessage message) {
            if (message.isDuplicate()) {
                return;
            }
            String msgString = message.toString();
            if(msgString.contains("{")) {
                // Just a really simple implementation of message parsing.
                // {"value": 1234.78} or {"timestamp": "2017-11-11T03:45:17.345Z", "value": 123.456}
                try {
                    WmMessage wmMessage = WmMqttClient.this.objectMapper().readValue(msgString, WmMessage.class);
                    try {
                        WmMqttClient.this.repository.saveMessage(topic, wmMessage);
                    } catch (DuplicateKeyException e) {
                        log.error(String.format("Value under this timestamp exists already: %s", message), e);
                    }
                } catch (JsonProcessingException e) {
                    log.error(String.format("Could not parse MqttMessage: %s", message), e);
                }
            } else {
                // Parse simple numeric message like 123.45
                try {
                    WmMessage wmMessage = new WmMessage(new BigDecimal(msgString));
                    try {
                        WmMqttClient.this.repository.saveMessage(topic, wmMessage);
                    } catch (DuplicateKeyException e) {
                        log.error(String.format("Value under this timestamp exists already: %s", message), e);
                    }
                } catch (NumberFormatException  e) {
                    log.error(String.format("Could not parse MqttMessage: %s", message), e);
                }
            }
        }

        @Override
        public void deliveryComplete(final IMqttDeliveryToken token) {
            throw new IllegalStateException("Never sent a message.");
        }
    }
}


