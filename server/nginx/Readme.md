# nginx Reverse Proxy
nginx ist als Reverse Proxy auf dem Ubuntu Server installiert.

## Konfiguration
`sudo nano /etc/nginx/sites-available/default`

```
server {

    server_name watermule.neusta-sd-west.de;

    proxy_redirect      off;
    proxy_set_header    X-Real-IP $remote_addr;
    proxy_set_header    X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header    Host $http_host;

    location / {
        proxy_pass http://localhost:8091/;
    }
    
    location /api/v1 {
        proxy_pass http://localhost:8080/api/v1/;
        proxy_set_header Host $host;
    }

    location /pgadmin/ {
        proxy_set_header X-Script-Name /pgadmin;
        proxy_set_header Host $host;
        proxy_redirect off;
        proxy_pass http://localhost:8081/;
    }

    location /auth/ {
        proxy_pass http://localhost:8085/auth/;
        proxy_set_header    Host               $host;
        proxy_set_header    X-Real-IP          $remote_addr;
        proxy_set_header    X-Forwarded-For    $proxy_add_x_forwarded_for;
        proxy_set_header    X-Forwarded-Host   $host;
        proxy_set_header    X-Forwarded-Server $host;
        proxy_set_header    X-Forwarded-Port   $server_port;
        proxy_set_header    X-Forwarded-Proto  $scheme;
    }


    listen [::]:443 ssl ipv6only=on; # managed by Certbot
    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/watermule.neusta-sd-west.de/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/watermule.neusta-sd-west.de/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot
}

server {
    if ($host = watermule.neusta-sd-west.de) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


        listen 80 default_server;
        listen [::]:80 default_server;

        server_name watermule.neusta-sd-west.de;
    return 404; # managed by Certbot

}
```

