package ru.nlp_project.story_line2.server_web;

import javax.ws.rs.client.Entity;

public interface IRequestExecutor {
	interface IImageData {
		String getUrl();
		boolean hasImageData();
		String getMediaType();
		byte[] bytes();
	}


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

	IImageData getImageDataByNewsArticleId(String newsArticleId);
}
