package eigervertx.receiver;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

public class ReceiverApp {
	
	private static final Logger logger = LogManager.getLogger("monitor");	
	public static EventBus eB;

	public static void main(String[] args) throws UnknownHostException {

		logger.info("Starting receiver");
		
		String ipAddress = Inet4Address.getLocalHost().getHostAddress();
        VertxOptions options = new VertxOptions();
        options.setClusterHost(ipAddress);
    	options.setClustered(true);
		
		Vertx.clusteredVertx(options, res -> {
		  if (res.succeeded()) {
		    Vertx vertx = res.result();	
		    
		    eB = vertx.eventBus();

		    logger.info("Deploying Receiver verticle");
		    
			vertx.deployVerticle((Verticle) new Receiver(eB));		    
		  } else {
			  logger.info("Failed: " + res.cause());
		  }
		});
	}
}
