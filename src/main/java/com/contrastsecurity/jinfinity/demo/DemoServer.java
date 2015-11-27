package com.contrastsecurity.jinfinity.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class DemoServer {
	
	public void runServer() throws Exception {
		Server server = new Server(8080);
		ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/ds", true, false);
        servletContextHandler.addServlet(DemoServlet.class, "/read");
        server.start();
	}
	
}
