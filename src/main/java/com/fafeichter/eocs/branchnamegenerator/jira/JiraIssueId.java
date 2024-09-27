package com.fafeichter.eocs.branchnamegenerator.jira;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.regex.Pattern;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class JiraIssueId {

    // regular expression pattern for valid Jira issue IDs
    private static final String VALID_JIRA_ISSUE_ID_REGEX = "^[A-Z]{1,10}-\\d+$";

    private final String id;

    public static JiraIssueId from(String issueId) {
        if (isValidJiraIssueId(issueId)) {
            return new JiraIssueId(issueId);
        } else {
            throw new IllegalArgumentException("Invalid Jira issue ID: " + issueId);
        }
    }

    private static boolean isValidJiraIssueId(String jiraIssueId) {
        return Pattern.compile(VALID_JIRA_ISSUE_ID_REGEX)
                .matcher(jiraIssueId)
                .matches();
    }
}