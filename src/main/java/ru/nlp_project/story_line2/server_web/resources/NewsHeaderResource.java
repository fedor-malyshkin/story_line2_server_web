package ru.nlp_project.story_line2.server_web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.ServerWeb;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 * 
 * @author fedor
 *
 */
@Path("/news_headers/{source_domain}")
@Produces(ServerWeb.MEDIA_TYPE_UTF8)
@Consumes(ServerWeb.MEDIA_TYPE_UTF8)
public class NewsHeaderResource {

	private IRequestExecutor executor;

	public NewsHeaderResource(IRequestExecutor executor2) {
		this.executor = executor2;
	}

	/**
	 * @param count
	 * @param sourceDomain
	 * @return
	 */
	@GET
	public Response listHeaders(@DefaultValue("10") @QueryParam("count") int count,
			@PathParam("source_domain") String sourceDomain) {
		Response result = Response.ok(executor.listNewsHeaders(sourceDomain, count)).build();
		return result;
	}

}
