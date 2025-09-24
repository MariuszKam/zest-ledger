# ADR-004 — FullName Policy (Normalization & Validation in Code)

**Status:** Accepted  
**Date:** 2025-09-24  
**Context:** M1 — Identity & Access

## Context
We need a robust `FullName` Value Object. Names are culturally complex, so imposing rigid “First/Last” structures is risky. Our goal is to store a canonical full name string and enforce minimal, language-agnostic invariants. Previous attempts to validate with a single complex regex triggered SonarQube warnings (e.g., S5998) and reduced maintainability.

## Decision
Adopt a simple, predictable policy with **code-based validation**:

1) **Canonicalization pipeline**  
   `trim` → **NFC** (Unicode normalization) → **collapse** horizontal whitespace (`\h+`) to a single space `" "`.

2) **Structure**
  - Canonical value must contain **at least two tokens** (words) separated by single spaces (after collapse).
  - We do **not** split/stored Given/Family Name in the domain model.

3) **Allowed characters (per token)**
  - Letters: any Unicode letter (`\p{L}`).
  - Optional internal separators: **`-`**, **`'`**, **`’`** (U+2019) between letters only.
  - A token **must start and end with a letter**; no two separators in a row.

4) **Length limit**
  - Maximum length **255 characters** checked **after trim** (before NFC/collapse).  
    *Rationale:* aligns with current implementation; total size is bounded early.

5) **Validation approach**
  - Perform validation **in code** (iterate code points), optionally preceded by a light character filter; avoid heavy regex patterns to eliminate backtracking risks and SonarQube findings.

## Consequences
**Positive**
- Predictable performance; no catastrophic regex backtracking (Sonar S5998 avoided).
- Clear, maintainable rules; easy to extend with additional separators/localization later.
- Canonical storage (`trim + NFC + collapse`) prevents duplicates that differ only by whitespace form.
- Tests are straightforward (positive/negative/boundary), good PIT/coverage.

**Trade-offs / Risks**
- No cultural/locale-specific parsing (Given/Family Name) — handled heuristically in accessors if needed.
- Length is enforced after trim (pre-NFC/collapse); if we ever need a post-canonicalization limit, we’ll update this ADR.

## Testing
- Positive: multiple tokens, Unicode letters, internal `-`/`'`/`’`, three-word names.
- Negative: single token, digits/punctuation, leading/trailing separators in a token, double separators, EN DASH (U+2013).
- Boundary: exactly 255 chars after trim (accept), 256 (reject).
- Ensure NFC normalization and whitespace collapse behavior.

## Alternatives considered
- **Single mega-regex** with possessive/atomic groups — still trips tooling/maintainability concerns.
- **External name parser** — overkill for M1 scope; inconsistent across locales.
- **Split into GivenName/FamilyName VOs** — inaccurate globally; introduces false invariants.

## Migration
- Existing values remain valid if they pass the new pipeline. Storage uses canonical form only.

