package ru.nlp_project.story_line2.server_web.impl;

import javax.inject.Inject;

import ru.nlp_project.story_line2.server_web.IRequestExecutor;
import ru.nlp_project.story_line2.server_web.IStormDRPCClient;

public class RequestExecutorImpl implements IRequestExecutor {


	@Inject
	IStormDRPCClient drpcClient;

	public void initialize() {

	}

}
