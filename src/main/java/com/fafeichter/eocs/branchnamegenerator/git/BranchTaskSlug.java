package com.fafeichter.eocs.branchnamegenerator.git;

/**
 * Represents the task slug component of a Git branch name.
 * <p>
 * The task slug is a brief, descriptive identifier that provides context for the development task.
 * It is typically derived from the Jira issue title or task summary and follows specific formatting rules.
 */
public record BranchTaskSlug(String name) {

}