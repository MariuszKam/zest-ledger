# ZestLedger
*Energy for your numbers.* ⚡️📒⚡️

## About
Public learning project (Java 21). I run small weekend sprints and learn by building.
Inspired by Clean Architecture, DDD, and TDD.

## Status
**M0 — Foundation in progress. What’s done (in a nutshell):**
- Multi‑module skeleton in place (`domain`, `application`, `adapters-in/rest`, `adapters-out/db`, `platform`, `architecture-tests`).
- Shared Gradle conventions: Spotless (AOSP), JaCoCo, PIT, OWASP Dependency‑Check, CycloneDX (via `build-logic/`).
- CI set up: base `ci.yaml` (build/tests/mutation) and separate `security.yaml` (Dependency‑Check + SBOM).
- PR hygiene: Conventional Commits, semantic‑PR check, commitlint + husky.
- Tooling: Java 21 toolchain, version catalog, baseline `.gitignore`.

Service not runnable yet — Spring Boot/Actuator wiring planned next.
## How to run
Not runnable yet. Instructions will appear once the first endpoints are added.

## Roadmap (summary)
- M0: Repo & pipeline foundations
- M1: Identity & Access (TBD)

## Conventional Commits
- `feat(rest): add create user endpoint`
- `fix(domain): handle invalid email`
- `docs(readme): seed project overview`
- `ci(gha): add mutation testing job`
- `chore(repo): add base .gitignore`

## License
TBD
