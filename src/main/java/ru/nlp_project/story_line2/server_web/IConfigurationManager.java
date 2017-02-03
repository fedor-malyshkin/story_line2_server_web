package ru.nlp_project.story_line2.server_web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface IConfigurationManager {

	public final static String CONFIGURATION_SYSTEM_KEY =
			"ru.nlp_project.story_line2.server_web.config";

	// To ignore any unknown properties in JSON input without exception:
	@JsonIgnoreProperties(ignoreUnknown = true)
	class MasterConfiguration {

		@JsonProperty("server_web.drpc.host")
		public String drpcHost;
		@JsonProperty("server_web.drpc.port")
		public int drpcPort;
	}


	MasterConfiguration getMasterConfiguration();
}
