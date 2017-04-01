package ru.nlp_project.story_line2.server_web.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryModel {
	@JsonProperty("name")
	public String name;
	@JsonProperty("id")
	public String id;

	public CategoryModel(String id, String name) {
		super();
		this.name = name;
		this.id = id;
	}

	public CategoryModel() {
		super();
	}

}
