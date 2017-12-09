package ru.nlp_project.story_line2.server_web;

import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.nlp_project.story_line2.server_web.impl.MetricsManagerImpl;
import ru.nlp_project.story_line2.server_web.impl.PooledStormDRPCClientImpl;
import ru.nlp_project.story_line2.server_web.impl.RequestExecutorImpl;


@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@EnableConfigurationProperties(ServerWebConfiguration.class)
public class ServerWebApplication {
	@Autowired
	ServerWebConfiguration configurationManager;
	@Autowired
	IMetricsManager metricsManager;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ServerWebApplication.class, args);
	}

	@Bean
	protected IStormDRPCClient drpcClient() {
		return new PooledStormDRPCClientImpl(configurationManager);
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


}

