package ru.nlp_project.story_line2.server_web.resources;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 *
 * @author fedor
 */
@RestController
@RequestMapping(value = "/categories", produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CategoryResource {

	@Autowired
	private IRequestExecutor executor;

	@GetMapping
	public ResponseEntity<String> listCategories() throws IOException {
		ResponseEntity<String> response = ResponseEntity.ok(executor.listCategories());
		return response;
	}

}
