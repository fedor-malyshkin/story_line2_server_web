package ru.nlp_project.story_line2.server_web.impl;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;

public class RequestExecutorImplTest {

	private RequestExecutorImpl testable;

	@Before
	public void setUp() {
		testable = new RequestExecutorImpl();
		IStormDRPCClient drpcClientMock = mock(IStormDRPCClient.class);
		testable.stormDRPCClient = drpcClientMock;
	}

	@Test
	public void testListSources() {
		testable.configuration = new ServerWebConfiguration();
		String list = testable.listSources();
		assertThat(list).isEqualTo("[ ]");
	}

}
