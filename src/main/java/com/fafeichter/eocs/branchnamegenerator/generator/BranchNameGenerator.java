package com.fafeichter.eocs.branchnamegenerator.generator;

import com.fafeichter.eocs.branchnamegenerator.git.Branch;
import com.fafeichter.eocs.branchnamegenerator.git.BranchPrefix;
import com.fafeichter.eocs.branchnamegenerator.git.BranchTaskSlug;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import static com.fafeichter.eocs.branchnamegenerator.git.Branch.MAX_ALLOWED_LENGTH;

@Service
@RequiredArgsConstructor
public class BranchNameGenerator implements CommandLineRunner {

    private final PrefixService prefixService;
    private final TaskSlugService taskSlugService;

    @Override
    public void run(String... args) {
        if (args.length == 0) {
            System.out.println("Please provide a Jira issue ID in order to generate a branch name!");
            return;
        }

        JiraIssueId jiraIssueId = JiraIssueId.from(args[0]);
        BranchPrefix providedPrefix = null;

        if (args.length > 1 && !args[1].startsWith("--")) {
            providedPrefix = BranchPrefix.fromAbbreviation(args[1]);
        }

        System.out.println(generateBranchName(jiraIssueId, providedPrefix).getName());
    }

    private Branch generateBranchName(JiraIssueId jiraIssueId, BranchPrefix providedPrefix) {
        BranchPrefix prefix = providedPrefix == null ? prefixService.generate(jiraIssueId) : providedPrefix;

        int taskSlugMaxLength = MAX_ALLOWED_LENGTH - prefix.getName().length() - 1 - jiraIssueId.getId().length();
        BranchTaskSlug branchTaskSlug = taskSlugService.generate(jiraIssueId, taskSlugMaxLength);

        return new Branch(prefix, jiraIssueId, branchTaskSlug);
    }
}