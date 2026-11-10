# Working rules for Claude

## Token-efficient working rules

### Before reading code
- If `graphify-out/graph.json` exists, query it first (`graphify query "<question>"`, `graphify explain "<Name>"`, `graphify path "<A>" "<B>"`) and open only the files it points to. If missing or stale after major changes, run `graphify update .` (code-only, no LLM cost).
- Use Grep/Glob with tight patterns before Read. Read only the needed line range of large files (offset/limit). Don't re-read a file already read or edited in this session.
- Check existing project helpers, models, and patterns before creating new ones.

### Large outputs (Headroom)
- User-scope MCP server `headroom` is configured (`headroom_compress`, `headroom_retrieve`, `headroom_stats`). Compress big command/Gradle/log/JSON outputs with `headroom_compress` before reasoning over them, and use `headroom_retrieve` only when exact detail is needed.
- The Headroom optimization proxy (`headroom proxy` + `ANTHROPIC_BASE_URL`) is NOT enabled; do not turn it on without asking.
- Builds and tests: show errors and failures only, not full logs.

### Doing the work (Ponytail)
- User-scope plugin `ponytail@ponytail` is enabled (`/ponytail-review`, `/ponytail-audit`). Follow Ponytail rules: smallest complete change, reuse existing code/helpers/patterns, no unrequested abstractions, options, dependencies, or "for later" code.
- Run independent tool calls in parallel. Don't spawn subagents for work that takes a few tool calls.
- Ask only when a decision is really the user's. Otherwise pick the sensible default and say so in one line.
- Don't load a skill or plugin unless the task needs it.
- Never put passwords, keys, or connection strings in new files; point to where they live.

### Answers
- Short: what changed (file links), how it was verified, what was not checked or is risky.
- No long explanations, no repeating code already written, no restating tool output.
- Reply in the user's language.

## Project facts
- Stack: Android app (Java 8, Android Gradle Plugin 7.4.2, `compileSdk 33`, `minSdk 23`, `targetSdk 33`, `viewBinding`, Retrofit 2.9 + Gson, Firebase Crashlytics/Analytics/Messaging, Google Maps/Location, Glide/Picasso).
- Main folders (`app/src/main/java/com/saidur/creatrix/unicorn/salessolution/`): `UI/`, `Network/`, `Database/`, `Model/`, `Adapters/`, `NormalAdapter/`, `Presentar/`, `Service/`, `UtilityHelper/`, `UtilsApp/`, `Approval/`, `Doctor/`, `webview/`, plus resources in `app/src/main/res/`.
- Local DB & API config: SQLite helpers in `Database/` (`DBMain.java`, `DatabaseInfomation.java`, `DB*Helper.java`); Retrofit clients in `Network/` (`RetrofitClientInstance.java`, `RetrofitOrderClientInstance.java`, `Api*.java`), reading base URLs from `UtilsApp/AppSetting.java`.
- Build (errors/quiet): `.\gradlew.bat assembleDebug -q` (or `.\gradlew.bat assembleDebug --console=plain`)
- Test: `.\gradlew.bat testDebugUnitTest --console=plain` (`app/src/test/`, `app/src/androidTest/`)
- Related repos: Naafco web backend (`D:\CSTL_Projects\Nafcoo\Naafco_Web`) serves API endpoints; do not modify it unless explicitly asked.

## graphify

This project has a knowledge graph at graphify-out/ with god nodes, community structure, and cross-file relationships.

Rules:
- When graphify-out/graph.json exists, run `graphify query "<question>"` before your first grep, search or source-file read for a codebase question, and put this rule in every subagent prompt that explores code. Read raw files only for lines the graph or an earlier read already located. Use `graphify path "<A>" "<B>"` for relationships and `graphify explain "<concept>"` for focused concepts. These return a scoped subgraph, usually much smaller than GRAPH_REPORT.md or raw grep output.
- If graphify-out/wiki/index.md exists, use it for broad navigation instead of raw source browsing.
- Read graphify-out/GRAPH_REPORT.md only for broad architecture review or when query/path/explain do not surface enough context.
- After modifying code, run `graphify update .` to keep the graph current (AST-only, no API cost).
- A committed graphify-out/graph.json is rewritten whole on every build, so a large diff to it in a pull request is expected. Do not flag it as churn or suggest skipping the graph rebuild or its hooks.
