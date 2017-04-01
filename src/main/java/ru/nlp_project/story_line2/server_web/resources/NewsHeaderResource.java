package ru.nlp_project.story_line2.server_web.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.dagger.ServerWebBuilder;

@Path("/news_headers/{source_domain}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsHeaderResource {

	@Inject
	IRequestExecutor executor;

	public NewsHeaderResource() {
		ServerWebBuilder.getComponent().inject(this);
	}

	/**
	 * @param count
	 * @param sourceDomain
	 * @return
	 */
	@GET
	public Response listHeaders(@DefaultValue("10") @QueryParam("count") int count,
			@PathParam("source_domain") String sourceDomain) {
		Response result = Response.ok(executor.listNewsHeaders(sourceDomain, count))
				.encoding("UTF-8").build();
		return result;
	}

}
