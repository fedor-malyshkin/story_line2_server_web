package ru.nlp_project.story_line2.server_web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 *
 * @author fedor
 */
@Path("/feedback")
public class FeedbackResource {

	private IRequestExecutor executor;

	public FeedbackResource(IRequestExecutor executor2) {
		this.executor = executor2;
	}


	@GET
	@Path("about")
	@Produces(MediaType.APPLICATION_XHTML_XML)
	public Response getAboutInfo() {
		Response result = Response.ok("<p>Cool!</p>").build();
		return result;
	}


	@PUT
	@Path("send")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response sendFeedback(@FormParam("from") String from,
			@FormParam("message") String message) {
		System.out.println("from = " + from);
		System.out.println("message = " + message);

		Response result = Response.ok().build();
		return result;
	}

}
