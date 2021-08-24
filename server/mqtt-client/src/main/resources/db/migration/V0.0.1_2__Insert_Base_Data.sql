--
-- Copyright (c) neusta analytics & insights GmbH and contributors. All rights reserved.
-- Licensed under the MIT license. See LICENSE file in the project root for details.
--

-- Anlage einfacher Testdaten für initiale Befüllung

INSERT INTO metadata.thing_nodes (id, parent_id, name, description)
VALUES ('wm', null, 'WaterMule Geräte', 'Globaler Einstiegspunkt (Wurzel)');

INSERT INTO metadata.things(id, thing_node_id, name, description)
VALUES ('wm/device1', 'wm', 'Gerät 1', 'Testgerät für Softwaretests');

INSERT INTO metadata.channels(id, thing_id, name, description, unit)
VALUES ('wm/device1/channel1', 'wm/device1', 'Testkanal 1', 'Testkanal für Softwaretests', 'ppm'),
       ('wm/device1/channel2', 'wm/device1', 'Testkanal 2', 'Testkanal für Softwaretests', '°C');
