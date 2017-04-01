package ru.nlp_project.story_line2.server_web;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.dropwizard.jackson.Jackson;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration.SourceConfiguration;



public class ServerWebConfigurationTest {
	private static final ObjectMapper MAPPER = Jackson.newObjectMapper(new YAMLFactory());

	@Test
	public void parseConfiguration_Base() throws Exception {
		ServerWebConfiguration configuration = MAPPER.readValue(
				fixture("ru/nlp_project/story_line2/server_web/test_server_web_config.yml"),
				ServerWebConfiguration.class);
		assertThat(configuration.drpcHost).isEqualTo("localhost");
		assertThat(configuration.drpcPort).isEqualTo(3333);

	}

	
	@Test
	public void parseConfiguration_Sources() throws Exception {
		ServerWebConfiguration configuration = MAPPER.readValue(
				fixture("ru/nlp_project/story_line2/server_web/test_server_web_config.yml"),
				ServerWebConfiguration.class);
		assertThat(configuration.sources.size()).isGreaterThan(1);
		SourceConfiguration source = configuration.sources.get(0);
		assertThat(source.name).isEqualToIgnoringCase("bnkomi.ru");
		assertThat(source.title).isEqualToIgnoringCase("Информационное агентство БНКоми");
		assertThat(source.titleShort).isEqualToIgnoringCase("БНКоми");

	}
}
