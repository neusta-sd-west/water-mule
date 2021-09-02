//
// Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//
package de.nsdw.watermule.mqtt.restcontroller;

import de.nsdw.watermule.mqtt.db.WmRepository;
import de.nsdw.watermule.mqtt.dto.ChannelDto;
import de.nsdw.watermule.mqtt.dto.DeviceDto;
import de.nsdw.watermule.mqtt.dto.NodeDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1")
public class WmRestController {
    WmRepository repository;

    public WmRestController(WmRepository repository){
        this.repository = repository;
    }

    @GetMapping(path = "/nodes")
    public ResponseEntity<List<NodeDto>> getNodes() {
        return new ResponseEntity<>(repository.getNodeList(), HttpStatus.OK);
    }

    @PostMapping(path = "/nodes")
    public ResponseEntity<NodeDto> saveNode(@RequestBody NodeDto dto) {
        NodeDto created = repository.createNode(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(path = "/nodes")
    public ResponseEntity<String> deleteNode(@RequestParam String id) {

        repository.deleteNode(id);
        return new ResponseEntity("{}", HttpStatus.OK);
    }

    @GetMapping(path = "/devices")
    public ResponseEntity<List<DeviceDto>> getDevices() {
        return new ResponseEntity<>(repository.getDeviceList(), HttpStatus.OK);
    }
    
    @PostMapping(path = "/devices")
    public ResponseEntity<DeviceDto> saveNode(@RequestBody DeviceDto dto) {
        DeviceDto created = repository.createDevice(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(path = "/devices")
    public ResponseEntity<String> deleteDevice(@RequestParam String id) {

        repository.deleteDevice(id);
        return new ResponseEntity("{}", HttpStatus.OK);
    }

    @GetMapping(path = "/channels")
    public ResponseEntity<List<ChannelDto>> getChannels() {
        return new ResponseEntity<>(repository.getChannelList(), HttpStatus.OK);
    }

    @PostMapping(path = "/channels")
    public ResponseEntity<ChannelDto> saveChannel(@RequestBody ChannelDto dto) {
        ChannelDto created = repository.createChannel(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(path = "/channels")
    public ResponseEntity<String> deleteChannel(@RequestParam String id) {

        repository.deleteChannel(id);
        return new ResponseEntity("{}", HttpStatus.OK);
    }

}
