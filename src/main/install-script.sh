#!/usr/bin/env bash
echo """Installation steps
clone diser-cli
clone diser

mvn diser-cli
mvn diser

run server
run replicated server

run dropwizard server

test from server
test from replicated server
"""
## clone diser and diser-cli
git clone https://github.com/sarathjiguru/diser

git clone http://github.com/sarathjiguru/diser-cli

## builid diser-cli
cd diser-cli

mvn clean package

mvn install:install-file -Dfile=target/diser-cli-1.0-SNAPSHOT.jar -DgroupId=com.sarathjiguru -DartifactId=diser-cli -Dversion=1.0-SNAPSHOT -Dpackaging=jar


## build diser
cd ../diser

mvn clean package


## running 1 server
java -jar target/diser-1.0-SNAPSHOT.jar -c src/test/resources/server-config.yml &

## running 2 server
java -jar target/diser-1.0-SNAPSHOT.jar -c src/test/resources/server-replicated-config.yml &

## running rest clients for servers
cd ../diser-cli

java -jar target/diser-cli-1.0-SNAPSHOT.jar server src/test/resources/diserconfig.yml &
java -jar target/diser-cli-1.0-SNAPSHOT.jar server src/test/resources/diserconfig-replicated.yml &

# testing replication:
curl -H "Content-type: application/json" -XPOST http://localhost:8998/v1/set/key -d '{"key":"a", "value":1000}'

curl -H "Content-type: application/json" -GET http://localhost:7998/v1/get/key/a



curl -H "Content-type: application/json" -XPOST http://localhost:7998/v1/set/key -d '{"key":"a", "value":"anewValue"}'

curl -H "Content-type: application/json" -GET http://localhost:8998/v1/get/key/a