package com.fafeichter.eocs.branchnamegenerator.configuration;

import com.atlassian.httpclient.api.Request;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import lombok.RequiredArgsConstructor;

/**
 * Custom authentication handler for Jira API requests.
 * <p>
 * This class implements {@link AuthenticationHandler} to add the required authentication header
 * (Bearer token) to each outgoing Jira API request. The token is passed during object construction.
 */
@RequiredArgsConstructor
public class JiraAuthenticationHandler implements AuthenticationHandler {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    // The authentication token to be included in the Authorization header
    private final String token;

    /**
     * Configures the request by adding the "Authorization" header with the Bearer token.
     *
     * @param builder The request builder to which the Authorization header will be added.
     */
    @Override
    public void configure(Request.Builder builder) {
        // Add the Authorization header with the Bearer token
        builder.setHeader(AUTHORIZATION_HEADER, "Bearer " + token);
    }
}