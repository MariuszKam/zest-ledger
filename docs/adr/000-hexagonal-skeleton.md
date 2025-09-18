# ADR-000: Hexagonal skeleton (domain / application / adapters-in / adapters-out / platform)

- **Status:** Accepted
- **Date:** 2025-09-18

## Context
Milestone M0 defines a minimal yet complete repository skeleton and CI quality/observability/security so each next vertical slice can pass the full DoD without fighting the infrastructure. 
The agreed architecture is Hexagonal with strict boundaries enforced by ArchUnit and verified in CI. Repository runs on JDK 21 with a multi-module Gradle layout: 
- `modules/domain`
- `modules/application`
- `modules/adapters-in/rest`
- `modules/adapters-out/db`
- `modules/platform`

plus:
- `docs/`
- `.github/workflows/`

This ADR is the single source of truth for naming, boundaries, and dependency rules.

## Decision
1) **Module split (physical folders)**
  - `modules/domain` — pure domain: Value Objects, Entities/Aggregates, **ports (interfaces)**, policies and invariants. **No** dependencies on frameworks or adapters.
  - `modules/application` — use-case implementations and orchestration; depends on domain ports.
  - `modules/adapters-in/rest` — controllers/DTO/mapping; HTTP/REST input; request validation and RAW→VO transformation.
  - `modules/adapters-out/db` — repositories/migrations for Postgres and other outputs.
  - `modules/platform` — shared bootstraps/starters, build conventions.

2) **Ports location and implementations**
  - Ports (interfaces) live **in `domain`**.
  - Implementations of ports live in `application` (use-cases) or in corresponding adapters (`adapters-out/*` for repositories/clients).
  - Adapters communicate with `application` **via domain ports**—no shortcuts.

3) **Dependency rules (enforced by ArchUnit)**
  - `domain` **must not** depend on `application` or any `adapters-*`.
  - `adapters-*` **must not** bypass `application`/domain ports.
  - **No cyclic** package dependencies.
  - Domain has **no** dependencies on 
Micrometer/OTel/Logback/Flyway/JDBC; instrumentation and I/O belong to adapters. Tests assert these rules.

4) **Package & naming conventions (examples)**
  - `com.zestledger.domain.<boundedcontext>..` — VOs, ports, domain model.
  - `com.zestledger.application.<usecase>..` — use-case implementations.
  - `com.zestledger.adapters.in.rest..` / `com.zestledger.adapters.out.db..` — inbound/outbound edges.
  - Suffixes: `*UseCase`, `*Port`, `*Repository`, `*Controller`, `*Mapper`.
  - Java 21 only (records/pattern matching where reasonable). VOs validate input in constructors/factories.

5) **CI/CD & DoD impact**
  - GitHub Actions pipeline stages: `build → unit → mutation → integration → package → SBOM/Dependency-Check → upload artifacts (JaCoCo, PIT, JFR, JMH, OpenAPI)`. Targets: JaCoCo ≥85%, PIT ≥70%. :contentReference[oaicite:9]{index=9}
  - Observability baseline and minimal security headers are exposed via the REST adapter (`/actuator/*`); domain stays framework-free.

6) **ADR numbering & style**
  - This document is **ADR-000** (M0 foundational decision).
  - Format: **Context / Decision / Consequences.
  - Further ADRs (e.g., quality gates) are numbered incrementally.

## Consequences
- **Pros:** clean domain, simpler tests (unit vs integration), replaceable adapters, consistent CI quality gates.
- **Enforcement:** ArchUnit rules block boundary violations on PR; failing build on rule breaks.
- **Costs:** initial structure/guardrails overhead; M0 keeps minimalism to avoid over-engineering.
- **Impact:** every vertical slice defines a port in `domain`, implements it in `application`, and adds relevant adapters; DoD requires artifacts (JaCoCo/PIT/JFR/JMH/SBOM) and contracts.
