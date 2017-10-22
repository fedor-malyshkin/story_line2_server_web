package ru.nlp_project.story_line2.server_web.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration.SourceConfiguration;
import ru.nlp_project.story_line2.server_web.utils.ImageUtils;
import ru.nlp_project.story_line2.server_web.utils.JSONUtils;

public class RequestExecutorImpl implements IRequestExecutor {

	private final Logger log;
	@Inject
	IStormDRPCClient stormDRPCClient;
	@Inject
	ServerWebConfiguration configuration;
	private String sourcesCache;

	@Inject
	public RequestExecutorImpl() {
		log = LoggerFactory.getLogger(this.getClass());
	}

	public void initialize() {
	}

	@Override
	public String listSources() {
		if (sourcesCache == null) {
			List<SourceModel> arr = new ArrayList<SourceModel>(configuration.sources.size());
			for (SourceConfiguration s : configuration.sources) {
				arr.add(new SourceModel(s.name, s.title, s.titleShort));
			}
			sourcesCache = JSONUtils.serialize(arr);
		}
		return sourcesCache;
	}

	@Override
	public byte[] processImageOperation(String operation, Integer width, Integer height,
			byte[] imageIn, String mediaType) {
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
	public String listCategories() {
		List<CategoryModel> list = Arrays.asList();
		return JSONUtils.serialize(list);
	}

	@Override
	public String listNewsHeaders(String source, int count, String lastNewsId) {
		return stormDRPCClient.getNewsHeaders(source, count, lastNewsId);
	}

	@Override
	public String getNewsArticleById(String newsArticleId) {
		return stormDRPCClient.getNewsArticleById(newsArticleId);
	}

	@Override
	public IImageData getImageDataByNewsArticleId(String newsArticleId) {
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
		String mediaType = "";
		String ext = StringUtils.substringAfterLast(image_url, ".");
		switch (ext) {
			case "jpg":
			case "jpeg": {
				mediaType = "image/jpeg";
				break;
			}
			case "png": {
				mediaType = "image/png";
				break;
			}
		}
		return new ImageDataImpl(bytes, mediaType, image_url);
	}

	public class ImageDataImpl implements IImageData {

		private byte[] imageData;
		private String mediaType;
		private String url;

		public ImageDataImpl(byte[] imageData, String mediaType, String url) {
			this.imageData = imageData;
			this.mediaType = mediaType;
			this.url = url;
		}

		@Override
		public String getMediaType() {
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
