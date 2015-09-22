package eigervertx.receiver;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class Receiver extends AbstractVerticle {

	  public void start() {
	    vertx.eventBus().consumer("ui.quotes.request", new Handler<Message<JsonObject>>() {
	      @Override
	      public void handle(Message<JsonObject> message) {
	        System.out.println(message.body().toString());
	      }
	    });
	  }
	}