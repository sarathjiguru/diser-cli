# diser-cli
Diser Client. Both Native and REST client.


## Testing Diser Interactively


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


### running 1 server
java -jar target/diser-1.0-SNAPSHOT.jar -c src/test/resources/server-config.yml 

### running 2 server another terminal
java -jar target/diser-1.0-SNAPSHOT.jar -c src/test/resources/server-replicated-config.yml &

### running rest clients for servers
cd ../diser-cli

### running rest client for server 1 in another terminal
java -jar target/diser-cli-1.0-SNAPSHOT.jar server src/test/resources/diserconfig.yml 

### running rest client for server 2 in another terminal
java -jar target/diser-cli-1.0-SNAPSHOT.jar server src/test/resources/diserconfig-replicated.yml 

# testing replication:
curl -H "Content-type: application/json" -XPOST http://localhost:8998/v1/set/key -d '{"key":"a", "value":1000}'

curl -H "Content-type: application/json" -GET http://localhost:7998/v1/get/key/a



curl -H "Content-type: application/json" -XPOST http://localhost:7998/v1/set/key -d '{"key":"a", "value":"anewValue"}'

curl -H "Content-type: application/json" -GET http://localhost:8998/v1/get/key/a
	
