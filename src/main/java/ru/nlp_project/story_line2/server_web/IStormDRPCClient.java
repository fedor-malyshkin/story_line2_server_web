package ru.nlp_project.story_line2.server_web;

public interface IStormDRPCClient {
	String getNewsHeaders(String source, int count, String lastNewsId);

	String getNewsArticleById(String newsArticleId);

	String getImageDataByNewsArticleId(String newsArticleId);
}
