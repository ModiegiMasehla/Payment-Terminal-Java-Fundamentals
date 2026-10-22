# GitLab setup for Section 1 (Q1.2, Q1.3) and the wiki (Q4.3)

This file is a ready-to-copy plan. The board, tickets and wiki page must be created in your own GitLab project.

## Q1.2 — Taskboard lanes (do NOT keep the default Open / Closed lists)

Create these lists in this order, from left to right:

| Lane | Label | Meaning |
|---|---|---|
| 1. Backlog | `backlog` | Written down, not yet refined or committed to |
| 2. Ready for Development | `ready-dev` | Refined: clear user story and acceptance criteria; a developer can pick it up |
| 3. In Development | `in-dev` | Being coded on a short-lived branch (or straight on trunk behind a flag), with its tests |
| 4. Code Review | `review` | Merge request open; a teammate is reviewing the change |
| 5. Ready for Testing | `ready-test` | Reviewed and merged to trunk; waiting for integration/acceptance testing |
| 6. In Testing | `testing` | Integration and acceptance checks are running or being verified |
| 7. Ready for Deployment | `ready-deploy` | All checks pass; waiting to be rolled out to terminals |
| 8. Deployed | `deployed` | Live on terminals; close the issue when it lands here |

Steps: Issues > Boards > create the labels > "Create list" for each > hide/remove the default Open and Closed lists.

## Q1.3 — Tickets

### Ticket 1: Terminal authorises a card payment

As a shop owner using a QuickTap terminal, I want the terminal to check that it is online, free, within its limit
and able to accept the customer's card before asking the bank to authorise a payment, so that customers are
only charged through a payment that the terminal can actually complete.

### Ticket 2: Terminal reverses a payment after a failed capture

As a shop customer paying by card, I want the terminal to automatically reverse the authorisation if the payment
cannot be completed, so that I am not charged for a purchase that failed and my money is released straight away.

## Q4.3 — Wiki

Create a wiki page called "Sequence diagram - failed capture and reversal", paste the PlantUML block from
`answers.txt` (Question 4.3) in a ```plantuml fence, and check that it renders. No external links.
