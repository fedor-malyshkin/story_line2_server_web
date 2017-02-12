package ru.nlp_project.story_line2.server_web.impl;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.CacheControl;

public class CacheConfiguration {

	public CacheControl getCategories() {
		CacheControl result = new CacheControl();
		int seconds = (int) TimeUnit.MINUTES.toSeconds(5);
		result.setMaxAge(seconds);
		return result;
	}

	public CacheControl getSources() {
		CacheControl result = new CacheControl();
		int seconds = (int) TimeUnit.MINUTES.toSeconds(5);
		result.setMaxAge(seconds);
		return result;
	}


}
