package ru.nlp_project.story_line2.server_web.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration.SourceConfiguration;
import ru.nlp_project.story_line2.server_web.utils.JSONUtils;

public class RequestExecutorImpl implements IRequestExecutor {

	@Inject
	IStormDRPCClient stormDRPCClient;

	@Inject
	ServerWebConfiguration configuration;
	private String sourcesCache;

	@Inject
	public RequestExecutorImpl() {
	}

	public void initialize() {
	}

	@Override
	public String listSources() {
		if (sourcesCache == null) {
			List<SourceModel> arr = new ArrayList<SourceModel>(configuration.sources.size());
			for (SourceConfiguration s : configuration.sources) {
				arr.add(new SourceModel(s.name, s.title, s.titleShort));
			}
			sourcesCache = JSONUtils.serialize(arr);
		}
		return sourcesCache;
	}

	@Override
	public String listCategories() {
		List<CategoryModel> list = Arrays.asList();
		return JSONUtils.serialize(list);
	}

	@Override
	public String listNewsHeaders(String source, int count, String lastNewsId) {
		return stormDRPCClient.getNewsHeaders(source, count, lastNewsId);
	}

	@Override
	public String getNewsArticleById(String newsArticleId) {
		return stormDRPCClient.getNewsArticleById(newsArticleId);
	}


}
