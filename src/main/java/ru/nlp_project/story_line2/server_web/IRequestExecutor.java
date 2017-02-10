package ru.nlp_project.story_line2.server_web;

public interface IRequestExecutor {

	default String getCategories() {
		return "";
	}

}
