package com.fafeichter.eocs.branchnamegenerator.jira;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@ConfigurationProperties(prefix = "jira")
@Validated
public record JiraProperties(@NotNull URI url, @NotEmpty String apiKey) {

}