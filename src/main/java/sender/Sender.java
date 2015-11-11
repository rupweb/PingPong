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

	public void start(Future<Void> startFuture) {
        logger.info("In Sender");
        
        JsonObject j = new JsonObject();
              
        j.put("object", "quoteRequest");
        j.put("clientRfqId", "TEST12345");
        j.put("symbol", "EUR/GBP");
        j.put("quantity", "162500");
        j.put("settlementDate", "20150928");     
        j.put("currency", "EUR");
    	
        logger.info("Sending JSON: " + j.toString());      
        
        eB.publish("quotes.request", j);
        
        logger.info("Sent JSON");
        
        System.exit(0);
    }
}