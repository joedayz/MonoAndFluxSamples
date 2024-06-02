# MonoAndFluxSamples
Project Reactor: Mono and Flux Samples. 


## Docker run for mongodb

docker run -d --name mongodb \
-p 27017:27017 \
-e MONGO_INITDB_ROOT_USERNAME=root \
-e MONGO_INITDB_ROOT_PASSWORD=example \
mongo
