package ru.nlp_project.story_line2.server_web.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.nlp_project.story_line2.server_web.ServerWeb;
import ru.nlp_project.story_line2.server_web.resources.CategoryResource;
import ru.nlp_project.story_line2.server_web.resources.NewsArticleResource;
import ru.nlp_project.story_line2.server_web.resources.NewsHeaderResource;
import ru.nlp_project.story_line2.server_web.resources.SourceResource;

@Component(modules = ServerWebModule.class)
@Singleton
public abstract class ServerWebComponent {


	public abstract void inject(CategoryResource instance);

	public abstract void inject(NewsArticleResource instance);

	public abstract void inject(NewsHeaderResource instance);

	public abstract void inject(SourceResource instance);

	public abstract void inject(ServerWeb result);
}
