package ru.nlp_project.story_line2.server_web;

public interface IRequestExecutor {

	default String listCategories() {
		return "";
	}

	default String listSources() {
		return "";
	}

	default String listNewsHeaders(String sourceDomain, int count, String lastNewsId) {
		return "";
	}

	default String getNewsArticleById(String newsArticleId) {
		return "";
	}
}
