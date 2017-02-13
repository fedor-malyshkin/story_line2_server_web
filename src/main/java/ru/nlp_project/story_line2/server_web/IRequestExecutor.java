package ru.nlp_project.story_line2.server_web;

public interface IRequestExecutor {

	default String getCategories() {
		return "";
	}

	default String getSources() {
		return "";
	}

	default String getNewsArticles(String sourceDomain, boolean headers, int count) {
		return "";
	}

	default String getNewsArticleById(String sourceDomain, String newsArticleId) {
		return "";
	}
}
