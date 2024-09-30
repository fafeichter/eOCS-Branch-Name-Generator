package com.fafeichter.eocs.branchnamegenerator.generator;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.fafeichter.eocs.branchnamegenerator.git.BranchPrefix;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;
import org.springframework.stereotype.Service;

/**
 * Service responsible for determining the appropriate Git branch prefix based on the Jira issue type.
 * <p>
 * The branch prefix is determined by querying Jira for the issue type. If the issue type name contains the word "bug",
 * the prefix is set to "bugfix". Otherwise, the default prefix (usually "feature") is used.
 */
@Service
public class PrefixService {

    private final IssueRestClient jiraIssueRestClient;

    /**
     * Constructor to initialize the {@link IssueRestClient} using the provided {@link JiraRestClient}.
     *
     * @param jiraRestClient The Jira REST client used to retrieve the issue client.
     */
    public PrefixService(JiraRestClient jiraRestClient) {
        this.jiraIssueRestClient = jiraRestClient.getIssueClient();
    }

    /**
     * Generates the appropriate branch prefix based on the type of the Jira issue.
     * <p>
     * If the issue type contains the word "bug", the "bugfix" prefix is applied. Otherwise, the default prefix is used.
     *
     * @param jiraIssueId The ID of the Jira issue for which the branch prefix will be generated.
     * @return The {@link BranchPrefix}, either BUGFIX if the issue is a bug, or the default prefix.
     */
    public BranchPrefix generate(JiraIssueId jiraIssueId) {
        // Fetch the issue type from Jira using the issue ID
        IssueType issueType = jiraIssueRestClient.getIssue(jiraIssueId.getId()).claim().getIssueType();

        // Check if the issue type contains the word "bug" and return the appropriate branch prefix
        if (issueType.getName().toLowerCase().contains("bug")) {
            return BranchPrefix.BUGFIX;
        } else {
            return BranchPrefix.getDefault();
        }
    }
}