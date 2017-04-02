package ru.nlp_project.story_line2.server_web.resources;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.ServerWeb;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 * 
 * @author fedor
 *
 */
@Path("/news_articles/")
@Produces(ServerWeb.MEDIA_TYPE_UTF8)
@Consumes(ServerWeb.MEDIA_TYPE_UTF8)
public class NewsArticleResource {

	private IRequestExecutor executor;

	public NewsArticleResource(IRequestExecutor executor2) {
		this.executor = executor2;
	}

	@GET
	@Path("/{article_id}")
	public Response getNewsArticleById(@PathParam("article_id") @NotNull String newsArticleId) {
		Response result =
				Response.ok(executor.getNewsArticleById(newsArticleId)).build();
		return result;

	}

}
