docker build -f services/microservices-artist_circle_web-artist/Dockerfile -t hoofkhanh/microservices-artist_circle_web-artist:1.0.0 .
docker build -f services/microservices-artist_circle_web-config_server/Dockerfile -t hoofkhanh/microservices-artist_circle_web-config_server:1.0.0 .
docker build -f services/microservices-artist_circle_web-discovery/Dockerfile -t hoofkhanh/microservices-artist_circle_web-discovery:1.0.0 .
docker build -f services/microservices-artist_circle_web-gateway/Dockerfile -t hoofkhanh/microservices-artist_circle_web-gateway:1.0.0 .
docker build -f services/microservices-artist_circle_web-search/Dockerfile -t hoofkhanh/microservices-artist_circle_web-search:1.0.0 .
docker build -f services/microservices-artist_circle_web-user/Dockerfile -t hoofkhanh/microservices-artist_circle_web-user:1.0.0 .