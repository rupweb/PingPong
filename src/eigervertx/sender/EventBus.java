package eigervertx.sender;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class EventBus {

	public static void main(String[] args) {

		VertxOptions options = new VertxOptions();
		
		Vertx.clusteredVertx(options, res -> {
		  if (res.succeeded()) {
		    Vertx vertx = res.result();
		    
		    io.vertx.core.eventbus.EventBus eventBus = vertx.eventBus();
		    
			vertx.deployVerticle(new EventBusSenderVerticle());
		    
		    System.out.println("We now have a clustered event bus: " + eventBus);
		  } else {
		    System.out.println("Failed: " + res.cause());
		  }
		});
	}
}
