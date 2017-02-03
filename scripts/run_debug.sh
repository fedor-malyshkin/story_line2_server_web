#!/bin/sh
gradle shadowJar
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000 -jar build/libs/server_web-*-all.jar server src/test/resources/ru/nlp_project/story_line2/server_web/server_web_config.yml

