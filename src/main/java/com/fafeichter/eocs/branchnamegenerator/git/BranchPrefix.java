package com.fafeichter.eocs.branchnamegenerator.git;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BranchPrefix {
    FEATURE("feature", "f"),
    BUGFIX("bugfix", "b"),
    CHERRY_PICK("cherry-pick", "cp");

    private final String name;
    private final String abbreviation;

    public static BranchPrefix getDefault() {
        return FEATURE;
    }

    public static BranchPrefix fromAbbreviation(final String abbreviation) {
        for (final BranchPrefix prefix : values()) {
            if (prefix.abbreviation.equals(abbreviation)) {
                return prefix;
            }
        }

        return null;
    }
}