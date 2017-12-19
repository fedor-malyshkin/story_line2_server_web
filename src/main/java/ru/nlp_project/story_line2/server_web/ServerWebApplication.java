package ru.nlp_project.story_line2.server_web;

import com.codahale.metrics.MetricRegistry;
import javax.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import ru.nlp_project.story_line2.server_web.impl.MetricsManagerImpl;
import ru.nlp_project.story_line2.server_web.impl.PooledStormDRPCClientImpl;
import ru.nlp_project.story_line2.server_web.impl.RequestExecutorImpl;


@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableConfigurationProperties(ServerWebConfiguration.class)
@EnableCaching
public class ServerWebApplication extends DelegatingWebMvcConfiguration {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ServerWebApplication.class, args);
	}

	/**
	 * to keep dot symbol in requests like "/news_headers/bnkomi.ru" (in "bnkomi.ru")
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}

	@Bean
	protected IStormDRPCClient drpcClient(ServerWebConfiguration configuration) {
		IStormDRPCClient result = new PooledStormDRPCClientImpl(configuration);
		result.initialize();
		return result;
	}

	@Bean
	protected IRequestExecutor requestExecutor() {
		return new RequestExecutorImpl();
	}

	@Bean
	protected IMetricsManager metricsManager(ServerWebConfiguration configuration,
			MetricRegistry metricRegistry) {
		IMetricsManager result = new MetricsManagerImpl(configuration, metricRegistry);
		result.initialize();
		return result;
	}


}

