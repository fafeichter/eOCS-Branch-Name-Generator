package com.fafeichter.eocs.branchnamegenerator.jira;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import org.springframework.stereotype.Component;

/**
 * Wrapper class for the Jira {@link IssueRestClient}, responsible for retrieving Jira issues by their ID.
 * <p>
 * This component interacts with the Jira REST API to fetch issue details using the provided {@link JiraIssueId}.
 */
@Component
public class JiraIssueRestClient {

    private final IssueRestClient issueRestClient;

    /**
     * Constructor that initializes the {@link IssueRestClient} using the provided {@link JiraRestClient}.
     *
     * @param restClient The Jira REST client used to retrieve the issue client.
     */
    public JiraIssueRestClient(JiraRestClient restClient) {
        this.issueRestClient = restClient.getIssueClient();
    }

    /**
     * Retrieves a Jira issue by its ID.
     * <p>
     * This method makes a call to the Jira API to fetch the issue details associated with the provided {@link JiraIssueId}.
     *
     * @param jiraIssueId The ID of the Jira issue to retrieve.
     * @return The {@link Issue} object containing details of the Jira issue.
     */
    public Issue getIssue(JiraIssueId jiraIssueId) {
        // Retrieve the issue from Jira and block until the result is available (claim())
        return issueRestClient.getIssue(jiraIssueId.getId()).claim();
    }
}