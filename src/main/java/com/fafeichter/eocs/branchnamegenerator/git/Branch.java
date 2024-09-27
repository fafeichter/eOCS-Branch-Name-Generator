package com.fafeichter.eocs.branchnamegenerator.git;

import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;

public record Branch(BranchPrefix prefix, JiraIssueId jiraIssueId, BranchTaskSlug branchTaskSlug) {

    public static final int MAX_ALLOWED_LENGTH = 60;

    public Branch(BranchPrefix prefix, JiraIssueId jiraIssueId, BranchTaskSlug branchTaskSlug) {
        int branchNameLength = prefix.getName().length() + 1 + jiraIssueId.getId().length()
                + branchTaskSlug.name().length();

        if (branchNameLength <= MAX_ALLOWED_LENGTH) {
            this.prefix = prefix;
            this.jiraIssueId = jiraIssueId;
            this.branchTaskSlug = branchTaskSlug;
        } else {
            throw new IllegalArgumentException("Prefix name length " + branchNameLength + " exceeded maximum " +
                    "allowed length " + MAX_ALLOWED_LENGTH);
        }
    }

    public String getName() {
        return prefix.getName() + "/" + jiraIssueId.getId() + "-" + branchTaskSlug.name();
    }
}