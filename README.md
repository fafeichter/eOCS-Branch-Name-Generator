# eOCS-Branch-Name-Generator

CLI tool that generates a Git branch name based on a Jira issue.

This is utilized for the development of the **eurofunk Operations Center Suite (eOCS)**. 👨‍💻

## Build

```bash
./gradlew nativeCompile
```

The binary is located in _build/native/nativeCompile/_.

## Installation

Put this in your Zsh configuration:

```zsh
alias j='(){ git checkout -b $(<PATH>/eOCS-Branch-Name-Generator $1 $2 -jira.api-key=<JIRA_PERSONAL_ACCESS_TOKEN> 
--spring.ai.openai.api-key=<OPEN_AI_API_KEY>)}'
```

Replace _PATH_, _JIRA_PERSONAL_ACCESS_TOKEN_ and _OPEN_AI_API_KEY_.

## Usage

```zsh
j EOCS-1234 [f] [b] [cp] # this generates a branch name and checks out a new branch
```

The second parameter is the optional **prefix**:

- **b** = bugfix
- **f** = feature
- **cp** = cherry-pick

If the optional parameter is **not set** the following **logic** is applied;

1. If the issue's **type** contains **"bug"** → **bugfix**.
2. Otherwise → **feature**.
