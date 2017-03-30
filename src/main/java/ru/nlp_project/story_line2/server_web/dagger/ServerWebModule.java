package ru.nlp_project.story_line2.server_web.dagger;

import com.codahale.metrics.MetricRegistry;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.impl.JacksonRequestExecutorImpl;
import ru.nlp_project.story_line2.server_web.impl.StormDRPCClientImpl;

@Module
class ServerWebModule {

	private MetricRegistry metricRegistry;
	private ServerWebConfiguration serverWebConfiguration;


	public ServerWebModule(MetricRegistry metricRegistry,
			ServerWebConfiguration serverWebConfiguration) {
		super();
		this.metricRegistry = metricRegistry;
		this.serverWebConfiguration = serverWebConfiguration;
	}


	@Provides
	public MetricRegistry provideMetricRegistry() {
		return metricRegistry;
	}

	@Provides
	public IStormDRPCClient provideStormDRPCClient(StormDRPCClientImpl instance) {
		instance.initialize();
		return instance;
	}


	@Provides
	public ServerWebConfiguration provideServerWebConfiguration() {
		return serverWebConfiguration;
	}


	@Provides
	public IRequestExecutor provideRequestExecutor(JacksonRequestExecutorImpl instance) {
		return instance;
	}

}
