package ru.nlp_project.story_line2.server_web.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;

public class JacksonRequestExecutorImpl implements IRequestExecutor {

	@Override
	public String getNewsArticleById(String newsArticleId) {
		// String id, String source, Date date, String title,
		// String content, String path, Date processingDate
		JacksonNewsArticleModel model = new JacksonNewsArticleModel("asbd3", "bnkomi.ru",
				new Date(3), "Новость 3", "Content of Новость 3 from netw",
				"https://www.bnkomi.ru/data/news/59446/", new Date(30));
		return JacksonJSONUtils.serialize(model);
	}

	public void initialize() {

	}

	@Override
	public String listCategories() {
		List<JacksonCategoryModel> list = Arrays.asList(new JacksonCategoryModel("asbd1", "auto"),
				new JacksonCategoryModel("asbd2", "Культура"),
				new JacksonCategoryModel("asbd2", "Новая Гвинея"),
				new JacksonCategoryModel("asbd3", "Технологии"));
		return JacksonJSONUtils.serialize(list);
	}



	@Override
	public String listNewsHeaders(String sourceDomain, int count) {
		List<JacksonNewsArticleModel> list = Arrays.asList(
				new JacksonNewsArticleModel("asbd1", sourceDomain, new Date(1), "Новость 1"),
				new JacksonNewsArticleModel("asbd2", sourceDomain, new Date(2), "Новость 2"),
				new JacksonNewsArticleModel("asbd3", sourceDomain, new Date(3), "Новость 3"));
		return JacksonJSONUtils.serialize(list);
	}

	@Override
	public String listSources() {
		List<JacksonSourceModel> list = Arrays.asList(
				new JacksonSourceModel("asbd1", "bnkomi.ru", "БНК",
						"Информационное агентство БНКоми"),
				new JacksonSourceModel("asbd2", "7x7-journal.ru", "\"7x7\"",
						"Межрегиональный интернет-журнал \"7x7\""),
				new JacksonSourceModel("asbd2", "komiinform.ru", "komiinform",
						"Информационное агенство Комиинформ"));
		return JacksonJSONUtils.serialize(list);
	}



}
