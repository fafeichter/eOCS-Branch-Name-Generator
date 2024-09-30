package com.fafeichter.eocs.branchnamegenerator.configuration;

import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Configuration class for bypassing SSL certificate validation.
 * <p>
 * This configuration is typically used when connecting to internal services over HTTPS that do not have trusted
 * certificates (e.g., when using a VPN). It configures the JVM to accept all SSL certificates, essentially disabling
 * SSL validation.
 * <p>
 * <strong>Note:</strong> Disabling SSL validation is a security risk and should only be used in trusted environments.
 */
@Configuration
public class SpecialVpnConfig {

    static {
        // Initialize an SSL context that trusts all certificates
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to initialize SSL context", e);
        }

        // Define a trust manager that accepts all certificates
        TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                        // Trust all client certificates (no validation)
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                        // Trust all server certificates (no validation)
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        // Return an empty array indicating that all issuers are trusted
                        return new X509Certificate[0];
                    }
                }
        };

        // Initialize the SSL context with the trust managers
        try {
            sslContext.init(null, trustManagers, null);
        } catch (KeyManagementException e) {
            throw new RuntimeException("Failed to initialize SSL context with trust managers", e);
        }

        // Set the default SSL socket factory to use the newly configured SSL context
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }
}