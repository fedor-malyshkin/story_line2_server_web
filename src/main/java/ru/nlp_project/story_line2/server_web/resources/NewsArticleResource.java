package ru.nlp_project.story_line2.server_web.resources;

import javax.validation.constraints.NotNull;
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
import ru.nlp_project.story_line2.server_web.dagger.ApplicationBuilder;
import ru.nlp_project.story_line2.server_web.impl.CacheConfiguration;

@Path("/news_articles/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsArticleResource {

	public IRequestExecutor executor;
	public CacheConfiguration cacheConfiguration;

	public NewsArticleResource() {
		ApplicationBuilder.inject(this);
	}

	@GET
	@Path("/{article_id}")
	public Response getNewsArticleById(@PathParam("article_id") @NotNull String newsArticleId) {
		Response result = Response.ok(executor.getNewsArticleById(newsArticleId))
				.cacheControl(cacheConfiguration.getNewsArticleById()).encoding("UTF-8").build();
		return result;

	}

}
