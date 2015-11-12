git pull origin master

open pom.xml and set the mainClass property
either: main.java.receiver.ReceiverApp
or main.java.sender.SenderApp

mvn clean package

then go to target and run:

java -jar PingPong-0.5-SNAPSHOT-fat.jar
