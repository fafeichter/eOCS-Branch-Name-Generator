package com.fafeichter.eocs.branchnamegenerator.git;

import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;

/**
 * Represents a Git branch consisting of a prefix, Jira issue ID, and a task slug.
 * <p>
 * The branch name is generated in the format: {prefix}/{jira-issue-id}-{task-slug}.
 * The branch name has a maximum allowed length of {@value MAX_ALLOWED_LENGTH} characters.
 */
public record Branch(BranchPrefix prefix, JiraIssueId jiraIssueId, BranchTaskSlug branchTaskSlug) {

    /**
     * The maximum allowed length for the branch name.
     */
    public static final int MAX_ALLOWED_LENGTH = 60;

    /**
     * Constructs a {@link Branch} object and validates that the total branch name length does not exceed
     * the maximum allowed length.
     *
     * @param prefix         The prefix for the branch (e.g., "feature/", "bugfix/").
     * @param jiraIssueId    The Jira issue ID that is part of the branch name.
     * @param branchTaskSlug The task slug that describes the development task.
     * @throws IllegalArgumentException If the total branch name exceeds the maximum allowed length.
     */
    public Branch(BranchPrefix prefix, JiraIssueId jiraIssueId, BranchTaskSlug branchTaskSlug) {
        // Calculate the total length of the branch name
        int branchNameLength = prefix.getName().length() + 1 + jiraIssueId.getId().length()
                + branchTaskSlug.name().length();

        // Validate that the total branch name length does not exceed the allowed limit
        if (branchNameLength <= MAX_ALLOWED_LENGTH) {
            this.prefix = prefix;
            this.jiraIssueId = jiraIssueId;
            this.branchTaskSlug = branchTaskSlug;
        } else {
            // Throw an exception if the branch name length exceeds the maximum limit
            throw new IllegalArgumentException("Branch name length " + branchNameLength
                    + " exceeds maximum allowed length of " + MAX_ALLOWED_LENGTH + " characters.");
        }
    }

    /**
     * Returns the full branch name in the format: {prefix}/{jira-issue-id}-{task-slug}.
     *
     * @return The generated branch name as a string.
     */
    public String getName() {
        return prefix.getName() + "/" + jiraIssueId.getId() + "-" + branchTaskSlug.name();
    }
}