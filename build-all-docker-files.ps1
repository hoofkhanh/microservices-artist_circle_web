docker build -f services/microservices-artist_circle_web-artist/Dockerfile -t hoofkhanh/artist-service:1.0.0 .
docker build -f services/microservices-artist_circle_web-config_server/Dockerfile -t hoofkhanh/config_server-service:1.0.0 .
docker build -f services/microservices-artist_circle_web-discovery/Dockerfile -t hoofkhanh/discovery-service:1.0.0 .
docker build -f services/microservices-artist_circle_web-gateway/Dockerfile -t hoofkhanh/gateway-service:1.0.0 .
docker build -f services/microservices-artist_circle_web-search/Dockerfile -t hoofkhanh/search-service:1.0.0 .
docker build -f services/microservices-artist_circle_web-user/Dockerfile -t hoofkhanh/user-service:1.0.0 .



