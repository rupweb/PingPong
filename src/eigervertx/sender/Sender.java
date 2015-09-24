package eigervertx.sender;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class Sender extends AbstractVerticle {

    public void start(Future<Void> startFuture) {
        System.out.println("In Sender");
        
        JsonObject j = new JsonObject();
              
        j.put("object", "quoteRequest");
        j.put("clientRfqId", "TEST12345");
        j.put("symbol", "EUR/GBP");
        j.put("quantity", "162500");
        j.put("settlementDate", "20150928");     
        j.put("currency", "EUR");
    	
        System.out.println("Sending JSON");      
        
        vertx.eventBus().publish("ui.quotes.request", j);
        
        System.out.println("Sent JSON");
    }
}