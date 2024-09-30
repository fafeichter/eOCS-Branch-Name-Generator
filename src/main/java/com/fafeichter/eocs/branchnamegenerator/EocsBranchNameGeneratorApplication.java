package com.fafeichter.eocs.branchnamegenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * The main entry point for the eOCS-Branch-Name-Generator application.
 * <p>
 * This class configures and runs the Spring Boot application, which is responsible for generating Git branch names
 * based on Jira issues. It uses Spring Boot's auto-configuration and scans for configuration properties.
 */
@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class EocsBranchNameGeneratorApplication {

    /**
     * The main method that launches the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application at startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(EocsBranchNameGeneratorApplication.class, args);
    }
}