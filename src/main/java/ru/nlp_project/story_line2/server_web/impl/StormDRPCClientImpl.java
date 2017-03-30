package ru.nlp_project.story_line2.server_web.impl;


import java.util.Collections;

import javax.inject.Inject;

import org.apache.storm.thrift.transport.TTransportException;
import org.apache.storm.utils.DRPCClient;

import ru.nlp_project.story_line2.server_web.IStormDRPCClient;
import ru.nlp_project.story_line2.server_web.ServerWebConfiguration;

public class StormDRPCClientImpl implements IStormDRPCClient {

	@Inject
	ServerWebConfiguration configurationManager;

	private boolean initilized;
	private DRPCClient drpcClient;

	@Inject
	public StormDRPCClientImpl() {}

	public void initialize() {
		try {
			drpcClient = new DRPCClient(Collections.emptyMap(), configurationManager.drpcHost,
					configurationManager.drpcPort);
			initilized = true;
		} catch (TTransportException e) {
			initilized = false;
		}
	}

}
