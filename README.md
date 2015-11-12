git pull origin master

open pom.xml and set the mainClass property
either: main.java.receiver.ReceiverApp
or main.java.sender.SenderApp

go to main/resources/cluster.xml and set the tcp interfaces to 2 or 3 servers on your network. set the interface to the IP stub for your network.

mvn clean package

then go to target and run:

java -jar PingPong-0.5-SNAPSHOT-fat.jar
