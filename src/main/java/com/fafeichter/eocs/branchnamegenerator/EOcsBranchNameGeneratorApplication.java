package com.fafeichter.eocs.branchnamegenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class EOcsBranchNameGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EOcsBranchNameGeneratorApplication.class, args);
    }
}