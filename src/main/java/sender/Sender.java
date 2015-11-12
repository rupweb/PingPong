package main.java.sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class Sender extends AbstractVerticle {
	
	private static final Logger logger = LogManager.getLogger("monitor");
	public EventBus eB;

    public Sender(EventBus eB) {
		this.eB = eB;
	}

	public void start(Future<Void> startFuture) throws InterruptedException {
        logger.info("In Sender");
        
        JsonObject j = new JsonObject();                
        j.put("test", "this");
        
        vertx.setPeriodic(1000, v -> {
        	
        	logger.info("Sending ping");       	

            eB.send("ping-address", "ping!", reply -> {
              if (reply.succeeded()) {
            	logger.info("Received reply: " + reply.result().body());
              } else {
            	logger.info("No reply");
              }
            });  
        });
    }
}