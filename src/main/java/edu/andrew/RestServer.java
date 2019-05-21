package edu.andrew;

import com.sun.net.httpserver.HttpServer;
import edu.andrew.dao.SessionFactoryProvider;
import edu.andrew.rest.MoneyTransferResource;
import org.apache.log4j.Logger;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class RestServer {
    private static final Logger log = Logger.getLogger(RestServer.class);
    private static final URI BASE_URI =  UriBuilder.fromUri("http://localhost/").port(8181).build();
    private static final boolean DONT_START_YET = false;
    private HttpServer server;

    public RestServer() {
        ResourceConfig resourceConfig = new ResourceConfig(MoneyTransferResource.class);
        resourceConfig.register(JacksonFeature.class);
        server = JdkHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, DONT_START_YET);
    }

    public void start() {
        server.start();
        log.info("Service has started.");

    }

    public void stop() {
        log.info("Shutting down...");
        SessionFactoryProvider.getSessionFactory().close();
        server.stop(0);
    }
}

