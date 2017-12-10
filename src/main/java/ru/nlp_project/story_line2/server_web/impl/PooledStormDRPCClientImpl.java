package ru.nlp_project.story_line2.server_web.impl;


import java.util.HashMap;
import java.util.Map;
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
 * В связи с тем, что DRPC клиент Storm ({@link org.apache.storm.utils.DRPCClient}) не
 * потокобезопасен -- требуется осуществлять работу с экземпляром из одного потока, в данном к
 * классе создаётся их пул.
 *
 * @author fedor
 */
public class PooledStormDRPCClientImpl implements IStormDRPCClient {

	private static final String DRPC_METHOD_GET_NEWS_HEADERS = "get_news_headers";
	private static final String DRPC_METHOD_GET_NEWS_ARTICLE = "get_news_article";
	private static final long MAX_WAIT_TO_BORROW_DRPC_CLIENT = 600;
	private static final Integer MAX_WAIT_TO_EXECUTE = 1000;
	private static final String DRPC_METHOD_GET_NEWS_IMAGES = "get_news_images";
	private final ServerWebConfiguration configurationManager;
	private Logger log;
	private GenericObjectPool<DRPCClient> drpcClientPool;

	public PooledStormDRPCClientImpl(ServerWebConfiguration configurationManager) {
		log = LoggerFactory.getLogger(this.getClass());
		this.configurationManager = configurationManager;
	}

	@Override
	public void initialize() {
		DRPCClientPooledObjectFactory objectFactory = new DRPCClientPooledObjectFactory();
		drpcClientPool = new GenericObjectPool<>(objectFactory);
	}

	@Override
	synchronized public String getNewsHeaders(String source, int count, String lastNewsId) {
		// args
		Map<String, Object> args = new HashMap<>();
		args.put("source", source);
		args.put("count", count);
		args.put("last_news_id", lastNewsId);
		String drpcResult = callPooledSDRPCClient(DRPC_METHOD_GET_NEWS_HEADERS, args);
		return removeSurroundingBrackets(drpcResult);
	}

	/**
	 * Вызвать указанный метод в клиенте из пула.
	 *
	 * @param methodName имя метода для DRPC клиента
	 * @param args аргкмент для DRPC клиента
	 * @return результат в виде строки
	 */
	private String callPooledSDRPCClient(String methodName, Map<String, Object> args) {
		boolean objectInPoolInvalidated = false;
		String drpcResult;
		DRPCClient client = null;
		try {
			client = drpcClientPool.borrowObject(MAX_WAIT_TO_BORROW_DRPC_CLIENT);
			drpcResult = client.execute(methodName, JSONUtils.serialize(args));
		} catch (Exception e) {
			// in case DRPC exception
			try {
				if (client != null) {
					objectInPoolInvalidated = true;
					drpcClientPool.invalidateObject(client);
				}
			} catch (Exception ignored) {
			}
			String message = String
					.format("Exception while calling DRPC method '%s' with args '%s' - %s.", methodName,
							args.toString(),
							e.getMessage());
			log.error(message, e);
			throw new IllegalStateException(e);
		} finally {
			if (client != null && !objectInPoolInvalidated) {
				drpcClientPool.returnObject(client);
			}
		}
		return drpcResult;
	}

	@Override
	synchronized public String getNewsArticleById(String id) {
		// args
		Map<String, Object> args = new HashMap<>();
		args.put("id", id);
		String drpcResult = callPooledSDRPCClient(DRPC_METHOD_GET_NEWS_ARTICLE, args);
		return removeSurroundingBrackets(drpcResult);
		// return convertArrayToElement(res);
	}

	@Override
	public String getImageDataByNewsArticleId(String id) {
		// args
		Map<String, Object> args = new HashMap<>();
		args.put("id", id);
		String drpcResult = callPooledSDRPCClient(DRPC_METHOD_GET_NEWS_IMAGES, args);
		return removeSurroundingBrackets(drpcResult);
		// return convertArrayToElement(res);
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
			return new DefaultPooledObject<>(obj);
		}

	}

}
