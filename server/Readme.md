# Water Mule Server
Dieses Dokument beschreibt die Architektur und Installation des Water Mule Servers.

Als Basissystem wird ein lauffähiges Ubuntu 20.04 System vorrausgesetzt.

## Seperater User für weitere Aufgaben
https://www.digitalocean.com/community/tutorials/initial-server-setup-with-ubuntu-20-04
adduser mule - passwort hk&42GG$ghk78
usermod -aG sudo mule
rsync --archive --chown=mule:mule ~/.ssh /home/mule

TODO: Firewall Setup?

## Architektur

## Voraussetzungen
Folgende Software Pakete sollten installiert sein:
- git
- ngnix
- Docker
- TimeScaleDB
- 
## Installation
Login 
ssh root@watermule.neusta-sd-west.de

Hostnamen setzen
sudo hostnamectl set-hostname watermule.neusta-sd-west.de

## TimeScale DB Installation
Installation PostgreSQL (PostgreSQL 12)
https://www.digitalocean.com/community/tutorials/how-to-install-and-use-postgresql-on-ubuntu-20-04
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo add-apt-repository ppa:timescale/timescaledb-ppa
sudo apt install timescaledb-postgresql-12
sudo apt install timescaledb-tools
sudo timescaledb-tune --quiet --yes
sudo systemctl restart postgresql.service



