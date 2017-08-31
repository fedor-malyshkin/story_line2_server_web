package ru.nlp_project.story_line2.server_web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Объект-конфигурация (требуется фреймворком dropwizard.io)
 *
 * @author fedor
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerWebConfiguration extends Configuration {

	public static class MetricsConfiguration {

		// enabled: true
		@NotEmpty
		@JsonProperty(value = "enabled")
		public boolean enabled = false;

		// influxdb_host: ""
		@JsonProperty(value = "influxdb_host")
		public String influxdbHost;


		// influxdb_port: ""
		@JsonProperty(value = "influxdb_port")
		public int influxdbPort;

		// influxdb_db: ""
		@JsonProperty(value = "influxdb_db")
		public String influxdbDB;

		// influxdb_user: ""
		@JsonProperty(value = "influxdb_user")
		public String influxdbUser;

		// influxdb_password: ""
		@JsonProperty(value = "influxdb_password")
		public String influxdbPassword;

		// reporting_period: 30
		@NotEmpty
		@JsonProperty(value = "reporting_period")
		public int reportingPeriod;
	}


	public static class SourceConfiguration {

		@NotEmpty
		@JsonProperty(value = "name")
		public String name = "";

		@NotEmpty
		@JsonProperty(value = "title")
		public String title;


		@NotEmpty
		@JsonProperty(value = "title_short")
		public String titleShort;

	}


	@JsonProperty("drpc_host")
	public String drpcHost;
	@JsonProperty("drpc_port")
	public int drpcPort;

	@JsonProperty(value = "influxdb_metrics")
	public MetricsConfiguration metrics = new MetricsConfiguration();
	@JsonProperty(value = "sources")
	public List<SourceConfiguration> sources = new ArrayList<SourceConfiguration>();

}
