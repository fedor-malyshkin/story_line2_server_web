package ru.nlp_project.story_line2.server_web.impl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import ru.nlp_project.story_line2.server_web.IRequestExecutor.IImageData;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;

public class RequestExecutorImplTest {

	private RequestExecutorImpl testable;

	@Before
	public void setUp() {
		testable = new RequestExecutorImpl(configurationManager);
		IStormDRPCClient drpcClientMock = mock(IStormDRPCClient.class);
		//testable.stormDRPCClient = drpcClientMock;
	}

	@Test
	public void testListSources() {
		testable.configuration = new ServerWebConfiguration();
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
		assertThat(imageData.getMediaType()).isEqualTo("image/jpeg");
	}

	@Test
	public void testConvertJsonToImagesData_NoBytes() {
		String json = "{\"image_url\":\"url.jpeg\", \"image_data\":\"\"}";
		IImageData imageData = testable.convertJsonToImagesData(json);
		assertThat(imageData).isNotNull();
		assertThat(imageData.hasImageData()).isFalse();
		assertThat(imageData.bytes()).isNotNull().hasSize(0);
		assertThat(imageData.getMediaType()).isEqualTo("image/jpeg");
	}


}
