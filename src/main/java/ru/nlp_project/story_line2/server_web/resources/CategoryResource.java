package ru.nlp_project.story_line2.server_web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.dagger.ApplicationBuilder;
import ru.nlp_project.story_line2.server_web.impl.CacheConfiguration;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

	public IRequestExecutor executor;
	public CacheConfiguration cacheConfiguration;

	public CategoryResource() {
		ApplicationBuilder.inject(this);
	}


	@GET
	public Response listCategories() {
		Response result = Response.ok(executor.listCategories())
				.cacheControl(cacheConfiguration.listCategories()).encoding("UTF-8").build();
		return result;
	}

}
