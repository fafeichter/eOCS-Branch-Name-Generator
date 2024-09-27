package com.fafeichter.eocs.branchnamegenerator.configuration;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JiraConfig {

    @Bean
    public JiraRestClient jiraRestClient(JiraProperties jiraProperties) {
        JiraAuthenticationHandler handler = new JiraAuthenticationHandler(jiraProperties.apiKey());
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();

        return factory.create(jiraProperties.url(), handler);
    }
}