//
// Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//
package de.nsdw.watermule.mqtt.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@AllArgsConstructor
public class WmMessage {

    LocalDateTime timestamp;
    BigDecimal value;

    public WmMessage() {
        this.timestamp = LocalDateTime.now();
        this.value = null;
    }

    public WmMessage(BigDecimal v) {
        this.timestamp = LocalDateTime.now();
        this.value = v;
    }

}
