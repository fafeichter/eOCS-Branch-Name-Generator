package com.fafeichter.eocs.branchnamegenerator.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import org.springframework.stereotype.Component;

@Component
public class JiraIssueRestClient {

    private final IssueRestClient issueRestClient;

    public JiraIssueRestClient(JiraRestClient restClient) {
        this.issueRestClient = restClient.getIssueClient();
    }

    public Issue getIssue(JiraIssueId jiraIssueId) {
        return issueRestClient.getIssue(jiraIssueId.getId())
                .claim();
    }
}