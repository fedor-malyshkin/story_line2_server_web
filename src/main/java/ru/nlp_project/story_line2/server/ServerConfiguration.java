package ru.nlp_project.story_line2.server;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class ServerConfiguration extends Configuration {
  private String configurationFile;

  @JsonProperty
  public String getConfigurationFile() {
    return configurationFile;
  }

  @JsonProperty
  public void setConfigurationFile(String configurationFile) {
    this.configurationFile = configurationFile;
  }

}
