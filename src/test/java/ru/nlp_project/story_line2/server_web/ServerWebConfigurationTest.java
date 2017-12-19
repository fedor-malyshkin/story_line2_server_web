package ru.nlp_project.story_line2.server_web;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nlp_project.story_line2.server_web.ServerWebConfigurationTest.TestClass;


// WARN: for unknown reasons '@TestPropertySource' does not work with YAML
@RunWith(SpringRunner.class)
//@TestPropertySource("classpath:ru/nlp_project/story_line2/server_web/test_server_web_config.yml")
@SpringBootTest()
@ContextConfiguration(classes = TestClass.class)
public class ServerWebConfigurationTest {

	@Autowired
	ServerWebConfiguration testable;

	@BeforeClass
	public static void setUpClass() {
		// WARN: for unknown reasons '@TestPropertySource' does not work with YAML
		System.setProperty("spring.config.location",
				"src/test/resources/ru/nlp_project/story_line2/server_web/test_server_web_config.yml");
	}

	@Test
	public void testToString() {
		Assertions.assertThat(testable.toString()).isEqualToIgnoringCase("ServerWebConfiguration{"
				+ "drpcHost='datahouse01.nlp-project.ru', drpcPort=3772, influxdbMetrics=MetricsConfiguration{"
				+ "enabled=true, influxdbHost='ci.nlp-project.ru', influxdbPort=8086, influxdbDb='storyline', influxdbUser='server_web', influxdbPassword='server_web', reportingPeriod=30}, "
				+ "sources=["
				+ "SourceConfiguration{name='bnkomi.ru', title='Информационное агентство БНКоми', titleShort='БНКоми'}, "
				+ "SourceConfiguration{name='7x7-journal.ru', title='Межрегиональный интернет-журнал \"7x7\"', titleShort='\"7x7\"'}, "
				+ "SourceConfiguration{name='komiinform.ru', title='Информационное агенство Комиинформ', titleShort='Комиинформ'}"
				+ "]"
				+ "}");
	}


	@EnableConfigurationProperties(ServerWebConfiguration.class)
	public static class TestClass {

		@Bean
		protected IMetricsManager metricsManager() {
			return mock(IMetricsManager.class);
		}
	}


}