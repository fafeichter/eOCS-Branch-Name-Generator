package com.fafeichter.eocs.branchnamegenerator.generator;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.fafeichter.eocs.branchnamegenerator.git.BranchTaskSlug;
import com.fafeichter.eocs.branchnamegenerator.jira.JiraIssueId;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

/**
 * Service responsible for generating a task slug based on the title of a Jira issue.
 * <p>
 * The task slug is generated using a predefined set of rules and guidelines. This service interacts
 * with a language model (via {@link ChatModel}) to generate a concise and context-aware task slug
 * for Git branch naming purposes.
 */
@Service
public class TaskSlugService {

    // Template for generating a task slug using predefined guidelines
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

    // The language model used to generate the task slug
    private final ChatModel chatModel;

    // Jira client used to retrieve issue details such as the issue type
    private final IssueRestClient jiraIssueRestClient;

    /**
     * Constructor that initializes the {@link IssueRestClient} using the provided {@link JiraRestClient}.
     *
     * @param chatModel      The AI language model used to generate the task slug.
     * @param jiraRestClient The Jira REST client used to retrieve the issue client.
     */
    public TaskSlugService(ChatModel chatModel, JiraRestClient jiraRestClient) {
        this.chatModel = chatModel;
        this.jiraIssueRestClient = jiraRestClient.getIssueClient();
    }

    /**
     * Generates a task slug based on the title of the given Jira issue. The slug is created
     * following a predefined set of rules and must adhere to the specified maximum length.
     *
     * @param jiraIssueId The ID of the Jira issue to generate the task slug for.
     * @param maxLength   The maximum allowed length of the generated task slug.
     * @return A {@link BranchTaskSlug} containing the generated slug.
     */
    public BranchTaskSlug generate(JiraIssueId jiraIssueId, Integer maxLength) {
        // Retrieve the Jira issue to get the issue summary (title)
        Issue issue = jiraIssueRestClient.getIssue(jiraIssueId.getId()).claim();

        // Replace placeholders in the prompt template with the task title and max length
        String prompt = PROMPT_TEMPLATE.replace("{taskTitle}", issue.getSummary())
                .replace("{maxLength}", maxLength.toString());

        // Call the chat model to generate the task slug based on the prompt
        String response = chatModel.call(prompt);

        // Return the generated task slug as a BranchTaskSlug object
        return new BranchTaskSlug(response);
    }
}