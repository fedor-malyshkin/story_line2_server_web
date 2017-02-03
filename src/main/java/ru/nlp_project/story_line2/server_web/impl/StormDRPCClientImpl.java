package ru.nlp_project.story_line2.server_web.impl;


import java.util.Collections;

import javax.inject.Inject;

import org.apache.storm.thrift.transport.TTransportException;
import org.apache.storm.utils.DRPCClient;

import ru.nlp_project.story_line2.server_web.IConfigurationManager;
import ru.nlp_project.story_line2.server_web.IConfigurationManager.MasterConfiguration;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;

public class StormDRPCClientImpl implements IStormDRPCClient {

	@Inject
	public IConfigurationManager configurationManager;
	private DRPCClient drpcClient;
	private boolean initilized;

	@Inject
	public StormDRPCClientImpl() {}

	public void initialize() {
		MasterConfiguration configuration = configurationManager.getMasterConfiguration();
		try {
			drpcClient = new DRPCClient(Collections.emptyMap(), configuration.drpcHost,
					configuration.drpcPort);
			initilized = true;
		} catch (TTransportException e) {
			initilized = false;
		}
	}

}
