# WaterMule MQTT Client

Der hier implementierte Client dient der Annahme und Weiterleitung von MQTT Nachrichten von Mosquitto. Die Nachrichten 
werden geparsed und in eine TimescaleDB Datenbank geschrieben. Die Konfiguration erfolgt über Umgebungsvariablen.

## Hinweise

Bei dieser Implementierung handelt es sich um eine **äußerst simple Umsetzung minimaler Anforderungen** auf Basis von 
Spring Boot. Um die Funktionalität zu verdeutlichen und zu vereinfachen, wurden auf diverse Best Practices verzichtet.

Der Server übernimmt aus Gründen der Vereinfachung auch die REST Dienste für das Watermule Frontend. Für ernsthafte Implementierungen sollten solche Dienste in separaten Microservices ausgelagert werden. Des weiteren wurde auf nötige Fehlerbehandlung etc. weitesgehend verzichtet.

## Docker Build

Zum Bau des Containers ist wird die aktuelle Version des Docker Desktops benötigt. Mit `docker build -t container_name:version .` kann ein Container gebaut werden. Für weitere Dokumentation wird auf die offizielle Docker Dokumentation verwiesen.

## Test der WaterMule Umgebung

Testclient http://www.hivemq.com/demos/websocket-client/

![hivemq Test Client](https://github.com/neusta-sd-west/water-mule/blob/main/docs/assets/hivemq_connect.PNG)