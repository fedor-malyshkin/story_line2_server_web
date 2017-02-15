package ru.nlp_project.story_line2.server_web.impl;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JacksonSourceModel {
	@JsonProperty("domain")
	public String domain;
	@JsonProperty("name")
	public String name;
	@JsonProperty("short_name")
	public String shortName;
	@JsonProperty("id")
	public String id;

	
	public JacksonSourceModel(String id, String domain, String shortName,  String name) {
		super();
		this.id = id;
		
		this.domain = domain;
		this.shortName = shortName;
		this.name = name;
	}


	public JacksonSourceModel() {
		super();
	}

}
