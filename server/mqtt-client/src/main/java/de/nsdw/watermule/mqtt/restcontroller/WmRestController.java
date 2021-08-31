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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/devices")
    public ResponseEntity<List<DeviceDto>> getDevices() {
        return new ResponseEntity<>(repository.getDeviceList(), HttpStatus.OK);
    }

    @GetMapping(path = "/channels")
    public ResponseEntity<List<ChannelDto>> getChannels() {
        return new ResponseEntity<>(repository.getChannelList(), HttpStatus.OK);
    }

}
