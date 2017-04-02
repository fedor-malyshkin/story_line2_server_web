package ru.nlp_project.story_line2.server_web;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.LoggerFactory;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Slf4jReporter;

import io.dropwizard.lifecycle.Managed;
import metrics_influxdb.HttpInfluxdbProtocol;
import metrics_influxdb.InfluxdbReporter;
import metrics_influxdb.api.measurements.CategoriesMetricMeasurementTransformer;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration.MetricsConfiguration;
import ru.nlp_project.story_line2.server_web.dagger.ServerWebBuilder;

/**
 * Крулер - основной класс бизнес-логики и построения компонентов.
 * 
 * @author fedor
 *
 */
public class ServerWeb implements Managed {

	public static final String MEDIA_TYPE_UTF8 = "application/json;charset=utf-8";
	public static final String METRICS_SUFFIX = ".server_web";

	public static ServerWeb newInstance() throws Exception {
		ServerWeb result = new ServerWeb();
		ServerWebBuilder.getComponent().inject(result);
		return result;
	}


	@Inject
	MetricRegistry metricRegistry;
	@Inject
	ServerWebConfiguration configuration;

	private ServerWeb() {}

	private void initializeMetricsLogging() throws UnknownHostException {
		MetricsConfiguration metricsConfiguration = configuration.metrics;


		final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry)
				.outputTo(LoggerFactory.getLogger("ru.nlp_project.story_line2.server_web"))
				.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(1, TimeUnit.MINUTES);

		if (metricsConfiguration.enabled) {
			String hostName = InetAddress.getLocalHost().getCanonicalHostName();
			final ScheduledReporter reporterInfluxDB = InfluxdbReporter.forRegistry(metricRegistry)
					.protocol(new HttpInfluxdbProtocol("http", metricsConfiguration.influxdbHost,
							metricsConfiguration.influxdbPort, metricsConfiguration.influxdbUser,
							metricsConfiguration.influxdbPassword, metricsConfiguration.influxdbDB))
					// rate + dim conversions
					.convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS)
					// filter
					.filter(MetricFilter.ALL)
					// don't skip
					.skipIdleMetrics(false)
					// hostname tag
					.tag("host", hostName)
					// !!! converter
					// al metrics must be of form: "processed_links.bnkomi_ru.crawling" -> "crawling
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
	public void start() throws Exception {
		// time to startup service
		initializeMetricsLogging();
	}


	@Override
	public void stop() throws Exception {}


}
