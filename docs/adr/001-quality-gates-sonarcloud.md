
# ADR-001: Quality gates — SonarCloud primary, Spotless mandatory (no Checkstyle/PMD/SpotBugs)

- **Status:** Accepted
- **Date:** 2025-09-18

## Context
M0 requires a reliable, low-friction quality baseline so every vertical slice can pass the DoD consistently. Running multiple linters early (Checkstyle/PMD/SpotBugs) creates duplicated, noisy signals and slows delivery. SonarCloud provides native Java analysis (SonarJava) with one consistent rule engine, quality gate, and history. We will **not** run or import external analyzers (Checkstyle, PMD, SpotBugs) — SonarCloud is our single source of truth.

## Decision
1) **Primary gate: SonarCloud**
- SonarCloud acts as the single **quality gate on PRs**. The build **fails** if the gate fails.
- Scope: code smells, bugs, vulnerabilities, hotspots, duplication, and coverage on new code.

2) **Formatting & style: Spotless**
- **Spotless is mandatory** locally and in CI (`spotlessCheck`/`spotlessApply`).
- Consistent formatting is treated as a release-blocking issue.

3) **External static analyzers**
- **Disabled permanently** to reduce signal duplication and friction: we do **not** run Checkstyle/PMD/SpotBugs in CI and do **not** import their reports into Sonar.
- If extra rules are needed, we adjust them in Sonar (one source of truth).

4) **CI pipeline (must remain intact)**
- Stages: `build → unit → mutation → integration → package → SBOM/Dependency-Check → upload artifacts (JaCoCo, PIT, JFR, JMH, OpenAPI) → Sonar scan / Quality Gate`.
- Thresholds: **JaCoCo ≥ 85% line/branch**, **PIT ≥ 70% mutation score**.
- Dependency-Check: **no critical vulnerabilities**.
- Artifacts are published for traceability (coverage, PIT, JFR/JMH runs, OpenAPI).

5) **Developer workflow**
- Run Spotless before commit; keep the code warning-free under SonarLint in IDE.
- Each PR must pass: Spotless, unit, and Jacoco, ArchUnit test, **and** SonarCloud Quality Gate.

## Consequences
- **Pros:** one strong gate (SonarCloud) simplifies feedback and reduces noise; formatting is automated; DoD stays strict via tests, coverage, mutation, and security checks.
- **Trade-offs:** fewer rule-level checks specific to external linters; potential minor style drift beyond formatting.
- **Mitigations:** SonarCloud rule set + SonarLint in IDE; we can tune Sonar profiles as the codebase matures.
- **Impact on CI:** PRs fail fast on gate violations; artifacts and reports remain first-class citizens for auditing and learning.

