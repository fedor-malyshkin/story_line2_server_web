package ru.nlp_project.story_line2.server_web.resources;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/news_articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NewsArticleResource {

	@GET
	// @CacheControl(maxAge = 5, maxAgeUnit = TimeUnit.MINUTES)
	public Response getNewsArticles(@DefaultValue("10") @QueryParam("count") int count) {
		CacheControl cacheControl = new CacheControl();
		cacheControl.setMaxAge(5);
		Response result = Response.ok("{'res': " + count + "}").status(200)
				.cacheControl(cacheControl).build();
		return result;
	}

	@GET
	@Path("/{article_id}")
	public Response getNewsArticleById(@PathParam("article_id") @NotNull String newsArticleId) {
		Response result =
				Response.ok("{'res': " + newsArticleId + "}").status(200).build();
		return result;
		// throw new WebApplicationException(Status.NOT_FOUND);

	}

}
