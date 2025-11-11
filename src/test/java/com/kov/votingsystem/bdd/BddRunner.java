package com.kov.votingsystem.bdd;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

//BDD Cucumber test runner для запуска feature-файлов через JUnit Platform.
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")// связь с voting.feature
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.kov.votingsystem.bdd")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-report.html")
public class BddRunner {
}
