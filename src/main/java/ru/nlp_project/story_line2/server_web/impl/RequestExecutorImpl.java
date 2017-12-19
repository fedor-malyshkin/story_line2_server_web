package ru.nlp_project.story_line2.server_web.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import ru.nlp_project.story_line2.server_web.IMetricsManager;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration.SourceConfiguration;
import ru.nlp_project.story_line2.server_web.utils.ImageUtils;
import ru.nlp_project.story_line2.server_web.utils.JSONUtils;


@CacheConfig
public class RequestExecutorImpl implements IRequestExecutor {

	private final Logger log;
	@Autowired
	private IMetricsManager metricManager;
	@Autowired
	private ServerWebConfiguration configuration;
	@Autowired
	private IStormDRPCClient stormDRPCClient;

	public RequestExecutorImpl() {
		log = LoggerFactory.getLogger(this.getClass());
	}


	@Override
	@Cacheable("sources")
	public String listSources() {
		metricManager.incrementInvocation(IMetricsManager.METHOD_LIST_SOURCES, IMetricsManager.NO_SOURCE);
		List<SourceModel> arr = new ArrayList<SourceModel>(configuration.sources.size());
		for (SourceConfiguration s : configuration.sources) {
			arr.add(new SourceModel(s.name, s.title, s.titleShort));
		}
		return JSONUtils.serialize(arr);
	}

	@Override
	@Cacheable("images")
	public byte[] processImageOperation(String operation, Integer width, Integer height,
			byte[] imageIn, MediaType mediaType) {
		byte[] result = null;
		try {
			if ("crop".equalsIgnoreCase(operation)) {
				return ImageUtils.crop(imageIn, width, height, mediaType);
			} else if ("scale".equalsIgnoreCase(operation)) {
				return ImageUtils.scale(imageIn, width, height, mediaType);
			}
		} catch (IOException e) {
			log.error("Exception while image manipulating: {} {}", e.getMessage(), e);
		}
		throw new IllegalArgumentException("NIE!");
	}

	@Override
	@Cacheable("categories")
	public String listCategories() {
		List<CategoryModel> list = Arrays.asList();
		return JSONUtils.serialize(list);
	}

	@Override
	@Cacheable("headers")
	public String listNewsHeaders(String source, int count, String lastNewsId) {
		metricManager.incrementInvocation(IMetricsManager.METHOD_LIST_HEADERS, source);
		long start = System.currentTimeMillis();
		// invocation
		String result = stormDRPCClient.getNewsHeaders(source, count, lastNewsId);

		long duration = System.currentTimeMillis() - start;
		metricManager.durationInvocation(IMetricsManager.METHOD_LIST_HEADERS, source, duration);
		return result;
	}

	@Override
	@Cacheable("articles")
	public String getNewsArticleById(String newsArticleId) {
		metricManager.incrementInvocation(IMetricsManager.METHOD_GET_NEWS_ARTICLE, IMetricsManager.NO_SOURCE);
		long start = System.currentTimeMillis();
		// invocation
		String result = stormDRPCClient.getNewsArticleById(newsArticleId);
		long duration = System.currentTimeMillis() - start;
		metricManager.durationInvocation(IMetricsManager.METHOD_GET_NEWS_ARTICLE, IMetricsManager.NO_SOURCE, duration);
		return result;
	}

	@Override
	@Cacheable("images")
	public IImageData getImageDataByNewsArticleId(String newsArticleId) {
		metricManager.incrementInvocation(IMetricsManager.METHOD_GET_NEWS_ARTICLE_IMAGE, IMetricsManager.NO_SOURCE);
		// возвражает JSON с 2-я ключами "image_url" (images's FQDN) и "images_data" (base64 on empty string)
		String json = stormDRPCClient.getImageDataByNewsArticleId(newsArticleId);
		return convertJsonToImagesData(json);
	}

	protected IImageData convertJsonToImagesData(String json) {
		Map<String, String> map = (Map<String, String>) JSONUtils.deserialize(json, HashMap.class);
		String image_url = map.get("image_url");
		String image_data = map.get("image_data");

		// bytes
		byte[] bytes = {};
		if (image_data != null && !image_data.isEmpty()) {
			Decoder decoder = Base64.getDecoder();
			bytes = decoder.decode(image_data);
		}

		// media type
		MediaType mediaType = MediaType.IMAGE_PNG;
		String ext = StringUtils.substringAfterLast(image_url, ".");
		switch (ext) {
			case "jpg":
			case "jpeg": {
				mediaType = MediaType.IMAGE_JPEG;
				break;
			}
			case "png": {
				mediaType = MediaType.IMAGE_PNG;
				break;
			}
		}
		return new ImageDataImpl(bytes, mediaType, image_url);
	}

	public static class ImageDataImpl implements IImageData, Serializable {

		private byte[] imageData;
		private MediaType mediaType;
		private String url;

		ImageDataImpl(byte[] imageData, MediaType mediaType, String url) {
			this.imageData = imageData;
			this.mediaType = mediaType;
			this.url = url;
		}

		@Override
		public MediaType getMediaType() {
			return mediaType;
		}

		@Override
		public byte[] bytes() {
			return imageData;
		}

		@Override
		public String getUrl() {
			return url;
		}

		@Override
		public boolean hasImageData() {
			return imageData.length != 0;
		}
	}
}
