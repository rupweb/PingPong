package eigervertx.sender;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class EventBusSenderVerticle extends AbstractVerticle {

    public void start(Future<Void> startFuture) {
        System.out.println("In EventBusSenderVerticle");
        
        JsonObject j = new JsonObject();
              
        j.put("object", "quoteRequest");
        j.put("clientRfqId", "TEST_20150922");
        j.put("symbol", "EUR/GBP");
        j.put("quantity", "162500");
        j.put("settlementDate", "20150924");     
        j.put("currency", "EUR");
    	
        System.out.println("Sending JSON");      
        
        vertx.eventBus().publish("ui.quotes.request", j);
        // vertx.eventBus().send("ui.quotes.request", j);
        
        System.out.println("Sent JSON");
    }
}