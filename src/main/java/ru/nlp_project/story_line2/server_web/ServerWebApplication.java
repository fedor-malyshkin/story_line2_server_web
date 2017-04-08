package ru.nlp_project.story_line2.server_web;

import javax.inject.Inject;

import org.glassfish.jersey.CommonProperties;

import io.dropwizard.Application;
import io.dropwizard.jersey.DropwizardResourceConfig;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.nlp_project.story_line2.server_web.dagger.ServerWebBuilder;
import ru.nlp_project.story_line2.server_web.resources.CategoryResource;
import ru.nlp_project.story_line2.server_web.resources.NewsArticleResource;
import ru.nlp_project.story_line2.server_web.resources.NewsHeaderResource;
import ru.nlp_project.story_line2.server_web.resources.SourceResource;

/**
 * Объект-приложение (требуется фреймворком dropwizard.io)/
 * 
 * 
 * @author fedor
 *
 */
public class ServerWebApplication extends Application<ServerWebConfiguration> {
	@Inject
	IRequestExecutor executor;

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
	public void run(ServerWebConfiguration configuration, Environment environment)
			throws Exception {
		ServerWebBuilder.setServerWebConfiguration(configuration);
		ServerWebBuilder.getComponent().inject(this);

		final ServerWebHealthCheck healthCheck = new ServerWebHealthCheck(configuration);


		// resources
		environment.jersey().register(new CategoryResource(executor));
		environment.jersey().register(new NewsArticleResource(executor));
		environment.jersey().register(new NewsHeaderResource(executor));
		environment.jersey().register(new SourceResource(executor));
		// OUTBOUND_CONTENT_LENGTH_BUFFER
		DropwizardResourceConfig resourceConfig = environment.jersey().getResourceConfig();
		resourceConfig.property(CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER, 1024 * 1024);

		// hc
		environment.healthChecks().register("server_web", healthCheck);
		// core
		ServerWeb serverWeb = ServerWeb.newInstance();
		environment.lifecycle().manage(serverWeb);

	}

}

