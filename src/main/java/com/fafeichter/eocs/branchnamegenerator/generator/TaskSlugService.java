package com.fafeichter.eocs.branchnamegenerator.generator;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.fafeichter.eocs.branchnamegenerator.git.BranchTaskSlug;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskSlugService {

    private static final String PROMPT_TEMPLATE = """
            Generate a task slug to describe the context of the development task titled "{taskTitle}" using the
            following guidelines:
            
            - Limit the length to a maximum of {maxLength} characters.
            - Ensure the slug is not a full sentence.
            - Only use allowed characters: letters (a-z), digits, hyphens, and underscores.
            - Replace umlauts: "ä" → "ae", "ö" → "oe", "ü" → "ue"; avoid "ß" when possible.
            - All letters must be in lowercase.
            - Separate all words with hyphens.
            - Omit unnecessary words like prepositions, conjunctions, and fillers, unless they are critical to clarity.
            - Do not end the slug with "-stable".
            - The slug must be in English.
            - It is recommended, though not mandatory, to begin the slug with the relevant area of changes (e.g.,
              "incident", "workflow", "workstation").
            """;

    private final ChatModel chatModel;
    private final JiraIssueRestClient jiraIssueRestClient;

    public BranchTaskSlug generate(JiraIssueId jiraIssueId, Integer maxLength) {
        Issue issue = jiraIssueRestClient.getIssue(jiraIssueId);

        String prompt = PROMPT_TEMPLATE.replace("{taskTitle}", issue.getSummary())
                .replace("{maxLength}", maxLength.toString());

        String response = chatModel.call(prompt);

        return new BranchTaskSlug(response);
    }
}