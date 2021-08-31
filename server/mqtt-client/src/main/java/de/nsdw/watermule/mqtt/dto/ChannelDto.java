//
// Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//
package de.nsdw.watermule.mqtt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelDto {
    private String id;
    private String deviceId;
    private String name;
    private String description;
    private String unit;
}
