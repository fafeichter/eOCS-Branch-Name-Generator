package com.fafeichter.eocs.branchnamegenerator.jira;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.regex.Pattern;

/**
 * Represents a Jira issue ID and provides validation to ensure the ID follows the correct format.
 * <p>
 * A valid Jira issue ID follows the format: {PROJECT_KEY}-{ISSUE_NUMBER}, where:
 * - PROJECT_KEY is an uppercase string of 1 to 10 letters.
 * - ISSUE_NUMBER is a positive integer.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class JiraIssueId {

    // Regular expression pattern to validate Jira issue IDs (e.g., "ABC-123")
    private static final String VALID_JIRA_ISSUE_ID_REGEX = "^[A-Z]{1,10}-\\d+$";

    // The Jira issue ID value
    private final String id;

    /**
     * Factory method to create a {@link JiraIssueId} after validating the provided issue ID.
     *
     * @param issueId The Jira issue ID as a string (e.g., "ABC-123").
     * @return A new {@link JiraIssueId} instance if the issue ID is valid.
     * @throws IllegalArgumentException if the issue ID is invalid.
     */
    public static JiraIssueId from(String issueId) {
        if (isValidJiraIssueId(issueId)) {
            return new JiraIssueId(issueId);
        } else {
            throw new IllegalArgumentException("Invalid Jira issue ID: " + issueId);
        }
    }

    /**
     * Validates if the given Jira issue ID matches the expected format.
     * <p>
     * The format should be: {PROJECT_KEY}-{ISSUE_NUMBER}, where the project key is 1 to 10 uppercase letters,
     * and the issue number is a positive integer.
     *
     * @param jiraIssueId The Jira issue ID to validate.
     * @return {@code true} if the issue ID is valid, {@code false} otherwise.
     */
    private static boolean isValidJiraIssueId(String jiraIssueId) {
        return Pattern.compile(VALID_JIRA_ISSUE_ID_REGEX)
                .matcher(jiraIssueId)
                .matches();
    }
}