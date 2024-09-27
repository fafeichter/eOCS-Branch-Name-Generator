package com.fafeichter.eocs.branchnamegenerator.generator;

import com.atlassian.jira.rest.client.api.domain.IssueType;
import com.fafeichter.eocs.branchnamegenerator.git.BranchPrefix;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrefixService {

    private final JiraIssueRestClient jiraIssueRestClient;

    public BranchPrefix generate(JiraIssueId jiraIssueId) {
        IssueType issueType = jiraIssueRestClient.getIssue(jiraIssueId).getIssueType();

        if (issueType.getName().toLowerCase().contains("bug")) {
            return BranchPrefix.BUGFIX;
        } else {
            return BranchPrefix.getDefault();
        }
    }
}