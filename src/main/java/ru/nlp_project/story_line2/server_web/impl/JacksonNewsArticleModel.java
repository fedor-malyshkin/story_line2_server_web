package ru.nlp_project.story_line2.server_web.impl;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_EMPTY)
public class JacksonNewsArticleModel {
	@JsonProperty("content")
	public String content;
	@JsonProperty("path")
	public String path;
	@JsonProperty("title")
	public String title;
	@JsonProperty("date")
	public Date date;
	@JsonProperty("processing_date")
	public Date processingDate;
	@JsonProperty("name")
	public String source;
	@JsonProperty("id")
	public String id;

	// headers
	public JacksonNewsArticleModel(String id, String source, Date date, String title) {
		super();
		this.title = title;
		this.date = date;
		this.source = source;
		this.id = id;
	}


	public JacksonNewsArticleModel() {
		super();
	}


	// articles
	public JacksonNewsArticleModel(String id, String source, Date date, String title,
			String content, String path, Date processingDate) {
		super();
		this.id = id;
		this.source = source;
		this.date = date;
		this.title = title;
		this.content = content;
		this.path = path;
		this.processingDate = processingDate;
	}



}
