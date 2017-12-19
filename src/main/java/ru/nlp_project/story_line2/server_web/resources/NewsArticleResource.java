package ru.nlp_project.story_line2.server_web.resources;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IRequestExecutor.IImageData;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 *
 * @author fedor
 */
@RestController
@RequestMapping(value = "/news_articles/", produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class NewsArticleResource {

	private final Logger log;
	private final CacheControl cacheControl;
	@Autowired
	private IRequestExecutor executor;

	public NewsArticleResource() {
		log = LoggerFactory.getLogger(this.getClass());
		cacheControl = CacheControl.maxAge(7, TimeUnit.DAYS);
	}

	@GetMapping(path = "/{article_id}")
	public ResponseEntity<String> getNewsArticleById(
			@PathVariable("article_id") @NotNull String newsArticleId) {

		ResponseEntity<String> result =
				ResponseEntity.ok().cacheControl(cacheControl)
						.body(executor.getNewsArticleById(newsArticleId));

		return result;
	}

	@GetMapping(path = "/{article_id}/images")
	public ResponseEntity<byte[]> getNewsArticleImageDataById(
			@PathVariable("article_id") @NotNull String newsArticleId,
			@RequestParam(value = "w", required = false) Integer width,
			@RequestParam(value = "h", required = false) Integer height,
			@RequestParam(value = "op", required = false) String operation,
			HttpServletRequest request) {

		IImageData imageData = executor.getImageDataByNewsArticleId(newsArticleId);

		if (imageData.hasImageData()) {
			byte[] imageBytes = imageData.bytes();
			if (operation != null) {
				imageBytes = executor
						.processImageOperation(operation, width, height, imageBytes, imageData.getMediaType());
			}
			return ResponseEntity.ok().cacheControl(cacheControl).contentType(imageData.getMediaType())
					.header("Content-Length", "" + imageBytes.length).body(imageBytes);

		} else if (imageData.getUrl() != null && !imageData.getUrl().isEmpty()) {
			return ResponseEntity.status(301).header("Location", imageData.getUrl())
					.cacheControl(cacheControl)
					.build();
		} else {
			// in any other case
			return ResponseEntity.notFound().build();
		}
	}

}
