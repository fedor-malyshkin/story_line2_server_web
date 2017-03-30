package ru.nlp_project.story_line2.server_web;

import com.codahale.metrics.health.HealthCheck;

/**
 * Объект для проверки здоровья сервиса (требуется фреймворком dropwizard.io)/
 * 
 * @author fedor
 *
 */
public class ServerWebHealthCheck extends HealthCheck {

	public ServerWebHealthCheck(ServerWebConfiguration configuration) {}

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
