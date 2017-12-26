package ru.nlp_project.story_line2.server_web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;

/**

 *
 * @author fedor
 */

@RestController
@RequestMapping(value = "/sources", produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SourceResource {

	private final CacheControl ccontrol;
	@Autowired
	private IRequestExecutor executor;


	public SourceResource() {
		ccontrol = CacheControl.noCache();
	}


	@GetMapping
	public ResponseEntity<String> listSources() {
		ResponseEntity<String> result = ResponseEntity.ok().cacheControl(ccontrol)
				.body(executor.listSources());
		return result;
	}

}
