# ADR-003 — Email Validation Refactor & Canonicalization

**Status:** Accepted — supersedes parts of ADR-002 related to the single mega-regex and adds canonicalization policy.  
**Date:** 2025-09-24  
**Context:** M1 — Identity & Access

## Context
ADR-002 introduced a single, complex regex with named groups and nested lookaheads to validate the `Email` Value Object (ASCII `dot-atom` local-part, LDH domain, FQDN, length limits). In practice, static analysis (SonarQube rules **S5843**, **S5860**, **S5998**) flagged the pattern as overly complex, at risk of catastrophic backtracking, and with unused named groups. We also need a clear policy for canonical storage to avoid duplicates such as `User@EXAMPLE.COM` vs `User@example.com`.

## Decision
Adopt a **hybrid validation** and **canonicalization** strategy:

1. **Pre-checks (code, not regex):**
  - `trim` the input.
  - Total length ≤ **254**.
  - Exactly one `@` and not at the ends.
  - Split into `local` and `domain`; `local.length ≤ 64`, `domain.length ≤ 253`.

2. **Local-part (ASCII dot-atom) — validate in code:**
  - Allow RFC `atext` chars and `.` as separator.
  - Reject leading/trailing `.` and `..` inside.
  - Optionally use a lightweight per-segment filter regex (no nested/lookahead constructs).
  - No `quoted-string`, no CFWS.

3. **Domain (LDH labels, FQDN) — validate in code:**
  - Require **at least one dot**; reject leading/trailing dot and `..`.
  - Split by `.` **preserving empty tokens**. For each label: length `1..63`, first/last are alphanumeric, middle chars are alnum or `-`.
  - Support IDN via **Punycode** (`xn--…`). No address-literals (`[IPv4]`, `[IPv6:…]`).

4. **Canonicalization (storage & equality):**
  - **Canonical form** = `local + "@" + lowercase(domain)` (domain is case-insensitive in DNS; local-part preserved).
  - `Email.value()` returns the canonical string.
  - `equals/hashCode` rely on canonical form.

5. **No named groups / no nested lookaheads:**
  - Replace the single mega-regex with small, readable checks and simple filters to satisfy SonarQube rules.

## Consequences
**Positive**
- Eliminates SonarQube issues:
  - **S5843** — overly complex regex → replaced with simple code checks.
  - **S5860** — unused named groups → no named groups.
  - **S5998** — stack overflow risk → no catastrophic backtracking paths.
- Better **maintainability** and **readability**; logic mirrors the spec.
- **Deterministic performance** (linear passes over strings).
- Reduced risk of **PII leaks** (consistent masking in errors/logs).
- Canonical storage prevents duplicates differing only by domain case.
- Atomized, domain-specific exceptions (e.g., illegal domain label, local-part length, double dots) that pinpoint failure causes, making defects faster to diagnose and safer to handle.

**Negative/Risks**
- Slightly more lines of code vs one regex.

## Migration
- Existing persisted values remain valid; new instances are stored in canonical form.
- DB uniqueness should target the canonical value (domain lowercased).

## Alternatives considered
- Keep a single mega-regex with atomic/possessive quantifiers — still trips **S5843** and hurts readability.
- Delegate to an external validator library — overkill for our restricted ASCII profile, misaligned with our ADR-driven domain invariants, and it undermines the project’s primary goal of learning by implementing the core logic ourselves.
- Lowercase the entire address — rejected due to theoretical case sensitivity of the local-part per RFC.

## References
- ADR-002 — Email Validation Strategy (superseded in parts)
- RFC 5321 / 5322 — SMTP and `addr-spec`
- RFC 5890–5891 — IDNA / Punycode
- SonarQube rules: S5843, S5860, S5998
