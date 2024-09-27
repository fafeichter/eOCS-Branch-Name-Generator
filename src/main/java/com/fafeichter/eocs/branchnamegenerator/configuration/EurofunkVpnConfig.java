package com.fafeichter.eocs.branchnamegenerator.configuration;

import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class EurofunkVpnConfig {

    static {
        // Create SSL context to trust all certificates
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Define trust managers to accept all certificates
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
            // Method to check client's trust - accepting all certificates
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            }

            // Method to check server's trust - accepting all certificates
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            }

            // Method to get accepted issuers - returning an empty array
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        // Initialize SSL context with the defined trust managers
        try {
            sslContext.init(null, trustManagers, null);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }

        // Set the default SSL socket factory to use the custom SSL context
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    }
}