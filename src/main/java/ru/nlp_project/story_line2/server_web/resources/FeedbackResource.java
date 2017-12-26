package ru.nlp_project.story_line2.server_web.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;

/**
 *
 * @author fedor
 */

@RestController
@RequestMapping(value = "/feedback", produces = {
		MediaType.APPLICATION_JSON_UTF8_VALUE})
public class FeedbackResource {

	@Autowired
	private IRequestExecutor executor;


	@GetMapping(path = "about", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
	public ResponseEntity<String> getAboutInfo() {
		ResponseEntity<String> result = ResponseEntity.ok("<p>Cool!</p>");
		return result;
	}


	@PutMapping(path = "send", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> sendFeedback(@RequestParam("from") String from,
			@RequestParam("message") String message) {
		System.out.println("from = " + from);
		System.out.println("message = " + message);

		ResponseEntity<String> result = ResponseEntity.ok().build();
		return result;
	}

}
