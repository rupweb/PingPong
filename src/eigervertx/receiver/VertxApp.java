package eigervertx.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

public class VertxApp {
	
	private static final Logger logger = LogManager.getLogger("VertxApp");	
	public static EventBus eB;

	public static void main(String[] args) {

		logger.info("Starting receiver");
		
		VertxOptions options = new VertxOptions();
		
		Vertx.clusteredVertx(options, res -> {
		  if (res.succeeded()) {
		    Vertx vertx = res.result();	
		    
		    eB = vertx.eventBus();

		    logger.info("Deploying verticle");
			vertx.deployVerticle((Verticle) new Receiver(eB));		    
		  } else {
			  logger.info("Failed: " + res.cause());
		  }
		});
	}
}
