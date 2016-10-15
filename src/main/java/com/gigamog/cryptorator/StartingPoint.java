package com.gigamog.cryptorator;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.gigamog.cryptorator.endpoints.TheEndpoints;

public class StartingPoint {
	
	
	private static final int DEFAULT_PORT = 8080;
	
	private int serverPort;
	
	public StartingPoint(int serverPort) throws Exception {
		this.serverPort = serverPort;
	
		
		Server server = configureServer();	        
        server.start();
        server.join();
	}	

	private Server configureServer() {
		MyApplication resourceConfig = new MyApplication();		
		//resourceConfig.packages(TheEndpoints.class.getPackage().getName());
	    ///resourceConfig.getClasses().add(GsonJerseyProvider.class);
		ServletContainer servletContainer = new ServletContainer(resourceConfig);
		ServletHolder sh = new ServletHolder(servletContainer);                
		Server server = new Server(serverPort);		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(sh, "/*");
        context.addFilter( new FilterHolder(new ContainerFilter()), "/*",  EnumSet.allOf(DispatcherType.class));
        
		server.setHandler(context);
		return server;
	}
	
	
	
	
	public static void main(String[] args){
		
		int serverPort = DEFAULT_PORT;
		
		if(args.length >= 1) {
			try {
				serverPort = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		try {
			new StartingPoint(serverPort);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		

		
	}
}
