package ru.nlp_project.story_line2.server_web;

import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import ru.nlp_project.story_line2.server_web.impl.MetricsManagerImpl;
import ru.nlp_project.story_line2.server_web.impl.PooledStormDRPCClientImpl;
import ru.nlp_project.story_line2.server_web.impl.RequestExecutorImpl;


@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableConfigurationProperties(ServerWebConfiguration.class)
public class ServerWebApplication extends DelegatingWebMvcConfiguration {
	@Autowired
	ServerWebConfiguration configurationManager;
	@Autowired
	IMetricsManager metricsManager;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ServerWebApplication.class, args);
	}

	@Bean
	protected IStormDRPCClient drpcClient() {
		IStormDRPCClient result = new  PooledStormDRPCClientImpl(configurationManager);
		result.initialize();
		return result;
	}

	@Bean
	protected IRequestExecutor requestExecutor() {
		return new RequestExecutorImpl(configurationManager);
	}

	@Bean
	protected IMetricsManager metricsManager() {
		IMetricsManager result = new MetricsManagerImpl(configurationManager);
		result.initialize();
		return result;
	}

	@PreDestroy
	public void stop() throws Exception {
		metricsManager.shutdown();
	}


	/**
	 * to keep dot symbol in requests like "/news_headers/bnkomi.ru" (in "bnkomi.ru")
	 * @param configurer
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false);
	}

}

