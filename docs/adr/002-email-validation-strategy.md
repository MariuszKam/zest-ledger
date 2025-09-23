# ADR 002 — Email Validation Strategy

## Context
We need a predictable, RFC-friendly way to validate email addresses for the `Email` Value Object in M1 (Identity & Access). The solution should interoperate on the public Internet, be easy to test (TDD), and avoid legacy SMTP quirks. We deliberately exclude Unicode local-parts (EAI) for now.

## Decision
Adopt a strict ASCII profile:
- **Local-part:** `dot-atom` with full RFC `atext`; no `quoted-string`, no CFWS.
- **Domain:** DNS LDH labels (letters/digits/hyphen), each 1–63 chars, not starting/ending with `-`; require **FQDN** (≥1 dot).
- **No address-literals** (`[IPv4]`, `[IPv6:…]`).
- **Length limits:** `local ≤ 64`, each label `≤ 63`, `domain ≤ 253`, whole `addr-spec ≤ 254` (includes `@`).

**Chosen regex:**
```regexp
^(?=.{1,254}$)(?<email>(?=.{1,64}@)(?<local>[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*)@(?=.{1,253}$)(?<domain>[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?(\.[A-Za-z0-9](?:[A-Za-z0-9-]{0,61}[A-Za-z0-9])?)+))$
```

## Consequences
**Positive**
- Clear, interoperable contract for `Email` VO.
- Simple to cover with parameterized tests and mutation testing.
- Supports IDN via **Punycode** (`xn--…`); no brittle TLD heuristics.

**Negative / Risks**
- No Unicode local-part (EAI) and no address-literals.
- *Mitigation:* future ADR to enable EAI; optional profile to allow address-literals if needed.


## References (normative)
- RFC 5321 — Simple Mail Transfer Protocol (syntax & length constraints)
- RFC 5322 — Internet Message Format (`addr-spec`, `dot-atom`, `atext`)
- RFC 5890–5891 — IDNA / Punycode for internationalized domain names
- RFC 6531/6532 — SMTPUTF8 / Internationalized Email (deferred)
