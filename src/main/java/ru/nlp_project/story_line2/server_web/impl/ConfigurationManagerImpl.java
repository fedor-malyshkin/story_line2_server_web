package ru.nlp_project.story_line2.server_web.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import ru.nlp_project.story_line2.config.ConfigurationException;
import ru.nlp_project.story_line2.config.YAMLConfigurationReader;
import ru.nlp_project.story_line2.server_web.IConfigurationManager;

public class ConfigurationManagerImpl implements IConfigurationManager {

	private MasterConfiguration masterConfiguration;
	protected File parentFile;

	public ConfigurationManagerImpl() {

	}

	public void initialize() {
		try {
			readConfiguration();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	private void readConfiguration()
			throws ConfigurationException, MalformedURLException, URISyntaxException {
		masterConfiguration = YAMLConfigurationReader.readConfigurationFromEnvironment(
				CONFIGURATION_SYSTEM_KEY, MasterConfiguration.class);
		String path = YAMLConfigurationReader
				.getConfigurationPathFromEnvironment(CONFIGURATION_SYSTEM_KEY);
		parentFile = new File(new URI(path).getPath()).getParentFile();
	}

	public MasterConfiguration getMasterConfiguration() {
		return masterConfiguration;
	}


}
