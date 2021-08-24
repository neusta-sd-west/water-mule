#!/bin/bash

# servername=$1
# while [ "$servername" == "" ]
# do
#     read -p "Bitte geben Sie den Servername oder IP inklusive Protokoll ein (z.B. http://1.2.3.4): " servername
# done

# echo "Create data directories"
# mkdir -p ./data/zookeeper/data
# mkdir -p ./data/zookeeper/datalog
# mkdir -p ./data/kafka/kafka-logs
# mkdir -p ./data/portainer
# mkdir -p ./data/postgres/data
mkdir -p ./data/pgadmin
# mkdir -p ./data/grafana/data
# mkdir -p ./data/grafana/config/
# mkdir -p ./data/greenplumdb/data/
# mkdir -p ./data/greenplumdb/scripts/
# mkdir -p ./data/greenplumss/scripts
# mkdir -p ./data/designer/runtime/obj
# mkdir -p ./data/designer/frontend/

# echo "Change data directories permission"
# chmod  775 -R data/postgres/data
chmod  777 -R data/pgadmin/
# chmod  777 -R data/grafana/data
# chmod  775 -R data/greenplumdb/data
# chmod  775 -R data/greenplumdb/scripts
# chmod  775 -R data/greenplumss/scripts

# echo "Replace SERVER_OR_IP in docker-compose with "$servername
# sed -i "s|<SERVER_OR_IP>|$servername|" docker-compose.yaml
# echo "Replace SERVER_OR_IP in grafana.ini with "$servername
# sed -i "s|<SERVER_OR_IP>|$servername|" ./data/grafana.ini
# echo "Replace SERVER_OR_IP in hetida config file with "$servername
# sed -i "s|<SERVER_OR_IP>|$servername|" ./data/hetida_designer_config.json

# echo "copy default files to target directories"
# cp ./data/grafana.ini ./data/grafana/config/.
# cp ./data/hetida_designer_config.json ./data/designer/frontend/.