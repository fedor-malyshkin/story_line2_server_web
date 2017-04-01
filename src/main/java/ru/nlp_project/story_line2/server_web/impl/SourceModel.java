package ru.nlp_project.story_line2.server_web.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceModel {
	@JsonProperty("name")
	public String name;
	@JsonProperty("title")
	public String title;
	@JsonProperty("title_short")
	public String titleShort;

	
	public SourceModel(String name, String title, String titleShort) {
		super();
		this.name = name;
		this.title = title;
		this.titleShort = titleShort;
	}




	public SourceModel() {
		super();
	}

}
