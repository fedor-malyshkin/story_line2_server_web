package ru.nlp_project.story_line2.server_web.di;

import javax.inject.Singleton;

import ru.nlp_project.story_line2.server_web.IConfigurationManager;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.impl.CacheConfiguration;
import ru.nlp_project.story_line2.server_web.impl.ConfigurationManagerImpl;
import ru.nlp_project.story_line2.server_web.impl.JacksonRequestExecutorImpl;
import ru.nlp_project.story_line2.server_web.impl.StormDRPCClientImpl;

class ApplicationModule {

	private StormDRPCClientImpl drpcClient;
	private ConfigurationManagerImpl configManager;
	private JacksonRequestExecutorImpl executor;
	private CacheConfiguration cacheConfig;

	@Singleton
	public IStormDRPCClient provideStormDRPCClient() {
		if (drpcClient == null) {
			drpcClient = new StormDRPCClientImpl();
			drpcClient.configurationManager = provideConfigurationManager();
			drpcClient.initialize();
		}
		return drpcClient;
	}


	@Singleton
	public IConfigurationManager provideConfigurationManager() {
		if (configManager == null) {
			configManager = new ConfigurationManagerImpl();
			configManager.initialize();
		}
		return configManager;
	}


	@Singleton
	public IRequestExecutor provideRequestExecutor() {
		if (executor == null) {
			executor = new JacksonRequestExecutorImpl();
			executor.initialize();
		}
		return executor;
	}

	@Singleton
	public CacheConfiguration provideCacheConfiguration() {
		if (cacheConfig == null)
			cacheConfig =new CacheConfiguration();
		return cacheConfig;
	}

}
