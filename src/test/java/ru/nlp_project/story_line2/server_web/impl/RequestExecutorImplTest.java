package ru.nlp_project.story_line2.server_web.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nlp_project.story_line2.server_web.IMetricsManager;
import ru.nlp_project.story_line2.server_web.IRequestExecutor.IImageData;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;
import ru.nlp_project.story_line2.server_web.impl.RequestExecutorImplTest.TestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestPropertySource("classpath:ru/nlp_project/story_line2/server_web/server_web_config.yml")
public class RequestExecutorImplTest {

	@Autowired
	IStormDRPCClient stormDRPCClient;
	@Autowired
	private RequestExecutorImpl testable;

	@Test
	public void testListSources() {
		String list = testable.listSources();
		assertThat(list).isEqualTo("[ ]");
	}

	@Test
	public void testConvertJsonToImagesData_HasBytes() {
		String json = "{\"image_url\":\"url.jpeg\", \"image_data\":\"MTIzNA==\"}";
		IImageData imageData = testable.convertJsonToImagesData(json);
		assertThat(imageData).isNotNull();
		assertThat(imageData.hasImageData()).isTrue();
		assertThat(imageData.bytes()).isNotNull().hasSize(4).isEqualTo("1234".getBytes());
		assertThat(imageData.getMediaType()).isEqualTo(MediaType.IMAGE_JPEG);
	}

	@Test
	public void testConvertJsonToImagesData_NoBytes() {
		String json = "{\"image_url\":\"url.jpeg\", \"image_data\":\"\"}";
		IImageData imageData = testable.convertJsonToImagesData(json);
		assertThat(imageData).isNotNull();
		assertThat(imageData.hasImageData()).isFalse();
		assertThat(imageData.bytes()).isNotNull().hasSize(0);
		assertThat(imageData.getMediaType()).isEqualTo(MediaType.IMAGE_JPEG);
	}

	@EnableConfigurationProperties(ServerWebConfiguration.class)
	public static class TestConfig {

		@Bean
		public RequestExecutorImpl requestExecutor() {
			return new RequestExecutorImpl();
		}

		@Bean
		public IStormDRPCClient stormDRPCClient() {
			return mock(IStormDRPCClient.class);
		}

		@Bean
		public IMetricsManager metricsManager () {
			return mock(IMetricsManager.class);
		}
	}


}
