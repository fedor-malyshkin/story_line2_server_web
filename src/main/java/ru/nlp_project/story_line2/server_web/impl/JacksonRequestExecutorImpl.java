package ru.nlp_project.story_line2.server_web.impl;

import java.util.Arrays;
import java.util.List;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;

public class JacksonRequestExecutorImpl implements IRequestExecutor {

	public void initialize() {

	}

	@Override
	public String getCategories() {
		List<JacksonCategoryModel> list = Arrays.asList(new JacksonCategoryModel("asbd1", "auto"),
				new JacksonCategoryModel("asbd2", "Культура"),
				new JacksonCategoryModel("asbd2", "Новая Гвинея"),
				new JacksonCategoryModel("asbd3", "Технологии"));
		return JacksonJSONUtils.serialize(list);
	}

	@Override
	public String getSources() {
		List<JacksonSourceModel> list = Arrays.asList(
				new JacksonSourceModel("asbd1", "bnkomi.ru", "Информационное агентство БНКоми"),
				new JacksonSourceModel("asbd2", "7x7-journal.ru",
						"Межрегиональный интернет-журнал \"7x7\""),
				new JacksonSourceModel("asbd2", "komiinform.ru",
						"Информационное агенство Комиинформ"));
		return JacksonJSONUtils.serialize(list);
	}



}
