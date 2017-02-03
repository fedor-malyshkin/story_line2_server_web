package ru.nlp_project.story_line2.server_web.dagger;

import javax.inject.Singleton;

import dagger.Component;
import ru.nlp_project.story_line2.server_web.resources.NewsArticleResource;

@Component(modules = ApplicationModule.class)
@Singleton
public abstract class ApplicationComponent {
	public abstract void inject(NewsArticleResource instance);


}
