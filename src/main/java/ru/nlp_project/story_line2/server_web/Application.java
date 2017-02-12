package ru.nlp_project.story_line2.server_web;

import javax.ws.rs.ext.RuntimeDelegate;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;

import ru.nlp_project.story_line2.server_web.resources.CategoryResource;
import ru.nlp_project.story_line2.server_web.resources.NewsArticleResource;
import ru.nlp_project.story_line2.server_web.resources.SourceResource;

public class Application {


	public static void main(String[] args) throws Exception {
		new Application().run(args);
	}

	private void run(String[] args) {
		HttpHandler endpoint = RuntimeDelegate.getInstance().createEndpoint(new MyApplication(),
				HttpHandler.class);
		HttpServer server = HttpServer.createSimpleServer();
		server.getServerConfiguration().addHttpHandler(endpoint);
		try {
			server.start();
			System.out.println("Press any key to stop the server...");
			System.in.read();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	public class MyApplication extends ResourceConfig {
		public MyApplication() {
			packages("ru.nlp_project.story_line2.server_web.resources");
			registerInstances(new CategoryResource(), new NewsArticleResource(),
					new SourceResource());
		}


	}



}
