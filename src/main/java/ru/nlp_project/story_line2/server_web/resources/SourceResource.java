package ru.nlp_project.story_line2.server_web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.di.ApplicationBuilder;
import ru.nlp_project.story_line2.server_web.impl.CacheConfiguration;

@Path("/sources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SourceResource {

	public IRequestExecutor executor;
	public CacheConfiguration cacheConfiguration;

	public SourceResource() {
		ApplicationBuilder.inject(this);
	}


	@GET
	public Response getSources() {
		Response result = Response.ok(executor.getSources())
				.cacheControl(cacheConfiguration.getSources()).encoding("UTF-8").build();
		return result;
	}

}
