# WaterMule MQTT Client
Der hier implementierte Client dient der Annahme und Weiterleitung von MQTT Nachrichten von Mosquitto. Die Nachrichten 
werden geparsed und in eine TimescaleDB Datenbank geschrieben. Die Konfiguration erfolgt über Umgebungsvariablen.

## Hinweise
Bei dieser Implementierung handelt es sich um eine äußerst simple Umsetzung minimaler Anforderungen auf Basis von 
Spring Boot. Um die Funktionalität zu verdeutlichen und zu vereinfachen, wurden auf diverse Best Practices verzichtet.

## Test der WaterMule Umgebung
Testclient http://www.hivemq.com/demos/websocket-client/

![title](../../docs/assets/hivemq_connect.png)