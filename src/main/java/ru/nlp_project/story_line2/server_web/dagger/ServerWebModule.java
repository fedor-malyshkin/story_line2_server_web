package ru.nlp_project.story_line2.server_web.dagger;

import com.codahale.metrics.MetricRegistry;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.impl.RequestExecutorImpl;
import ru.nlp_project.story_line2.server_web.impl.StormDRPCClientImpl;

@Module
class ServerWebModule {

	private MetricRegistry metricRegistry;
	private ServerWebConfiguration serverWebConfiguration;
	private boolean testingMode;


	public ServerWebModule(MetricRegistry metricRegistry,
			ServerWebConfiguration serverWebConfiguration, boolean testingMode) {
		super();
		this.testingMode = testingMode;
		this.metricRegistry = metricRegistry;
		this.serverWebConfiguration = serverWebConfiguration;
	}


	@Provides
	public MetricRegistry provideMetricRegistry() {
		return metricRegistry;
	}

	@Provides
	public IStormDRPCClient provideStormDRPCClient(StormDRPCClientImpl instance) {
		if (!testingMode) instance.initialize();
		return instance;
	}


	@Provides
	public ServerWebConfiguration provideServerWebConfiguration() {
		return serverWebConfiguration;
	}


	@Provides
	public IRequestExecutor provideRequestExecutor(RequestExecutorImpl instance) {
		if (!testingMode) instance.initialize();
		return instance;
	}

}
