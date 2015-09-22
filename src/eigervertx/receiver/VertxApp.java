package eigervertx.receiver;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class VertxApp {

	public static void main(String[] args) {

		System.out.println("Starting receiver");
		
		VertxOptions options = new VertxOptions();
		
		Vertx.clusteredVertx(options, res -> {
		  if (res.succeeded()) {
		    Vertx vertx = res.result();	    
			vertx.deployVerticle((Verticle) new Receiver());		    
		  } else {
		    System.out.println("Failed: " + res.cause());
		  }
		});
	}
}
