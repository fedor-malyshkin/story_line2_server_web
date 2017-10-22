package ru.nlp_project.story_line2.server_web.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.ServerWeb;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 *
 * @author fedor
 */
@Path("/sources")

@Produces(ServerWeb.MEDIA_TYPE_UTF8)
@Consumes(ServerWeb.MEDIA_TYPE_UTF8)
public class SourceResource {

	private final CacheControl ccontrol;
	private IRequestExecutor executor;


	public SourceResource(IRequestExecutor executor2) {
		this.executor = executor2;
		ccontrol = new CacheControl();
		ccontrol.setNoCache(true);
	}


	@GET
	public Response listSources() {
		Response result = Response.ok(executor.listSources()).cacheControl(ccontrol).build();
		return result;
	}

}
