package ru.nlp_project.story_line2.server_web.resources;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IRequestExecutor.IImageData;
import ru.nlp_project.story_line2.server_web.ServerWeb;

/**
 * Не используем Dagger2 тут, т.к. идёт конфлик использования @Inject с Jersey.
 *
 * @author fedor
 */
@Path("/news_articles/")
@Produces(ServerWeb.MEDIA_TYPE_UTF8)
@Consumes(ServerWeb.MEDIA_TYPE_UTF8)
public class NewsArticleResource {

	// 1 week = 7 * 24 * 60 * 60
	public static final int CACHE_MAX_AGE = 604800;
	private final CacheControl ccontrol;
	private final Logger log;
	@Context
	UriInfo uriInfo;
	private IRequestExecutor executor;

	public NewsArticleResource(IRequestExecutor executor2) {
		this.executor = executor2;
		ccontrol = new CacheControl();
		ccontrol.setMaxAge(CACHE_MAX_AGE);
		log = LoggerFactory.getLogger(this.getClass());
	}

	@GET
	@Path("/{article_id}")
	public Response getNewsArticleById(@PathParam("article_id") @NotNull String newsArticleId) {
		Response result =
				Response.ok(executor.getNewsArticleById(newsArticleId)).cacheControl(ccontrol).build();
		return result;
	}

	@GET
	@Path("/{article_id}/images")
	@Produces("image/*")
	public Response getNewsArticleImageDataById(
			@PathParam("article_id") @NotNull String newsArticleId, @QueryParam("w") Integer width,
			@QueryParam("h") Integer height, @QueryParam("op") String operation) {

		IImageData imageData = executor.getImageDataByNewsArticleId(newsArticleId);
		if (imageData.hasImageData()) {
			byte[] imageBytes = imageData.bytes();
			if (operation != null) {
				imageBytes = executor
						.processImageOperation(operation, height, width, imageBytes, imageData.getMediaType());
			}
			// if (in any case some shit happened - log it and return 404 code)
			if (imageBytes != null && imageBytes.length > 0) {
				return Response.ok(imageBytes, imageData.getMediaType())
						.header("Content-Length", imageBytes.length).cacheControl(ccontrol).build();
			} else {
				log.error("Some error happened while processing request: {}",
						uriInfo.getRequestUri().toString());
				return Response.status(404).build();
			}
		} else if (imageData.getUrl() != null && !imageData.getUrl().isEmpty()) {
			return Response.status(301).header("Location", imageData.getUrl()).cacheControl(ccontrol)
					.build();
		} else {
			return Response.status(404).build();
		}
	}

}
