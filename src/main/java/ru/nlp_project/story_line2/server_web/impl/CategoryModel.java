package ru.nlp_project.story_line2.server_web.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JacksonCategoryModel {
	@JsonProperty("name")
	public String name;
	@JsonProperty("id")
	public String id;

	public JacksonCategoryModel(String id, String name) {
		super();
		this.name = name;
		this.id = id;
	}

	public JacksonCategoryModel() {
		super();
	}

}
