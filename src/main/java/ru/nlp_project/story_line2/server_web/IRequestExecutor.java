package ru.nlp_project.story_line2.server_web;

import org.springframework.http.MediaType;

public interface IRequestExecutor {

	byte[] processImageOperation(String operation, Integer width, Integer height, byte[] imageIn,
			MediaType mediaType);

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

	/**
	 * Interface for data about images in news.
	 */
	interface IImageData {

		/**
		 * Get image url.
		 *
		 * @return url or null in abscence case.
		 */
		String getUrl();

		/**
		 * Has/Hasn't image data.
		 */
		boolean hasImageData();

		/**
		 * Get media type of image.
		 *
		 * @return media type or empty string
		 */
		MediaType getMediaType();

		/**
		 * Get image.
		 *
		 * @return image bytes or empty array.
		 */
		byte[] bytes();
	}
}
