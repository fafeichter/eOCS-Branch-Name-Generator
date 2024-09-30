package com.fafeichter.eocs.branchnamegenerator.generator;

import com.fafeichter.eocs.branchnamegenerator.git.Branch;
import com.fafeichter.eocs.branchnamegenerator.git.BranchPrefix;
import com.fafeichter.eocs.branchnamegenerator.git.BranchTaskSlug;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import static com.fafeichter.eocs.branchnamegenerator.git.Branch.MAX_ALLOWED_LENGTH;

/**
 * Service responsible for generating Git branch names based on Jira issue IDs.
 * The branch name is generated in the format: {prefix}/{jira-issue-id}-{task-slug}
 * <p>
 * This service implements {@link CommandLineRunner} to support CLI execution.
 */
@Service
@RequiredArgsConstructor
public class BranchNameGenerator implements CommandLineRunner {

    private final PrefixService prefixService;
    private final TaskSlugService taskSlugService;

    /**
     * Executes the branch name generation logic when the application is started from the command line.
     *
     * @param args The command-line arguments where the first argument is expected to be the Jira issue ID, and
     *             an optional second argument can be provided to specify a branch prefix (e.g., feature, bugfix).
     */
    @Override
    public void run(String... args) {
        if (args.length == 0) {
            System.out.println("Error: Please provide a Jira issue ID to generate a branch name.");
            return;
        }

        // Parse the provided Jira issue ID
        JiraIssueId jiraIssueId = JiraIssueId.from(args[0]);

        // Parse the provided branch prefix, if any, ignoring any additional options
        BranchPrefix providedPrefix = null;
        if (args.length > 1 && !args[1].startsWith("--")) {
            providedPrefix = BranchPrefix.fromAbbreviation(args[1]);
        }

        // Generate and print the branch name
        System.out.println(generateBranchName(jiraIssueId, providedPrefix).getName());
    }

    /**
     * Generates a branch name based on the provided Jira issue ID and optional prefix.
     * <p>
     * If no prefix is provided, the prefix will be determined based on the Jira issue type.
     * The branch name is limited by {@link Branch#MAX_ALLOWED_LENGTH}, and the task slug is truncated accordingly.
     *
     * @param jiraIssueId    The Jira issue ID used to generate the branch name.
     * @param providedPrefix An optional prefix specified via the command line.
     * @return A {@link Branch} object representing the generated branch name.
     */
    private Branch generateBranchName(JiraIssueId jiraIssueId, BranchPrefix providedPrefix) {
        // Use the provided prefix if available, otherwise generate based on the Jira issue ID
        BranchPrefix prefix = providedPrefix == null ? prefixService.generate(jiraIssueId) : providedPrefix;

        // Calculate the maximum allowed length for the task slug to ensure the branch name does not exceed the limit
        int taskSlugMaxLength = MAX_ALLOWED_LENGTH - prefix.getName().length() - 1 - jiraIssueId.getId().length();
        BranchTaskSlug branchTaskSlug = taskSlugService.generate(jiraIssueId, taskSlugMaxLength);

        // Return the complete branch name
        return new Branch(prefix, jiraIssueId, branchTaskSlug);
    }
}