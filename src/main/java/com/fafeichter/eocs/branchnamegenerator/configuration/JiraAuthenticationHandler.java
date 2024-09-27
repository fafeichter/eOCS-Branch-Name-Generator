package com.fafeichter.eocs.branchnamegenerator.configuration;

import com.atlassian.httpclient.api.Request;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JiraAuthenticationHandler implements AuthenticationHandler {
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final String token;

    @Override
    public void configure(Request.Builder builder) {
        builder.setHeader(AUTHORIZATION_HEADER, "Bearer " + token);
    }
}