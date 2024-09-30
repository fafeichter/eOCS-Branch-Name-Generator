package com.fafeichter.eocs.branchnamegenerator.jira;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

/**
 * Configuration properties class for Jira settings.
 * <p>
 * This class holds the necessary properties to connect to the Jira API, such as the Jira URL and the API key.
 * It is automatically bound to configuration properties with the prefix "jira".
 */
@ConfigurationProperties(prefix = "jira")
@Validated
public record JiraProperties(
        @NotNull URI url, // The base URI for the Jira instance, cannot be null
        @NotEmpty String apiKey // The API key for authenticating Jira API requests, cannot be empty
) {

}