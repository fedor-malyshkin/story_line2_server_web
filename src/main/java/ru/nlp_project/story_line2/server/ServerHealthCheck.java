package ru.nlp_project.story_line2.server;

import com.codahale.metrics.health.HealthCheck;

public class ServerHealthCheck extends HealthCheck {

  public ServerHealthCheck() {}

  @Override
  protected Result check() throws Exception {
    return Result.healthy();
  }
}
