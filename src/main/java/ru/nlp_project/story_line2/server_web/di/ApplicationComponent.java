package ru.nlp_project.story_line2.server_web.di;

import ru.nlp_project.story_line2.server_web.resources.CategoryResource;
import ru.nlp_project.story_line2.server_web.resources.NewsArticleResource;
import ru.nlp_project.story_line2.server_web.resources.SourceResource;

class ApplicationComponent {
	ApplicationModule module = new ApplicationModule();

	public void inject(NewsArticleResource instance) {
		instance.executor = module.provideRequestExecutor();
		instance.cacheConfiguration = module.provideCacheConfiguration();
	}

	public void inject(CategoryResource instance) {
		instance.executor = module.provideRequestExecutor();
		instance.cacheConfiguration = module.provideCacheConfiguration();
	}

	public void inject(SourceResource instance) {
		instance.executor = module.provideRequestExecutor();
		instance.cacheConfiguration = module.provideCacheConfiguration();

	}

}
