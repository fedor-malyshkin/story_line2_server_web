package ru.nlp_project.story_line2.server_web.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.nlp_project.story_line2.server_web.IMetricsManager;

// see: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests
// MockMvc usage see: https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework
@RunWith(SpringRunner.class)
@WebMvcTest(MaintenanceResource.class)
public class MaintenanceResourceTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private MaintenanceResource resource;

	@Test
	public void testExecuteCommandWithNoParams() throws Exception {
		mvc.perform(get("/maintenance?command=test"))
				.andExpect(status().isOk());
		verify(resource).execute("test", null, null);
	}

	@Test
	public void testExecuteCommandWithParams() throws Exception {
		mvc.perform(get("/maintenance?command=test&param1=123"))
				.andExpect(status().isOk());
		verify(resource).execute("test", "123", null);
	}


	@TestConfiguration
	static class Config {


		@Bean
		protected IMetricsManager metricsManager() {
			return mock(IMetricsManager.class);
		}


	}

}