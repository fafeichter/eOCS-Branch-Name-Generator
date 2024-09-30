package com.fafeichter.eocs.branchnamegenerator.configuration;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the Jira REST client.
 * <p>
 * This configuration uses properties defined in {@link JiraProperties} to connect to the Jira API.
 */
@Configuration
public class JiraConfig {

    /**
     * Creates and configures a {@link JiraRestClient} bean using the provided {@link JiraProperties}.
     * The JiraRestClient allows interaction with Jira via the REST API.
     *
     * @param jiraProperties A configuration object holding the necessary Jira connection details such as
     *                       the Jira API URL and the API key.
     * @return An instance of {@link JiraRestClient} to interact with the Jira API.
     */
    @Bean
    public JiraRestClient jiraRestClient(JiraProperties jiraProperties) {
        // Create an authentication handler using the Jira API key
        JiraAuthenticationHandler handler = new JiraAuthenticationHandler(jiraProperties.apiKey());

        // Create a Jira REST client factory for asynchronous communication
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();

        // Build and return a JiraRestClient using the provided URL and authentication handler
        return factory.create(jiraProperties.url(), handler);
    }
}