package ru.nlp_project.story_line2.server_web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.dagger.ServerWebBuilder;

@Path("/sources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SourceResource {

	public IRequestExecutor executor;

	public SourceResource() {
		ServerWebBuilder.getComponent().inject(this);
	}


	@GET
	public Response listSources() {
		Response result = Response.ok(executor.listSources()).encoding("UTF-8").build();
		return result;
	}

}
