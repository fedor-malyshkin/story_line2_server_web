package ru.nlp_project.story_line2.server_web.dagger;

import ru.nlp_project.story_line2.server_web.resources.CategoryResource;
import ru.nlp_project.story_line2.server_web.resources.NewsArticleResource;

class ApplicationComponent {
	ApplicationModule module = new ApplicationModule();

	public void inject(NewsArticleResource instance) {
		instance.executor = module.provideRequestExecutor();
	}

	public void inject(CategoryResource instance) {
		instance.executor = module.provideRequestExecutor();
		instance.cacheConfiguration = module.provideCacheConfiguration();
	}

}
