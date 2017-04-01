package ru.nlp_project.story_line2.server_web.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.dagger.ServerWebBuilder;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

	@Inject
	IRequestExecutor executor;

	public CategoryResource() {
		ServerWebBuilder.getComponent().inject(this);
	}


	@GET
	public Response listCategories() {
		Response result = Response.ok(executor.listCategories()).encoding("UTF-8").build();
		return result;
	}

}
