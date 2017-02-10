package ru.nlp_project.story_line2.server_web.dagger;

import ru.nlp_project.story_line2.server_web.resources.CategoryResource;
import ru.nlp_project.story_line2.server_web.resources.NewsArticleResource;

public class ApplicationBuilder {

	private static ApplicationComponent builder;

	public static void inject(NewsArticleResource instance) {
		getBuilder().inject(instance);

	}

	private static ApplicationComponent getBuilder() {
		if (builder == null)
			builder =  new ApplicationComponent();
		return builder;

	}

	public static void inject(CategoryResource instance) {
		getBuilder().inject(instance);		
	}


}
