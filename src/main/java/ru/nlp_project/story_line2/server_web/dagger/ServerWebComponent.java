package ru.nlp_project.story_line2.server_web.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.nlp_project.story_line2.server_web.ServerWeb;
import ru.nlp_project.story_line2.server_web.ServerWebApplication;
import ru.nlp_project.story_line2.server_web.impl.SynchronizedStormDRPCClientImpl;

@Component(modules = ServerWebModule.class)
@Singleton
public abstract class ServerWebComponent {
	public abstract void inject(ServerWebApplication instance);
	public abstract void inject(ServerWeb result);
	public abstract void inject(SynchronizedStormDRPCClientImpl stormDRPCClientImpl) ;
}
