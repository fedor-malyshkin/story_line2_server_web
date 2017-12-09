package ru.nlp_project.story_line2.server_web;

public interface IMetricsManager {

	String METRICS_SUFFIX = ".server_web";

	void initialize();

	void shutdown();
}
