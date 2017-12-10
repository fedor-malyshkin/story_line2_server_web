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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// see: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests
// MockMvc usage see: https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework
@RunWith(SpringRunner.class)
// @SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@WebMvcTest(NewsHeaderResource.class)
public class NewsHeaderResourceTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private NewsHeaderResource resource;

	@Test
	public void listHeaders() throws Exception {
		mvc.perform(get("/news_headers/bnkomi.ru"))
				.andExpect(status().isOk());
		verify(resource).listHeaders(10, "bnkomi.ru", null);
	}

	@TestConfiguration
	static class Config extends DelegatingWebMvcConfiguration {

		@Override
		public void configurePathMatch(PathMatchConfigurer configurer) {
			configurer.setUseSuffixPatternMatch(false);
		}

	}
}