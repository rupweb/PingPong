package eigervertx.sender;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

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
	
	public static void main(String[] args) throws UnknownHostException, SocketException {
		
		logger.info("Starting sender");

		String ipAddress = "";
		if (System.getProperty("os.name").contains("Windows"))
		{
		  ipAddress = Inet4Address.getLocalHost().getHostAddress(); 
		}
		else
		{
		  // On Linux have to use NetworkInterface to find the network IP for the server
		  Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
		  while (en.hasMoreElements()) {
		    NetworkInterface ni= (NetworkInterface) en.nextElement();
		    Enumeration<InetAddress> ee = ni.getInetAddresses();
		    while (ee.hasMoreElements()) {
		      InetAddress ia= (InetAddress) ee.nextElement();
		      if (ia.getHostAddress().contains("192"))
		    	ipAddress = ia.getHostAddress();
		    }
		  }
		}
		
		VertxOptions options = new VertxOptions();
		Config hazelcastConfig = new Config();
		hazelcastConfig.getNetworkConfig().setPortAutoIncrement(true);
		hazelcastConfig.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		hazelcastConfig.getNetworkConfig().getJoin().getTcpIpConfig().
		addMember("192.168.20.61").addMember("192.168.20.39").
		addMember("192.168.25.8").setRequiredMember(null).setEnabled(true);
		hazelcastConfig.getNetworkConfig().getInterfaces().setEnabled(true).addInterface("192.168.20.*");
		options.setClusterManager(new HazelcastClusterManager(hazelcastConfig));
		options.setClusterHost(ipAddress);
		options.setHAEnabled(true);
    	
    	logger.info("ipAddress: " + ipAddress.toString());
    	logger.info("options: " + options.toString());
		
		Vertx.clusteredVertx(options, res -> {
		  if (res.succeeded()) {
		    Vertx vertx = res.result();
		    
		    eB = vertx.eventBus();
		    
		    logger.info("Deploying Sender verticle in 10 seconds...");
		    
		    try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		    
			vertx.deployVerticle((Verticle) new Sender(eB));	
		  } else {
		    System.out.println("Failed: " + res.cause());
		  }
		});
	}
}
