package ru.nlp_project.story_line2.server_web;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Объект-приложение (требуется фреймворком dropwizard.io)/
 * 
 * 
 * @author fedor
 *
 */
public class ServerWebApplication extends Application<ServerWebConfiguration> {
	public static void main(String[] args) throws Exception {
		new ServerWebApplication().run(args);
	}

	@Override
	public String getName() {
		return "server_web";
	}

	@Override
	public void initialize(Bootstrap<ServerWebConfiguration> bootstrap) {}

	@Override
	public void run(ServerWebConfiguration configuration, Environment environment) throws Exception {
		final ServerWebHealthCheck healthCheck = new ServerWebHealthCheck(configuration);

		environment.healthChecks().register("server_web", healthCheck);
		ServerWeb crawler = ServerWeb.newInstance(configuration);
		environment.lifecycle().manage(crawler);

	}

}

