package eigervertx.receiver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public class Receiver extends AbstractVerticle {
	
	private static final Logger logger = LogManager.getLogger("receiver");
	public EventBus eB;

  public Receiver(EventBus eB) {
		this.eB = eB;
	}

  public void start(Future<Void> startFuture) throws Exception {
    logger.info("In start");
      
	MessageConsumer<JsonObject> rfq = eB.consumer("ui.quotes.request");
    MessageConsumer<JsonObject> quotes = eB.consumer("ui.quotes.response");
	MessageConsumer<String> data = eB.consumer("ui.marketdata");

	rfq.handler(message -> {
		logger.info("RFQ: " + message.body().toString());
	});		
	
	quotes.handler(message -> {
		logger.info("QUOTE: " + message.body().toString());
	});	
	
	data.handler(message -> {
		logger.info("DATA: " + message.body().toString());
	});	
  }
}