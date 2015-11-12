package main.java.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public class Receiver extends AbstractVerticle {
	
	private static final Logger logger = LogManager.getLogger("monitor");
	public EventBus eB;

  public Receiver(EventBus eB) {
		this.eB = eB;
	}

  public void start(Future<Void> startFuture) throws Exception {
    logger.info("In start");
      
    eB.consumer("ping-address", message -> {

    	logger.info("Received message: " + message.body());
        // Now send back reply
        message.reply("pong!");
      });
  }
}