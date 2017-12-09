package ru.nlp_project.story_line2.server_web.resources;

import java.util.concurrent.TimeUnit;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 *
 * @author fedor
 */

@RestController
@RequestMapping(value = "/news_headers", produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class NewsHeaderResource {

	private final CacheControl cacheControl;
	private IRequestExecutor executor;

	public NewsHeaderResource(IRequestExecutor executor2) {
		this.executor = executor2;
		cacheControl = CacheControl.maxAge(5, TimeUnit.MINUTES);
	}

	/**
	 * @param count
	 * @param sourceDomain
	 * @return
	 */
	@GetMapping(path = "/{source_domain}")
	public ResponseEntity<String> listHeaders(
			@RequestParam(value = "count", defaultValue = "10") int count,
			@PathVariable("source_domain") String sourceDomain,
			@RequestParam("last_news_id") String lastNewsId) {
		ResponseEntity<String> result = ResponseEntity.ok().cacheControl(cacheControl)
				.body(executor.listNewsHeaders(sourceDomain, count, lastNewsId));
		return result;
	}

}
