package ru.nlp_project.story_line2.server_web.impl;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.CacheControl;

public class CacheConfiguration {


	public CacheControl listCategories() {
		CacheControl result = new CacheControl();
		int seconds = (int) TimeUnit.HOURS.toSeconds(1);
		result.setMaxAge(seconds);
		return result;
	}

	public CacheControl listSources() {
		CacheControl result = new CacheControl();
		int seconds = (int) TimeUnit.MINUTES.toSeconds(5);
		result.setMaxAge(seconds);
		return result;
	}

	public CacheControl getNewsArticleById() {
		CacheControl result = new CacheControl();
		// для статей - час
		int seconds = (int) TimeUnit.HOURS.toSeconds(1);
		result.setMaxAge(seconds);
		return result;
	}

	public CacheControl listNewsHeaders() {
		CacheControl result = new CacheControl();
		int seconds = (int) TimeUnit.MINUTES.toSeconds(1);
		result.setMaxAge(seconds);
		return result;
	}


}
