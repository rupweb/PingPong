package eigervertx.sender;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hazelcast.config.Config;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class SenderApp {
	
	private static final Logger logger = LogManager.getLogger("monitor");	
	public static EventBus eB;
	
	public static void main(String[] args) throws UnknownHostException {
		
		logger.info("Starting sender");

		String ipAddress = Inet4Address.getLocalHost().getHostAddress();	
    	VertxOptions options = new VertxOptions();
    	Config hazelcastConfig = new Config();
    	hazelcastConfig.getNetworkConfig().setPortAutoIncrement(false).setReuseAddress(true);
    	hazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
    	hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
    	options.setClusterManager(new HazelcastClusterManager(hazelcastConfig));
    	options.setClusterHost(ipAddress);	
    	
    	logger.info("ipAddress: " + ipAddress.toString());
    	logger.info("options: " + options.toString());
		
		Vertx.clusteredVertx(options, res -> {
		  if (res.succeeded()) {
		    Vertx vertx = res.result();
		    
		    eB = vertx.eventBus();
		    
		    logger.info("Deploying Sender verticle");
		    
			vertx.deployVerticle((Verticle) new Sender(eB));	
		  } else {
		    System.out.println("Failed: " + res.cause());
		  }
		});
	}
}
