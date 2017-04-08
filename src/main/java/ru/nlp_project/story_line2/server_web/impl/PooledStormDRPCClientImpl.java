package ru.nlp_project.story_line2.server_web.impl;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.storm.utils.DRPCClient;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.utils.JSONUtils;

/**
 * Клиент Storm (с пулом фактических клиентов к Storm DRPC).
 * 
 * В связи с тем, что DRPC> клиент Storm ({@link org.apache.storm.utils.DRPCClient}) не
 * потокобезопасен -- требуется осуществлять работу с экземпляром из одного потока, в данном к
 * классе создаётся их пул.
 * 
 * @author fedor
 *
 */
public class PooledStormDRPCClientImpl implements IStormDRPCClient {

	public class DRPCClientPooledObjectFactory extends BasePooledObjectFactory<DRPCClient> {

		@Override
		public DRPCClient create() throws Exception {
			// get configuration (default, my, and command-line combined)
			Map<String, Object> config = Utils.readStormConfig();
			return new DRPCClient(config, configurationManager.drpcHost,
					configurationManager.drpcPort, MAX_WAIT_TO_EXECUTE);
		}

		@Override
		public PooledObject<DRPCClient> wrap(DRPCClient obj) {
			return new DefaultPooledObject<DRPCClient>(obj);
		}

	}

	private static final String DRPC_METHOD_GET_NEWS_HEADERS = "get_news_headers";
	private static final String DRPC_METHOD_GET_NEWS_ARTICLE = "get_news_article";
	private static final long MAX_WAIT_TO_BORROW_DRPC_CLIENT = 50;
	private static final Integer MAX_WAIT_TO_EXECUTE = 500;

	@Inject
	ServerWebConfiguration configurationManager;
	private Logger log;
	private GenericObjectPool<DRPCClient> drpcClientPool;

	@Inject
	public PooledStormDRPCClientImpl() {
		log = LoggerFactory.getLogger(this.getClass());
	}

	public void initialize() {
		DRPCClientPooledObjectFactory objectFactory = new DRPCClientPooledObjectFactory();
		drpcClientPool = new GenericObjectPool<DRPCClient>(objectFactory);
	}

	@Override
	synchronized public String getNewsHeaders(String source, int count) {
		// args
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("source", source);
		args.put("count", count);
		String drpcResult;
		DRPCClient client = null;
		try {
			client = drpcClientPool.borrowObject(MAX_WAIT_TO_BORROW_DRPC_CLIENT);
			drpcResult = client.execute(DRPC_METHOD_GET_NEWS_HEADERS, JSONUtils.serialize(args));
		} catch (Exception e) {
			// in case DRPC exception
			try {
				if (client != null)
					drpcClientPool.invalidateObject(client);
			} catch (Exception e1) {}
			
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		} finally {
			if (client != null)
				drpcClientPool.returnObject(client);
		}
		return removeSurroundingBrackets(drpcResult);
	}

	@Override
	synchronized public String getNewsArticleById(String id) {
		// args
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("id", id);
		String drpcResult;
		DRPCClient client = null;
		try {
			client = drpcClientPool.borrowObject(MAX_WAIT_TO_BORROW_DRPC_CLIENT);
			drpcResult = client.execute(DRPC_METHOD_GET_NEWS_ARTICLE, JSONUtils.serialize(args));
		} catch (Exception e) {
			// in case DRPC exception
			try {
				if (client != null)
					drpcClientPool.invalidateObject(client);
			} catch (Exception e1) {}
			client = null;
			log.error(e.getMessage(), e);
			throw new IllegalStateException(e);
		} finally {
			if (client != null)
				drpcClientPool.returnObject(client);
		}
		String res = removeSurroundingBrackets(drpcResult);
		return convertArrayToElement(res);
	}


	private String convertArrayToElement(String input) {
		String res = StringUtils.removeStartIgnoreCase(input, "[");
		res = StringUtils.removeEndIgnoreCase(res, "]");
		return res;
	}

	private String removeSurroundingBrackets(String input) {
		String res = StringUtils.removeStartIgnoreCase(input, "[[\"");
		res = StringUtils.removeEndIgnoreCase(res, "\"]]");
		res = StringEscapeUtils.unescapeJson(res);
		return res;
	}

}
