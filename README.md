# day13ws

if no jar
mvn spring-boot:run -Dspring-boot.run.arguments="--dataDir=/Users/your-username/tmp/data"



for jar
mvn clean package
java -jar target/day13ws-0.0.1-SNAPSHOT.jar --dataDir /Users/your-username/tmp/data

java -jar <name of jar file> --dataDir=“/users/sarahchan/Desktop/workshop 13” 
quotes if got space in file names
no quotes if no space

ilsk