package com.fafeichter.eocs.branchnamegenerator.git;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the different prefixes for Git branches.
 * <p>
 * Each prefix has a full name and an abbreviation that can be used for identification.
 */
@Getter
@RequiredArgsConstructor
public enum BranchPrefix {

    FEATURE("feature", "f"),
    BUGFIX("bugfix", "b"),
    CHERRY_PICK("cherry-pick", "cp");

    // The full name of the branch prefix (e.g., "feature", "bugfix")
    private final String name;

    // The abbreviation used to represent the branch prefix (e.g., "f", "b", "cp")
    private final String abbreviation;

    /**
     * Returns the default branch prefix, which is {@link BranchPrefix#FEATURE}.
     *
     * @return The default branch prefix (FEATURE).
     */
    public static BranchPrefix getDefault() {
        return FEATURE;
    }

    /**
     * Retrieves a {@link BranchPrefix} based on the provided abbreviation.
     * If the abbreviation matches one of the defined prefixes, the corresponding prefix is returned.
     *
     * @param abbreviation The abbreviation used to look up the branch prefix (e.g., "f", "b", "cp").
     * @return The corresponding {@link BranchPrefix} if the abbreviation matches, or {@code null} if no match is found.
     */
    public static BranchPrefix fromAbbreviation(final String abbreviation) {
        for (final BranchPrefix prefix : values()) {
            if (prefix.abbreviation.equals(abbreviation)) {
                return prefix;
            }
        }
        return null; // Return null if no match is found
    }
}