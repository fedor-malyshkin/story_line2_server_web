package ru.nlp_project.story_line2.server_web.resources;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

// see: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests
// MockMvc usage see: https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-framework
@RunWith(SpringRunner.class)
@WebMvcTest(NewsArticleResource.class)
public class NewsArticleResourceTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private NewsArticleResource resource;


	@Ignore
	@Test
	public void testGetNewsArticleImageDataById_ThereIsImageDate() {

	}

	@Ignore
	@Test
	public void testGetNewsArticleImageDataById_ThereIsNoImageDate() {
	}

	@Ignore
	@Test
	public void testGetNewsArticleImageDataById_ImageOperation() {
	}
}