package ru.nlp_project.story_line2.server_web.resources;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;

/**
 * @author fedor
 */
@RestController
@RequestMapping(value = "/maintenance")
public class MaintenanceResource {

	@Autowired
	private IRequestExecutor executor;

	@GetMapping
	public ResponseEntity execute(
			@RequestParam(value = "command", required = true) String command,
			@RequestParam(value = "param1", required = false) String param1,
			@RequestParam(value = "param2", required = false) String param2) throws IOException {
		executor.maintenanceCommand(command, param1, param2);
		return ResponseEntity.ok().body(null);
	}

}
