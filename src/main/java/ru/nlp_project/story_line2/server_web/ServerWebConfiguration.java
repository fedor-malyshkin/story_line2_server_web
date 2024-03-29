package ru.nlp_project.story_line2.server_web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config")
public class ServerWebConfiguration {

	public String drpcHost;
	public int drpcPort;
	public MetricsConfiguration influxdbMetrics = new MetricsConfiguration();
	public List<SourceConfiguration> sources = new ArrayList<SourceConfiguration>();

	public String getDrpcHost() {
		return drpcHost;
	}

	public void setDrpcHost(String drpcHost) {
		this.drpcHost = drpcHost;
	}

	public int getDrpcPort() {
		return drpcPort;
	}

	public void setDrpcPort(int drpcPort) {
		this.drpcPort = drpcPort;
	}

	public MetricsConfiguration getInfluxdbMetrics() {
		return influxdbMetrics;
	}

	public void setInfluxdbMetrics(
			MetricsConfiguration influxdbMetrics) {
		this.influxdbMetrics = influxdbMetrics;
	}

	public List<SourceConfiguration> getSources() {
		return sources;
	}

	public void setSources(
			List<SourceConfiguration> sources) {
		this.sources = sources;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("ServerWebConfiguration{");
		sb.append("drpcHost='").append(drpcHost).append('\'');
		sb.append(", drpcPort=").append(drpcPort);
		sb.append(", influxdbMetrics=").append(influxdbMetrics);
		sb.append(", sources=").append(sources);
		sb.append('}');
		return sb.toString();
	}

	public static class MetricsConfiguration {

		public boolean enabled = false;
		public String influxdbHost;
		public int influxdbPort;
		public String influxdbDb;
		public String influxdbUser;
		public String influxdbPassword;
		public int reportingPeriod;
		public int logReportingPeriod;

		public int getLogReportingPeriod() {
			return logReportingPeriod;
		}

		public void setLogReportingPeriod(int logReportingPeriod) {
			this.logReportingPeriod = logReportingPeriod;
		}

		public String getInfluxdbHost() {
			return influxdbHost;
		}

		public void setInfluxdbHost(String influxdbHost) {
			this.influxdbHost = influxdbHost;
		}

		public int getInfluxdbPort() {
			return influxdbPort;
		}

		public void setInfluxdbPort(int influxdbPort) {
			this.influxdbPort = influxdbPort;
		}

		public String getInfluxdbDb() {
			return influxdbDb;
		}

		public void setInfluxdbDb(String influxdbDb) {
			this.influxdbDb = influxdbDb;
		}

		public String getInfluxdbUser() {
			return influxdbUser;
		}

		public void setInfluxdbUser(String influxdbUser) {
			this.influxdbUser = influxdbUser;
		}

		public String getInfluxdbPassword() {
			return influxdbPassword;
		}

		public void setInfluxdbPassword(String influxdbPassword) {
			this.influxdbPassword = influxdbPassword;
		}

		public int getReportingPeriod() {
			return reportingPeriod;
		}

		public void setReportingPeriod(int reportingPeriod) {
			this.reportingPeriod = reportingPeriod;
		}

		public boolean isEnabled() {

			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("MetricsConfiguration{");
			sb.append("enabled=").append(enabled);
			sb.append(", influxdbHost='").append(influxdbHost).append('\'');
			sb.append(", influxdbPort=").append(influxdbPort);
			sb.append(", influxdbDb='").append(influxdbDb).append('\'');
			sb.append(", influxdbUser='").append(influxdbUser).append('\'');
			sb.append(", influxdbPassword='").append(influxdbPassword).append('\'');
			sb.append(", reportingPeriod=").append(reportingPeriod);
			sb.append(", logReportingPeriod=").append(logReportingPeriod);
			sb.append('}');
			return sb.toString();
		}
	}

	public static class SourceConfiguration {

		public String name = "";
		public String title;
		public String titleShort;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getTitleShort() {
			return titleShort;
		}

		public void setTitleShort(String titleShort) {
			this.titleShort = titleShort;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder("SourceConfiguration{");
			sb.append("name='").append(name).append('\'');
			sb.append(", title='").append(title).append('\'');
			sb.append(", titleShort='").append(titleShort).append('\'');
			sb.append('}');
			return sb.toString();
		}
	}
}
