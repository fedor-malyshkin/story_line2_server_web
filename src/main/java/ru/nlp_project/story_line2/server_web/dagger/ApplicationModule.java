package ru.nlp_project.story_line2.server_web.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.nlp_project.story_line2.server_web.IConfigurationManager;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.impl.ConfigurationManagerImpl;
import ru.nlp_project.story_line2.server_web.impl.StormDRPCClientImpl;

@Module
public class ApplicationModule {

	@Singleton
	@Provides
	public IStormDRPCClient provideStormDRPCClient(StormDRPCClientImpl instance) {
		instance.initialize();
		return instance;
	}
	
	
	@Singleton
	@Provides
	public IConfigurationManager provideConfigurationManager(ConfigurationManagerImpl instance) {
		instance.initialize();
		return instance;
	}

}
