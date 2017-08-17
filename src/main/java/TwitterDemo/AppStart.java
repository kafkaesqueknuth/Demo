package TwitterDemo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class AppStart {

	public static void main(String[] args) throws Exception {

        ResourceConfig config = new ResourceConfig();
        config.packages("TwitterDemo");
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));
		TwitterWorker.initialize(); 
        Server server = new Server(8080);
        
        for(Connector y : server.getConnectors()) {
            for(ConnectionFactory x  : y.getConnectionFactories()) {
                if(x instanceof HttpConnectionFactory) {
                    ((HttpConnectionFactory)x).getHttpConfiguration().setSendServerVersion(false);
                }
            }
        }
		
        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(servlet, "/*");
        context.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
        context.setErrorHandler(new ErrorHandler());

		try {
		     server.start();
		     server.join();
		 } finally {
		     server.destroy();
		 }
	}

    static class ErrorHandler extends ErrorPageErrorHandler {
        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.getWriter()
                .append("{\"status\":\"ERROR\",\"message\":\"HTTP ")
                .append(String.valueOf(response.getStatus()))
                .append("\"}");
        }
    }
}
