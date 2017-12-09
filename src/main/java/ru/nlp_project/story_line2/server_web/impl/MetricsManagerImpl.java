package ru.nlp_project.story_line2.server_web.impl;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import metrics_influxdb.HttpInfluxdbProtocol;
import metrics_influxdb.InfluxdbReporter;
import metrics_influxdb.api.measurements.CategoriesMetricMeasurementTransformer;
import org.slf4j.LoggerFactory;
import ru.nlp_project.story_line2.server_web.IMetricsManager;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration.MetricsConfiguration;

public class MetricsManagerImpl implements IMetricsManager {

	private final ServerWebConfiguration configurationManager;
	private MetricRegistry registry;
	private ScheduledReporter reporterInfluxDB;

	public MetricsManagerImpl(ServerWebConfiguration configurationManager) {
		this.configurationManager = configurationManager;
	}

	private void initializeMetricsLogging() throws UnknownHostException {
		MetricsConfiguration metricsConfiguration = configurationManager.influxdbMetrics;

		final Slf4jReporter reporter = Slf4jReporter.forRegistry(registry)
				.outputTo(LoggerFactory.getLogger("ru.nlp_project.story_line2.server_web"))
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(5, TimeUnit.MINUTES);

		if (metricsConfiguration.enabled) {
			String hostName = InetAddress.getLocalHost().getCanonicalHostName();
			reporterInfluxDB = InfluxdbReporter.forRegistry(registry)
					.protocol(new HttpInfluxdbProtocol("http", metricsConfiguration.influxdbHost,
							metricsConfiguration.influxdbPort, metricsConfiguration.influxdbUser,
							metricsConfiguration.influxdbPassword, metricsConfiguration.influxdbDb))
					// rate + dim conversions
					.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS)
					// filter
					.filter(MetricFilter.ALL)
					// don't skip
					.skipIdleMetrics(false)
					// hostname tag
					.tag("host", hostName)
					// !!! converter
					// al influxdbMetrics must be of form: "processed_links.bnkomi_ru.crawling" -> "crawling
					// source=bnkomi_ru, param=processed_links value=0.1"
					.transformer(new CategoriesMetricMeasurementTransformer("param", "source"))
					.build();
			reporterInfluxDB.start(metricsConfiguration.reportingPeriod, TimeUnit.SECONDS);
		}
		/*
		 * final JmxReporter reporter2 = JmxReporter.forRegistry(metricRegistry).build();
		 * reporter2.start();
		 */
	}

	@Override
	public void initialize() {
		registry = new MetricRegistry();
		try {
			initializeMetricsLogging();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
		reporterInfluxDB.stop();
	}
}
