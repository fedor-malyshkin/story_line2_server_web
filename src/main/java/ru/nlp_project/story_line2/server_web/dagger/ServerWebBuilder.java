package ru.nlp_project.story_line2.server_web.dagger;

import com.codahale.metrics.MetricRegistry;

import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;

public class ServerWebBuilder {


	private static ServerWebComponent component;
	private static ServerWebConfiguration serverWebConfiguration;


	public static ServerWebComponent getComponent() {
		if (component == null) {
			MetricRegistry metricRegistry = new MetricRegistry();
			ServerWebModule module = new ServerWebModule(metricRegistry, serverWebConfiguration);
			component = DaggerServerWebComponent.builder().serverWebModule(module).build();
		}
		return component;
	}

	public static void setServerWebConfiguration(ServerWebConfiguration configuration) {
		serverWebConfiguration = configuration;
	}

}
