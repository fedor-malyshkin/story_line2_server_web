package ru.nlp_project.story_line2.server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.nlp_project.story_line2.server.resources.NewsResource;

public class Server extends Application<ServerConfiguration> {
  public static void main(String[] args) throws Exception {
    new Server().run(args);
  }

  @Override
  public String getName() {
    return "story-line-server";
  }

  @Override
  public void initialize(Bootstrap<ServerConfiguration> bootstrap) {
    // nothing to do yet
  }

  @Override
  public void run(ServerConfiguration configuration, Environment environment) {

    final NewsResource resource = new NewsResource();
    environment.jersey().register(resource);
    final ServerHealthCheck healthCheck = new ServerHealthCheck();
    environment.healthChecks().register("server", healthCheck);
  }
}
