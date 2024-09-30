# eOCS-Branch-Name-Generator

A **command-line tool (CLI)** that generates a **Git branch name** in the format `{prefix}/{jira-issue-id}-{task-slug}`
based on a **Jira issue**.

This tool is designed for use in the development of the **eurofunk Operations Center Suite (eOCS)**. 👨‍💻

## Usage

```zsh
 j [JIRA-ISSUE-ID] [OPTIONS]
 
 Options:
    f      Uses the "feature/" prefix.
    b      Uses the "bugfix/" prefix.
    cp     Uses the "cherry-pick/" prefix.
```

If no prefix is specified, and the Jira issue type contains the word "bug", the `b` option is applied. Otherwise, the
`f` option is used by default.

## Build

To build the project, run the following command:

```bash
./gradlew nativeCompile
```

The compiled binary will be located in `build/native/nativeCompile/`.

## Installation

To install, add the following alias to your shell configuration file (e.g., `.zshrc`, `.bashrc`, etc.):

```zsh
alias j='() {
    branch_name=$(/path/to/eOCS-Branch-Name-Generator $1 $2 \
      --jira.api-key=YOUR_JIRA_API_KEY \
      --spring.ai.openai.api-key=YOUR_OPENAI_API_KEY 2>&1)

    if [ $? -eq 0 ]; then
        if git rev-parse --verify "$branch_name" >/dev/null; then
            git checkout "$branch_name"
        else
            git checkout -b "$branch_name"
        fi
    else
        echo "$branch_name"
    fi
}'
```