package main.java.sender;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

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
		options.setClusterHost(ipAddress);
		options.setClustered(true);
    	
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
