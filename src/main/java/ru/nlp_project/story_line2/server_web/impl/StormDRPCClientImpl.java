package ru.nlp_project.story_line2.server_web.impl;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.storm.thrift.TException;
import org.apache.storm.thrift.transport.TTransportException;
import org.apache.storm.utils.DRPCClient;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.utils.JSONUtils;

public class StormDRPCClientImpl implements IStormDRPCClient {

	private static final String DRPC_METHOD_GET_NEWS_HEADERS = "get_news_headers";
	private static final String DRPC_METHOD_GET_NEWS_ARTICLE = "get_news_article";

	@Inject
	ServerWebConfiguration configurationManager;

	private boolean initilized;
	private DRPCClient drpcClient;

	private Logger log;

	@Inject
	public StormDRPCClientImpl() {
		log = LoggerFactory.getLogger(this.getClass());
	}

	public void initialize() {
		try {
			// get configuration (default, my, and command-line combined)
			Map<String, Object> config = Utils.readStormConfig();
			drpcClient = new DRPCClient(config, configurationManager.drpcHost,
					configurationManager.drpcPort, 500);
			initilized = true;
		} catch (TTransportException e) {
			initilized = false;
		}
	}

	@Override
	public String getNewsHeaders(String source, int count) {
		DRPCClient client = getClient();
		// args
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("source", source);
		args.put("count", count);
		String drpcResult;
		try {
			drpcResult = client.execute(DRPC_METHOD_GET_NEWS_HEADERS, JSONUtils.serialize(args));
		} catch (TException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		}
		return removeSurroundingBrackets(drpcResult);
	}

	private DRPCClient getClient() {
		if (!initilized)
			initialize();
		return drpcClient;
	}

	@Override
	public String getNewsArticleById(String id) {
		DRPCClient client = getClient();
		// args
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("id", id);
		String drpcResult;
		try {
			drpcResult = client.execute(DRPC_METHOD_GET_NEWS_ARTICLE, JSONUtils.serialize(args));
		} catch (TException e) {
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		}
		return removeSurroundingBrackets(drpcResult);
	}


	private String removeSurroundingBrackets(String execute) {
		String res = StringUtils.removeStartIgnoreCase(execute, "[[\"");
		res = StringUtils.removeEndIgnoreCase(res, "\"]]");
		res = StringEscapeUtils.unescapeJson(res);
		return res;
	}

}
